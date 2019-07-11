package com.batman.baselibrary.di.component;

import android.app.Application;

import com.batman.baselibrary.di.module.AppModule;
import com.network.di.module.ClientModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, ClientModule.class})
public interface AppComponent {

    Application getApplication();

//    ApiService getApiService();

    OkHttpClient okHttpClient();

    Retrofit getRetrofit();

}
