package com.batman.logincomponent.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.batman.logincomponent.data.bean.request.CheckVERIFY;
import com.batman.logincomponent.data.bean.request.CheckVerifyRequest;
import com.batman.logincomponent.data.bean.request.VERIFY;
import com.batman.logincomponent.data.bean.request.VerifyRequest;
import com.batman.logincomponent.data.bean.result.CheckVerifyResult;
import com.batman.logincomponent.data.bean.result.VerifyResult;
import com.batman.logincomponent.viewmodel.LoginViewModel;
import com.blankj.utilcode.util.RegexUtils;
import com.network.Resource;
import com.ui.widget.UIClearEditText;
import com.ui.widget.UINavigationView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 找回密码
 *
 * @author guqian
 */
@Route(path = RouterConstants.LOGIN_COMPONENT_FIND_PASSWORD_PATH)
public class FindPasswordActivity extends BaseActivity {


    @BindView(R2.id.uinv)
    UINavigationView mUinv;
    @BindView(R2.id.et_find_password_phone_number)
    UIClearEditText mEtFindPasswordPhoneNumber;
    @BindView(R2.id.et_find_password_verification_code)
    UIClearEditText mEtFindPasswordVerificationCode;
    @BindView(R2.id.tv_find_password_verification_code)
    TextView mTvFindPasswordVerificationCode;
    @BindView(R2.id.tv_find_password_error)
    TextView mTvFindPasswordError;
    @BindView(R2.id.btn_find_password_next)
    Button mBtnFindPasswordNext;

    private LoginViewModel mViewModel;

    private String mPhone;
    private String mMessage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_find_password;
    }

    @Override
    public void initViews() {
        mUinv.setNavigationTitle(R.string.text_find_password);
    }

    @Override
    public void loadData(Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }


    @OnClick({R2.id.tv_find_password_verification_code, R2.id.btn_find_password_next})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_find_password_verification_code) {
            sendVerifyCode();
        } else if (i == R.id.btn_find_password_next) {
            next();
        }
    }


    private void sendVerifyCode() {

        mPhone = mEtFindPasswordPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            ToastUtils.showLongToast(this, getString(R.string.hint_input_phone_number));
            return;
        }
        if (!RegexUtils.isMobileSimple(mPhone)) {
            ToastUtils.showLongToast(this, getString(R.string.hint_input_correct_phone_number));
            return;
        }
        VerifyRequest request = new VerifyRequest();
        request.phone = mPhone;
        request.busitype = VERIFY.TYPE_RESET_PASSWORD;
        HashMap requestParams = ApiParams.getVerifyParams(request);

        mViewModel.verify(requestParams).observe(this, new BaseObserver<VerifyResult>(this) {

            @Override
            public void uiSuccessful(Resource<VerifyResult> resource) {

            }

            @Override
            public void uiError(Resource<VerifyResult> verifyResultResource) {
                mTvFindPasswordError.setText(verifyResultResource.message);
            }
        });


    }

    /**
     * 下一步
     */
    private void next() {

        mPhone = mEtFindPasswordPhoneNumber.getText().toString().trim();
        mMessage = mEtFindPasswordVerificationCode.getText().toString().trim();

        if (TextUtils.isEmpty(mPhone)) {
            ToastUtils.showLongToast(this, getString(R.string.hint_input_phone_number));
            return;
        }
        if (!RegexUtils.isMobileSimple(mPhone)) {
            ToastUtils.showLongToast(this, getString(R.string.hint_input_correct_phone_number));
            return;
        }
        if (TextUtils.isEmpty(mMessage)) {
            ToastUtils.showLongToast(this, getString(R.string.hint_input_verification_code));
            return;
        }

        CheckVerifyRequest request = new CheckVerifyRequest();
        request.phone = mPhone;
        request.busitype = CheckVERIFY.TYPE_RESET_PASSWORD;
        request.message = mMessage;
        HashMap requestParams = ApiParams.checkVerifyParams(request);

        mViewModel.checkVerify(requestParams).observe(this, new BaseObserver<CheckVerifyResult>(this) {

            @Override
            public void uiSuccessful(Resource<CheckVerifyResult> resource) {

                if (resource.data != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(RouterConstants.KEY_PHONE, mPhone);
                    bundle.putString(RouterConstants.KEY_MESSAGE_ID, resource.data.msgid);
                    bundle.putString(RouterConstants.KEY_ACCID, resource.data.accid);
                    bundle.putString(RouterConstants.KEY_TOKEN, resource.data.token);
                    ActivityUtils.openActivity(RouterConstants.LOGIN_COMPONENT_RESET_PASSWORD_PATH, bundle);
                    finish();
                }
            }

            @Override
            public void uiError(Resource<CheckVerifyResult> verifyResultResource) {
                mTvFindPasswordError.setText(verifyResultResource.message);
            }
        });


    }

}
