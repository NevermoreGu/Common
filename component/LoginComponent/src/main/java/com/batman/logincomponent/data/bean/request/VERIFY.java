package com.batman.logincomponent.data.bean.request;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.batman.logincomponent.data.bean.request.VERIFY.TYPE_BIND;
import static com.batman.logincomponent.data.bean.request.VERIFY.TYPE_CHANGE_CODE;
import static com.batman.logincomponent.data.bean.request.VERIFY.TYPE_PAY;
import static com.batman.logincomponent.data.bean.request.VERIFY.TYPE_REGISTER;
import static com.batman.logincomponent.data.bean.request.VERIFY.TYPE_RESET_PASSWORD;

@StringDef({TYPE_REGISTER, TYPE_BIND, TYPE_PAY, TYPE_RESET_PASSWORD, TYPE_CHANGE_CODE})
@Retention(RetentionPolicy.SOURCE)
public @interface VERIFY {

    public static final String TYPE_REGISTER = "0";
    public static final String TYPE_BIND = "2";
    public static final String TYPE_PAY = "3";
    public static final String TYPE_RESET_PASSWORD = "4";
    public static final String TYPE_CHANGE_CODE = "5";
}
