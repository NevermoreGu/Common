package com.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ui.R;


/**
 * Created by Administrator on 2016/5/23.
 */
public class CustomOneDialog extends Dialog {


    public TextView subTvTitle;
    public TextView tvTitle;
    public Button buttonOK;
    public Button buttonCancel;


    public CustomOneDialog(Context context) {
        super(context, R.style.CustomDialog);
        init(context);
    }

    public CustomOneDialog(Context context, int theme) {
        super(context, R.style.CustomDialog);
        init(context);
    }

    private void init(Context context) {
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_custom_one, null);
        tvTitle = (TextView) view.findViewById(R.id.tv_tile);
        subTvTitle = (TextView) view.findViewById(R.id.tv_sub_tile);
        buttonCancel = (Button) view.findViewById(R.id.btn_cancel);
        buttonOK = (Button) view.findViewById(R.id.btn_ok);
        setContentView(view);
    }

}
