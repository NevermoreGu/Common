package com.batman.logincomponent;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.batman.baselibrary.RouterConstants;
import com.batman.baselibrary.componentservice.login.UserService;

@Route(path = RouterConstants.LOGIN_COMPONENT_SERVICE)
public class LoginImpl implements UserService {


    @Override
    public String getUserAccount(String account) {
        return null;
    }

    @Override
    public void login(String phone, String password) {

    }

    @Override
    public void init(Context context) {

    }
}
