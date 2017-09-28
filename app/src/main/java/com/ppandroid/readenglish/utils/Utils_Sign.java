/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Base64;


/**
 * Created by yeqinfu on 2017/9/26.
 */

public class Utils_Sign {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
  //  private static final String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALOsXH4UwWs/NkBTWdGUTSFaEnKYk5UHFVZgZz4BUjeWsjvwgwQsDdIJRtK4x3N4uc0BnedC+ZJOLkxc0JMZoNc68//XQb20WCnks4KQZJuCPOufOCqoTS+nkF2XivppubS6c3jhIr5LH9BznKHY5YPNj44IvBzSXidGTLNTb6pjAgMBAAECgYEApuAuY7fZUUe7lpETuhDYdmKXAbJuWBrlGX2eRjup5hFj50HiHczg5TRSgEK4lKoLP7oBs5pD/Dv6ylfmLMm/1LhBCZXofFcVUmoih2UTQJZ9p6tg2jpAgeFpvUIGILdsD3LLAUUw1RQBcegdwtxuB6B3CyjgoZt4cJoMNmtMQrkCQQDj2HyJcD/tLnVON96lhiYITHTUeE6mZpy21x1ojguS53mrC64X8tLs52lBK7K1exEEcrtEz27eDvCAwousMt1nAkEAyeAF8GmWTsh2THdV6SrJTE5NWBpFqwpVvzgk1aOt96CE7Ssv3Ke6QhX5pb0zjF7v3vvgg6cwpLtvo8j6fV/xpQJACEuPbSmLCkWQu8JI9tnFdLPj5QBkTKabugRm67GQNk77HQEV24VZrIpYSKkgnn7WhcTvkHfE34zHsMhkcWaArQJAQy9WaquJRZD7pfk1U1NVCRsqLH8Z3pGi9e/Gen9t33LBqnjsBeCgYWwU2K7+H+JcrHZhQw96Zeinf/fcPauXrQJAU5oaCOPTcVzzpMO5qn6Ftz2/DpcFxpHuqYvCg3Ju34y/Xjc+e8sJ8s6fVNTMIw3UfMFZ0Hp7efLaxmtHt3TFmA==";

    public static final String KEY_ALGORITHM = "RSA";

    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
    /** map中的公钥key */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /** map中的私钥key */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
	 * 二、用来验签（即数据签名），私钥用于签名，公钥用于验签<br/>
	 * 用私钥对信息生成数字签名
	 *
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = Base64.decode(privateKey, Base64.DEFAULT);
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);
		return Base64.encodeToString(signature.sign(), Base64.NO_WRAP);
	}

	/**
	 * 二、用来验签（即数据签名），私钥用于签名，公钥用于验签<br/>
	 * 用私钥对信息生成数字签名
	 *
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(String data, String privateKey) throws Exception {
		return sign(data.getBytes("UTF-8"), privateKey);
	}

	public static String sign(String data){
        try {
            return sign(data,getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String signGet(String url){
        String[] group=url.split("\\?");
        Map<String, String> map=new HashMap<>();
        if (group.length>1){
            String p=group[1];
            String[] params=p.split("&");
            for (String item:params) {
                String[] keyvalue=item.split("=");
                if (keyvalue.length>1){
                    map.put(keyvalue[0],keyvalue[1]);
                }
            }
        }
        String linkStr=createLinkString(map);
        String result=sign(linkStr);
        return result;
    }
    public static String signPost(Map<String,String> map){
        return sign(createLinkString(map));
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native static String getPrivateKey();

}
