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

package com.ui.widget.roundwidget;

import android.content.Context;
import android.util.AttributeSet;

import com.ui.R;
import com.ui.alpha.UIAlphaButton;
import com.ui.util.UIViewHelper;

/**
 * 使按钮能方便地指定圆角、边框颜色、边框粗细、背景色
 * <p>
 * 注意: 因为该控件的圆角采用 View 的 background 实现, 所以与原生的 <code>android:background</code> 有冲突。
 * <ul>
 * <li>如果在 xml 中用 <code>android:background</code> 指定 background, 该 background 不会生效。</li>
 * <li>如果在该 View 构造完后用 {@link #setBackgroundResource(int)} 等方法设置背景, 该背景将覆盖圆角效果。</li>
 * </ul>
 * </p>
 * <p>
 * 如需在 xml 中指定圆角、边框颜色、边框粗细、背景色等值,采用 xml 属性 {@link com.ui.R.styleable#UIRoundButton}
 * </p>
 * <p>
 * 如需在 Java 中指定以上属性, 需要通过 {@link #getBackground()} 获取 {@link UIRoundButtonDrawable} 对象,
 * 然后使用 {@link UIRoundButtonDrawable} 提供的方法进行设置。
 * </p>
 * <p>
 * @see UIRoundButtonDrawable
 * </p>
 */
public class UIRoundButton extends UIAlphaButton {

    public UIRoundButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public UIRoundButton(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.UIButtonStyle);
        init(context, attrs, R.attr.UIButtonStyle);
    }

    public UIRoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        UIRoundButtonDrawable bg = UIRoundButtonDrawable.fromAttributeSet(context, attrs, defStyleAttr);
        UIViewHelper.setBackgroundKeepingPadding(this, bg);
        setChangeAlphaWhenDisable(false);
        setChangeAlphaWhenPress(false);
    }
}
