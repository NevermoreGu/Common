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

package com.ui.widget.textview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.ui.link.UILinkTouchMovementMethod;
import com.ui.span.UITouchableSpan;


/**
 * <p>
 * 修复了 {@link TextView} 与 {@link android.text.style.ClickableSpan} 一起使用时，
 * 点击 {@link android.text.style.ClickableSpan} 也会触发 {@link TextView} 的事件的问题。
 * </p>
 * <p>
 * 同时通过 {@link #setNeedForceEventToParent(boolean)} 控制该 TextView 的点击事件能否传递给其 Parent，
 * 修复了 {@link TextView} 默认情况下如果添加了 {@link android.text.style.ClickableSpan} 之后就无法把点击事件传递给 {@link TextView} 的 Parent 的问题。
 * </p>
 * <p>
 * 注意: 使用该 {@link TextView} 时, 用 {@link UITouchableSpan} 代替 {@link android.text.style.ClickableSpan},
 * 且同时可以使用 {@link UITouchableSpan} 达到修改 span 的文字颜色和背景色的目的。
 * </p>
 * <p>
 * 注意: 使用该 {@link TextView} 时, 需调用 {@link #setMovementMethodDefault()} 方法设置默认的 {@link UILinkTouchMovementMethod},
 * TextView 会在 {@link #onTouchEvent(MotionEvent)} 时将事件传递给 {@link UILinkTouchMovementMethod},
 * 然后传递给 {@link UITouchableSpan}, 实现点击态的变化和点击事件的响应。
 * </p>
 *
 * @author cginechen
 * @date 2017-03-20
 * @see UITouchableSpan
 * @see UILinkTouchMovementMethod
 */
public class UISpanTouchFixTextView extends AppCompatTextView implements ISpanTouchFix {
    /**
     * 记录当前 Touch 事件对应的点是不是点在了 span 上面
     */
    private boolean mTouchSpanHit;

    /**
     * 记录每次真正传入的press，每次更改mTouchSpanHint，需要再调用一次setPressed，确保press状态正确
     */
    private boolean mIsPressedRecord = false;
    /**
     * TextView是否应该消耗事件
     */
    private boolean mNeedForceEventToParent = false;

    public UISpanTouchFixTextView(Context context) {
        this(context, null);
    }

    public UISpanTouchFixTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UISpanTouchFixTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHighlightColor(Color.TRANSPARENT);
    }

    public void setNeedForceEventToParent(boolean needForceEventToParent) {
        mNeedForceEventToParent = needForceEventToParent;
        setFocusable(!needForceEventToParent);
        setClickable(!needForceEventToParent);
        setLongClickable(!needForceEventToParent);
    }

    /**
     * 使用者主动调用
     */
    public void setMovementMethodDefault(){
        setMovementMethodCompat(UILinkTouchMovementMethod.getInstance());
    }

    public void setMovementMethodCompat(MovementMethod movement){
        setMovementMethod(movement);
        if(mNeedForceEventToParent){
            setNeedForceEventToParent(true);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!(getText() instanceof Spannable)) {
            return super.onTouchEvent(event);
        }
        mTouchSpanHit = true;
        // 调用super.onTouchEvent,会走到UILinkTouchMovementMethod
        // 会走到UILinkTouchMovementMethod#onTouchEvent会修改mTouchSpanHint
        boolean ret = super.onTouchEvent(event);
        if(mNeedForceEventToParent){
            return mTouchSpanHit;
        }
        return ret;
    }

    @Override
    public void setTouchSpanHit(boolean hit) {
        if (mTouchSpanHit != hit) {
            mTouchSpanHit = hit;
            setPressed(mIsPressedRecord);
        }
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean performClick() {
        if (!mTouchSpanHit && !mNeedForceEventToParent) {
            return super.performClick();
        }
        return false;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean performLongClick() {
        if (!mTouchSpanHit && !mNeedForceEventToParent) {
            return super.performLongClick();
        }
        return false;
    }

    @Override
    public final void setPressed(boolean pressed) {
        mIsPressedRecord = pressed;
        if (!mTouchSpanHit) {
            onSetPressed(pressed);
        }
    }

    protected void onSetPressed(boolean pressed) {
        super.setPressed(pressed);
    }
}
