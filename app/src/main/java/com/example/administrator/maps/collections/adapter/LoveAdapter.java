package com.example.administrator.maps.collections.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.example.administrator.maps.R;
import com.example.administrator.maps.love.model.SortBean;
import com.example.administrator.maps.main.model.LovePoi;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by ldq on 2018/6/4.
 */

public class LoveAdapter extends RecyclerView.Adapter<LoveAdapter.ViewHolder> {
    private Context mContext;
    private List<LovePoi> mList;

    public LoveAdapter(Context context, List<LovePoi> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LovePoi bean = mList.get(position);
        holder.mTvTitle.setText(bean.getTitle());
        holder.mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(bean.tranPoiItem());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
