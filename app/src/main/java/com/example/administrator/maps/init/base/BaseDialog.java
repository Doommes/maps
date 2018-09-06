package com.example.administrator.maps.init.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.maps.R;


/**
 * Created by Hot on 16/8/1.
 */
public class BaseDialog {
    public Context mContext;
    private Dialog mDialog;

    public static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public BaseDialog builder() {
            return new BaseDialog(this);
        }
    }

    public BaseDialog(Builder builder) {
        this.mContext = builder.context;
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = null;

        mDialog = new Dialog(mContext, R.style.alert_dialog);
        mDialog.setCancelable(false);
        mDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

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
