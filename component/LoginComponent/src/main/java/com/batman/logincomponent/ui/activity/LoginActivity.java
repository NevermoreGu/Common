package com.batman.logincomponent.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.batman.baselibrary.RouterConstants;
import com.batman.baselibrary.api.ResponseCode;
import com.batman.baselibrary.base.BaseObserver;
import com.batman.baselibrary.preference.UserPres;
import com.batman.baselibrary.preference.UserResult;
import com.batman.baselibrary.utils.ActivityUtils;
import com.batman.baselibrary.utils.PhoneUtil;
import com.batman.baselibrary.utils.ToastUtils;
import com.batman.logincomponent.R;
import com.batman.logincomponent.R2;
import com.batman.logincomponent.api.ApiParams;
import com.batman.logincomponent.data.bean.request.UserRequest;
import com.batman.logincomponent.data.bean.request.WxLoginRequest;
import com.batman.logincomponent.viewmodel.LoginViewModel;
import com.network.Resource;
import com.network.utils.MD5;
import com.share.weixin.login.WeiXinLoginUtils;
import com.ui.widget.UINavigationView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录页
 *
 * @author guqian
 */
@Route(path = RouterConstants.LOGIN_COMPONENT_LOGIN_PATH)
public class LoginActivity extends BaseLoginActivity {

    private static final String KICK_OUT = "KICK_OUT";

    @BindView(R2.id.uinv)
    UINavigationView mUinv;
    @BindView(R2.id.et_login_phone_number)
    EditText mEtLoginPhoneNumber;
    @BindView(R2.id.et_login_user_password)
    EditText mEtLoginUserPassword;

    private LoginViewModel mViewModel;

    private String mPhone;
    private String mPassword;

    public static void start(Context context) {
        start(context, false);
    }

    public static void start(Context context, boolean kickOut) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KICK_OUT, kickOut);
        ActivityUtils.openActivitySingleTop(RouterConstants.LOGIN_COMPONENT_LOGIN_PATH, bundle);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        mUinv.setNavigationTitle(R.string.text_login_title);
        mUinv.setNavigationRightText(R.string.text_register, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.openActivity(RouterConstants.LOGIN_COMPONENT_REGISTER_PATH);
            }
        });

        String phone = UserPres.getInstance().phone;
        mEtLoginPhoneNumber.setText(phone);
        mEtLoginUserPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEtLoginPhoneNumber.clearFocus();
                }
            }
        });
    }

    @Override
    public void loadData(Bundle savedInstanceState) {
        registerThreeLoginBroadcastReceiver();
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }


    @OnClick({R2.id.btn_login_login, R2.id.tv_login_forget_passsword, R2.id.img_login_wei_chat})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_login_login) {
            login();
        } else if (i == R.id.tv_login_forget_passsword) {

            ActivityUtils.openActivity(RouterConstants.LOGIN_COMPONENT_FIND_PASSWORD_PATH);

        } else if (i == R.id.img_login_wei_chat) {

            WeiXinLoginUtils.login();
        }
    }


    // 云信只提供消息通道，并不包含用户资料逻辑。开发者需要在管理后台或通过服务器接口将用户帐号和token同步到云信服务器。
    // 在这里直接使用同步到云信服务器的帐号和token登录。
    // 这里为了简便起见，demo就直接使用了密码的md5作为token。
    // 如果开发者直接使用这个demo，只更改appkey，然后就登入自己的账户体系的话，需要传入同步到云信服务器的token，而不是用户密码。
    private void login() {

        mPhone = mEtLoginPhoneNumber.getText().toString().trim();
        mPassword = mEtLoginUserPassword.getText().toString().trim();

        if (TextUtils.isEmpty(mPhone)) {
            ToastUtils.showLongToast(LoginActivity.this, getString(R.string.hint_input_phone_number));
            return;
        }
        if (TextUtils.isEmpty(mPassword)) {
            ToastUtils.showLongToast(LoginActivity.this, getString(R.string.hint_input_login_password));
            return;
        }

        UserRequest request = new UserRequest();
        request.login_account = mPhone;
        request.pwd = MD5.getStringMD5(mPassword);
        request.firstLogin = "0";
        request.deviceToken = PhoneUtil.getIMEI(mContext);
        request.deviceName = PhoneUtil.getSystemModel();

        HashMap requestParams = ApiParams.getLoginRequestParams(request);

        mViewModel.login(requestParams).observe(this, new BaseObserver<UserResult>(this) {

            @Override
            public void uiSuccessful(Resource<UserResult> resource) {
                if (resource.data != null && resource.data.yxuser != null) {

                    loginSuccess(resource);
                }
            }

            @Override
            public void uiError(Resource<UserResult> userResultResource) {
                super.uiError(userResultResource);
                if (userResultResource.errorCode.equals(ResponseCode.ERROR_CODE_10316)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(RouterConstants.KEY_PHONE, userResultResource.data.phone);
                    ActivityUtils.openActivity(RouterConstants.LOGIN_COMPONENT_NEW_DEVICE_LOGIN_PATH, bundle);
                }
            }

            @Override
            public void uiCancel(DialogInterface dialog) {

            }
        });

    }

    private void wxlogin(String openId) {

        WxLoginRequest request = new WxLoginRequest();
        request.wx_openid = openId;
        request.firstLogin = "0";

        HashMap requestParams = ApiParams.getWXLoginParams(request);

        mViewModel.wxLogin(requestParams).observe(this, new BaseObserver<UserResult>(this) {

            @Override
            public void uiSuccessful(Resource<UserResult> resource) {
                if (resource.data != null && resource.data.yxuser != null) {

                    loginSuccess(resource);
                }
            }

            @Override
            public void uiError(Resource<UserResult> userResultResource) {

                if (userResultResource.errorCode.equals(ResponseCode.ERROR_CODE_30001)) {
                    BindPhoneActivity.start(mContext, openId);
                } else {
                    super.uiError(userResultResource);
                }
            }

            @Override
            public void uiCancel(DialogInterface dialog) {

            }
        });

    }


    private class LoinBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String accessToken = intent.getStringExtra("accessToken");
            String refreshToken = intent.getStringExtra("refreshToken");
            String from = intent.getStringExtra("from");
            String scope = intent.getStringExtra("scope");
            String openId = intent.getStringExtra("openId");

            wxlogin(openId);

        }
    }

    @Override
    protected void onDestroy() {
        unRegisterThreeLoginBroadcastReceiver();
        super.onDestroy();
    }

    private LoinBroadcastReceiver mBroadcastReceiver;


    private void registerThreeLoginBroadcastReceiver() {
        mBroadcastReceiver = new LoinBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("LOGIN_GET_TOKEN_SUCCESS");
        registerReceiver(mBroadcastReceiver, filter);
    }

    private void unRegisterThreeLoginBroadcastReceiver() {

        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
    }
}
