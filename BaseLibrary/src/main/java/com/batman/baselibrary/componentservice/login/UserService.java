package com.batman.baselibrary.componentservice.login;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author guqian
 */
public interface UserService extends IProvider {

    String getUserAccount(String account);

    void login(String phone,String password);
}

