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

package com.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.ui.util.UINotchHelper;

public class UINotchConsumeLayout extends FrameLayout implements INotchInsetConsumer {
    public UINotchConsumeLayout(Context context) {
        this(context, null);
    }

    public UINotchConsumeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UINotchConsumeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFitsSystemWindows(false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!UINotchHelper.isNotchOfficialSupport()) {
            notifyInsetMaybeChanged();
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!UINotchHelper.isNotchOfficialSupport()) {
            notifyInsetMaybeChanged();
        }
    }

    @Override
    public boolean notifyInsetMaybeChanged() {
        setPadding(
                UINotchHelper.getSafeInsetLeft(this),
                UINotchHelper.getSafeInsetTop(this),
                UINotchHelper.getSafeInsetRight(this),
                UINotchHelper.getSafeInsetBottom(this)
        );
        return true;
    }
}
