package com.share.weixin.login;

import com.share.weixin.ShareApp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

public class WeiXinLoginUtils {


    public static void login() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        ShareApp.getApi().sendReq(req);
    }
}
