/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.http;

/**
 * Created by yeqinfu on 2017/8/11.
 */

public class HttpConstant {
    /** 服务器异常 */
    public static final String	SERVICE_ERROR				= "002";
    /** TOKEN 为空 */
    public static final String	TOKEN_BLANK_ERROR			= "003";
    /** TOKEN 失效 */
    public static final String	TOKEN_INVALID_ERROR			= "004";
    /** 自动登录已过期，请重新登录 */
    public static final String	RELOAAGIN_MD5_INVALID_ERROR	= "005";
    /** 校验数据为空 比如验证码输入为空，评论输入为空 */
    public static final String	BLANK_ERROR					= "006";
    /** 校验数据错误 如验证码输入错误，评论输入错误 */
    public static final String	CHECK_ERROR					= "007";
}
