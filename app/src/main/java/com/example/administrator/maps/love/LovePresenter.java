package com.example.administrator.maps.love;

import android.content.Context;

import com.example.administrator.maps.init.base.BasePresenter;
import com.example.administrator.maps.init.base.MyApplication;
import com.example.administrator.maps.init.util.LogUtil;
import com.example.administrator.maps.love.model.SortBean;
import com.example.administrator.maps.love.Biz.SortBiz;
import com.example.administrator.maps.main.model.LovePoi;

/**
 * Created by ldq on 2018/6/1.
 */

public class LovePresenter extends BasePresenter<LoveContract.view> implements LoveContract.presenter {
    private String TAG = "LovePresenter";
    private Context mContext;
    private MyApplication mApplication;
    private SortBiz mSortBiz;
    LovePresenter(Context context) {
        mContext = context;
        mApplication = MyApplication.getInstances();
        mSortBiz = new SortBiz();
    }

    @Override
    public void attachView(LoveContract.view mvpView) {
        super.attachView(mvpView);
    }


    @Override
    public void SelectSortFromDB() {
        getView().SortCallBack(SortBean.SelectSortFromDB(mApplication, ""));
    }

    @Override
    public void InsertSortFromDB(SortBean sortBean) {
        SortBean.InsertSortFromDB(mApplication, sortBean);
        getView().SortCallBack(SortBean.SelectSortFromDB(mApplication, ""));
    }

    @Override
    public void InsertLoveFromDB(LovePoi lovePoi, String string) {
        LovePoi.InsertLoveFromDB(mApplication, lovePoi, string);
    }
}
