package com.network;

import com.google.gson.annotations.SerializedName;


public class ResponseResult<T> {

    @SerializedName("detail")
    public T detail;

    public String cmd;
    public int result;
    public String resultNote;
}
