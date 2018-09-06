package com.example.administrator.maps.init.base;

import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.List;

/**
 * Created by ldq on 2018/1/18.
 */

public interface BaseView {

    void showDialog();

    void dismissDialog();

    void showToast(String str);

    Snackbar showSnackBar(View view, String str);

}
