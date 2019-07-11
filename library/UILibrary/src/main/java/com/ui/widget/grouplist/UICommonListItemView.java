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
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v4.widget.Space;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ui.R;
import com.ui.util.UIDisplayHelper;
import com.ui.util.UILangHelper;
import com.ui.util.UIResHelper;
import com.ui.util.UIViewHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 作为通用列表 {@link UIGroupListView} 里的 item 使用，也可以单独使用。
 * 支持以下样式:
 * <ul>
 * <li>通过 {@link #setText(CharSequence)} 设置一行文字</li>
 * <li>通过 {@link #setDetailText(CharSequence)} 设置一行说明文字, 并通过 {@link #setOrientation(int)} 设置说明文字的位置,
 * 也可以在 xml 中使用 {@link R.styleable#UICommonListItemView_ui_orientation} 设置。</li>
 * <li>通过 {@link #setAccessoryType(int)} 设置右侧 View 的类型, 可选的类型见 {@link UICommonListItemAccessoryType},
 * 也可以在 xml 中使用 {@link R.styleable#UICommonListItemView_ui_accessory_type} 设置。</li>
 * </ul>
 *
 * @author chantchen
 * @date 2015-1-8
 */
public class UICommonListItemView extends RelativeLayout {

    /**
     * 右侧不显示任何东西
     */
    public final static int ACCESSORY_TYPE_NONE = 0;
    /**
     * 右侧显示一个箭头
     */
    public final static int ACCESSORY_TYPE_CHEVRON = 1;
    /**
     * 右侧显示一个开关
     */
    public final static int ACCESSORY_TYPE_SWITCH = 2;
    /**
     * 自定义右侧显示的 View
     */
    public final static int ACCESSORY_TYPE_CUSTOM = 3;

    /**
     * detailText 在 title 文字的下方
     */
    public final static int VERTICAL = 0;
    /**
     * detailText 在 item 的右方
     */
    public final static int HORIZONTAL = 1;

    /**
     * 红点在左边
     */
    public final static int REDDOT_POSITION_LEFT = 0;
    /**
     * 红点在右边
     */
    public final static int REDDOT_POSITION_RIGHT = 1;

    @IntDef({ACCESSORY_TYPE_NONE, ACCESSORY_TYPE_CHEVRON, ACCESSORY_TYPE_SWITCH, ACCESSORY_TYPE_CUSTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UICommonListItemAccessoryType {
    }

    @IntDef({VERTICAL, HORIZONTAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UICommonListItemOrientation {
    }

    @IntDef({REDDOT_POSITION_LEFT, REDDOT_POSITION_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UICommonListItemRedDotPosition {
    }

    /**
     * Item 右侧的 View 的类型
     */
    @UICommonListItemAccessoryType
    private int mAccessoryType;

    /**
     * 控制 detailText 是在 title 文字的下方还是 item 的右方
     */
    private int mOrientation = HORIZONTAL;

    /**
     * 控制红点的位置
     */
    @UICommonListItemRedDotPosition
    private int mRedDotPosition = REDDOT_POSITION_LEFT;


    protected ImageView mImageView;
    private ViewGroup mAccessoryView;
    protected LinearLayout mTextContainer;
    protected TextView mTextView;
    protected TextView mDetailTextView;
    protected Space mTextDetailSpace;
    protected CheckBox mSwitch;
    private ImageView mRedDot;
    private ViewStub mNewTipViewStub;
    private View mNewTip;

    public UICommonListItemView(Context context) {
        this(context, null);
    }

    public UICommonListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.UICommonListItemViewStyle);
    }

    public UICommonListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.ui_common_list_item, this, true);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UICommonListItemView, defStyleAttr, 0);
        @UICommonListItemOrientation int orientation = array.getInt(R.styleable.UICommonListItemView_ui_orientation, HORIZONTAL);
        @UICommonListItemAccessoryType int accessoryType = array.getInt(R.styleable.UICommonListItemView_ui_accessory_type, ACCESSORY_TYPE_NONE);
        final int initTitleColor = array.getColor(R.styleable.UICommonListItemView_ui_commonList_titleColor, UIResHelper.getAttrColor(getContext(), R.attr.ui_config_color_gray_1));
        final int initDetailColor = array.getColor(R.styleable.UICommonListItemView_ui_commonList_detailColor, UIResHelper.getAttrColor(getContext(), R.attr.ui_config_color_gray_5));
        array.recycle();

        mImageView = (ImageView) findViewById(R.id.group_list_item_imageView);
        mTextContainer = (LinearLayout) findViewById(R.id.group_list_item_textContainer);
        mTextView = (TextView) findViewById(R.id.group_list_item_textView);
        mTextView.setTextColor(initTitleColor);
        mRedDot = (ImageView) findViewById(R.id.group_list_item_tips_dot);
        mNewTipViewStub = (ViewStub) findViewById(R.id.group_list_item_tips_new);
        mDetailTextView = (TextView) findViewById(R.id.group_list_item_detailTextView);
        mTextDetailSpace = (Space) findViewById(R.id.group_list_item_space);
        mDetailTextView.setTextColor(initDetailColor);
        LinearLayout.LayoutParams detailTextViewLP = (LinearLayout.LayoutParams) mDetailTextView.getLayoutParams();
        if (UIViewHelper.getIsLastLineSpacingExtraError()) {
            detailTextViewLP.bottomMargin = -UIResHelper.getAttrDimen(context, R.attr.ui_common_list_item_detail_line_space);
        }
        if (orientation == VERTICAL) {
            detailTextViewLP.topMargin = UIDisplayHelper.dp2px(getContext(), 6);
        } else {
            detailTextViewLP.topMargin = 0;
        }
        mAccessoryView = (ViewGroup) findViewById(R.id.group_list_item_accessoryView);
        setOrientation(orientation);
        setAccessoryType(accessoryType);
    }


    public void updateImageViewLp(LayoutParamConfig lpConfig) {
        if(lpConfig != null){
            LayoutParams lp = (LayoutParams) mImageView.getLayoutParams();
            mImageView.setLayoutParams(lpConfig.onConfig(lp));
        }
    }

    public void setImageDrawable(Drawable drawable) {
        if (drawable == null) {
            mImageView.setVisibility(View.GONE);
        } else {
            mImageView.setImageDrawable(drawable);
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    public void setRedDotPosition(@UICommonListItemRedDotPosition int redDotPosition) {
        mRedDotPosition = redDotPosition;
        requestLayout();
    }

    public CharSequence getText() {
        return mTextView.getText();
    }

    public void setText(CharSequence text) {
        mTextView.setText(text);
        if (UILangHelper.isNullOrEmpty(text)) {
            mTextView.setVisibility(View.GONE);
        } else {
            mTextView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 切换是否显示小红点
     *
     * @param isShow 是否显示小红点
     */
    public void showRedDot(boolean isShow) {
        showRedDot(isShow, true);
    }

    public void showRedDot(boolean isShow, boolean configToShow) {
        mRedDot.setVisibility((isShow && configToShow) ? VISIBLE : GONE);
    }

    /**
     * 切换是否显示更新提示
     *
     * @param isShow 是否显示更新提示
     */
    public void showNewTip(boolean isShow) {
        if (isShow) {
            if (mNewTip == null) {
                mNewTip = mNewTipViewStub.inflate();
            }
            mNewTip.setVisibility(View.VISIBLE);
            mRedDot.setVisibility(GONE);
        } else {
            if (mNewTip != null && mNewTip.getVisibility() == View.VISIBLE) {
                mNewTip.setVisibility(View.GONE);
            }
        }
    }

    public CharSequence getDetailText() {
        return mDetailTextView.getText();
    }


    public void setDetailText(CharSequence text) {
        mDetailTextView.setText(text);
        if (UILangHelper.isNullOrEmpty(text)) {
            mDetailTextView.setVisibility(View.GONE);
        } else {
            mDetailTextView.setVisibility(View.VISIBLE);
        }
    }

    public int getOrientation() {
        return mOrientation;
    }

    public void setOrientation(@UICommonListItemOrientation int orientation) {
        mOrientation = orientation;

        LinearLayout.LayoutParams spaceLp = (LinearLayout.LayoutParams) mTextDetailSpace.getLayoutParams();
        // 默认文字是水平布局的
        if (mOrientation == VERTICAL) {
            mTextContainer.setOrientation(LinearLayout.VERTICAL);
            mTextContainer.setGravity(Gravity.LEFT);
            spaceLp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            spaceLp.height = UIDisplayHelper.dp2px(getContext(), 4);
            spaceLp.weight = 0;
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIResHelper.getAttrDimen(getContext(), R.attr.ui_common_list_item_title_v_text_size));
            mDetailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIResHelper.getAttrDimen(getContext(), R.attr.ui_common_list_item_detail_v_text_size));
        } else {
            mTextContainer.setOrientation(LinearLayout.HORIZONTAL);
            mTextContainer.setGravity(Gravity.CENTER_VERTICAL);
            spaceLp.width = 0;
            spaceLp.height = 0;
            spaceLp.weight = 1;
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIResHelper.getAttrDimen(getContext(), R.attr.ui_common_list_item_title_h_text_size));
            mDetailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIResHelper.getAttrDimen(getContext(), R.attr.ui_common_list_item_detail_h_text_size));
        }
    }

    public int getAccessoryType() {
        return mAccessoryType;
    }

    /**
     * 设置右侧 View 的类型。
     *
     * @param type 见 {@link UICommonListItemAccessoryType}
     */
    public void setAccessoryType(@UICommonListItemAccessoryType int type) {
        mAccessoryView.removeAllViews();
        mAccessoryType = type;

        switch (type) {
            // 向右的箭头
            case ACCESSORY_TYPE_CHEVRON: {
                ImageView tempImageView = getAccessoryImageView();
                tempImageView.setImageDrawable(UIResHelper.getAttrDrawable(getContext(), R.attr.ui_common_list_item_chevron));
                mAccessoryView.addView(tempImageView);
                mAccessoryView.setVisibility(VISIBLE);
            }
            break;
            // switch开关
            case ACCESSORY_TYPE_SWITCH: {
                if (mSwitch == null) {
                    mSwitch = new CheckBox(getContext());
                    mSwitch.setButtonDrawable(UIResHelper.getAttrDrawable(getContext(), R.attr.ui_common_list_item_switch));
                    mSwitch.setLayoutParams(getAccessoryLayoutParams());
                    // disable掉且不可点击，然后通过整个item的点击事件来toggle开关的状态
                    mSwitch.setClickable(false);
                    mSwitch.setEnabled(false);
                }
                mAccessoryView.addView(mSwitch);
                mAccessoryView.setVisibility(VISIBLE);
            }
            break;
            // 自定义View
            case ACCESSORY_TYPE_CUSTOM:
                mAccessoryView.setVisibility(VISIBLE);
                break;
            // 清空所有accessoryView
            case ACCESSORY_TYPE_NONE:
                mAccessoryView.setVisibility(GONE);
                break;
        }
    }

    private ViewGroup.LayoutParams getAccessoryLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private ImageView getAccessoryImageView() {
        ImageView resultImageView = new ImageView(getContext());
        resultImageView.setLayoutParams(getAccessoryLayoutParams());
        resultImageView.setScaleType(ImageView.ScaleType.CENTER);
        return resultImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public TextView getDetailTextView() {
        return mDetailTextView;
    }

    public CheckBox getSwitch() {
        return mSwitch;
    }

    public ViewGroup getAccessoryContainerView() {
        return mAccessoryView;
    }

    /**
     * 添加自定义的 Accessory View
     *
     * @param view 自定义的 Accessory View
     */
    public void addAccessoryCustomView(View view) {
        if (mAccessoryType == ACCESSORY_TYPE_CUSTOM) {
            mAccessoryView.addView(view);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        // 红点的位置
        if (mRedDot != null && mRedDot.getVisibility() == View.VISIBLE) {

            int top = getHeight() / 2 - mRedDot.getMeasuredHeight() / 2;
            int textLeft = mTextContainer.getLeft();
            int left;

            if (mRedDotPosition == REDDOT_POSITION_LEFT) {
                //红点在左
                float textWidth = mTextView.getPaint().measureText(mTextView.getText().toString()); // 文字宽度
                left = (int) (textLeft + textWidth + UIDisplayHelper.dp2px(getContext(), 4)); // 在原来红点位置的基础上右移

            } else if (mRedDotPosition == REDDOT_POSITION_RIGHT) {
                //红点在右
                left = textLeft + mTextContainer.getWidth() - mRedDot.getMeasuredWidth();

            } else {
                return;
            }

            mRedDot.layout(left,
                    top,
                    left + mRedDot.getMeasuredWidth(),
                    top + mRedDot.getMeasuredHeight());

        }

        // New的位置
        if (mNewTip != null && mNewTip.getVisibility() == View.VISIBLE) {
            int textLeft = mTextContainer.getLeft();
            float textWidth = mTextView.getPaint().measureText(mTextView.getText().toString()); // 文字宽度
            int left = (int) (textLeft + textWidth + UIDisplayHelper.dp2px(getContext(), 4)); // 在原来红点位置的基础上右移
            int top = getHeight() / 2 - mNewTip.getMeasuredHeight() / 2;
            mNewTip.layout(left,
                    top,
                    left + mNewTip.getMeasuredWidth(),
                    top + mNewTip.getMeasuredHeight());
        }
    }


    public interface LayoutParamConfig {
        LayoutParams onConfig(LayoutParams lp);
    }
}
