package com.batman.baselibrary.base;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.batman.baselibrary.utils.TipDialogUtils;
import com.batman.baselibrary.utils.ToastUtils;
import com.network.Exception.NetworkException;
import com.network.Resource;
import com.network.Status;

public abstract class BaseObserver<T> implements Observer<Resource<T>> {

    private Context mContext;
    private boolean mIsShow = true;

    public BaseObserver(Context context) {
        this.mContext = context;
    }

    public BaseObserver(Context context, boolean isShow) {
        this.mContext = context;
        this.mIsShow = isShow;
    }

    @Override
    public void onChanged(@Nullable Resource<T> tResource) {

//        if (!NetworkUtil.isNetAvailable(mContext)) {
//            ToastUtils.showShort("网络连接失败，请检查你的网络");
//            return;
//        }
        if (tResource.status.equals(Status.LOADING)) {
            if (mIsShow) {
                uiLoading();
            }
        } else {
            dismissDialog();
            if (tResource.status.equals(Status.COMPLETE)) {
                uiComplete(tResource);
            } else if (tResource.status.equals(Status.SUCCESS)) {
                uiSuccessful(tResource);
            } else if (tResource.status.equals(Status.ERROR)) {
                uiError(tResource);
            } else if (tResource.status.equals(Status.THROWABLE)) {
                uiFailure(tResource);
            }
        }
    }

    public void uiSuccessful(Resource<T> tResource) {

    }


    public void uiError(Resource<T> tResource) {
        if (!TextUtils.isEmpty(tResource.message)) {
            ToastUtils.showLongToast(mContext, tResource.message);
        }


    }

    public void uiFailure(Resource<T> tResource) {
        String str = NetworkException.handleException(tResource.mException).getMessage();
        tResource.message = str;
        if (!TextUtils.isEmpty(str)) {
            ToastUtils.showLongToast(mContext, str);
        }
    }

    public void uiLoading() {
        TipDialogUtils.showProgressDialog(mContext, "", true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                uiCancel(dialog);
            }
        });
    }

    public void uiCancel(DialogInterface dialog) {

    }

    public void dismissDialog() {
        TipDialogUtils.dismissProgressDialog();
    }

    public void uiComplete(Resource<T> tResource) {
    }
}
