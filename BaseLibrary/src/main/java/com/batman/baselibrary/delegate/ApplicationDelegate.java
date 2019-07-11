package com.batman.baselibrary.delegate;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.batman.baselibrary.di.component.AppComponent;
import com.batman.baselibrary.di.component.DaggerAppComponent;
import com.batman.baselibrary.di.module.AppModule;
import com.network.di.module.ClientModule;
import com.network.http.HttpHandler;

import okhttp3.Interceptor;


/**
 * 由于多个组件均含有各自的Application。使用多组件时需要手动派发Application的onCreate时间。使用此类做
 * 约束。避免同一个Application被多次调用
 */
public abstract class ApplicationDelegate extends MultiDexApplication implements IApplicationDelegate {

    private ClientModule mClientModule;

    AppComponent mAppComponent;

    @Override
    protected final void attachBaseContext(Context base) {
        ApplicationDispatcher.get().init(this);
        ApplicationDispatcher.get().link(this);
        ApplicationDispatcher.get().performAttachBaseContext(base);
        super.attachBaseContext(base);
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        this.mClientModule = ClientModule//用于提供okhttp和retrofit的单列
                .builder()
                .baseUrl(getBaseUrl())
                .httpHandler(getHttpHandler())
                .interceptors(getInterceptors())
                .build();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .clientModule(getClientModule())
                .build();
        ApplicationDispatcher.get().performCreate();

    }

    @Override
    public final void onTerminate() {
        ApplicationDispatcher.get().performTerminal();
        super.onTerminate();
    }

    @Override
    public final void onConfigurationChanged(Configuration newConfig) {
        ApplicationDispatcher.get().performConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public final void onLowMemory() {
        ApplicationDispatcher.get().performLowMemory();
        super.onLowMemory();
    }

    @Override
    public final void onTrimMemory(int level) {
        ApplicationDispatcher.get().performTrimMemory(level);
        super.onTrimMemory(level);
    }

    @Override
    public Context getApplicationContext() {
        return ApplicationDispatcher.get().getApplicationContext();
    }

    @Override
    public void attachBaseContextDelegate(Context base) {
        Log.d(getClass().getCanonicalName(), "attachBaseContextDelegate invoked!");
    }

    @Override
    public void onTerminateDelegate() {
        Log.d(getClass().getCanonicalName(), "onTerminateDelegate invoked!");
    }

    @Override
    public void onConfigurationChangedDelegate(Configuration newConfig) {
        Log.d(getClass().getCanonicalName(), "onConfigurationChangedDelegate invoked!");
    }

    @Override
    public void onLowMemoryDelegate() {
        Log.d(getClass().getCanonicalName(), "onLowMemoryDelegate invoked!");
    }

    @Override
    public void onTrimMemoryDelegate(int level) {
        Log.d(getClass().getCanonicalName(), "onTrimMemoryDelegate invoked!");
    }

    protected String getBaseUrl() {
        return null;
    };

    public ClientModule getClientModule() {
        return mClientModule;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    /**
     * 这里可以提供一个全局处理http响应结果的处理类,
     * 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
     * 默认不实现,如果有需求可以重写此方法
     *
     * @return
     */
    protected HttpHandler getHttpHandler() {
        return null;
    }

    /**
     * 用来提供interceptor,如果要提供额外的interceptor可以让子application实现此方法
     *
     * @return
     */
    protected Interceptor[] getInterceptors() {
        return null;
    }


    /**
     * 用来提供处理所有错误的监听
     * 如果要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法)
     * 则让子application重写此方法
     *
     * @return
     */
//    protected ResponseErrorListener getResponseErrorListener() {
//        return new ResponseErrorListener() {
//            @Override
//            public void handleResponseError(Context context, Exception e) {
//
//            }
//        };
//    }
}
