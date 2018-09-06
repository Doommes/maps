package com.example.administrator.maps.search;

import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.administrator.maps.init.base.BasePresenter;
import com.example.administrator.maps.init.util.LogUtil;

/**
 * Created by ldq on 2018/5/22.
 */

public class SearchPresenter extends BasePresenter<SearchContract.view> implements SearchContract.presenter {
    private String TAG = "SearchPresenter";
    private Context mContext;

    public SearchPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void attachView(SearchContract.view mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void searchPOI(String keyWord, String city) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", city);
        query.setPageSize(10);//返回条数
        query.setPageNum(1);//当前查询页码
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                getView().CallBackSearchResult(poiResult);
                LogUtil.d(TAG, ">>>>>>>>>>>>>>>>>>>>onPoiSearched<<<<<<<<<<<<<<<<<<<<: " + poiResult.getPois().toString());
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }
}
