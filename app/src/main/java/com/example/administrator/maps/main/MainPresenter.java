package com.example.administrator.maps.main;

import android.content.Context;

import com.amap.api.maps.model.Marker;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.administrator.maps.init.base.BasePresenter;
import com.example.administrator.maps.init.base.MyApplication;
import com.example.administrator.maps.init.util.LogUtil;
import com.example.administrator.maps.main.model.LovePoi;

/**
 * Created by ldq on 2018/2/1.
 */

public class MainPresenter extends BasePresenter<MainContract.view> implements MainContract.presenter {
    private String TAG = "MainPresenter";
    private Context mContext;
    private MyApplication mApplication;
    MainPresenter(Context context) {
        this.mContext = context;
        mApplication = MyApplication.getInstances();
    }

    @Override
    public void attachView(MainContract.view mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void SelectLoveWithIdFromDB(String poiId) {
        if (! LovePoi.SelectLoveFromDB(mApplication, "where POI_ID = ?",poiId).isEmpty()){
            getView().isLoveCallBack(true);
        }else {
            getView().isLoveCallBack(false);
        }
    }

    @Override
    public void DeleteLoveFromDB(PoiItem poiItem) {
        LovePoi.DeleteLoveFromDB(mApplication, "", poiItem.getPoiId());
    }
}
