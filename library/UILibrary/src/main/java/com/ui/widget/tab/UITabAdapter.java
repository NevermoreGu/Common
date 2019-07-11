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

package com.ui.widget.tab;

import android.view.ViewGroup;

import com.ui.widget.UIItemViewsAdapter;

public class UITabAdapter extends UIItemViewsAdapter<UITab, UITabView> implements UITabView.Callback {
    private UITabSegment mTabSegment;

    public UITabAdapter(UITabSegment tabSegment, ViewGroup parentView) {
        super(parentView);
        mTabSegment = tabSegment;
    }

    @Override
    protected UITabView createView(ViewGroup parentView) {
        return new UITabView(parentView.getContext());
    }

    @Override
    protected final void bind(UITab item, UITabView view, int position) {
        onBindTab(item, view, position);
        view.setCallback(this);
    }

    protected void onBindTab(UITab item, UITabView view, int position) {
        view.bind(item);
    }

    @Override
    public void onClick(UITabView view) {
        int index = getViews().indexOf(view);
        mTabSegment.onClickTab(index);
    }

    @Override
    public void onDoubleClick(UITabView view) {
        int index = getViews().indexOf(view);
        mTabSegment.onDoubleClick(index);
    }

    @Override
    public void onLongClick(UITabView view) {
    }
}
