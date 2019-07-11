package com.batman.logincomponent.data.bean.request;

public class UserRequest {

    //*手机号码
    public String login_account;
    //*登录密码
    public String pwd;
    //是否第一次登录 0:否 1:是{注册成功第一次登录要传，其他登录可以不传}
    public String firstLogin;
    //*设备号
    public String deviceToken;
    public String deviceName;

}
