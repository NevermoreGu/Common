package com.batman.baselibrary.base;

import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.batman.baselibrary.R;
import com.batman.baselibrary.utils.ProgressWebView;


public class BaseWebViewActivity extends BaseActivity {

    protected ProgressWebView mWebView;

    @Override
    public void initViews() {
        mWebView = (ProgressWebView) findViewById(R.id.web_view);
        WebSettings webSettings = mWebView.getSettings();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (handlerOverrideUrlLoading(view, url)) {
                    view.loadUrl(url);
                }


                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  // 接受所有网站的证书
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK
                            && mWebView.canGoBack()) {
                        mWebView.goBack(); // 后退
                        return true; // 已处理
                    }
                }
                return false;
            }
        });
//        webview.setWebChromeClient(new WebChromeClient());
        webSettings.setJavaScriptEnabled(true); // 启用JS脚本
        webSettings.setLoadWithOverviewMode(true);//
        webSettings.setUseWideViewPort(true);// 设置概览模式
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);// 设置启动缓存
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);// 设置最大缓存
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //启用数据库
        webSettings.setDatabaseEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        // 启用地理定位
        webSettings.setGeolocationEnabled(true);
        // 设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(dir);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //设置本地调用对象及其接口
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true); // 启用内置缩放装置
    }

    @Override
    public void loadData(Bundle savedInstanceState) {

    }

    public void setUrl(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_webview;
    }

    public boolean handlerOverrideUrlLoading(WebView view, String url) {

        return true;
    }

}
