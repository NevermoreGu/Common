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
import com.batman.baselibrary.preference.UserResult;
import com.batman.baselibrary.utils.PhoneUtil;
import com.batman.baselibrary.utils.ToastUtils;
import com.batman.logincomponent.R;
import com.batman.logincomponent.R2;
import com.batman.logincomponent.api.ApiParams;
import com.batman.logincomponent.data.bean.request.NewDeviceLoginRequest;
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


/**
 * 新设备验证手机号
 *
 * @author guqian
 */
@Route(path = RouterConstants.LOGIN_COMPONENT_NEW_DEVICE_LOGIN_PATH)
public class NewDeviceLoginActivity extends BaseActivity {

    @BindView(R2.id.uinv)
    UINavigationView mUinv;

    @BindView(R2.id.tv_phone)
    TextView mTvPhone;

    @BindView(R2.id.et_find_password_verification_code)
    UIClearEditText mEtFindPasswordVerificationCode;
    @BindView(R2.id.tv_find_password_verification_code)
    TextView mTvFindPasswordVerificationCode;

    @BindView(R2.id.btn_find_password_next)
    Button mBtnFindPasswordNext;

    private LoginViewModel mViewModel;

    private String mPhone;
    private String mMessage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_device_login;
    }

    @Override
    public void initViews() {
        mUinv.setNavigationTitle(R.string.text_find_password);
    }

    @Override
    public void loadData(Bundle savedInstanceState) {
//        ARouter.getInstance().inject(this);
        mPhone = getIntent().getStringExtra(RouterConstants.KEY_PHONE);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        if (!TextUtils.isEmpty(mPhone)) {
            mTvPhone.setText("检测到新设备登录，需验证手机号：" + mPhone.substring(0, 3) + "****" + mPhone.substring(mPhone.length() - 4, mPhone.length()));
        }
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
        request.busitype = VERIFY.TYPE_CHANGE_CODE;
        HashMap requestParams = ApiParams.getVerifyParams(request);

        mViewModel.verify(requestParams).observe(this, new BaseObserver<VerifyResult>(this) {

            @Override
            public void uiSuccessful(Resource<VerifyResult> resource) {

            }

        });


    }

    /**
     * 下一步
     */
    private void next() {

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

        NewDeviceLoginRequest request = new NewDeviceLoginRequest();
        request.phone = mPhone;
        request.deviceToken = PhoneUtil.getIMEI(mContext);
        request.deviceName = PhoneUtil.getSystemModel();
        request.message = mMessage;
        request.busitype = VERIFY.TYPE_CHANGE_CODE;

        HashMap requestParams = ApiParams.getNewDeviceLoginParams(request);

        mViewModel.newDevicelogin(requestParams).observe(this, new BaseObserver<UserResult>(this) {

            @Override
            public void uiSuccessful(Resource<UserResult> resource) {

                finish();
            }

        });


    }

}
