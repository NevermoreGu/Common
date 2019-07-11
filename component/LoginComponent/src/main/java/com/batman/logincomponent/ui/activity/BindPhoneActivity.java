package com.batman.logincomponent.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.batman.baselibrary.api.ResponseCode;
import com.batman.baselibrary.base.BaseActivity;
import com.batman.baselibrary.base.BaseObserver;
import com.batman.baselibrary.utils.ToastUtils;
import com.batman.logincomponent.R;
import com.batman.logincomponent.R2;
import com.batman.logincomponent.api.ApiParams;
import com.batman.logincomponent.data.bean.request.VerifyPhoneRequest;
import com.batman.logincomponent.data.bean.result.NoResult;
import com.batman.logincomponent.viewmodel.LoginViewModel;
import com.network.Resource;
import com.ui.widget.UIClearEditText;
import com.ui.widget.UINavigationView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity {

    @BindView(R2.id.uinv)
    UINavigationView mUinv;
    @BindView(R2.id.et_login_phone_number)
    UIClearEditText mEtLoginPhoneNumber;

    private String mPhone;
    private String mwx_openid;

    private LoginViewModel mViewModel;

    public static void start(Context context, String wx_openid) {
        Intent intent = new Intent();
        intent.setClass(context, BindPhoneActivity.class);
        intent.putExtra("wx_openid", wx_openid);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void initViews() {
        mUinv.setNavigationTitle("绑定手机号");
    }

    @Override
    public void loadData(Bundle savedInstanceState) {
        mwx_openid = getIntent().getStringExtra("wx_openid");
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }


    @OnClick(R2.id.btn_login_login)
    public void onViewClicked() {

        verifyPhone();
    }

    /**
     * 微信验证手机号
     */
    private void verifyPhone() {

        mPhone = mEtLoginPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(mPhone)) {
            ToastUtils.showLongToast(mContext, getString(R.string.hint_input_phone_number));
            return;
        }

        VerifyPhoneRequest request = new VerifyPhoneRequest();
        request.phone = mPhone;
        request.wx_openid = mwx_openid;

        HashMap requestParams = ApiParams.getVerifyPhoneParams(request);

        mViewModel.verifyPhone(requestParams).observe(this, new BaseObserver<NoResult>(this) {

            @Override
            public void uiSuccessful(Resource<NoResult> resource) {
                if (resource.data != null) {

                }
            }

            @Override
            public void uiError(Resource<NoResult> userResultResource) {
                if (userResultResource.errorCode.equals(ResponseCode.ERROR_CODE_21409)) {
                    BindPhoneRegisterActivity.start(mContext, mwx_openid, mPhone);
                } else if (userResultResource.errorCode.equals(ResponseCode.ERROR_CODE_21410)) {
                    VerifyWeiXinPhoneRegisterActivity.start(mContext, mwx_openid, mPhone);
                }
                super.uiError(userResultResource);
            }

            @Override
            public void uiCancel(DialogInterface dialog) {

            }
        });

    }
}
