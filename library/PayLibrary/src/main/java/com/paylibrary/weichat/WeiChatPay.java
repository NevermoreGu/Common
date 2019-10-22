package com.paylibrary.weichat;

import android.content.Context;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Toast;

import com.paylibrary.Pay;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class WeiChatPay {

    private Context context;

    private IWXAPI api;

    public WeiChatPay(Context context) {
        this.context = context;
        api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, false);
    }

    public void weiChatPay(View payBtn, String content, String packages) {

        Log.e("get server pay params:", content);
        try {
            JSONObject json = new JSONObject(content);
            if (null != json && !json.has("retcode")) {
                PayReq req = new PayReq();
                req.appId = json.getString("appid");
                req.partnerId = json.getString("partnerid");
                req.prepayId = json.getString("prepayid");
                req.nonceStr = json.getString("noncestr");
                req.timeStamp = json.getString("timestamp");
//                req.timeStamp = System.currentTimeMillis() / 1000 + "";
//                req.packageValue = json.getString("package");
                req.packageValue = packages;

//                SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
//
//                parameters.put("appid", req.appId);
//                parameters.put("noncestr", req.nonceStr);
//                parameters.put("package", req.packageValue);
//                parameters.put("partnerid", req.partnerId);
//                parameters.put("prepayid", req.prepayId);
//                parameters.put("timestamp", req.timeStamp);
//
//                String characterEncoding = "UTF-8";
//                String sign = createSign(characterEncoding, parameters);

                req.sign = json.getString("signstr");
                req.extData = "app data"; // optional
                api.sendReq(req);
            } else {
                Log.d("PAY_GET", "???????" + json.getString("retmsg"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (payBtn != null) {
            payBtn.setEnabled(true);
        }

    }

//    public void Pay(View v, String orderInfo) {
//        weiChatPay(v, orderInfo);
//    }
}
