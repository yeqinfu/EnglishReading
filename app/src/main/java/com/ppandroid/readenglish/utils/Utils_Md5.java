/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;

import java.security.MessageDigest;

public class Utils_Md5 {
	
	protected final static String SECRET = "bzw";

    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs.append("0").append(stmp);
            else hs.append(stmp);
        }
        return hs.toString();
    }
    
    /**
     * 
     * md5Digest 
     * XMPP 登陆时，passport加密
     * @param content
     * @return 
     * String
     * @exception 
     * @since  1.0.0
     */
    public static String md5Digest(String content){
		StringBuffer digestContent = new StringBuffer(SECRET);
		digestContent.append(content).append(SECRET);
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = byte2hex(md.digest(digestContent.toString().getBytes("utf-8")));
		} catch (Exception e) {
			throw new RuntimeException("sign error !");
		}
		return result;
	}
    
    
    public static String MD5Sign(String content){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return byte2hex(md.digest(content.getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }
    }
}
