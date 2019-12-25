package com.utils;

import android.text.TextUtils;

import com.batman.baselibrary.preference.UserPres;
import com.google.gson.Gson;
import com.network.utils.CxSecureInnerUtil;
import com.network.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UrlUtils {


    public static HashMap<String, String> createGetMap() {
        return new HashMap<>();
    }

    public static String getHeadParams(JSONObject root) {

        try {

            root.put("userId", UserPres.getInstance().userId);
            root.put("employeeId", UserPres.getInstance().employeeId);
            root.put("deviceId", UserPres.getInstance().deviceId);
            root.put("deviceCode", UserPres.getInstance().deviceCode);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root.toString();
    }

    /**
     * 拼接url参数
     *
     * @param namesAndValues
     * @return
     */
    public static String namesAndValuesToQueryString(Map<String, String> namesAndValues) {
        StringBuilder out = new StringBuilder();

        int i = 0;
        for (Map.Entry<String, String> entry : namesAndValues.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            if (i > 0) {
                out.append('&');
            }
            i++;
            out.append(name);
            if (value != null) {
                out.append('=');
                try {
//                    if (name.equals("name")) {
//                        out.append(value);
//                    } else {
                    out.append(URLEncoder.encode(value, "UTF-8"));
//                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        LogUtils.d("httpParam", out.toString());
        return out.toString();
    }

    /**
     * 获取 对象的属性和参数
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParamMap(Object request) {
        Map<String, String> namesAndValues = createGetMap();
        Field[] fields = request.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String name = field.getName();
            Object object;
            String value = "";
            try {
                object = fields[i].get(request);
                if (object != null) {
                    if (object instanceof String) {
                        value = (String) fields[i].get(request);
                        //参数不能为空，要么不传
                        if (!TextUtils.isEmpty(value)) {
                            namesAndValues.put(name, value);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return namesAndValues;
    }


//    public static RequestBody getEncodeParam(Object request) {
//        String authParam = "";
//        Map<String, String> namesAndValues = getParamMap(request);
//        JSONObject root = new JSONObject();
//        Iterator<Map.Entry<String, String>> entries = namesAndValues.entrySet().iterator();
//        while (entries.hasNext()) {
//            Map.Entry<String, String> entry = entries.next();
//            String key = entry.getKey();
//            String value = entry.getValue();
//            try {
//                root.put(key, value);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        getHeadParams(root);
//        authParam = root.toString();
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), authParam);
//        return requestBody;
//    }

    public static RequestBody getEncodeBody(Object request) {
        Gson gson = new Gson();
        String content = gson.toJson(request);
        String authParam = "";
        try {
            JSONObject root = new JSONObject(content);
            getHeadParams(root);
            authParam = root.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), authParam);
        return requestBody;
    }

    /**
     * 拼接url
     *
     * @param request
     * @return
     */
    public static HashMap<String, String> getEncodeUrlParam(Object request) {
        Map<String, String> namesAndValues = getParamMap(request);
        String param = UrlUtils.namesAndValuesToQueryString(namesAndValues);

        try {
            return CxSecureInnerUtil.encryptTradeInfoMap(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
