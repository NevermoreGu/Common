package com.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;


import com.ui.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class UISendValidateButton extends AppCompatTextView {

    public static final int DISABLE_TIME = 90;
    private static final int MSG_TICK = 0;
    private Timer mTimer = null;
    private TimerTask mTask = null;
    private int mDisableTime = DISABLE_TIME; // 倒计时时间，默认60秒
    private String mEnableString;
    private String mDisableString = "后重新获取";
    private String Second;
    private boolean mClickBle = true;
    private SendValidateButtonListener mListener = null;

    private Drawable defaultDrawable;
    private Drawable disAbleDrawable;
    private int disAbleColor;
    private int defaultColor;
    private Context mContext;
    private MyHandler mHandler;

    public int getDisableTime() {
        return mDisableTime;
    }

    public String getEnableString() {
        return mEnableString;
    }

    public boolean isDisable() {
        return this.isEnabled();
    }

    public void setEnableString(String mEnableString) {

        this.mEnableString = mEnableString;

        if (this.mEnableString != null) {

            this.setText(mEnableString);
        }
    }

    public String getDisableString() {
        return mDisableString;
    }

    public void setDisableString(String mDisableString) {

        this.mDisableString = mDisableString;

    }

    public void setListener(SendValidateButtonListener mListener) {
        this.mListener = mListener;
    }

    public UISendValidateButton(Context context) {
        this(context, null);
    }

    public UISendValidateButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UISendValidateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context, attrs, defStyleAttr);
    }

    public class MyHandler extends Handler {

        private final WeakReference<Activity> mTarget;

        MyHandler(Activity target) {
            mTarget = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity target = mTarget.get();
            if (target != null) {
                switch (msg.what) {

                    case MSG_TICK:

                        tickWork();

                        break;

                    default:

                        break;
                }
            }
        }
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.UISendValidateButton, defStyleAttr, 0);
        defaultDrawable = a.getDrawable(R.styleable.UISendValidateButton_ui_defaultDrawable);
        disAbleDrawable = a.getDrawable(R.styleable.UISendValidateButton_ui_disAblDrawable);
        a.recycle();
        defaultColor = getResources().getColor(R.color.ui_config_color_main);
        disAbleColor = getResources().getColor(R.color.ui_config_color_gray_6);
        mEnableString = mContext.getString(R.string.send_code);
        this.setText(mEnableString);
        this.setGravity(Gravity.CENTER);
        if (defaultDrawable != null) {
            this.setBackgroundDrawable(defaultDrawable);

        }
        this.setTextColor(defaultColor);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null && mClickBle) {
                    mListener.onClickSendValidateButton();
                }
            }
        });
        initTimer();
        mHandler = new MyHandler((Activity) mContext);
        Second = mContext.getString(R.string.second);
    }

    private void initTimer() {
        mTimer = new Timer();
    }

    private void initTimerTask() {
        if (mTask != null) {
            mTask.cancel();
        }
        mTask = new SendValidateTimerTask((Activity) mContext);
    }

    public class SendValidateTimerTask extends TimerTask {

        private final WeakReference<Activity> mTarget;

        SendValidateTimerTask(Activity target) {
            mTarget = new WeakReference<>(target);
        }

        @Override
        public void run() {
            Activity target = mTarget.get();
            if (target != null) {
                mHandler.sendEmptyMessage(MSG_TICK);
            }
        }
    }

    /**
     * 发送请求成功后再开设倒计时
     */
    public void startTickWork() {
        if (mClickBle) {
            mClickBle = false;
            if (disAbleDrawable != null) {
                this.setBackgroundDrawable(disAbleDrawable);
            }
            this.setTextColor(disAbleColor);
            initTimerTask();
            UISendValidateButton.this.setText(mDisableTime
                    + Second + mDisableString);
            this.setEnabled(false);
            mTimer.schedule(mTask, 0, 1000);
        }
    }

    /**
     * 每秒钟调用一次
     */
    private void tickWork() {

        mDisableTime--;
        this.setText(mDisableTime
                + Second + mDisableString);

        if (mListener != null) {
            mListener.onTick();
        }
        if (mDisableTime <= 0) {
            stopTickWork();
        }
    }

    public void stopTickWork() {
        stopTask();
        mDisableTime = DISABLE_TIME;
        this.setText(mEnableString);
        this.setEnabled(true);
        if (defaultDrawable != null) {
            this.setBackgroundDrawable(defaultDrawable);

        }
        this.setTextColor(defaultColor);
        mClickBle = true;
    }

    public void stopTask() {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
    }

    public interface SendValidateButtonListener {

        void onClickSendValidateButton();

        void onTick();
    }
}