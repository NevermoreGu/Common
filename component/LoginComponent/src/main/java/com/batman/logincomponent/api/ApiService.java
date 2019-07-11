package com.batman.logincomponent.api;


import com.batman.baselibrary.preference.UserResult;
import com.batman.logincomponent.data.bean.result.CheckVerifyResult;
import com.batman.logincomponent.data.bean.result.NoResult;
import com.batman.logincomponent.data.bean.result.RegisterResult;
import com.batman.logincomponent.data.bean.result.VerifyResult;
import com.network.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiService {

    /**
     * 上传文件
     *
     * @return
     */
//    @Multipart
//    @POST(ApiParams.UPLOAD_FILE)
//    Observable<ApiResponse<UploadFileResult>> upLoadFile(@Part MultipartBody.Part files);
    @POST(ApiParams.QUERY_LOGIN)
    Observable<ApiResponse<UserResult>> login(@QueryMap HashMap<String, String> params);

    @POST(ApiParams.QUERY_GET_VERIFY)
    Observable<ApiResponse<VerifyResult>> getVerify(@QueryMap HashMap<String, String> params);

    @POST(ApiParams.QUERY_LOGIN_REGISTER)
    Observable<ApiResponse<RegisterResult>> register(@QueryMap HashMap<String, String> params);

    @POST(ApiParams.QUERY_CHECK_VERIFY)
    Observable<ApiResponse<CheckVerifyResult>> checkVerify(@QueryMap HashMap<String, String> params);

    @POST(ApiParams.QUERY_LOGIN_RESET_PASSWORD)
    Observable<ApiResponse<NoResult>> resetPassword(@QueryMap HashMap<String, String> params);

    @POST(ApiParams.QUERY_LOGIN_NEW_DEVICE)
    Observable<ApiResponse<UserResult>> newDeviceLogin(@QueryMap HashMap<String, String> params);

    @POST(ApiParams.QUERY_WX_LOGIN)
    Observable<ApiResponse<UserResult>> wxLogin(@QueryMap HashMap<String, String> params);

    @POST(ApiParams.QUERY_VERIFY_PHONE)
    Observable<ApiResponse<NoResult>> verifyPhone(@QueryMap HashMap<String, String> params);

    @POST(ApiParams.QUERY_VERIFY_PHONE)
    Observable<ApiResponse<UserResult>> codeLogin(@QueryMap HashMap<String, String> params);


}
