package com.batman.baselibrary.utils;

import android.text.TextUtils;

import com.network.utils.CxSecureInnerUtil;
import com.network.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UrlUtils {


    public static HashMap<String, String> createGetMap() {
        return new HashMap<>();
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
            String value = "";
            try {
                value = (String) fields[i].get(request);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //参数不能为空，要么不传
            if (!TextUtils.isEmpty(value)) {
                namesAndValues.put(name, value);
            }
        }
        return namesAndValues;
    }

    public static HashMap<String, String> getEncodeParam(Object request) {
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
