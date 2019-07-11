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

package com.ui.nestedScroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class UIContinuousNestedScrollLayout extends CoordinatorLayout implements
        IUIContinuousNestedTopView.OnScrollNotifier,
        UIContinuousNestedTopAreaBehavior.Callback {

    private IUIContinuousNestedTopView mTopView;
    private IUIContinuousNestedBottomView mBottomView;

    private UIContinuousNestedTopAreaBehavior mTopAreaBehavior;
    private UIContinuousNestedBottomAreaBehavior mBottomAreaBehavior;
    private List<OnScrollListener> mOnScrollListeners = new ArrayList<>();

    public UIContinuousNestedScrollLayout(@NonNull Context context) {
        super(context);
    }

    public UIContinuousNestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UIContinuousNestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addOnScrollListener(@NonNull OnScrollListener onScrollListener) {
        if (!mOnScrollListeners.contains(onScrollListener)) {
            mOnScrollListeners.add(onScrollListener);
        }
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListeners.remove(onScrollListener);
    }

    private void dispatchTopScroll(int offset, int range, int innerOffset, int innerRange) {
        for (OnScrollListener onScrollListener : mOnScrollListeners) {
            onScrollListener.onTopScroll(offset, range, innerOffset, innerRange);
        }
    }


    public void setTopAreaView(View topView, @Nullable LayoutParams layoutParams) {
        if (!(topView instanceof IUIContinuousNestedTopView)) {
            throw new IllegalStateException("topView must implement from IUIContinuousNestedTopView");
        }
        if (mTopView != null) {
            removeView(((View) mTopView));
        }
        mTopView = (IUIContinuousNestedTopView) topView;
        mTopView.injectScrollNotifier(this);
        if (layoutParams == null) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        Behavior behavior = layoutParams.getBehavior();
        if (behavior instanceof UIContinuousNestedTopAreaBehavior) {
            mTopAreaBehavior = (UIContinuousNestedTopAreaBehavior) behavior;
        } else {
            mTopAreaBehavior = new UIContinuousNestedTopAreaBehavior(getContext());
            layoutParams.setBehavior(mTopAreaBehavior);
        }
        mTopAreaBehavior.setCallback(this);
        addView(topView, layoutParams);
    }

    public void setBottomAreaView(View bottomView, @Nullable LayoutParams layoutParams) {
        if (!(bottomView instanceof IUIContinuousNestedBottomView)) {
            throw new IllegalStateException("topView must implement from IUIContinuousNestedTopView");
        }
        if (mBottomView != null) {
            removeView(((View) mBottomView));
        }
        mBottomView = (IUIContinuousNestedBottomView) bottomView;
        if (layoutParams == null) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        Behavior behavior = layoutParams.getBehavior();
        if (behavior instanceof UIContinuousNestedBottomAreaBehavior) {
            mBottomAreaBehavior = (UIContinuousNestedBottomAreaBehavior) behavior;
        } else {
            mBottomAreaBehavior = new UIContinuousNestedBottomAreaBehavior();
            layoutParams.setBehavior(mBottomAreaBehavior);
        }
        addView(bottomView, layoutParams);
    }

    public void scrollBottomViewToTop() {
        if (mBottomView != null) {
            int top = ((View) mBottomView).getTop();
            if (top <= 0) {
                mBottomView.consumeScroll(Integer.MIN_VALUE);
                return;
            }
        }
        if (mTopView != null) {
            // consume the max value
            mTopView.consumeScroll(Integer.MAX_VALUE);
            if (mBottomView != null) {
                int contentHeight = mBottomView.getContentHeight();
                if (contentHeight != IUIContinuousNestedBottomView.HEIGHT_IS_ENOUGH_TO_SCROLL) {
                    // bottomView can not scroll
                    mTopAreaBehavior.setTopAndBottomOffset(getHeight() - contentHeight - ((View) mTopView).getHeight());
                    return;
                }
                mTopAreaBehavior.setTopAndBottomOffset(-((View) mTopView).getHeight());
            }
        }
    }

    public void scrollToTop() {
        if (mBottomView != null) {
            mBottomView.consumeScroll(Integer.MIN_VALUE);
        }
        if (mTopView != null) {
            mTopAreaBehavior.setTopAndBottomOffset(0);
            mTopView.consumeScroll(Integer.MIN_VALUE);
        }
    }


    public void scrollToBottom() {
        if (mTopView != null) {
            // consume the max value
            mTopView.consumeScroll(Integer.MAX_VALUE);
            if (mBottomView != null) {
                int contentHeight = mBottomView.getContentHeight();
                if (contentHeight != IUIContinuousNestedBottomView.HEIGHT_IS_ENOUGH_TO_SCROLL) {
                    // bottomView can not scroll
                    mTopAreaBehavior.setTopAndBottomOffset(getHeight() - contentHeight - ((View) mTopView).getHeight());
                    return;
                }
                mTopAreaBehavior.setTopAndBottomOffset(-((View) mTopView).getHeight());
            }
        }
        if (mBottomView != null) {
            mBottomView.consumeScroll(Integer.MAX_VALUE);
        }
    }

    @Override
    public void notify(int innerOffset, int innerRange) {
        dispatchTopScroll(-mTopAreaBehavior.getTopAndBottomOffset(), ((View) mTopView).getHeight(),
                innerOffset, innerRange);
    }

    @Override
    public void onTopAreaOffset(int offset) {
        dispatchTopScroll(-offset, ((View) mTopView).getHeight(),
                mTopView.getCurrentScroll(), mTopView.getScrollRange());
    }

    public interface OnScrollListener {
        void onTopScroll(int offset, int range, int innerOffset, int innerRange);
    }
}
