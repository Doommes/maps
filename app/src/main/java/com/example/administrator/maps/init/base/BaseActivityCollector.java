package com.example.administrator.maps.init.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldq on 2018/1/18.
 */

public class BaseActivityCollector {
    public static List<Activity> activityList = new ArrayList<>();

    public static void AddActivity(Activity activity){
        activityList.add(activity);
    }

    public static void RemovedActivity(Activity activity){
        activityList.remove(activity);
        activity.finish();
    }
    public static void FinishActivity(){
        for (Activity activity : activityList) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
