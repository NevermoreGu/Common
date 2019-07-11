package com.batman.logincomponent.api;


import com.batman.baselibrary.utils.UrlUtils;
import com.batman.logincomponent.data.bean.request.CheckVerifyRequest;
import com.batman.logincomponent.data.bean.request.CodeLoginRequest;
import com.batman.logincomponent.data.bean.request.NewDeviceLoginRequest;
import com.batman.logincomponent.data.bean.request.RegisterRequest;
import com.batman.logincomponent.data.bean.request.ResetPasswordRequest;
import com.batman.logincomponent.data.bean.request.UserRequest;
import com.batman.logincomponent.data.bean.request.VerifyPhoneRequest;
import com.batman.logincomponent.data.bean.request.VerifyRequest;
import com.batman.logincomponent.data.bean.request.WxLoginRequest;

import java.util.HashMap;

public class ApiParams {


    /**
     * 接口前缀
     */
    public static final String QUERY_LOGIN = "apis/user/newLogin";
    public static final String QUERY_GET_VERIFY = "apis/msg/getverify";
    public static final String QUERY_LOGIN_REGISTER = "apis/user/create";
    public static final String QUERY_CHECK_VERIFY = "apis/msg/checkVertifyMsg";
    public static final String QUERY_LOGIN_RESET_PASSWORD = "apis/user/resetLoginPwd";
    public static final String QUERY_LOGIN_NEW_DEVICE = "apis/user/loginValidateMsg";
    public static final String QUERY_WX_LOGIN = "apis/user/wxopenidLogin";
    public static final String QUERY_VERIFY_PHONE = "apis/user/verifyPhone";
    public static final String QUERY_CODE_LOGIN = "apis/user/codeLogin";


    /**
     * 登录
     *
     * @return
     */
    public static HashMap getLoginRequestParams(UserRequest request) {

        return UrlUtils.getEncodeParam(request);
    }

    /**
     * 获取短信验证码
     *
     * @return
     */
    public static HashMap getVerifyParams(VerifyRequest request) {

        return UrlUtils.getEncodeParam(request);
    }


    /**
     * 校验短信验证码
     *
     * @return
     */
    public static HashMap checkVerifyParams(CheckVerifyRequest request) {

        return UrlUtils.getEncodeParam(request);
    }


    /**
     * 注册
     *
     * @param request
     * @return
     */
    public static HashMap getRegisterParams(RegisterRequest request) {

        return UrlUtils.getEncodeParam(request);
    }


    /**
     * 重置密码
     *
     * @param request
     * @return
     */
    public static HashMap getResetPasswordParams(ResetPasswordRequest request) {

        return UrlUtils.getEncodeParam(request);
    }

    /**
     * 登录设备号改变
     *
     * @param request
     * @return
     */
    public static HashMap getNewDeviceLoginParams(NewDeviceLoginRequest request) {

        return UrlUtils.getEncodeParam(request);
    }


    /**
     * 微信授权登录
     *
     * @param request
     * @return
     */
    public static HashMap getWXLoginParams(WxLoginRequest request) {

        return UrlUtils.getEncodeParam(request);
    }

    public static HashMap getVerifyPhoneParams(VerifyPhoneRequest request) {

        return UrlUtils.getEncodeParam(request);
    }

    public static HashMap getCodeLoginParams(CodeLoginRequest request) {

        return UrlUtils.getEncodeParam(request);
    }


}
