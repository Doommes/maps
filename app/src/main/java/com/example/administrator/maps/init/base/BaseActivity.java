package com.example.administrator.maps.init.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.maps.init.util.LogUtil;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * Activity 的基类
 * Created by ldq on 2018/1/3.
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements BaseView{
    private String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        BaseActivityCollector.AddActivity(this);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initView();
        initDate();
    }

    public void initDate() {

    }

    public void initView() {
    }

    public void onEventMainThread(BaseEventBus eventBus) {

    }

    public int setLayout() {
        return 0;
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Snackbar showSnackBar(View view, String str) {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseActivityCollector.RemovedActivity(this);
    }
}
