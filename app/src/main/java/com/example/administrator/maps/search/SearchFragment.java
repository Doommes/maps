package com.example.administrator.maps.search;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.maps.R;
import com.example.administrator.maps.search.adapter.SearchAdapter;
import com.example.administrator.maps.init.base.BaseFragment;
import com.example.administrator.maps.init.base.MyApplication;
import com.example.administrator.maps.init.util.ActivityUtils;
import com.example.administrator.maps.init.util.LogUtil;
import com.example.administrator.maps.init.util.Tools;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * 负责搜索POI数据
 * Created by ldq on 2018/5/22.
 */

public class SearchFragment extends BaseFragment implements SearchContract.view {
    @BindView(R.id.tb_bar)
    Toolbar mTbBar;
    @BindView(R.id.sv_search)
    SearchView mSvSearch;
    @BindView(R.id.rv_search_result)
    RecyclerView mRvSearchResult;
    @BindView(R.id.tv_search_all)
    TextView mTvSearchAll;
    @BindView(R.id.cv_search_result)
    CardView mCvSearchResult;
    private Boolean isSearchResultVisible = false;

    private String TAG = "SearchFragment";
    SearchPresenter mPresenter;
    //全局变量
    private MyApplication mApplication;
    private SearchAdapter mAdapter;
    List<PoiItem> mList;

    @Override
    public int setLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void initData() {
        mPresenter = new SearchPresenter(mContext);
        mPresenter.attachView(this);

        mApplication = (MyApplication) getActivity().getApplication();

        mTbBar.setFitsSystemWindows(true);
        SearchListener();

    }

    @OnClick(R.id.tv_search_all)
    public void OnClick(){
        Tools.HideSoft(getActivity());
        ActivityUtils.hideFragmentToActivity(getFragmentManager(), SearchFragment.this);
        EventBus.getDefault().post(mList);
    }
    /**
     * 添加Search监听
     */
    private void SearchListener() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        mSvSearch.setOnQueryTextListener(new QueryTextListener(subscriber));
                    }
                })
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mPresenter.searchPOI(s, mApplication.getLocationCity());
                    }
                });
    }

    /**
     * 重写SearchLister接口OnQueryTextListener
     */
    class QueryTextListener implements SearchView.OnQueryTextListener{
        private Subscriber<? super String> mSubscription;
        public QueryTextListener(Subscriber<? super String> subscriber) {
            this.mSubscription = subscriber;
        }
        @Override
        public boolean onQueryTextSubmit(String query) {return false;}
        @Override
        public boolean onQueryTextChange(String newText) {
            if (!isSearchResultVisible){
                mCvSearchResult.setVisibility(View.VISIBLE);
                isSearchResultVisible = true;
            }
            mTvSearchAll.setText(String.format(getResources().getString(R.string.fragment_search_header), newText));
            if (null != newText && !newText.isEmpty()) {
                mSubscription.onNext(newText);
            } else {
                if (null != mList) {
                    mList.removeAll(mList);
                    mAdapter.notifyDataSetChanged();
                    mCvSearchResult.setVisibility(View.INVISIBLE);
                    isSearchResultVisible = false;
                }
            }
            return false;
        }
    }

    @Override
    public void CallBackSearchResult(PoiResult poiResult) {
        if (null == mAdapter) {
            mList = poiResult.getPois();
            mAdapter = new SearchAdapter(R.layout.item_search_poi, mList);
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int i) {
                    Tools.HideSoft(getActivity());
                    PoiItem poiItem = (PoiItem) adapter.getItem(i);
                    EventBus.getDefault().post(poiItem);
                    ActivityUtils.hideFragmentToActivity(getFragmentManager(), SearchFragment.this);
                    LogUtil.d(TAG, ">>>>>>>>>>>>>>>>>>>>onItemChildClick<<<<<<<<<<<<<<<<<<<<: " + adapter.getData().get(i).toString());
                }
            });
            LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            mRvSearchResult.setLayoutManager(manager);
            mRvSearchResult.setAdapter(mAdapter);
        } else {
            mList.clear();
            mList.addAll(poiResult.getPois());
            mAdapter.notifyDataSetChanged();
        }
    }
}