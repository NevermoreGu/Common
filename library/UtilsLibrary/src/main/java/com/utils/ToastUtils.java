package com.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author guqian
 */
public class ToastUtils {

    /**
     * 系统toast
     *
     * @param context
     * @param message
     */
    public static void showShortToast(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(Context context, int messageId) {
        Toast.makeText(context.getApplicationContext(), context.getString(messageId), Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(Context context, int messageId) {
        Toast.makeText(context.getApplicationContext(), context.getString(messageId), Toast.LENGTH_LONG).show();
    }

}
