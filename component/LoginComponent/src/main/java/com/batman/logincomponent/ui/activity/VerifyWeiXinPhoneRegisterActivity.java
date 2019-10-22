package com.batman.logincomponent.ui.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.batman.baselibrary.base.BaseObserver;
import com.batman.baselibrary.preference.UserResult;
import com.batman.baselibrary.utils.ToastUtils;
import com.batman.baselibrary.widget.SendValidateButton;
import com.batman.logincomponent.R;
import com.batman.logincomponent.R2;
import com.batman.logincomponent.api.ApiParams;
import com.batman.logincomponent.data.bean.request.CodeLoginRequest;
import com.batman.logincomponent.data.bean.request.VERIFY;
import com.batman.logincomponent.data.bean.request.VerifyRequest;
import com.batman.logincomponent.data.bean.result.VerifyResult;
import com.batman.logincomponent.viewmodel.LoginViewModel;
import com.blankj.utilcode.util.RegexUtils;
import com.network.Resource;
import com.ui.widget.UIClearEditText;
import com.ui.widget.UINavigationView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class VerifyWeiXinPhoneRegisterActivity extends BaseLoginActivity {

    @BindView(R2.id.uinv)
    UINavigationView mUinv;
    @BindView(R2.id.et_register_verification_code)
    UIClearEditText mEtRegisterVerificationCode;

    @BindView(R2.id.tv_register_verification_code)
    SendValidateButton mTvRegisterVerificationCode;

    private String mPhone;
    private String mMessage;
    private String wx_openid;

    private LoginViewModel mViewModel;

    public static void start(Context context, String wx_openid, String phone) {
        Intent intent = new Intent();
        intent.setClass(context, BindPhoneActivity.class);
        intent.putExtra("wx_openid", wx_openid);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wei_xin_bind_phone_register;
    }

    @Override
    public void initViews() {
        mUinv.setNavigationTitle("验证手机号");
    }

    @Override
    public void loadData(Bundle savedInstanceState) {
        mPhone = getIntent().getStringExtra("phone");
        wx_openid = getIntent().getStringExtra("wx_openid");
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mTvRegisterVerificationCode.setListener(new SendValidateButton.SendValidateButtonListener() {
            @Override
            public boolean onClickSendValidateButton() {
                return sendVerifyCode();
            }

            @Override
            public void onTick() {

            }
        });
    }


    @OnClick({R2.id.tv_register_user_agreement, R2.id.btn_login_login})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_login_login) {
            register();
        } else if (i == R.id.tv_register_user_agreement) {
            ServiceWebActivity.start(mContext, "", "用户使用协议");
        }
    }

    /**
     * 注册微信
     */
    private void register() {

        mMessage = mEtRegisterVerificationCode.getText().toString().trim();

        if (TextUtils.isEmpty(mMessage)) {
            ToastUtils.showLongToast(this, getString(R.string.hint_input_verification_code));
            return;
        }

        CodeLoginRequest request = new CodeLoginRequest();
        request.phone = mPhone;
        request.message = mMessage;
        request.wx_openid = wx_openid;
        request.firstLogin = "0";

        HashMap requestParams = ApiParams.getCodeLoginParams(request);

        mViewModel.codeLogin(requestParams).observe((LifecycleOwner) mContext, new BaseObserver<UserResult>(mContext) {

            @Override
            public void uiSuccessful(Resource<UserResult> resource) {
                loginSuccess(resource);
            }
        });
    }

    /**
     * 发送验证码
     */
    private boolean sendVerifyCode() {

        if (TextUtils.isEmpty(mPhone)) {
            ToastUtils.showLongToast(this, getString(R.string.hint_input_phone_number));
            return false;
        }
        if (!RegexUtils.isMobileSimple(mPhone)) {
            ToastUtils.showLongToast(this, getString(R.string.hint_input_correct_phone_number));
            return false;
        }
        VerifyRequest request = new VerifyRequest();
        request.phone = mPhone;
        request.busitype = VERIFY.TYPE_BIND;
        HashMap requestParams = ApiParams.getVerifyParams(request);

        mViewModel.verify(requestParams).observe(this, new BaseObserver<VerifyResult>(this) {

            @Override
            public void uiSuccessful(Resource<VerifyResult> resource) {

            }
        });

        return true;

    }


}
