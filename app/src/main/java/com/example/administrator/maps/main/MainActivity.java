package com.example.administrator.maps.main;

import android.Manifest;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.administrator.maps.R;
import com.example.administrator.maps.collections.CollectionFragment;
import com.example.administrator.maps.common.db.DaoSession;
import com.example.administrator.maps.common.db.LovePoiDao;
import com.example.administrator.maps.init.base.BaseMapActivity;
import com.example.administrator.maps.init.base.MyApplication;
import com.example.administrator.maps.init.util.ActivityUtils;
import com.example.administrator.maps.init.util.LogUtil;
import com.example.administrator.maps.init.util.Tools;
import com.example.administrator.maps.love.LoveActivity;
import com.example.administrator.maps.search.SearchFragment;
import com.example.administrator.maps.main.model.LovePoi;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

public class MainActivity extends BaseMapActivity implements MainContract.view {

    @BindView(R.id.tv_main_marker_detail)
    TextView mTvMainMarkerDetail;
    @BindView(R.id.tv_main_marker_detail_love)
    ImageView mTvMainMarkerDetailLove;
    @BindView(R.id.tv_main_marker_detail_guide)
    TextView mTvMainMarkerDetailGuide;
    @BindView(R.id.tb_bar)
    Toolbar mTbBar;
    @BindView(R.id.fl_fragment)
    FrameLayout mFlFragment;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.rl_main_bottom)
    LinearLayout mRlMainBottom;
    @BindView(R.id.fl_love)
    FrameLayout mFlLove;

    private String TAG = "MainActivity";

    private String[] mPermissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION};

    final RxPermissions rxPermissions = new RxPermissions(this);
    //全局变量
    private MyApplication mApplication;
    //presenter
    private MainPresenter mPresenter;
    //搜索fragment
    private SearchFragment mSearchFragment;
    
    //地图
    //地图控制器
    private AMap mMapControl;
    // 搜索返回的poi列表
    private List<PoiItem> mSelectedPoiItemList = new ArrayList<>();
    //当前选择标点信息
    private PoiItem mSelectedPoiItem;
    //当前选择标点是否已喜欢
    private boolean mIsLove = false;

    //底部View
    //滑动速度
    private VelocityTracker mTracker;
    //底部View是否在滑动
    private static final int DISTANCE_TOUCH_SLOP = 8;
    private static final int BOTTOM_VIEW_IS_HIDED = 0;
    private static final int BOTTOM_VIEW_IS_SCROLL = 1;
    private static final int BOTTOM_VIEW_IS_SHOWED = 2;
    private static final int BOTTOM_VIEW_IS_END = 3;
    //是否正在动画
    private boolean isBottomAnimating = false;
    //快速滑动的临界值
    private static final int FLING_SCROLL = 800;
    private static final int MIN_SCROLL_DISTANCE = 500;
    private int mBottomScrollDistance = 0;
    //view的初始状态
    private int mBottomViewStatus = 0;
    private int mLastBottomViewStatus = -1;
    //底部View初始位置
    private int mInitBottom;
    //底部View上滑的最终位置
    private int mShowBottom;
    //底部View滑动坐标
    private float mDownX;
    private float mDownY;

    //本地数据库
    private DaoSession mDaoSession;
    private LovePoiDao mLovePoiDao;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        //permission
        //requestRuntimePermissions(mPermissions);
        rxPermissions
                .request(Manifest.permission.READ_PHONE_STATE
                        ,Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(gtanted -> {
                   if (gtanted){
                       initData();
                   }else {

                   }
                });

        //初始化Bmob(云端数据库)
        //Bmob.initialize(this, BaseConfig.BMOB_APP_KEY);
    }
    @Override
    public void initData() {
        //沉浸式模式
        Tools.SteepStatusBar(this);
        //AMap初始化
        mMapControl = mMapView.getMap();
        //presenter
        mPresenter = new MainPresenter(this);
        mPresenter.attachView(this);
        //全局变量
        mApplication = MyApplication.getInstances();
        //初始化数据库
        mDaoSession = mApplication.getDaoSession();
        mLovePoiDao = mDaoSession.getLovePoiDao();

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new CollectionFragment(), R.id.fl_love);
        //获取定位点信息
        final GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                mApplication.setLocationCity(regeocodeResult.getRegeocodeAddress().getCity());
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        //显示当前定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(true);//是否显示定位点
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位方式, 当前定位方式为: 单次定位, 且将定位点移动到画面中心
        myLocationStyle.interval(2000);//2000ms 定位一次
        myLocationStyle.anchor(0.5f, 0.5f);
        mMapControl.getUiSettings().setZoomControlsEnabled(false);//关闭缩放按钮
        mMapControl.setMyLocationStyle(myLocationStyle);
        mMapControl.setOnMarkerClickListener(new MarkerOnClick());
        mMapControl.setMyLocationEnabled(true);//设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
        mMapControl.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLonPoint latLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
                mMapControl.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
                geocodeSearch.getFromLocationAsyn(query);
            }
        });
    }

    @Override
    public void isLoveCallBack(boolean isLove) {
        mIsLove = isLove;
        if (isLove){
            mTvMainMarkerDetailLove.setImageDrawable(getDrawable(R.drawable.main_loved));
        }
    }

    private class MarkerOnClick implements AMap.OnMarkerClickListener{
        @Override
        public boolean onMarkerClick(Marker marker) {
            marker.setInfoWindowEnable(false);
            for (PoiItem item : mSelectedPoiItemList){
                if (item.getTitle().equals(marker.getSnippet())){
                    mTvMainMarkerDetail.setText(item.getTitle());
                    mPresenter.SelectLoveWithIdFromDB(item.getPoiId());
                    mSelectedPoiItem = item;
                    break;
                }
            }

            return false;
        }
    }

    @OnClick({R.id.iv_search, R.id.tv_main_marker_detail, R.id.tv_main_marker_detail_love, R.id.tv_main_marker_detail_guide})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                mSearchFragment = new SearchFragment();
                mSearchFragment.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.main_to_search));
                mSearchFragment.setSharedElementReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.main_to_search));
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new SearchFragment(),R.id.fl_fragment, mTbBar, mTbBar.getTransitionName());
                break;
            case R.id.tv_main_marker_detail:
                break;
            case R.id.tv_main_marker_detail_love:
                if (mSelectedPoiItem != null){
                    if (!mIsLove){
                        Intent intent = new Intent(MainActivity.this, LoveActivity.class);
                        intent.putExtra("poi", mSelectedPoiItem);
                        startActivity(intent);
                    }else {
                        mPresenter.DeleteLoveFromDB(mSelectedPoiItem);
                        mTvMainMarkerDetailLove.setImageDrawable(getDrawable(R.drawable.main_love));
                        mIsLove = false;
                    }
                } else {
                    showToast("未选中");
                }
                break;
            case R.id.tv_main_marker_detail_guide:
                List<LovePoi> list = mLovePoiDao.loadAll();
                for (LovePoi poi : list){
                    LogUtil.d(TAG, ">>>>>>>>>>>>>>>>>>>>onViewClicked<<<<<<<<<<<<<<<<<<<<: " + poi.toString());
                }
                break;
        }
    }

    @OnTouch(R.id.tv_main_marker_detail)
    public boolean OnTouch(final View v, MotionEvent e){
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (0 == mInitBottom){
                    mInitBottom = mRlMainBottom.getTop();
                    mShowBottom = (int) (mInitBottom - getResources().getDimension(R.dimen.x320));
                }
                mTracker = VelocityTracker.obtain();
                mDownX = e.getRawX();
                mDownY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isBottomAnimating) break;
                mTracker .addMovement(e);
                int temp = (int) (e.getRawY() - mDownY);
                mBottomScrollDistance += temp;
                mDownX = e.getRawX();
                mDownY = e.getRawY();
                //未开始滑动前(mBottomViewStatus != BOTTOM_VIEW_IS_SCROLL), 限定只能向上滑动, 且有最小滑动距离限制
                //开始滑动后(mBottomViewStatus == BOTTOM_VIEW_IS_SCROLL),  可以自由上下滑动, 且无最小滑动距离限制
                switch (mBottomViewStatus){
                    case BOTTOM_VIEW_IS_HIDED:
                        if (Math.abs(temp) >= DISTANCE_TOUCH_SLOP && temp < 0){
                            mRlMainBottom.offsetTopAndBottom(temp);
                            mLastBottomViewStatus = BOTTOM_VIEW_IS_HIDED;
                            mBottomViewStatus = BOTTOM_VIEW_IS_SCROLL;
                        }
                        break;
                    case BOTTOM_VIEW_IS_SCROLL:
                        mTracker.computeCurrentVelocity(1000);
                        int yVelocity = Math.abs((int) mTracker.getYVelocity());
                        if(yVelocity >= FLING_SCROLL || Math.abs(mBottomScrollDistance) >= MIN_SCROLL_DISTANCE){
                            mBottomViewStatus = BOTTOM_VIEW_IS_END;
                            if (mLastBottomViewStatus == BOTTOM_VIEW_IS_HIDED && !isBottomAnimating){
                                StartBottomAnimate(mRlMainBottom.getTop(), mShowBottom);
                            }else if (mLastBottomViewStatus == BOTTOM_VIEW_IS_SHOWED && !isBottomAnimating){
                                StartBottomAnimate(mRlMainBottom.getTop(), mInitBottom);
                            }
                        }else {
                            mRlMainBottom.offsetTopAndBottom(temp);
                        }
                        break;
                    case BOTTOM_VIEW_IS_SHOWED:
                        if (Math.abs(temp) >= DISTANCE_TOUCH_SLOP && temp > 0){
                            mRlMainBottom.offsetTopAndBottom(temp);
                            mLastBottomViewStatus = BOTTOM_VIEW_IS_SHOWED;
                            mBottomViewStatus = BOTTOM_VIEW_IS_SCROLL;
                        }
                        break;
                    case BOTTOM_VIEW_IS_END:
                        showToast("end");
                        break;
                    default:
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                mBottomScrollDistance = 0;
                switch (mLastBottomViewStatus){
                    case BOTTOM_VIEW_IS_HIDED:
                        if (!isBottomAnimating && mRlMainBottom.getTop() != mShowBottom){
                            StartBottomAnimate(mRlMainBottom.getTop(), mInitBottom);
                        }
                        break;
                    case BOTTOM_VIEW_IS_SHOWED:
                        if (!isBottomAnimating && mRlMainBottom.getTop() != mInitBottom){
                            StartBottomAnimate(mRlMainBottom.getTop(), mShowBottom);
                        }
                        break;
                    default:
                        break;
                }
                mTracker.clear();
                mTracker.recycle();
                break;
            default:
                break;
        }
        return false;
    }

    private void StartBottomAnimate(int start, final int end){
        isBottomAnimating = true;
        if (end == mInitBottom){
            mBottomViewStatus = BOTTOM_VIEW_IS_HIDED;
        }else {
            mBottomViewStatus = BOTTOM_VIEW_IS_SHOWED;
        }
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addListener(new AnimatorListenerAdapter() {
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRlMainBottom.layout(mRlMainBottom.getLeft(), (int)animation.getAnimatedValue(), mRlMainBottom.getRight(), (int)animation.getAnimatedValue() + mRlMainBottom.getHeight());
                if ((int)animation.getAnimatedValue() == end) {
                    if (end == mInitBottom) {
                        mFlLove.setVisibility(View.GONE);
                    }
                    else {
                        mFlLove.setVisibility(View.VISIBLE);
                    }
                    isBottomAnimating = false;
                }
            }
        });
        animator.start();
    }

    public void onEventMainThread(PoiItem poiItem) {
        if (!isBottomAnimating && mBottomViewStatus == BOTTOM_VIEW_IS_SHOWED){
            StartBottomAnimate(mShowBottom, mInitBottom);
        }
        mMapControl.clear();
        mSelectedPoiItemList.clear();
        mSelectedPoiItemList.add(poiItem);
        LatLonPoint point = poiItem.getLatLonPoint();
        LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
        mMapControl.addMarker(new MarkerOptions().position(latLng).title(poiItem.getCityName()).snippet(poiItem.getTitle()));
        mMapControl.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    public void onEventMainThread(List<PoiItem> list){
        mMapControl.clear();
        mSelectedPoiItemList.clear();
        mSelectedPoiItemList.addAll(list);
        LatLngBounds.Builder builder= new LatLngBounds.Builder();
        for (PoiItem poiItem : list){
            LatLonPoint point = poiItem.getLatLonPoint();
            LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
            mMapControl.addMarker(new MarkerOptions().position(latLng).title(poiItem.getCityName()).snippet(poiItem.getTitle()));
            builder.include(latLng);
        }
        mMapControl.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
        LogUtil.d(TAG, ">>>>>>>>>>>>>>>>>>>>onEventMainThread<<<<<<<<<<<<<<<<<<<<: "+list.toString());
    }

}
