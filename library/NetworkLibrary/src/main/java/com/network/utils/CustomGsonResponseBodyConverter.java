package com.network.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String response = value.string();
        CerResponse cerResponse = gson.fromJson(response, CerResponse.class);
        String result ="";
        try {
            result = rezultProcess(cerResponse);
            LogUtils.d("HttpIntercept",result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Reader reader = new StringReader(result);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }

    /**
     * 解码返回数据
     */
    public  String rezultProcess(CerResponse cerResponse) throws Exception {

        String cer = cerResponse.cer;
        String data = cerResponse.data;
        String sign = cerResponse.sign;
        String rezult = CxSecureInnerUtil.decryptTradeInfo(cer, data, sign);

        return rezult;
    }
}
