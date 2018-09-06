package com.example.administrator.maps.init.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.maps.common.db.DaoMaster;
import com.example.administrator.maps.common.db.DaoSession;

/**
 * Created by ldq on 2018/2/2.
 */

public class MyApplication extends Application {
    //当前选择的城市
    private String LOCATION_CITY;
    private DaoSession mDaoSession;

    //静态单例
    public static MyApplication instances;

    public String getLocationCity() {
        return LOCATION_CITY;
    }

    public void setLocationCity(String city) {
        this.LOCATION_CITY = city;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        setDatabase();
    }

    public static MyApplication getInstances(){
        return instances;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "map.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
