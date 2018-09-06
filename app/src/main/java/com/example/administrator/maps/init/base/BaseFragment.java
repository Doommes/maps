package com.example.administrator.maps.init.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.maps.init.util.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * Created by ldq on 2018/5/22.
 */

public class BaseFragment extends Fragment implements BaseView{
    private String TAG = "BaseFragment";
    public View mView;
    public Context mContext;
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mView){
            mView = inflater.inflate(setLayout(), container, false);
            mContext = getContext();
            unbinder = ButterKnife.bind(this, mView);
            EventBus.getDefault().register(this);
            initView();
            initData();
        }
        return mView;
    }

    public void initView() {

    }

    public void initData() {
    }


    public int setLayout() {
        return 0;
    }

    public void onEventMainThread(BaseEventBus eventBus) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(TAG, ">>>>>>>>>>>>>>>>>>>>onDestroyView<<<<<<<<<<<<<<<<<<<<: ");
        unbinder.unbind();
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Snackbar showSnackBar(View view, String str) {
        return null;
    }
}
