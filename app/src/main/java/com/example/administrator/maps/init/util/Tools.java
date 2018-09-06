package com.example.administrator.maps.init.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 一些常用工具类的集合
 * Created by ldq on 2018/5/22.
 */

public class Tools {

    /**
     * 沉浸式状态栏
     * 在布局最外层设置android:fitsSystemWindows属性
     * fitsSystemWindows = "true" 表示在系统顶部自动留出系统状态栏大小的padding
     * @param activity activity实例
     */
    public static void SteepStatusBar(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                activity.getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                activity.getWindow()
                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * 收起键盘
     * @param activity activity实例
     */
    public static void HideSoft(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static int TouchSlop(Context context){
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 布局为Gone时, 获取其宽高
     * @param view 布局
     * @return
     */
    public static int[] unDisplayViewSize(View view) {
        int size[] = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        size[0] = view.getMeasuredWidth();
        size[1] = view.getMeasuredHeight();
        return size;
    }
}
