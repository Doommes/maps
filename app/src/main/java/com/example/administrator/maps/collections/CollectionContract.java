package com.example.administrator.maps.collections;

import com.example.administrator.maps.init.base.BaseView;
import com.example.administrator.maps.love.model.SortBean;
import com.example.administrator.maps.main.model.LovePoi;

import java.util.List;

/**
 * Created by ldq on 2018/6/1.
 */
public interface CollectionContract {

    interface view extends BaseView {

        void SortsCallback(List<SortBean> sortBeans);

        void LovesCallBack(List<LovePoi> lovePois);
    }

    interface presenter{

        void SelectSortFromDB();

        void SelectLoveFromDB(String sort);
    }
}
