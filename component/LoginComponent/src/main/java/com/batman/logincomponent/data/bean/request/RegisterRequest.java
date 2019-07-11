package com.batman.logincomponent.data.bean.request;

public class RegisterRequest {

    //* 手机号码
    public String phone;
    //* 短信验证码
    public String message;
    //* 登录密码
    public String pwd;
    //* 昵称
    public String name;
    //* 头像
    public String icon;

    //微信
    public String msgid;
    public String wx_openid;
}
