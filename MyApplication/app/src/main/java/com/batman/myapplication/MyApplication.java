package com.batman.myapplication;

import com.batman.baselibrary.api.RequestHeadParam;
import com.batman.baselibrary.delegate.ApplicationDelegate;
import com.batman.logincomponent.LoginApplication;
import com.network.http.HttpHandler;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyApplication extends ApplicationDelegate {

    @Override
    protected String getBaseUrl() {
        return "https://api.sit.youxinchat.com:8443/";
    }

    @Override
    public int getLevel() {
        return LEVEL_APP;
    }

    @Override
    public Class[] subDelegates() {
        return new Class[]{LoginApplication.class};
    }

    @Override
    public void onCreateDelegate() {
    }

    /**
     * 动态添加请求头
     */
    @Override
    protected HttpHandler getHttpHandler() {
        return new HttpHandler() {
            @Override
            public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                return response;
            }

            @Override
            public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                HashMap<String, String> hashHeads = RequestHeadParam.getInstance().getBaseParameters();
                Request.Builder builder = request.newBuilder();
                for (Map.Entry<String, String> entry : hashHeads.entrySet()) {
                    String name = entry.getKey();
                    String value = entry.getValue();
                    builder.addHeader(name, value);
                }
                return builder.build();
            }
        };
    }
}
