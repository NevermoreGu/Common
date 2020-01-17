package com.ui.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.ui.widget.dialog.CustomOneDialog;
import com.ui.widget.dialog.UIBottomSheet;
import com.ui.widget.dialog.UIDialog;
import com.ui.widget.dialog.UIDialogAction;

import java.util.List;

public class UIDialogHelper {


    public static void showCommonDialog(Context context, String title, String message,
                                        String negativeText, UIDialogAction.ActionListener negativeListener,
                                        String positiveText, UIDialogAction.ActionListener positiveListener) {
        new UIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .addAction(negativeText, negativeListener)
                .addAction(positiveText, positiveListener)
                .create().show();
    }


    public static void showCommonDialog(Context context, String title, String message,
                                        UIDialogAction.ActionListener negativeListener,
                                        UIDialogAction.ActionListener positiveListener) {

        showCommonDialog(context, title, message, "取消", negativeListener, "确定", positiveListener);
    }

    public static void showCommonDialog(Context context, String message,
                                        UIDialogAction.ActionListener negativeListener,
                                        UIDialogAction.ActionListener positiveListener) {

        showCommonDialog(context, "提示", message, "取消", negativeListener, "确定", positiveListener);
    }


    /**
     * 菜單
     *
     * @param context
     * @param items
     * @param listener
     */
    public static void showCommonMenuDialog(Context context, CharSequence[] items,
                                            DialogInterface.OnClickListener listener
    ) {

        new UIDialog.MenuDialogBuilder(context)
                .addItems(items, listener)
                .create().show();
    }

    /**
     * ListBottomSheet
     *
     * @param context
     * @param items
     * @param listener
     */

    public static void showListBottomSheet(Context context, List<String> items, UIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener listener) {
        UIBottomSheet.BottomListSheetBuilder bottomListSheetBuilder = new UIBottomSheet.BottomListSheetBuilder(context);
        for (String item :
                items) {
            bottomListSheetBuilder
                    .addItem(item);
        }
        bottomListSheetBuilder
                .setOnSheetItemClickListener(listener)
                .build()
                .show();
    }

    public static Dialog showCommonDialog(Context context, String title, String message, boolean isForse,
                                          UIDialogAction.ActionListener negativeListener,
                                          UIDialogAction.ActionListener positiveListener) {

        CustomOneDialog dialog = new CustomOneDialog(context);
        dialog.tvTitle.setText(message);
        dialog.subTvTitle.setText(title);
        dialog.subTvTitle.setVisibility(View.VISIBLE);
        if (isForse) {
            dialog.buttonCancel.setVisibility(View.GONE);
        } else {
            dialog.buttonCancel.setVisibility(View.VISIBLE);
        }
        dialog.buttonCancel.setText("取消");
        dialog.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                negativeListener.onClick(dialog, 0);
            }
        });
        dialog.buttonOK.setText("确定");
        dialog.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positiveListener.onClick(dialog, 1);
            }
        });
        dialog.setCancelable(false);
        return dialog;
    }


}
