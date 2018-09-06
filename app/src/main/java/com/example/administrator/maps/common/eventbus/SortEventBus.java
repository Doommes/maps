package com.example.administrator.maps.common.eventbus;

/**
 * Created by ldq on 2018/6/4.
 */

public class SortEventBus {
    private String mTitle;

    public SortEventBus(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
