package com.example.administrator.maps.main;

import com.amap.api.services.core.PoiItem;
import com.example.administrator.maps.init.base.BaseView;

/**
 * Created by ldq on 2018/2/1.
 */

public interface MainContract{

    interface view extends BaseView {
        void isLoveCallBack(boolean isLove);
    }

    interface presenter {

        void SelectLoveWithIdFromDB(String poiId);

        void DeleteLoveFromDB(PoiItem poiItem);
    }

}
