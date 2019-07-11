package com.batman.logincomponent.data.bean.request;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.batman.logincomponent.data.bean.request.CheckVERIFY.TYPE_BIND;
import static com.batman.logincomponent.data.bean.request.CheckVERIFY.TYPE_PAY;
import static com.batman.logincomponent.data.bean.request.CheckVERIFY.TYPE_REGISTER;
import static com.batman.logincomponent.data.bean.request.CheckVERIFY.TYPE_RESET_PASSWORD;
import static com.batman.logincomponent.data.bean.request.CheckVERIFY.TYPE_RESET_PAY_PASSWORD;

@StringDef({TYPE_REGISTER, TYPE_PAY, TYPE_BIND, TYPE_RESET_PAY_PASSWORD, TYPE_RESET_PASSWORD})
@Retention(RetentionPolicy.SOURCE)
public @interface CheckVERIFY {

    //0注册 1消费 2绑定 3重置支付密码 4重置登录密码
    public static final String TYPE_REGISTER = "0";
    public static final String TYPE_PAY = "1";
    public static final String TYPE_BIND = "2";
    public static final String TYPE_RESET_PAY_PASSWORD = "3";
    public static final String TYPE_RESET_PASSWORD = "4";
}
