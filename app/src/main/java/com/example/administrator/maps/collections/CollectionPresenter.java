package com.example.administrator.maps.collections;

import android.content.Context;

import com.example.administrator.maps.init.base.BasePresenter;
import com.example.administrator.maps.init.base.MyApplication;
import com.example.administrator.maps.love.Biz.SortBiz;
import com.example.administrator.maps.love.model.SortBean;
import com.example.administrator.maps.main.model.LovePoi;

import java.util.List;

/**
 * Created by ldq on 2018/6/1.
 */

public class CollectionPresenter extends BasePresenter<CollectionContract.view> implements CollectionContract.presenter{
    private String TAG = "CollectionPresenter";
    private Context mContext;
    private MyApplication mApplication;
    CollectionPresenter(Context context) {
        mContext = context;
        mApplication = MyApplication.getInstances();
    }

    @Override
    public void attachView(CollectionContract.view mvpView) {
        super.attachView(mvpView);
    }


    @Override
    public void SelectSortFromDB() {
        getView().SortsCallback(SortBean.SelectSortFromDB(mApplication, ""));
    }

    @Override
    public void SelectLoveFromDB(String sort) {
        List<SortBean> sortBeans = SortBean.SelectSortFromDB(mApplication, sort);
        if (!sortBeans.isEmpty()){
            Long id = sortBeans.get(0).getId();
            getView().LovesCallBack(LovePoi.SelectLoveFromDB(mApplication, "where SORT_ID = ?",String.valueOf(id)));
        }
    }
}
