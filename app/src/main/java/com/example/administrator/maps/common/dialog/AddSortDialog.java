package com.example.administrator.maps.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.maps.R;
import com.example.administrator.maps.common.eventbus.SortEventBus;
import com.example.administrator.maps.love.model.SortBean;

import de.greenrobot.event.EventBus;


/**
 * Created by Hot on 16/8/1.
 */
public class AddSortDialog implements View.OnClickListener {
    public Context mContext;
    private Dialog mDialog;
    private EditText mSortEt;
    private TextView mCancelTv;
    private TextView mSureTv;

    public static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public AddSortDialog builder() {
            return new AddSortDialog(this);
        }
    }

    public AddSortDialog(Builder builder) {
        this.mContext = builder.context;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_add_sort, null);

        mSortEt = view.findViewById(R.id.et_sort);
        mCancelTv = view.findViewById(R.id.tv_cancel);
        mSureTv = view.findViewById(R.id.tv_sure);

        mCancelTv.setOnClickListener(this);
        mSureTv.setOnClickListener(this);

        mDialog = new Dialog(mContext, R.style.alert_dialog);
        mDialog.setCancelable(false);
        mDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismissDialog();
                break;
            case R.id.tv_sure:
                SortBean sortBean = new SortBean();
                sortBean.setId(null);
                sortBean.setTitle(mSortEt.getText().toString());
                EventBus.getDefault().post(sortBean);
                dismissDialog();
                break;
            default:
                break;
        }
    }

    public void showDialog() {
        if (!mDialog.isShowing() && mDialog != null) {
            mDialog.show();
        }
    }


    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
