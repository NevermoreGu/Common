package com.batman.logincomponent;

import com.batman.baselibrary.base.BaseApplication;
import com.batman.baselibrary.delegate.ApplicationDelegate;

public class LoginApplication extends ApplicationDelegate {

    @Override
    public int getLevel() {
        return LEVEL_COMPONENT;
    }

    @Override
    public Class[] subDelegates() {
        return new Class[]{BaseApplication.class};
    }

    @Override
    public void onCreateDelegate() {

    }
}
