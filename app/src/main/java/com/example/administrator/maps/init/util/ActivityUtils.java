package com.example.administrator.maps.init.util;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by ldq on 2018/1/4.
 */

public class ActivityUtils {

    @SuppressLint("RestrictedApi")
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded()){
            transaction.show(fragment).commit();
        }else {
            transaction.add(frameId, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    @SuppressLint("RestrictedApi")
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId, View view, String name) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded()){
            transaction.show(fragment).commit();
        }else {
            if (null != view && null != name){
                transaction.addSharedElement(view, name);
            }
            transaction.add(frameId, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    @SuppressLint("RestrictedApi")
    public static void hideFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                               @NonNull Fragment fragment) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            transaction.hide(fragment).commit();
        }
    }
}
