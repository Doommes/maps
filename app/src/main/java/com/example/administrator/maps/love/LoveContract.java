package com.example.administrator.maps.love;

import com.example.administrator.maps.init.base.BaseView;
import com.example.administrator.maps.love.model.SortBean;
import com.example.administrator.maps.main.model.LovePoi;

import java.util.List;

/**
 * Created by ldq on 2018/6/1.
 */

public interface LoveContract {
    interface view extends BaseView {
        /**
         * 返回分类列表
         * @param beanList 数据
         */
        void SortCallBack(List<SortBean> beanList);

    }

    interface presenter{
        void SelectSortFromDB();

        void InsertSortFromDB(SortBean bean);

        void InsertLoveFromDB(LovePoi lovePoi, String string);
    }
}
