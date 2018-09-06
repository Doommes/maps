package com.example.administrator.maps.search.adapter;

import android.support.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.maps.R;

import java.util.List;

/**
 * Created by ldq on 2018/5/22.
 */

public class SearchAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {
    private List<PoiItem> mList;
    
    public SearchAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
        this.mList = data;
    }

    @Override
    protected void convert(BaseViewHolder holder, PoiItem poiItem) {
        holder.setText(R.id.tv_address, poiItem.getTitle());
    }
}
