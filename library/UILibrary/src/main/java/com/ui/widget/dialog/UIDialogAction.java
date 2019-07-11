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

package com.ui.widget.dialog;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

import com.ui.R;
import com.ui.layout.UIButton;
import com.ui.util.UISpanHelper;
import com.ui.util.UIViewHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author cginechen
 * @date 2015-10-20
 */
public class UIDialogAction {

    @IntDef({ACTION_PROP_NEGATIVE, ACTION_PROP_NEUTRAL, ACTION_PROP_POSITIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Prop {
    }

    //用于标记positive/negative/neutral
    public static final int ACTION_PROP_POSITIVE = 0;
    public static final int ACTION_PROP_NEUTRAL = 1;
    public static final int ACTION_PROP_NEGATIVE = 2;


    private Context mContext;
    private CharSequence mStr;
    private int mIconRes;
    private int mActionProp;
    private ActionListener mOnClickListener;
    private UIButton mButton;
    private boolean mIsEnabled = true;

    //region 构造器

    /**
     * 无图标Action
     *
     * @param context         context
     * @param strRes          文案
     * @param onClickListener 点击事件
     */
    public UIDialogAction(Context context, int strRes, ActionListener onClickListener) {
        this(context, context.getResources().getString(strRes), ACTION_PROP_NEUTRAL, onClickListener);
    }

    public UIDialogAction(Context context, String str, ActionListener onClickListener) {
        this(context, str, ACTION_PROP_NEUTRAL, onClickListener);
    }


    /**
     * @param context         context
     * @param strRes          文案
     * @param actionProp      属性
     * @param onClickListener 点击事件
     */
    public UIDialogAction(Context context, int strRes, @Prop int actionProp, ActionListener onClickListener) {
        mContext = context;
        mStr = mContext.getResources().getString(strRes);
        mActionProp = actionProp;
        mOnClickListener = onClickListener;
    }

    public UIDialogAction(Context context, CharSequence str, @Prop int actionProp, ActionListener onClickListener) {
        mContext = context;
        mStr = str;
        mActionProp = actionProp;
        mOnClickListener = onClickListener;
    }

    public UIDialogAction(Context context, int iconRes, CharSequence str, @Prop int actionProp, ActionListener onClickListener) {
        mContext = context;
        mIconRes = iconRes;
        mStr = str;
        mActionProp = actionProp;
        mOnClickListener = onClickListener;
    }

    //endregion


    public void setOnClickListener(ActionListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;
        if (mButton != null) {
            mButton.setEnabled(enabled);
        }
    }

    public UIButton buildActionView(final UIDialog dialog, final int index) {
        mButton = generateActionButton(dialog.getContext(), mStr, mIconRes);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null && mButton.isEnabled()) {
                    mOnClickListener.onClick(dialog, index);
                }
            }
        });
        return mButton;
    }

    /**
     * 生成适用于对话框的按钮
     */
    private UIButton generateActionButton(Context context, CharSequence text, int iconRes) {
        // button 有提供 buttonStyle, 覆盖第三个参数不是好选择
        UIButton button = new UIButton(context);
        UIViewHelper.setBackground(button, null);
        button.setMinHeight(0);
        button.setMinimumHeight(0);
        button.setChangeAlphaWhenDisable(true);
        button.setChangeAlphaWhenPress(true);
        TypedArray a = context.obtainStyledAttributes(null, R.styleable.UIDialogActionStyleDef, R.attr.ui_dialog_action_style, 0);
        int count = a.getIndexCount();
        int paddingHor = 0, iconSpace = 0;
        ColorStateList negativeTextColor = null, positiveTextColor = null;
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.UIDialogActionStyleDef_android_gravity) {
                button.setGravity(a.getInt(attr, -1));
            } else if (attr == R.styleable.UIDialogActionStyleDef_android_textColor) {
                button.setTextColor(a.getColorStateList(attr));
            } else if (attr == R.styleable.UIDialogActionStyleDef_android_textSize) {
                button.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.UIDialogActionStyleDef_ui_dialog_action_button_padding_horizontal) {
                paddingHor = a.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.UIDialogActionStyleDef_android_background) {
                UIViewHelper.setBackground(button, a.getDrawable(attr));
            } else if (attr == R.styleable.UIDialogActionStyleDef_android_minWidth) {
                int miniWidth = a.getDimensionPixelSize(attr, 0);
                button.setMinWidth(miniWidth);
                button.setMinimumWidth(miniWidth);
            } else if (attr == R.styleable.UIDialogActionStyleDef_ui_dialog_positive_action_text_color) {
                positiveTextColor = a.getColorStateList(attr);
            } else if (attr == R.styleable.UIDialogActionStyleDef_ui_dialog_negative_action_text_color) {
                negativeTextColor = a.getColorStateList(attr);
            } else if (attr == R.styleable.UIDialogActionStyleDef_ui_dialog_action_icon_space) {
                iconSpace = a.getDimensionPixelSize(attr, 0);
            }else if(attr == R.styleable.UITextCommonStyleDef_android_textStyle){
                int styleIndex = a.getInt(attr, -1);
                button.setTypeface(null, styleIndex);
            }
        }

        a.recycle();
        button.setPadding(paddingHor, 0, paddingHor, 0);
        if (iconRes <= 0) {
            button.setText(text);
        } else {
            button.setText(UISpanHelper.generateSideIconText(true, iconSpace, text, ContextCompat.getDrawable(context, iconRes)));
        }

        button.setClickable(true);
        button.setEnabled(mIsEnabled);

        if (mActionProp == ACTION_PROP_NEGATIVE) {
            button.setTextColor(negativeTextColor);
        } else if (mActionProp == ACTION_PROP_POSITIVE) {
            button.setTextColor(positiveTextColor);
        }
        return button;
    }

    public int getActionProp() {
        return mActionProp;
    }

    public interface ActionListener {
        void onClick(UIDialog dialog, int index);
    }
}
