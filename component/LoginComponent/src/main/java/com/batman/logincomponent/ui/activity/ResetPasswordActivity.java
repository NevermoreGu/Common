package com.batman.logincomponent.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.batman.baselibrary.RouterConstants;
import com.batman.baselibrary.base.BaseActivity;
import com.batman.baselibrary.base.BaseObserver;
import com.batman.baselibrary.utils.ActivityUtils;
import com.batman.baselibrary.utils.ToastUtils;
import com.batman.logincomponent.R;
import com.batman.logincomponent.R2;
import com.batman.logincomponent.api.ApiParams;
import com.batman.logincomponent.data.bean.request.ResetPasswordRequest;
import com.batman.logincomponent.data.bean.result.NoResult;
import com.batman.logincomponent.viewmodel.LoginViewModel;
import com.network.Resource;
import com.network.utils.MD5;
import com.ui.widget.UIClearEditText;
import com.ui.widget.UINavigationView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 重置登录密码
 *
 * @author guqian
 */
@Route(path = RouterConstants.LOGIN_COMPONENT_RESET_PASSWORD_PATH)
public class ResetPasswordActivity extends BaseActivity {

    @BindView(R2.id.uinv)
    UINavigationView mUinv;
    @BindView(R2.id.tv_reset_password_phone)
    TextView mTvResetPasswordPhone;
    @BindView(R2.id.et_reset_password)
    UIClearEditText mEtResetPassword;
    @BindView(R2.id.et_reset_password_again)
    UIClearEditText mEtResetPasswordAgain;
    @BindView(R2.id.tv_reset_password_error)
    TextView mTvResetPasswordError;
    @BindView(R2.id.btn_reset_password_confirm)
    Button mBtnResetPasswordConfirm;

    private LoginViewModel mViewModel;

    private String mPassword;
    private String mPasswordAgain;

    private String mPhone;
    private String mMessageId;
    private String mAccId;
    private String mToken;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initViews() {
        mUinv.setNavigationTitle(R.string.text_reset_password);
    }

    @Override
    public void loadData(Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        parseIntent();
    }


    private void parseIntent() {
        mPhone = getIntent().getStringExtra(RouterConstants.KEY_PHONE);
        mMessageId = getIntent().getStringExtra(RouterConstants.KEY_MESSAGE_ID);
        mAccId = getIntent().getStringExtra(RouterConstants.KEY_ACCID);
        mToken = getIntent().getStringExtra(RouterConstants.KEY_TOKEN);
        mTvResetPasswordPhone.setText(mPhone);
    }


    @OnClick(R2.id.btn_reset_password_confirm)
    public void onViewClicked() {
        confirm();
    }


    /**
     * 确认
     */
    private void confirm() {

        mPassword = mEtResetPassword.getText().toString().trim();
        mPasswordAgain = mEtResetPasswordAgain.getText().toString().trim();

        if (TextUtils.isEmpty(mPassword)) {
            ToastUtils.showLongToast(this, getString(R.string.hint_input_new_password));
            return;
        }
        if (TextUtils.isEmpty(mPasswordAgain)) {
            ToastUtils.showLongToast(this, getString(R.string.hint_confirm_input_new_password));
            return;
        }

        if (!mPassword.equals(mPasswordAgain)) {
            ToastUtils.showLongToast(this, getString(R.string.hint_password_inconformity));
            return;
        }

        ResetPasswordRequest request = new ResetPasswordRequest();
        request.phone = mPhone;
        request.msgid = mMessageId;
        request.msgid = MD5.getStringMD5(mPassword);

        request.yx_username = mAccId;
        request.yx_usertoken = mToken;
        HashMap requestParams = ApiParams.getResetPasswordParams(request);

        mViewModel.resetPassword(requestParams).observe(this, new BaseObserver<NoResult>(this) {

            @Override
            public void uiSuccessful(Resource<NoResult> resource) {
                ActivityUtils.openActivitySingleTop(RouterConstants.LOGIN_COMPONENT_LOGIN_PATH);
                finish();
            }

            @Override
            public void uiError(Resource<NoResult> verifyResultResource) {
                mTvResetPasswordError.setText(verifyResultResource.message);
            }
        });


    }


}
