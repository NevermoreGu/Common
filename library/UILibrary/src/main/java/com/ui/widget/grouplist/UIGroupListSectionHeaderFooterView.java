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

package com.ui.widget.grouplist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ui.R;
import com.ui.util.UILangHelper;


/**
 * 用作通用列表 {@link UIGroupListView} 里每个 {@link UIGroupListView.Section} 的头部或尾部，也可单独使用。
 *
 * @author molicechen
 * @date 2015-01-07
 */

public class UIGroupListSectionHeaderFooterView extends LinearLayout {

    private TextView mTextView;

    public UIGroupListSectionHeaderFooterView(Context context) {
        this(context, null, R.attr.UIGroupListSectionViewStyle);
    }


    public UIGroupListSectionHeaderFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.UIGroupListSectionViewStyle);
    }

    public UIGroupListSectionHeaderFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public UIGroupListSectionHeaderFooterView(Context context, CharSequence titleText) {
        this(context);
        setText(titleText);
    }

    public UIGroupListSectionHeaderFooterView(Context context, CharSequence titleText, boolean isFooter) {
        this(context);

        if (isFooter) {
            // Footer View 不需要 padding bottom
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), 0);
        }

        setText(titleText);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.ui_group_list_section_layout, this, true);
        setGravity(Gravity.BOTTOM);

        mTextView = (TextView) findViewById(R.id.group_list_section_header_textView);
    }

    public void setText(CharSequence text) {
        if (UILangHelper.isNullOrEmpty(text)) {
            mTextView.setVisibility(GONE);
        } else {
            mTextView.setVisibility(VISIBLE);
        }
        mTextView.setText(text);
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextGravity(int gravity) {
        mTextView.setGravity(gravity);
    }
}
