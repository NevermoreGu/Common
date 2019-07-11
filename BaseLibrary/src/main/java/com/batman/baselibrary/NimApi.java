/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.batman.baselibrary;

/**
 * 接口地址
 */
public class NimApi {

    public static boolean isServerDebug() {
        return true;
    }

    /* 友赞小助手accid （固定死的） */
    private static final String ASSISTANT_ACCID_DEBUG = "a91a3ad9670b55af20a67177ef851adb";//测试
    private static final String ASSISTANT_ACCID_RELEASE = "625c6fbb82aa7e488e903b42b2f0b041";//生产
    public static final String ASSISTANT_ACCID = isServerDebug() ? ASSISTANT_ACCID_DEBUG : ASSISTANT_ACCID_RELEASE;

    /* 支付小助手accid （固定死的） */
    private static final String CONSUME_ASSISTANT_ACCID_DEBUG = "558ad7f46022e0a947262113218690ee";//测试
    private static final String CONSUME_ASSISTANT_ACCID_RELEASE = "1ea0b99a271cb67d2454d886ef5dd70b";//生产
    public static final String CONSUME_ASSISTANT_ACCID = isServerDebug() ? CONSUME_ASSISTANT_ACCID_DEBUG : CONSUME_ASSISTANT_ACCID_RELEASE;

    /* 主域名 */
    // private static final String IP_ADDRESS_DEBUG = "http://testwallet.youapp.cn:8082";//测试

    private static final String IP_ADDRESS_DEBUG = "https://api.sit.youxinchat.com:8443";//测试
    //    private static final String IP_ADDRESS_DEBUG = "https://47.110.5.238:8443";//测试
    private static final String IP_ADDRESS_RELEASE = "https://api.youxinchat.com";//生产

    private static final String IP_ADDRESS = isServerDebug() ? IP_ADDRESS_DEBUG : IP_ADDRESS_RELEASE;

    private static final String WEB_ADDRESS_DEBUG = "http://wap.youxinchat.com";//测试
    private static final String WEB_ADDRESS_RELEASE = "http://wap.youxinchat.com";//运营
    private static final String WEB_ADDRESS = isServerDebug() ? WEB_ADDRESS_DEBUG : WEB_ADDRESS_RELEASE;


    public static final String WEB_HELP = WEB_ADDRESS + "/support.html#/";
    public static final String WEB_BANK_HELP = WEB_ADDRESS + "/support.html#/list/card";
    public static final String WEB_USER_SERVICE = WEB_ADDRESS + "/user_agreement.html";
    public static final String WEB_BANK_SUPPORT = WEB_ADDRESS + "/support.html#/detail/card/1";


    public static final String QRCODE = WEB_ADDRESS + "/youxinweb/";


    /* BASE */
    //private static final String BASE_PATH = "/youxin/";
    private static final String BASE_PATH = "/";
    public static final String URL_BASE = IP_ADDRESS + BASE_PATH + "apis/";


    /**
     * *********************************** 登录注册相关 ***********************************
     */

    /* 获取验证码 */
    public static final String URL_VERIFY = URL_BASE + "msg/getverify";

    /* 注册 */
    public static final String URL_REGIST = URL_BASE + "user/create";

    /* 登录 */
    public static final String URL_LOGIN = URL_BASE + "user/login";

    /* 登录（新接口） */
    public static final String URL_NEW_LOGIN = URL_BASE + "user/newLogin";
    /*微信登录 */
    public static final String URL_WECHAT_LOGIN = URL_BASE + "user/wxopenidLogin";
    /*第三方登录绑定 */
    public static final String URL_THIRD_LOGIN = URL_BASE + "user/codeLogin";
    /* 第三方登录验证手机号 */
    public static final String URL_THIRD_LOGIN_VERIFY_PHONE = URL_BASE + "user/verifyPhone";
    /* 校验验证码(重置登录密码) */
    public static final String URL_CHECKRESETPWDMSG = URL_BASE + "msg/checkVertifyMsg";

    /* 重置登录密码 */
    public static final String URL_RESETLOGINPWD = URL_BASE + "user/resetLoginPwd";

    /* 搜索好友 */
    public static final String URL_SEARCH_FRIEND = URL_BASE + "friend/search";

    /* 搜索好友（新接口） */
    public static final String URL_SEARCH_FRIEND_NEW = URL_BASE + "friend/searchAccid";

    /* 查找手机通讯录 */
    public static final String URL_MOBILE_CONTACT = URL_BASE + "friend/searchAccids";

    /* 登录设备号改变验证短信验证码 */
    public static final String URL_VERIFY_PHONE = URL_BASE + "user/loginValidateMsg";

    /* 游戏列表 */
    public static final String GAME_LIST = URL_BASE + "application/searchAppDetail";
    /* 搜索应用 */
    public static final String SEARCH_APP = URL_BASE + "application/searchAppDetailByWord";

    /**
     * *********************************** 红包相关 ***********************************
     */


    /* 个人红包发送 */
    @Deprecated
    public static final String URL_SEND_RED_PACKET_SINGLE = URL_BASE + "redpackage/normalsend";
    /* 个人红包发送 */
    public static final String URL_SEND_RED_PACKET_SINGLE_FROM_SERVICE = URL_BASE + "redpackage/normalsendNew";
    /* 个人支付宝红包发送 */
    public static final String URL_SEND_ALIPAY_RED_PACKET_SINGLE_FROM_SERVICE = URL_BASE + "alipay_redpackage/normalsend";
    /* 群红包发送 */
    @Deprecated
    public static final String URL_SEND_RED_PACKET_TEAM = URL_BASE + "redpackage/groupsend";
    /* 群红包发送 */
    public static final String URL_SEND_RED_PACKET_TEAM_FROM_SERVICE = URL_BASE + "redpackage/groupsendNew";
    /* 支付宝群红包发送 */
    public static final String URL_SEND_ALIPAY_RED_PACKET_TEAM_FROM_SERVICE = URL_BASE + "alipay_redpackage/groupsend";
    /* 定向红包发送 */
    @Deprecated
    public static final String URL_SEND_RED_PACKET_DIRECTIONAL = URL_BASE + "redpackage/directionalsend";
    /* 定向红包发送 */
    public static final String URL_SEND_RED_PACKET_DIRECTIONAL_FROM_SERVICE = URL_BASE + "redpackage/directionalsendNew";
    /* 支付宝定向红包发送 */
    public static final String URL_SEND_ALIPAY_RED_PACKET_DIRECTIONAL_FROM_SERVICE = URL_BASE + "alipay_redpackage/directionalsend";
    /* alipay */
    public static final String URL_ALIPAY = URL_BASE + "pay/alipayprecharge";
    /* alipay new */
    public static final String URL_ALIPAY_NEW = URL_BASE + "pay/alipayprechargeNew";
    /* wxpay */
    public static final String URL_WXPAY = URL_BASE + "pay/wxpayprecharge";
    /* jdpay */
    public static final String URL_JDPAY = URL_BASE + "pay/jdpayprecharge";
    /* 银行卡支付下单*/
    public static final String URL_BANK_PAY_REQ_ORDER = URL_BASE + "pay/bankPay/bankBingPayorRecharge";
    /* 银行卡支付发短信*/
    public static final String URL_BANK_PAY_SMS = URL_BASE + "pay/bankPay/unionMerSendSms";
    /* 银行卡支付 */
    public static final String URL_BANK_PAY = URL_BASE + "pay/bankPay/unionMerConfirmPay";
    /* 支付结果查询 */
    public static final String URL_CHECK_PAY_STATUS = URL_BASE + "pay/checkPrechargeStatus";
    /* 提现 */
    public static final String URL_WITH_DRAW_DEPOSIT = URL_BASE + "withdraw/applyWithdraw";
    /* 提现到银行卡 */
    public static final String URL_WITH_DRAW_DEPOSIT_TO_BANK = URL_BASE + "withdraw/applyWithdrawByBank";
    /* 获取服务器时间 */
    public static final String URL_GET_SERVICE_TIME = URL_BASE + "withdraw/getSystemTime";
    /* mob 获取银行卡信息 */
    public static final String URL_GET_BANK_CARD_INFO = "http://apicloud.mob.com/appstore/bank/card/query";
    /* 获取银行卡信息 */
    public static final String URL_GET_BANK_CARD_INFO_FROM_SERVICE = URL_BASE + "bankcard/checkCard";
    /* 获取用户银行卡相关信息 */
    public static final String URL_GET_USER_INFO = URL_BASE + "bankcard/queryUserinfo";
    /* 创建银行卡信息 */
    public static final String URL_CREATE_BANK_CARD = URL_BASE + "bankcard/createBankCard";
    /* 获取用户银行卡列表 */
    public static final String URL_QUERY_USER_BANK_CARD = URL_BASE + "bankcard/qusryMyBankCard";
    /* 解绑银行卡 */
    public static final String URL_UNTYING_BANK_CARD = URL_BASE + "bankcard/untieBankCard";
    /* 余额消费 */
    public static final String URL_BALANCE_CONSUME = URL_BASE + "consume/pay";
    /* 充值提现配置信息 */
    public static final String URL_RECHARGE_AND_WITHDRAW_CONFIG = URL_BASE + "payConfig/queryPayConfig";
    /* 解绑 */
    public static final String URL_UNTIED_PAYMENT = URL_BASE + "untyingWxOrAli";
    /**
     * *********************************** 个人中心 ***********************************
     */


    /*更新用户信息*/
    public static final String URL_UPDATE_USERINFO = URL_BASE + "user/updateUinfo";
    /*微信授权*/
    public static final String URL_WECHAT_AUTH = URL_BASE + "user/updateWxInfo";
    /*验证原始登录密码*/
    public static final String URL_CHECK_LOGIN_PWD = URL_BASE + "user/checkLoginPwd";
    /*修改登录密码*/
    public static final String URL_MODIFY_LOGIN_PWD = URL_BASE + "user/modifyLoginPwd";
    /*发起群AA*/
    public static final String URL_SEND_GROUP_AA = URL_BASE + "groupaa/start";
    /*显示群AA支付或收款*/
    public static final String URL_GET_GROUP_AA_INFO = URL_BASE + "groupaa/groupAaShow";
    /*群AA收款*/
    public static final String URL_GROUP_AA_PAYEE = URL_BASE + "groupaa/groupAaPayee";
    /*检测更新*/
    public static final String URL_UPDATE_APP_UPGRADE = URL_BASE + "user/updateAppupgrade";
    /*群AA支付*/
    public static final String URL_GET_GROUP_AA_PAY = URL_BASE + "groupaa/groupAaPay";
    /*更改手机号搜索按钮*/
    public static final String URL_UPDATE_SEARCH_FLAG = URL_BASE + "user/updateIsSerarch";
    /*更改友赞号搜索按钮*/
    public static final String URL_UPDATE_SEARCH_CXY = URL_BASE + "user/updateCxNoIsSerarch";
    /*更换手机号*/
    public static final String URL_CHANGE_MOBILE = URL_BASE + "user/changePhone";
    /*钱包明细*/
//    public static final String URL_WALLET_DETAILS = URL_BASE + "pay/walletDetail";
    public static final String URL_WALLET_DETAILS = URL_BASE + "pay/newWalletDetail";
    /*账单详情*/
    public static final String URL_BILL_DETAILS = URL_BASE + "pay/showWalletDetailPage";
    /*筛选选项*/
    public static final String URL_FILTER_OPTIONS = URL_BASE + "pay/showDetailType";
    /*筛选账单*/
//    public static final String URL_FILTER_BILL = URL_BASE + "pay/showWalletDetailByType";
    public static final String URL_FILTER_BILL = URL_BASE + "pay/showWalletDetailByType";
    /*删除钱包明细*/
//    public static final String URL_DELETE_WALLET_DETAILS = URL_BASE + "pay/deleteWalletDetail";
    public static final String URL_DELETE_WALLET_DETAILS = URL_BASE + "pay/deleteNewWalletDetail";
    /*钱包余额*/
    public static final String URL_WALLET_BALANCE = URL_BASE + "pay/showWalletBalance";
    /*意见反馈*/
    public static final String URL_FEED_BACK = URL_BASE + "feedback/saveFeedback";
    public static final String URL_TONG_SIGN = URL_BASE + "withdraw/signContracturl";
    public static final String URL_GET_SIGN = URL_BASE + "user/realnameInfo";
    /*获取已删消息*/
    public static final String URL_GET_DELETED_MESSAGES = URL_BASE + "msg/showcloudnews";
    /*上传已删消息*/
    public static final String URL_UPLOAD_DELETED_MESSAGES = URL_BASE + "msg/savecloudnews";
    /*各种开关*/
    public static final String URL_CX_APP_SWITCH = URL_BASE + "application/searchCxswitch";
    /*设置友赞号*/
    public static final String URL_CREATE_CXH = URL_BASE + "user/createCxNo";
    /*二维码登录*/
    public static final String URL_QRCODE_LOGIN = URL_BASE + "cxweb/weblogin";
    /*验证支付密码*/
    public static final String URL_VERIFY_PAYPWD = URL_BASE + "user/checkPayPwd";
    /*保存群聊信息*/
    public static final String URL_SAVE_TEAM_INFO = URL_BASE + "team/saveTeam";
    /*获取广告列表*/
    public static final String URL_GET_ADVERTISEMENT = URL_BASE + "ads/showAds";
    /*获取群状态*/
    public static final String URL_QUERY_TEAM_STATE = URL_BASE + "team/teamIsForbid";
    /*提交举报投诉*/
    public static final String URL_SUBMIT_COMPLAINT = IP_ADDRESS + BASE_PATH + "backyxuser/userComplaint";
    /**
     * Soter
     */
    /*是否支持soter*/
    public static final String URL_SUPPORT_SOTER = URL_BASE + "fingerpay/android/is_support";
    /*ASK*/
    public static final String URL_VERIFY_ASK = URL_BASE + "fingerpay/android/verify_ask";
    /*AUTHKEY*/
    public static final String URL_VERIFY_AUTHKEY = URL_BASE + "fingerpay/android/verify_authKey";
    /*获取挑战因子*/
    public static final String URL_GET_CHALLENGE = URL_BASE + "fingerpay/android/getChallenge";
    /*验证最终签名*/
    public static final String URL_VERIFY_SIGNATURE = URL_BASE + "fingerpay/android/verify_final_signature";
    /*是否开通指纹支付*/
    public static final String URL_FINGERPAY_ISBINDING = URL_BASE + "fingerpay/android/isbinding";
    /*关闭指纹支付功能*/
    public static final String URL_FINGERPAY_CLOSE = URL_BASE + "fingerpay/android/close";
    /*个人红包发送*/
    public static final String URL_FINGERPAY_NORMALSEND = URL_BASE + "fingerpay/android/normalsend";
    /*群红包发送*/
    public static final String URL_FINGERPAY_GROUPSEND = URL_BASE + "fingerpay/android/groupsend";
    /*定向红包发送*/
    public static final String URL_FINGERPAY_DIRECTIONALSEND = URL_BASE + "fingerpay/android/directionalsend";
    /*申请提现*/
    public static final String URL_FINGERPAY_APPLYWITHDRAW = URL_BASE + "fingerpay/android/applyWithdraw";
    /*V1.4申请提现（银行卡）*/
    public static final String URL_FINGERPAY_APPLYWITHDRAWBYBANK = URL_BASE + "fingerpay/android/applyWithdrawByBank";
    /*余额消费支付*/
    public static final String URL_FINGERPAY_CONSUME_PAY = URL_BASE + "fingerpay/android/consume/pay";


    /**
     * *********************************** SDK ***********************************
     */

    /*分享*/
    public static final String URL_ACTION_SHARE = URL_BASE + "user/share";

    /*授权登录*/
    public static final String URL_AUTH_LOGIN = URL_BASE + "user/sdklogin";
}
