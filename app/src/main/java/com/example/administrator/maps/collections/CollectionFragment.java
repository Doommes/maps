package com.example.administrator.maps.collections;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.maps.R;
import com.example.administrator.maps.collections.adapter.LoveAdapter;
import com.example.administrator.maps.collections.adapter.SortAdapter;
import com.example.administrator.maps.init.base.BaseFragment;
import com.example.administrator.maps.love.model.SortBean;
import com.example.administrator.maps.main.model.LovePoi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ldq on 2018/6/1.
 */

public class CollectionFragment extends BaseFragment implements CollectionContract.view {
    @BindView(R.id.rv_sort)
    RecyclerView mRvSort;
    @BindView(R.id.rv_love)
    RecyclerView mRvLove;

    private CollectionPresenter mPresenter;

    private SortAdapter mSortAdapter;
    private List<SortBean> mSortsList;

    private LoveAdapter mLoveAdapter;
    private List<LovePoi> mLoveList;


    @Override
    public int setLayout() {
        return R.layout.fragment_collection;
    }

    @Override
    public void initView() {
        mPresenter = new CollectionPresenter(getContext());
        mPresenter.attachView(this);
    }

    @Override
    public void initData() {
        mPresenter.SelectSortFromDB();
        mPresenter.SelectLoveFromDB("");
    }

    @Override
    public void SortsCallback(List<SortBean> sortBeans) {
        mSortsList = sortBeans;
        if (null == mSortAdapter){
            mSortAdapter = new SortAdapter(getContext(), mSortsList, mPresenter);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mRvSort.setLayoutManager(manager);
            mRvSort.setAdapter(mSortAdapter);
        }else {
            mSortAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void LovesCallBack(List<LovePoi> lovePois) {
        if (null == mLoveAdapter){
            mLoveList = lovePois;
            mLoveAdapter = new LoveAdapter(getContext(), lovePois);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mRvLove.setLayoutManager(manager);
            mRvLove.setAdapter(mLoveAdapter);
        }else {
            mLoveList.clear();
            mLoveList.addAll(lovePois);
            mLoveAdapter.notifyDataSetChanged();
        }
    }
}
