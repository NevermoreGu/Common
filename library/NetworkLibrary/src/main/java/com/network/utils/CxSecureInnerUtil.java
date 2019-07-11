package com.network.utils;

import android.text.TextUtils;

import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Random;

/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 */
public class CxSecureInnerUtil {

    public static String decryptTradeInfo(String cer, String data, String sign) throws Exception {
        String aesKey = "qwemoQAPydFvWWLq";
        String tradeJson = "";
        String signKey = InnerAES.decrypt(cer, aesKey, "UTF-8");
        String checksign = createSign(data, signKey);
        if (TextUtils.equals(sign, checksign)) {
            tradeJson = InnerAES.decrypt(data, aesKey, "UTF-8");
        } else {
            tradeJson = "签名错误";
        }
        return tradeJson;
    }

    public static String encryptTradeInfo(String tradeJson) throws Exception {
        String aesKey = "qwemoQAPydFvWWLq";
        // 1.随机生成一个signKey
        String signKey = getRandomStringByLength(16);
        String data = null;
        String cer = null;

        try {
            // 2.AES秘钥加密交易信息JSON字符,得到数据集合密文data
            data = InnerAES.encrypt(tradeJson, aesKey, "UTF-8");
            cer = InnerAES.encrypt(signKey, aesKey, "UTF-8");

        } catch (Exception e) {
            throw new SecurityException("加密异常");
        }
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(data);
        String text = sBuffer.toString();
        String sign = null;
        try {
            // 4.按照merchant+cer+data的顺序,中间不含任何拼接字符,生成字符串,用自己的私钥签名
            sign = createSign(data, signKey);
        } catch (Exception e) {
            throw new SignatureException("签名异常");
        }

        return "cer=" + URLEncoder.encode(cer) + "&data=" + URLEncoder.encode(data) + "&sign=" + URLEncoder.encode(sign);
    }

    public static HashMap<String, String> encryptTradeInfoMap(String tradeJson) throws Exception {
        String aesKey = "qwemoQAPydFvWWLq";
        // 1.随机生成一个signKey
        String signKey = getRandomStringByLength(16);
        String data = null;
        String cer = null;

        try {
            // 2.AES秘钥加密交易信息JSON字符,得到数据集合密文data
            data = InnerAES.encrypt(tradeJson, aesKey, "UTF-8");
            cer = InnerAES.encrypt(signKey, aesKey, "UTF-8");

        } catch (Exception e) {
            throw new SecurityException("加密异常");
        }
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(data);
        String text = sBuffer.toString();
        String sign = null;
        try {
            // 4.按照merchant+cer+data的顺序,中间不含任何拼接字符,生成字符串,用自己的私钥签名
            sign = createSign(data, signKey);
        } catch (Exception e) {
            throw new SignatureException("签名异常");
        }

        HashMap<String, String> param = new HashMap<>();

        param.put("cer", cer);
        param.put("data", data);
        param.put("sign", sign);

        return param;
    }

    public static String createSign(String busidata, String signkey) {
        String content = busidata + "key=" + signkey;
        String result = "";
        result = MD5.MD5Encode(content).toUpperCase();
        return result;
    }

    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        // 需要加密的字串
        String aesKey = "qwemoQAPydFvWWLq";
        System.out.println(aesKey);
//		System.out.println(encryptTradeInfo("phone=18651883255&busitype=0"));
    }

}