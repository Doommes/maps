package com.example.administrator.maps.love.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.maps.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ldq on 2018/6/4.
 */

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {
    private String TAG = "SortAdapter";
    private Context mContext;
    private List<String> mList;

    public SortAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvTitle.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
