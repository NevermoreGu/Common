package com.network.utils;


import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author gaozh@haodaibao.com
 * @Title AES.java
 * @Description AES工具类
 * @date 2013.01.15
 */
public class InnerAES {
    public static final String ALGORITHM = "AES";

    public static String IV_STRING = "wOzCypMUYVuiQO9f";

    public static String encrypt(String text, String key, String charset) throws Exception {

        byte[] byteContent = text.getBytes(charset);
        byte[] enCodeFormat = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        byte[] initParam = IV_STRING.getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(byteContent);
        return new String(Base64.encode(encryptedBytes, Base64.NO_WRAP), "UTF-8");
    }

    public static String encrypt(String text, String key, String transformation, String charset) throws Exception {
        byte[] byteContent = text.getBytes(charset);
        byte[] enCodeFormat = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(byteContent);
        return new String(Base64.encode(encryptedBytes, Base64.NO_WRAP), "UTF-8");
    }

    public static String decrypt(String text, String key, String charset) throws Exception {

        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV_STRING.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(text, Base64.NO_WRAP);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, charset);
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }
}
