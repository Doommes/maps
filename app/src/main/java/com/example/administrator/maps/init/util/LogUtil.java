package com.example.administrator.maps.init.util;

import android.util.Log;

/**
 * Created by ldq on 2017/12/21.
 */

public class LogUtil {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO= 3;
    public static final int WARN= 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 0;
    private static int level = ERROR;
    public static void v(String tag, String msg){
        if (VERBOSE <= level){
            Log.v(tag, msg);
        }
    }
    public static void d(String tag, String msg){
        if (DEBUG <= level){
            Log.d(tag, msg);
        }
    }
    public static void i(String tag, String msg){
        if (INFO <= level){
            Log.i(tag, msg);
        }
    }
    public static void w(String tag, String msg){
        if (WARN <= level){
            Log.w(tag, msg);
        }
    }
    public static void e(String tag, String msg){
        if (ERROR <= level){
            Log.e(tag, msg);
        }
    }
}
