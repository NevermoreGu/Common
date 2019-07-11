package com.batman.logincomponent.ui.activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.batman.baselibrary.base.BaseActivity;
import com.batman.baselibrary.componentservice.im.IMService;
import com.batman.baselibrary.preference.UserPres;
import com.batman.baselibrary.preference.UserResult;
import com.network.Resource;

public abstract class BaseLoginActivity extends BaseActivity {

    protected void loginSuccess(Resource<UserResult> resource) {
        //保存数据
        UserPres.save(resource);

        String account = resource.data.yxuser.accid;
        String token = resource.data.yxuser.token;
        nimUIKitLogin(account, token);
    }

    /**
     * 请求网易云信登录
     *
     * @param account
     * @param token
     */
    protected void nimUIKitLogin(String account, String token) {

        ARouter.getInstance().navigation(IMService.class).nimLogin(account, token, this);
    }
}
