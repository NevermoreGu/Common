package com.batman.baselibrary;

public class RouterConstants {


    public static final String COMMON_MAIN_PATH = "/main";
    public static final String COMMON_MAIN_SERVICE = COMMON_MAIN_PATH + "/service";

    /**
     * 登录
     */
    public static final String LOGIN_COMPONENT_PATH = "/login";
    //登录
    public static final String LOGIN_COMPONENT_LOGIN_PATH = LOGIN_COMPONENT_PATH + "/login";
    public static final String LOGIN_COMPONENT_SERVICE = LOGIN_COMPONENT_PATH + "/service";

    //注册
    public static final String LOGIN_COMPONENT_REGISTER_PATH = LOGIN_COMPONENT_PATH + "/register";
    //找回密码
    public static final String LOGIN_COMPONENT_FIND_PASSWORD_PATH = LOGIN_COMPONENT_PATH + "/find_password";
    //重置密码
    public static final String LOGIN_COMPONENT_RESET_PASSWORD_PATH = LOGIN_COMPONENT_PATH + "/reset_password";
    public static final String LOGIN_COMPONENT_NEW_DEVICE_LOGIN_PATH = LOGIN_COMPONENT_PATH + "/new_device_login";


    public static final String KEY_PHONE = "KEY_PHONE";
    public static final String KEY_MESSAGE_ID = "KEY_MESSAGE_ID";
    public static final String KEY_ACCID = "KEY_ACCID";
    public static final String KEY_TOKEN = "KEY_TOKEN";


    /**
     * IM
     */
    public static final String IM_COMPONENT_PATH = "/IM";
    //黑名单
    public static final String IM_COMPONENT_BLACK_LIST_PATH = IM_COMPONENT_PATH + "/black_list";
    public static final String IM_COMPONENT_COMMON_SETTING_PATH = IM_COMPONENT_PATH + "/common_setting";
    //首页
    public static final String IM_COMPONENT_MAIN_PATH = IM_COMPONENT_PATH + "/main";
    public static final String IM_COMPONENT_SERVICE = IM_COMPONENT_PATH + "/service";

    //添加好友
    public static final String IM_COMPONENT_ADD_FRIEND_PATH = IM_COMPONENT_PATH + "/add_friend";
    //搜索好友
    public static final String IM_COMPONENT_SEARCH_FRIEND_PATH = IM_COMPONENT_PATH + "/search_friend";

    /**
     * COMMON
     */
    public static final String COMMON_COMPONENT_PATH = "/common";
    public static final String COMMON_COMPONENT_PATH_SETTING = COMMON_COMPONENT_PATH + "/setting";
}
