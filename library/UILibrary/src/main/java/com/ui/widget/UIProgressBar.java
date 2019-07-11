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

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ui.R;
import com.ui.util.UIDisplayHelper;

/**
 * 一个进度条控件，通过颜色变化显示进度，支持环形和矩形两种形式，主要特性如下：
 * <ol>
 * <li>支持在进度条中以文字形式显示进度，支持修改文字的颜色和大小。</li>
 * <li>可以通过 xml 属性修改进度背景色，当前进度颜色，进度条尺寸。</li>
 * <li>支持限制进度的最大值。</li>
 * </ol>
 *
 * @author cginechen
 * @date 2015-07-29
 */
public class UIProgressBar extends View {

    public static int TYPE_RECT = 0;
    public static int TYPE_CIRCLE = 1;
    public static int TYPE_ROUND_RECT = 2;
    public static int TOTAL_DURATION = 1000;
    public static int DEFAULT_PROGRESS_COLOR = Color.BLUE;
    public static int DEFAULT_BACKGROUND_COLOR = Color.GRAY;
    public static int DEFAULT_TEXT_SIZE = 20;
    public static int DEFAULT_TEXT_COLOR = Color.BLACK;
    /*circle_progress member*/
    public static int DEFAULT_STROKE_WIDTH = UIDisplayHelper.dpToPx(40);
    UIProgressBarTextGenerator mUIProgressBarTextGenerator;
    /*rect_progress member*/
    RectF mBgRect;
    RectF mProgressRect;
    /*common member*/
    private int mWidth;
    private int mHeight;
    private int mType;
    private int mProgressColor;
    private int mBackgroundColor;
    private boolean isAnimating = false;
    private int mMaxValue;
    private int mValue;
    private int mTextSize;
    private int mTextColor;
    private boolean mRoundCap;
    private ValueAnimator mAnimator;
    private Paint mBackgroundPaint = new Paint();
    private Paint mPaint = new Paint();
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mArcOval = new RectF();
    private String mText = "";
    private int mStrokeWidth;
    private int mCircleRadius;
    private Point mCenterPoint;


    public UIProgressBar(Context context) {
        super(context);
        setup(context, null);
    }

    public UIProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public UIProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    public void setup(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UIProgressBar);
        mType = array.getInt(R.styleable.UIProgressBar_ui_type, TYPE_RECT);
        mProgressColor = array.getColor(R.styleable.UIProgressBar_ui_progress_color, DEFAULT_PROGRESS_COLOR);
        mBackgroundColor = array.getColor(R.styleable.UIProgressBar_ui_background_color, DEFAULT_BACKGROUND_COLOR);

        mMaxValue = array.getInt(R.styleable.UIProgressBar_ui_max_value, 100);
        mValue = array.getInt(R.styleable.UIProgressBar_ui_value, 0);

        mRoundCap = array.getBoolean(R.styleable.UIProgressBar_ui_stroke_round_cap, false);

        mTextSize = DEFAULT_TEXT_SIZE;
        if (array.hasValue(R.styleable.UIProgressBar_android_textSize)) {
            mTextSize = array.getDimensionPixelSize(R.styleable.UIProgressBar_android_textSize, DEFAULT_TEXT_SIZE);
        }
        mTextColor = DEFAULT_TEXT_COLOR;
        if (array.hasValue(R.styleable.UIProgressBar_android_textColor)) {
            mTextColor = array.getColor(R.styleable.UIProgressBar_android_textColor, DEFAULT_TEXT_COLOR);
        }

        if (mType == TYPE_CIRCLE) {
            mStrokeWidth = array.getDimensionPixelSize(R.styleable.UIProgressBar_ui_stroke_width, DEFAULT_STROKE_WIDTH);
        }
        array.recycle();
        configPaint(mTextColor, mTextSize, mRoundCap);

        setProgress(mValue);
    }

    private void configShape() {
        if (mType == TYPE_RECT || mType == TYPE_ROUND_RECT) {
            mBgRect = new RectF(getPaddingLeft(), getPaddingTop(), mWidth + getPaddingLeft(), mHeight + getPaddingTop());
            mProgressRect = new RectF();
        } else {
            mCircleRadius = (Math.min(mWidth, mHeight) - mStrokeWidth) / 2;
            mCenterPoint = new Point(mWidth / 2, mHeight / 2);
        }
    }

    private void configPaint(int textColor, int textSize, boolean isRoundCap) {
        mPaint.setColor(mProgressColor);
        mBackgroundPaint.setColor(mBackgroundColor);
        if (mType == TYPE_RECT || mType == TYPE_ROUND_RECT) {
            mPaint.setStyle(Paint.Style.FILL);
            mBackgroundPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setAntiAlias(true);
            if (isRoundCap) {
                mPaint.setStrokeCap(Paint.Cap.ROUND);
            }
            mBackgroundPaint.setStyle(Paint.Style.STROKE);
            mBackgroundPaint.setStrokeWidth(mStrokeWidth);
            mBackgroundPaint.setAntiAlias(true);
        }
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setType(int type) {
        mType = type;
        configPaint(mTextColor, mTextSize, mRoundCap);
        invalidate();
    }

    public void setBarColor(int backgroundColor, int progressColor) {
        mBackgroundColor = backgroundColor;
        mProgressColor = progressColor;
        mBackgroundPaint.setColor(mBackgroundColor);
        mPaint.setColor(mProgressColor);
        invalidate();
    }

    /**
     * 设置进度文案的文字大小
     *
     * @see #setTextColor(int)
     * @see #setUIProgressBarTextGenerator(UIProgressBarTextGenerator)
     */
    public void setTextSize(int textSize) {
        mTextPaint.setTextSize(textSize);
        invalidate();
    }

    /**
     * 设置进度文案的文字颜色
     *
     * @see #setTextSize(int)
     * @see #setUIProgressBarTextGenerator(UIProgressBarTextGenerator)
     */
    public void setTextColor(int textColor) {
        mTextPaint.setColor(textColor);
        invalidate();
    }

    /**
     * 设置环形进度条的两端是否有圆形的线帽，类型为{@link #TYPE_CIRCLE}时生效
     */
    public void setStrokeRoundCap(boolean isRoundCap) {
        mPaint.setStrokeCap(isRoundCap ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        invalidate();
    }

    /**
     * 通过 {@link UIProgressBarTextGenerator} 设置进度文案
     */
    public void setUIProgressBarTextGenerator(UIProgressBarTextGenerator UIProgressBarTextGenerator) {
        mUIProgressBarTextGenerator = UIProgressBarTextGenerator;
    }

    public UIProgressBarTextGenerator getUIProgressBarTextGenerator() {
        return mUIProgressBarTextGenerator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mUIProgressBarTextGenerator != null) {
            mText = mUIProgressBarTextGenerator.generateText(this, mValue, mMaxValue);
        }
        if (mType == TYPE_RECT) {
            drawRect(canvas);
        } else if(mType == TYPE_ROUND_RECT) {
            drawRoundRect(canvas);
        } else {
            drawCircle(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        configShape();
        setMeasuredDimension(mWidth, mHeight);
    }

    private void drawRect(Canvas canvas) {
        canvas.drawRect(mBgRect, mBackgroundPaint);
        mProgressRect.set(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + parseValueToWidth(), getPaddingTop() + mHeight);
        canvas.drawRect(mProgressRect, mPaint);
        if (mText != null && mText.length() > 0) {
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            float baseline = mBgRect.top + (mBgRect.height() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(mText, mBgRect.centerX(), baseline, mTextPaint);
        }
    }

    private void drawRoundRect(Canvas canvas) {
        float round = mHeight  / 2f;
        canvas.drawRoundRect(mBgRect, round, round, mBackgroundPaint);
        mProgressRect.set(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + parseValueToWidth(), getPaddingTop() + mHeight);
        canvas.drawRoundRect(mProgressRect, round, round, mPaint);
        if (mText != null && mText.length() > 0) {
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            float baseline = mBgRect.top + (mBgRect.height() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(mText, mBgRect.centerX(), baseline, mTextPaint);
        }
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mCircleRadius, mBackgroundPaint);
        mArcOval.left = mCenterPoint.x - mCircleRadius;
        mArcOval.right = mCenterPoint.x + mCircleRadius;
        mArcOval.top = mCenterPoint.y - mCircleRadius;
        mArcOval.bottom = mCenterPoint.y + mCircleRadius;
        canvas.drawArc(mArcOval, 270, 360 * mValue / mMaxValue, false, mPaint);
        if (mText != null && mText.length() > 0) {
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            float baseline = mArcOval.top + (mArcOval.height() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(mText, mCenterPoint.x, baseline, mTextPaint);
        }
    }

    private int parseValueToWidth() {
        return mWidth * mValue / mMaxValue;
    }

    public int getProgress() {
        return mValue;
    }

    public void setProgress(int progress) {
        setProgress(progress, true);
    }
    
    public void setProgress(int progress, boolean animated) {
        if (progress > mMaxValue || progress < 0) {
            return;
        }
        if (isAnimating) {
            isAnimating = false;
            mAnimator.cancel();
        }
        int oldValue = mValue;
        mValue = progress;
        if (animated) {
            startAnimation(oldValue, progress);
        } else {
            invalidate();
        }
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        mMaxValue = maxValue;
    }

    private void startAnimation(int start, int end) {
        mAnimator = ValueAnimator.ofInt(start, end);
        int duration = Math.abs(TOTAL_DURATION * (end - start) / mMaxValue);
        mAnimator.setDuration(duration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValue = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mAnimator.start();
    }

    public interface UIProgressBarTextGenerator {
        /**
         * 设置进度文案, {@link UIProgressBar} 会在进度更新时调用该方法获取要显示的文案
         * @param value 当前进度值
         * @param maxValue 最大进度值
         * @return 进度文案
         */
        String generateText(UIProgressBar progressBar, int value, int maxValue);
    }
}
