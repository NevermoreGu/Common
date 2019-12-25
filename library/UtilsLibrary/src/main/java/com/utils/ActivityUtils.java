package com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;


public class ActivityUtils {

    /**
     * 通过包名类名启动activity
     *
     * @param activity
     * @param activityClass
     */
    public static void openActivity(Activity activity, Class<?> activityClass) {
        openActivity(activity, activityClass, null);
    }

    public static void openActivity(Fragment fragment, Class<?> activityClass) {
        openActivity(fragment, activityClass, null);
    }

    public static void openActivitySingleTop(Context activity, Class<?> activityClass) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    public static void openActivitySingleTop(Activity activity, Class<?> activityClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public static void openActivityClearTopBundle(Activity activity, Class<?> activityClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        activity.startActivity(intent);

    }

    public static void openActivityClearTop(Activity activity, Class<?> activityClass) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void openActivityClearTop(Activity activity, Class<?> activityClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public static void openActivity(Activity activity, Class<?> activityClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public static void openActivity(Fragment fragment, Class<?> activityClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(fragment.getActivity(), activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        fragment.startActivity(intent);
    }

    public static void openActivityForResult(Activity activity, Class<?> activityClass, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void openActivityForResult(Fragment fragment, Class<?> activityClass, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(fragment.getActivity(), activityClass);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void openActivityForResult(Activity activity, Class<?> activityClass, int requestCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void openActivityForResult(Fragment fragment, Class<?> activityClass, int requestCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(fragment.getActivity(), activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 通过action启动activity
     *
     * @param activity
     * @param action
     */
    public static void openActivity(Activity activity, String action) {
        openActivity(activity, action, null);
    }

    public static void openActivity(Activity activity, String action, Bundle bundle) {
        Intent intent = new Intent(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    public static void openActivityForResult(Activity activity, String action, int requestCode) {
        Intent intent = new Intent(action);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager,
                                             Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager,
                                             int frameId, Fragment... fragments) {
        for (Fragment fragment : fragments) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();
        }
    }


    /**
     * 通过 aRouter
     *
     * @param path
     */
    public static void openActivity(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public static void openActivity(String path, Context context) {
        ARouter.getInstance().build(path).navigation(context);
    }

    public static void openActivity(String path, Bundle bundle) {
        ARouter.getInstance().build(path).with(bundle).navigation();
    }

    public static void openActivitySingleTop(String path, Context context) {
        ARouter.getInstance().build(path).withFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP).navigation(context);
    }

    public static void openActivitySingleTop(String path, Context context, Bundle bundle) {
        ARouter.getInstance().build(path).with(bundle).withFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP).navigation(context);
    }

    public static void openActivityNewTask(String path) {
        ARouter.getInstance().build(path).withFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_NEW_TASK).navigation();
    }

    public static void openActivityNewTask(String path, Context context) {
        ARouter.getInstance().build(path).withFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_NEW_TASK).navigation(context);
    }

    public static void openActivityNewTask(String path, Bundle bundle) {
        ARouter.getInstance().build(path).withFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_NEW_TASK).with(bundle).navigation();
    }

    public static void openActivityForResult(String path, Activity context, int requestCode) {
        ARouter.getInstance().build(path).navigation(context, requestCode);
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phone
     */
    public static void toPhone(Context context, String phone) {

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String getAppName(Context context) {
        String appName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            appName = context.getResources().getString(labelRes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return appName;
    }
}
