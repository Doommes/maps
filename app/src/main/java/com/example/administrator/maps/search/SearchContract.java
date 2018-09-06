package com.example.administrator.maps.search;

import com.amap.api.services.poisearch.PoiResult;
import com.example.administrator.maps.init.base.BaseView;

/**
 * Created by ldq on 2018/5/22.
 */

public class SearchContract {
    interface view extends BaseView{
        void CallBackSearchResult(PoiResult poiResult);
    }
    interface presenter{
        /**
         * 搜索POI数据
         * @param keyWord 搜索关键词
         * @param city 搜索城市
         */
        void searchPOI(String keyWord, String city);
    }
}
