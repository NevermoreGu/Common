package com.batman.baselibrary.utils;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;

import com.ui.widget.dialog.UITipDialog;

import java.lang.ref.WeakReference;

public class TipDialogUtils {

    private static WeakReference<UITipDialog> sProgressDialogRef;

    public static UITipDialog showProgressDialog(Context context) {
        return showProgressDialog(context, "加载中", true, null);
    }

    public static UITipDialog showProgressDialog(Context context, String message) {
        return showProgressDialog(context, message, true, null);
    }

    public static UITipDialog showProgressDialog(Context context, String message,
                                                 boolean canCancelable, OnCancelListener listener) {

        UITipDialog dialog = getDialog();

        if (dialog != null && dialog.getContext() != context) {
            // maybe existing dialog is running in a destroyed activity cotext we should recreate one
            dismissProgressDialog();
            Log.e("dialog", "there is a leaked window here,orign context: " + dialog.getContext() + " now: " + context);
            dialog = null;
        }

        if (dialog == null) {
            dialog = new UITipDialog.Builder(context)
                    .setIconType(UITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(message)
                    .create();
            sProgressDialogRef = new WeakReference<>(dialog);
        }

        dialog.setCancelable(canCancelable);
        dialog.setOnCancelListener(listener);
        dialog.show();
        return dialog;
    }

    public static void dismissProgressDialog() {
        UITipDialog dialog = getDialog();
        if (null == dialog) {
            return;
        }
        sProgressDialogRef.clear();
        if (dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                // maybe we catch IllegalArgumentException here.
            }
        }

    }

//    public static void setMessage(String message) {
//        UITipDialog dialog = getDialog();
//        if (null != dialog && dialog.isShowing() && !TextUtils.isEmpty(message)) {
//            dialog.setMessage(message);
//        }
//    }

//    public static void updateLoadingMessage(String message) {
//        UITipDialog dialog = getDialog();
//        if (null != dialog && dialog.isShowing() && !TextUtils.isEmpty(message)) {
//            dialog.updateLoadingMessage(message);
//        }
//    }

    public static boolean isShowing() {
        UITipDialog dialog = getDialog();
        return (dialog != null && dialog.isShowing());
    }

    private static UITipDialog getDialog() {
        return sProgressDialogRef == null ? null : sProgressDialogRef.get();
    }
}
