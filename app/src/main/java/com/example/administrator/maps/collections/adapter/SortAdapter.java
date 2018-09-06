package com.example.administrator.maps.collections.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.maps.R;
import com.example.administrator.maps.collections.CollectionContract;
import com.example.administrator.maps.love.model.SortBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ldq on 2018/6/4.
 */

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {
    private Context mContext;
    private List<SortBean> mList;
    private CollectionContract.presenter mPresenter;
    public SortAdapter(Context context, List<SortBean> list, CollectionContract.presenter presenter) {
        this.mContext = context;
        this.mList = list;
        this.mPresenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SortBean bean = mList.get(position);
        holder.mTvTitle.setText(bean.getTitle());
        holder.mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.SelectLoveFromDB(bean.getTitle());
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
