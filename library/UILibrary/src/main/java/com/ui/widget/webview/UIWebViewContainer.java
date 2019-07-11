/*
 * Tencent is pleased to support the open source community by making UI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ui.widget.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.ui.util.UINotchHelper;
import com.ui.widget.UIWindowInsetLayout;


public class UIWebViewContainer extends UIWindowInsetLayout {

    private UIWebView mWebView;
    private UIWebView.OnScrollChangeListener mOnScrollChangeListener;

    public UIWebViewContainer(Context context) {
        super(context);
    }

    public UIWebViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void addWebView(@NonNull UIWebView webView, boolean needDispatchSafeAreaInset) {
        mWebView = webView;
        mWebView.setNeedDispatchSafeAreaInset(needDispatchSafeAreaInset);
        mWebView.addCustomOnScrollChangeListener(new UIWebView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(WebView webView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (mOnScrollChangeListener != null) {
                    mOnScrollChangeListener.onScrollChange(webView, scrollX, scrollY, oldScrollX, oldScrollY);
                }
            }
        });
        addView(mWebView, getWebViewLayoutParams());
    }

    protected FrameLayout.LayoutParams getWebViewLayoutParams() {
        return new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public void setNeedDispatchSafeAreaInset(boolean needDispatchSafeAreaInset) {
        if (mWebView != null) {
            mWebView.setNeedDispatchSafeAreaInset(needDispatchSafeAreaInset);
        }
    }

    public void destroy() {
        removeView(mWebView);
        removeAllViews();
        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        mWebView.destroy();
    }


    public void setCustomOnScrollChangeListener(UIWebView.OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener;
    }

    @Override
    @TargetApi(19)
    public boolean applySystemWindowInsets19(Rect insets) {
        if (getFitsSystemWindows()) {
            Rect childInsets = new Rect(insets);
            mUIWindowInsetHelper.computeInsetsWithGravity(this, childInsets);
            setPadding(childInsets.left, childInsets.top, childInsets.right, childInsets.bottom);
            return true;
        }
        return super.applySystemWindowInsets19(insets);
    }

    @Override
    @TargetApi(21)
    public boolean applySystemWindowInsets21(Object insets) {
        if (getFitsSystemWindows()) {
            int insetLeft = 0, insetRight = 0, insetTop = 0, insetBottom = 0;
            if (insets instanceof WindowInsetsCompat) {
                WindowInsetsCompat windowInsetsCompat = (WindowInsetsCompat) insets;
                insetLeft = windowInsetsCompat.getSystemWindowInsetLeft();
                insetRight = windowInsetsCompat.getSystemWindowInsetRight();
                insetTop = windowInsetsCompat.getSystemWindowInsetTop();
                insetBottom = windowInsetsCompat.getSystemWindowInsetBottom();
            } else if (insets instanceof WindowInsets) {
                WindowInsets windowInsets = (WindowInsets) insets;
                insetLeft = windowInsets.getSystemWindowInsetLeft();
                insetRight = windowInsets.getSystemWindowInsetRight();
                insetTop = windowInsets.getSystemWindowInsetTop();
                insetBottom = windowInsets.getSystemWindowInsetBottom();
            }

            if (UINotchHelper.needFixLandscapeNotchAreaFitSystemWindow(this) &&
                    getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                insetLeft = Math.max(insetLeft, UINotchHelper.getSafeInsetLeft(this));
                insetRight = Math.max(insetRight, UINotchHelper.getSafeInsetRight(this));
            }

            Rect childInsets = new Rect(insetLeft, insetTop, insetRight, insetBottom);
            mUIWindowInsetHelper.computeInsetsWithGravity(this, childInsets);
            setPadding(childInsets.left, childInsets.top, childInsets.right, childInsets.bottom);
            return true;
        }

        return super.applySystemWindowInsets21(insets);
    }
}
