package com.utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.batman.baselibrary.R;
import com.network.utils.LogUtils;
import com.ui.widget.dialog.UIDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class MyDatePicker {
    //后面类型需要再增加
    public static int TYPE_DATE = 0;
    public static int TYPE_TIME = 1;
    public static int TYPE_DATE_TIME = 2;
    public static int TYPE_DATE_YEAR_MONTH = 3;
    Context context;
    int type;
    DateCallBack callBack;
    private String mDateValue, mTimeValue;
    public TimePickerDialog timePickerDialog;
    public DatePickerDialog datePickerDialog;
    public UIDialog yearMonthDialog;
    private static final int DEFAULT_MIN_YEAR = 2000;
    private int mYear, mMonth, mDay, mHour, mMinute;
    long minDate, maxDate;
    FragmentManager fragmentManager;
    final static String DATE_TAG = "DatePickerDialog";
    final static String TIME_TAG = "TimePickerDialog";

    public void showDateDialog(DateCallBack callBack) {
        this.callBack = callBack;
        createDialog();
        if (type == TYPE_DATE || type == TYPE_DATE_TIME) {
            datePickerDialog.show(fragmentManager, DATE_TAG);
        } else if (type == TYPE_TIME) {
            timePickerDialog.show(fragmentManager, TIME_TAG);
        } else if (type == TYPE_DATE_YEAR_MONTH) {
            yearMonthDialog.show();
        }
    }

    public interface DateCallBack {
        void onDateSet(String date);
    }

    public interface YearMonthDateCallBack {
        void onDateSet(int year, int month);
    }

    public MyDatePicker(Context context, int type) {
        this.context = context;
        this.type = type;
        fragmentManager = ((Activity) context).getFragmentManager();
        init();
    }

    public void setMaxDate(long value) {
        this.maxDate = value;
    }

    public void setMinDate(long value) {
        this.minDate = value;
    }

    private void init() {
        Calendar mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DATE);
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
    }

    private void createDialog() {
        if (type == TYPE_DATE || type == TYPE_DATE_TIME) {
            datePickerDialog = createDatePicker();
            timePickerDialog = createTimePicker();
        } else if (type == TYPE_TIME) {
            timePickerDialog = createTimePicker();
        }
    }

    private String getFixData(int data) {
        return data < 10 ? "0" + data : data + "";
    }


    public TimePickerDialog createTimePicker() {
        TimePickerDialog tpd = null;
        if (timePickerDialog == null) {
            tpd = TimePickerDialog.newInstance(
                    timeSetListener,
                    mHour,
                    mMinute,
                    true
            );
        } else {
            tpd = timePickerDialog;
            timePickerDialog.initialize(timeSetListener,
                    mHour,
                    mMinute,
                    1,
                    true);
        }
        tpd.vibrate(false);
        tpd.dismissOnPause(false);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        return tpd;
    }

    public DatePickerDialog createDatePicker() {
        DatePickerDialog dpd = null;
        if (datePickerDialog == null) {
            dpd = DatePickerDialog.newInstance(dateSetListener, mYear, mMonth, mDay);
        } else {
            datePickerDialog.initialize(dateSetListener, mYear, mMonth, mDay);
            dpd = datePickerDialog;
        }
        dpd.dismissOnPause(true);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        dpd.vibrate(false);
        Calendar calendar = Calendar.getInstance();
        if (minDate != 0) {
            calendar.setTimeInMillis(minDate);
            dpd.setMinDate(calendar);
        }
        if (maxDate != 0) {
            calendar.setTimeInMillis(maxDate);
            dpd.setMaxDate(calendar);
        }
        //dialog.setTitle("");
        return dpd;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            LogUtils.d("datePicker", "year:" + year + "month:" + monthOfYear + "day" + dayOfMonth);
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            mDateValue = year + "-" + getFixData(monthOfYear + 1) + "-" + getFixData(dayOfMonth);
            if (type == TYPE_DATE) {
                callBack.onDateSet(mDateValue);

            } else if (type == TYPE_DATE_TIME) {

                timePickerDialog.show(fragmentManager, "timePickerDialog");
            }
        }
    };
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
            LogUtils.d("datePicker", "hourOfDay:" + hourOfDay + "minute:" + minute);
            mHour = hourOfDay;
            mMinute = minute;
            mTimeValue = getFixData(hourOfDay) + ":" + getFixData(minute);
            if (!TextUtils.isEmpty(mDateValue)) {
                callBack.onDateSet(mDateValue + " " + mTimeValue);
            } else {
                callBack.onDateSet(mTimeValue);
            }
        }
    };
}
