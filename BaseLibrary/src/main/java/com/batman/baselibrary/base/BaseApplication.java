package com.batman.baselibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;

import com.alibaba.android.arouter.launcher.ARouter;
import com.batman.baselibrary.delegate.ApplicationDelegate;
import com.batman.baselibrary.delegate.ApplicationDispatcher;
import com.batman.baselibrary.delegate.IApplicationDelegate;
import com.batman.baselibrary.utils.ActivityStack;
import com.bumptech.glide.Glide;
import com.network.BuildConfig;
import com.share.weixin.ShareApp;

import timber.log.Timber;

/**
 * 由于多个组件均含有各自的Application。使用多组件时需要手动派发Application的onCreate时间。使用此类做
 * 约束。避免同一个Application被多次调用
 */
public class BaseApplication extends ApplicationDelegate implements IApplicationDelegate {

    @Override
    public int getLevel() {
        return LEVEL_BASE_LIB;
    }

    @Override
    public Class[] subDelegates() {
        return null;
    }

    @Override
    public void onCreateDelegate() {

        ShareApp.register((Application) getApplicationContext());
        ((Application) getApplicationContext()).registerActivityLifecycleCallbacks(new ActivityCallback());

        registerComponentCallbacks(new ComponentCallbacks2() {
            @Override
            public void onTrimMemory(int level) {
//                MemorySizeCalculator calculator = new MemorySizeCalculator(getApplicationContext ());
//                int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
//                int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
                //内存不够，gc
                Glide.get(getApplicationContext()).clearMemory();
                System.gc();
            }

            @Override
            public void onConfigurationChanged(Configuration newConfig) {

            }

            @Override
            public void onLowMemory() {

            }
        });
        initDebug();
        inARouter();

    }

    private static class ActivityCallback implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            ActivityStack.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityStack.pop(activity);
        }
    }

    private void initDebug() {
        if (BuildConfig.LOG_DEBUG) {
            initLog();
            installLeakCanary();
            initStrictModeVmPolicy();
            initStrictModeThreadPolicy();
        }
    }

    protected void initLog() {
        inTimber();
        inDebugARouter();
    }

    protected void inTimber() {
        Timber.plant(new Timber.DebugTree());
    }


    protected void inDebugARouter() {
        ARouter.openLog();
        ARouter.openDebug();
    }

    /**
     * leakCanary内存泄露检查
     */
    protected void installLeakCanary() {

    }

    protected void inARouter() {
        ARouter.init((Application) ApplicationDispatcher.get().getApplicationContext());
    }

    //严苛模式
    private void initStrictModeVmPolicy() {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()/*检测Activity内存泄露*/
                .detectLeakedClosableObjects()/*检测未关闭的Closable对象*/
                .detectLeakedSqlLiteObjects() /*检测Sqlite对象是否关闭*/
                /*也可以采用detectAll()来检测所有想检测的东西*/
                .penaltyLog().build());
    }

    private void initStrictModeThreadPolicy() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectCustomSlowCalls() /* 侦测自定义的耗时操作*/
                .detectDiskReads()/*磁盘读取操作检测*/
                .detectDiskWrites()/*检测磁盘写入操作*/
                .detectNetwork() /*检测网络操作*/
                /*也可以采用detectAll()来检测所有想检测的东西*/
                .penaltyLog().build());
    }

}