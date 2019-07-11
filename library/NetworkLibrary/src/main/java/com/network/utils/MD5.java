package com.network.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5 {
	
	public static String getStringMD5(String value) {
        if (value == null || value.trim().length() < 1) {
            return null;
        }
        try {
            return getMD5(value.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String getMD5(byte[] source) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return HexDump.toHex(md5.digest(source));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

//	public static String md5(String string) {
//		if (TextUtils.isEmpty(string)) {
//			return "";
//		}
//		MessageDigest md5;
//		try {
//			md5 = MessageDigest.getInstance("MD5");
//			byte[] bytes = md5.digest(string.getBytes());
//			StringBuilder result = new StringBuilder();
//			for (byte b : bytes) {
//				String temp = Integer.toHexString(b & 0xff);
//				if (temp.length() == 1) {
//					temp = "0" + temp;
//				}
//				result.append(temp);
//			}
//			return result.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
    
    public static String getStreamMD5(String filePath) {
    	String hash=null;
    	byte[] buffer = new byte[4096];
    	BufferedInputStream in=null;
    	try  
    	{  
    		MessageDigest md5 = MessageDigest.getInstance("MD5");
    		in = new BufferedInputStream(new FileInputStream(filePath));
    		int numRead = 0;    
    		while ((numRead = in.read(buffer)) > 0) {    
    			md5.update(buffer, 0, numRead);    
    		}    
    		in.close();   
    		hash = HexDump.toHex(md5.digest());
    	} catch (Exception e) {
    		e.printStackTrace();  
    	} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
    	}
    	return hash;
    }

	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "a", "b", "c", "d", "e", "f"};

	/**
	 * 转换字节数组为16进制字串
	 *
	 * @param b 字节数组
	 *
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();
		for (byte aB : b) {
			resultSb.append(byteToHexString(aB));
		}
		return resultSb.toString();
	}

	/**
	 * 转换byte到16进制
	 *
	 * @param b 要转换的byte
	 *
	 * @return 16进制格式
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * MD5编码
	 *
	 * @param origin 原始字符串
	 *
	 * @return 经过MD5加密之后的结果
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = origin;
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

}
