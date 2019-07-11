package com.batman.logincomponent.viewmodel;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.batman.baselibrary.preference.UserResult;
import com.batman.logincomponent.data.bean.result.CheckVerifyResult;
import com.batman.logincomponent.data.bean.result.NoResult;
import com.batman.logincomponent.data.bean.result.RegisterResult;
import com.batman.logincomponent.data.bean.result.VerifyResult;
import com.network.ApiResponse;
import com.network.NetworkBoundResource;
import com.network.Resource;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * 登录 ViewModel
 */
public class LoginViewModel extends BaseViewModel {

    /**
     * 登录
     *
     * @param param
     * @return
     */
    public LiveData<Resource<UserResult>> login(HashMap param) {

        return new NetworkBoundResource<UserResult>() {

            @Override
            protected void saveCallResult(@NonNull UserResult item) {

            }

            @NonNull
            @Override
            protected Observable<UserResult> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Observable<ApiResponse<UserResult>> createCall() {

                return mApiService.login(param);
            }

        }.asLiveData();

    }


    /**
     * 微信登录
     * @param param
     * @return
     */
    public LiveData<Resource<UserResult>> wxLogin(HashMap param) {

        return new NetworkBoundResource<UserResult>() {

            @Override
            protected void saveCallResult(@NonNull UserResult item) {

            }

            @NonNull
            @Override
            protected Observable<UserResult> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Observable<ApiResponse<UserResult>> createCall() {

                return mApiService.wxLogin(param);
            }

        }.asLiveData();

    }

    /**
     * 微信验证手机号
     * @param param
     * @return
     */
    public LiveData<Resource<NoResult>> verifyPhone(HashMap param) {

        return new NetworkBoundResource<NoResult>() {

            @Override
            protected void saveCallResult(@NonNull NoResult item) {

            }

            @NonNull
            @Override
            protected Observable<NoResult> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Observable<ApiResponse<NoResult>> createCall() {

                return mApiService.verifyPhone(param);
            }

        }.asLiveData();

    }


    /**
     * openid绑定认证并登录
     *
     * @param param
     * @return
     */
    public LiveData<Resource<UserResult>> codeLogin(HashMap param) {

        return new NetworkBoundResource<UserResult>() {

            @Override
            protected void saveCallResult(@NonNull UserResult item) {

            }

            @NonNull
            @Override
            protected Observable<UserResult> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Observable<ApiResponse<UserResult>> createCall() {

                return mApiService.codeLogin(param);
            }

        }.asLiveData();

    }


    /**
     * 注册
     *
     * @param param
     * @return
     */
    public LiveData<Resource<RegisterResult>> register(HashMap param) {

        return new NetworkBoundResource<RegisterResult>() {

            @Override
            protected void saveCallResult(@NonNull RegisterResult item) {

            }

            @NonNull
            @Override
            protected Observable<RegisterResult> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Observable<ApiResponse<RegisterResult>> createCall() {

                return mApiService.register(param);
            }

        }.asLiveData();

    }

    /**
     * 发送验证码
     *
     * @param param
     * @return
     */
    public LiveData<Resource<VerifyResult>> verify(HashMap param) {

        return new NetworkBoundResource<VerifyResult>() {

            @Override
            protected void saveCallResult(@NonNull VerifyResult item) {

            }

            @NonNull
            @Override
            protected Observable<VerifyResult> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Observable<ApiResponse<VerifyResult>> createCall() {

                return mApiService.getVerify(param);
            }

        }.asLiveData();

    }

    /**
     * 找回登录密码
     *
     * @param param
     * @return
     */
    public LiveData<Resource<NoResult>> resetPassword(HashMap param) {

        return new NetworkBoundResource<NoResult>() {

            @Override
            protected void saveCallResult(@NonNull NoResult item) {

            }

            @NonNull
            @Override
            protected Observable<NoResult> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Observable<ApiResponse<NoResult>> createCall() {

                return mApiService.register(param);
            }

        }.asLiveData();

    }

    /**
     * 校验验证码
     *
     * @param param
     * @return
     */
    public LiveData<Resource<CheckVerifyResult>> checkVerify(HashMap param) {

        return new NetworkBoundResource<CheckVerifyResult>() {

            @Override
            protected void saveCallResult(@NonNull CheckVerifyResult item) {

            }

            @NonNull
            @Override
            protected Observable<CheckVerifyResult> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Observable<ApiResponse<CheckVerifyResult>> createCall() {

                return mApiService.checkVerify(param);
            }

        }.asLiveData();

    }


    /**
     * 登录
     *
     * @param param
     * @return
     */
    public LiveData<Resource<UserResult>> newDevicelogin(HashMap param) {

        return new NetworkBoundResource<UserResult>() {

            @Override
            protected void saveCallResult(@NonNull UserResult item) {

            }

            @NonNull
            @Override
            protected Observable<UserResult> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Observable<ApiResponse<UserResult>> createCall() {

                return mApiService.newDeviceLogin(param);
            }

        }.asLiveData();

    }




}
