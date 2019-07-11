package com.batman.logincomponent.data.bean.request;

public class ResetPasswordRequest {

    //* 手机号码
    public String phone;
    //* 短信验证码ID
    public String msgid;
    //* 登录密码
    public String pwd;
    //* 用户账户
    public String yx_username;
    //* token
    public String yx_usertoken;
}
