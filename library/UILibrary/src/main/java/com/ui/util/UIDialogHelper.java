package com.ui.util;

import android.content.Context;
import android.content.DialogInterface;

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


}
