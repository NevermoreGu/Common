package com.network.utils;

import android.util.Log;

import com.network.BuildConfig;


/**
 * @author batman
 */
public class LogUtils {

    public static boolean DEBUG = false;

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        boolean isDebug = DEBUG;
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (DEBUG) {
            Log.e(tag, msg, throwable);
        }
    }

    public static void logi(String message, Object... args) {
        if (DEBUG) {
            Log.i("LogUtils", message);
        }
    }

    public static void logd(String message, Object... args) {
        if (DEBUG) {
            Log.d("LogUtils", message);
        }
    }

    public static void loge(String message, Object... args) {
        if (DEBUG) {
            Log.e("LogUtils", message);
        }
    }

}
