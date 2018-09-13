package com.example.administrator.maps.init.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.MapView;
import com.example.administrator.maps.R;
import com.example.administrator.maps.init.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * Activity 的基类
 * Created by ldq on 2018/1/3.
 */

@SuppressLint("Registered")
public class BaseMapActivity extends AppCompatActivity implements BaseView{
    private String TAG = "BaseActivity";
    public MapView mMapView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        //同意管理所有实例化的Activity
        BaseActivityCollector.AddActivity(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mMapView = findViewById(R.id.mv_map);
        mMapView.onCreate(savedInstanceState);
        initView();
        //initData();
    }

    public void initView() {

    }

    public void initData() {

    }

    public void onEventMainThread(BaseEventBus eventBus) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null){
            mMapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMapView != null){mMapView.onPause();}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseActivityCollector.RemovedActivity(this);
        if (mMapView != null){mMapView.onDestroy();}
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null){mMapView.onSaveInstanceState(outState);}
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
        return Snackbar.make(this.mMapView, str,Snackbar.LENGTH_LONG);
    }

    public void requestRuntimePermissions(String[] permissions) {
        List<String> permissionsList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissionsList.add(permission);
            }
        }
        if (permissionsList.isEmpty()){
        }else {
            ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]), 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    List<String> deniedPermissionsList = new ArrayList<>();
                    for (int result = 0; result < grantResults.length; result++) {
                        int grantResult = grantResults[result];
                        if (grantResult == PackageManager.PERMISSION_DENIED){
                            deniedPermissionsList.add(permissions[result]);
                        }
                    }
                    if (deniedPermissionsList.isEmpty()){
                        showSnackBar(this.mMapView, "权限已获取").show();
                    }else {
                        showSnackBar(this.mMapView, "权限未授予,将影响部分功能的使用")
                                .setAction("去设置", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        intent.setData(Uri.parse("package:" + getPackageName()));
                                        startActivity(intent);
                                    }
                                }).show();
                    }
                }
        }
    }
}
