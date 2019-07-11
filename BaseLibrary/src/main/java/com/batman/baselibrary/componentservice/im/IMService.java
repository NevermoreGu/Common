package com.batman.baselibrary.componentservice.im;

import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author guqian
 */
public interface IMService extends IProvider {

    /**
     * 下线通知
     */
    void kickout(Intent intent, Context context);

    void nimLogin(String account, String token, Context context);

    void mainLogin(String account, String token, Context context);

    void logout(Context context);

}

