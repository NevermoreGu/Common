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

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.Gravity;

import com.ui.util.UIDisplayHelper;


/**
 * use {@link UITabSegment#tabBuilder()} to get a instance
 */
public class UITabBuilder {
    /**
     * icon in normal state
     */
    private @Nullable
    Drawable normalDrawable;
    /**
     * icon in selected state
     */
    private @Nullable Drawable selectedDrawable;
    /**
     * change icon by tint color, if true, selectedDrawable will not work
     */
    private boolean dynamicChangeIconColor = false;
    /**
     * text size in normal state
     */
    private int normalTextSize;
    /**
     * text size in selected state
     */
    private int selectTextSize;

    /**
     * text color(icon color in if dynamicChangeIconColor == true) in  normal state
     */
    private int normalColor;
    /**
     * text color(icon color in if dynamicChangeIconColor == true) in  selected state
     */
    private int selectedColor;
    /**
     * icon position(left/top/right/bottom)
     */
    private @UITab.IconPosition int iconPosition = UITab.ICON_POSITION_TOP;
    /**
     * gravity of text
     */
    private int gravity = Gravity.CENTER;
    /**
     * text
     */
    private CharSequence text;

    /**
     * text typeface in normal state
     */
    private Typeface normalTypeface;

    /**
     * text typeface in selected state
     */
    private Typeface selectedTypeface;

    /**
     * width of tab icon in normal state
     */
    private int normalTabIconWidth = UITabIcon.TAB_ICON_INTRINSIC;
    /**
     * height of tab icon in normal state
     */
    int normalTabIconHeight = UITabIcon.TAB_ICON_INTRINSIC;
    /**
     * scale of tab icon in selected state
     */
    float selectedTabIconScale = 1f;

    /**
     * signCount or redPoint
     */
    private int signCount = UITab.NO_SIGN_COUNT_AND_RED_POINT;

    /**
     * max signCount digits, if the number is over the digits, use 'xx+' to present
     * if signCountDigits == 2 and number is 110, then component will show '99+'
     */
    private int signCountDigits = 2;
    /**
     * the margin left of signCount(redPoint) view
     */
    private int signCountLeftMarginWithIconOrText;
    /**
     * the margin top of signCount(redPoint) view
     */
    private int signCountBottomMarginWithIconOrText;

    /**
     * the gap between icon and text
     */
    private int iconTextGap;

    /**
     * allow icon draw outside of tab view
     */
    private boolean allowIconDrawOutside = true;


    UITabBuilder(Context context) {
        iconTextGap = UIDisplayHelper.dp2px(context, 2);
        normalTextSize = selectTextSize = UIDisplayHelper.dp2px(context, 12);
        signCountLeftMarginWithIconOrText = UIDisplayHelper.dp2px(context, 3);
        signCountBottomMarginWithIconOrText = signCountLeftMarginWithIconOrText;
    }

    UITabBuilder(UITabBuilder other) {
        this.normalDrawable = other.normalDrawable;
        this.selectedDrawable = other.selectedDrawable;
        this.dynamicChangeIconColor = other.dynamicChangeIconColor;
        this.normalTextSize = other.normalTextSize;
        this.selectTextSize = other.selectTextSize;
        this.normalColor = other.normalColor;
        this.selectedColor = other.selectedColor;
        this.iconPosition = other.iconPosition;
        this.gravity = other.gravity;
        this.text = other.text;
        this.signCount = other.signCount;
        this.signCountDigits = other.signCountDigits;
        this.signCountLeftMarginWithIconOrText = other.signCountLeftMarginWithIconOrText;
        this.signCountBottomMarginWithIconOrText = other.signCountBottomMarginWithIconOrText;
        this.normalTypeface = other.normalTypeface;
        this.selectedTypeface = other.selectedTypeface;
        this.normalTabIconWidth = other.normalTabIconWidth;
        this.normalTabIconHeight = other.normalTabIconHeight;
        this.selectedTabIconScale = other.selectedTabIconScale;
        this.iconTextGap = other.iconTextGap;
        this.allowIconDrawOutside = other.allowIconDrawOutside;
    }

    public UITabBuilder setAllowIconDrawOutside(boolean allowIconDrawOutside) {
        this.allowIconDrawOutside = allowIconDrawOutside;
        return this;
    }

    public UITabBuilder setNormalDrawable(Drawable normalDrawable) {
        this.normalDrawable = normalDrawable;
        return this;
    }

    public UITabBuilder setSelectedDrawable(Drawable selectedDrawable) {
        this.selectedDrawable = selectedDrawable;
        return this;
    }

    public UITabBuilder setTextSize(int normalTextSize, int selectedTextSize) {
        this.normalTextSize = normalTextSize;
        this.selectTextSize = selectedTextSize;
        return this;
    }

    public UITabBuilder setTypeface(Typeface normalTypeface, Typeface selectedTypeface) {
        this.normalTypeface = normalTypeface;
        this.selectedTypeface = selectedTypeface;
        return this;
    }

    public UITabBuilder setNormalIconSizeInfo(int normalWidth, int normalHeight) {
        this.normalTabIconWidth = normalWidth;
        this.normalTabIconHeight = normalHeight;
        return this;
    }

    public UITabBuilder setSelectedIconScale(float selectedScale) {
        this.selectedTabIconScale = selectedScale;
        return this;
    }

    public UITabBuilder setIconTextGap(int iconTextGap) {
        this.iconTextGap = iconTextGap;
        return this;
    }

    public UITabBuilder setSignCount(int signCount) {
        this.signCount = signCount;
        return this;
    }

    public UITabBuilder setSignCountMarginInfo(int digit,
                                                 int leftMarginWithIconOrText, int bottomMarginWithIconOrText) {
        this.signCountDigits = digit;
        this.signCountLeftMarginWithIconOrText = leftMarginWithIconOrText;
        this.signCountBottomMarginWithIconOrText = bottomMarginWithIconOrText;
        return this;
    }

    public UITabBuilder setColor(int normalColor, int selectedColor) {
        this.normalColor = normalColor;
        this.selectedColor = selectedColor;
        return this;
    }

    public UITabBuilder setDynamicChangeIconColor(boolean dynamicChangeIconColor) {
        this.dynamicChangeIconColor = dynamicChangeIconColor;
        return this;
    }

    public UITabBuilder setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public UITabBuilder setIconPosition(@UITab.IconPosition int iconPosition) {
        this.iconPosition = iconPosition;
        return this;
    }

    public UITabBuilder setText(CharSequence text) {
        this.text = text;
        return this;
    }

    public UITab build() {
        UITab tab = new UITab(this.text);
        if (normalDrawable != null) {
            if (dynamicChangeIconColor || selectedDrawable == null) {
                tab.tabIcon = new UITabIcon(normalDrawable, null);
            } else {
                tab.tabIcon = new UITabIcon(normalDrawable, selectedDrawable);
            }
            tab.tabIcon.setBounds(0, 0, normalTabIconWidth, normalTabIconHeight);
        }
        tab.normalTabIconWidth = this.normalTabIconWidth;
        tab.normalTabIconHeight = this.normalTabIconHeight;
        tab.selectedTabIconScale = this.selectedTabIconScale;
        tab.gravity = this.gravity;
        tab.iconPosition = this.iconPosition;
        tab.normalTextSize = this.normalTextSize;
        tab.selectedTextSize = this.selectTextSize;
        tab.normalTypeface = this.normalTypeface;
        tab.selectedTypeface = this.selectedTypeface;
        tab.normalColor = this.normalColor;
        tab.selectedColor = this.selectedColor;
        tab.signCount = this.signCount;
        tab.signCountDigits = this.signCountDigits;
        tab.signCountLeftMarginWithIconOrText = this.signCountLeftMarginWithIconOrText;
        tab.signCountBottomMarginWithIconOrText = this.signCountBottomMarginWithIconOrText;
        tab.iconTextGap = this.iconTextGap;
        return tab;
    }
}
