package com.example.administrator.maps.love;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.example.administrator.maps.R;
import com.example.administrator.maps.common.dialog.AddSortDialog;
import com.example.administrator.maps.init.base.BaseActivity;
import com.example.administrator.maps.init.util.LogUtil;
import com.example.administrator.maps.love.model.SortBean;
import com.example.administrator.maps.main.model.LovePoi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoveActivity extends BaseActivity implements LoveContract.view {
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.spinner_sort)
    Spinner mSpinnerSort;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_sure)
    TextView mTvSure;

    private String TAG = "LoveActivity";
    private LovePresenter mPresenter;

    private LovePoi mLovePoi;

    private List<String> mSortList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    public int setLayout() {
        return R.layout.activity_love;
    }

    @Override
    public void initDate() {
        //presenter
        mPresenter = new LovePresenter(this);
        mPresenter.attachView(this);

        PoiItem item = getIntent().getParcelableExtra("poi");
        mLovePoi = new LovePoi(item);

        mPresenter.SelectSortFromDB();
        mSortList.add("+");

        //SortAdapter adapter = new SortAdapter(this, mSortList);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mSortList);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSort.setAdapter(mAdapter);
        mSpinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == mSortList.size() - 1) {
                    new AddSortDialog(new AddSortDialog.Builder(view.getContext())).showDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onEventMainThread(SortBean bean) {
        mPresenter.InsertSortFromDB(bean);
    }

    @Override
    public void SortCallBack(List<SortBean> beanList) {
        for (SortBean bean : beanList) {
            LogUtil.d(TAG, ">>>>>>>>>>>>>>>>>>>>SortCallBack<<<<<<<<<<<<<<<<<<<<: " + bean.getTitle());
            if (0 != mSortList.size()) {
                mSortList.add(mSortList.size() - 1, bean.getTitle());
            } else {
                mSortList.add(bean.getTitle());
            }
        }
        if (null != mAdapter) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.tv_cancel, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_sure:
                finish();
                mPresenter.InsertLoveFromDB(mLovePoi, mSpinnerSort.getSelectedItem().toString());
                break;
        }
    }
}
