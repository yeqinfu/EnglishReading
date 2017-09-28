/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.im.utils

/**
 * Created by yeqinfu on 2017/7/28.
 */

class Contants {
    companion object {
        /** 服务器异常  */
        val SERVICE_ERROR = "002"
        /** TOKEN 为空  */
        val TOKEN_BLANK_ERROR = "003"
        /** TOKEN 失效  */
        val TOKEN_INVALID_ERROR = "004"
        /** 自动登录已过期，请重新登录  */
        val RELOAAGIN_MD5_INVALID_ERROR = "005"
        /** 校验数据为空 比如验证码输入为空，评论输入为空  */
        val BLANK_ERROR = "006"
        /** 校验数据错误 如验证码输入错误，评论输入错误  */
        val CHECK_ERROR = "007"

  //  var baseUrl = "http://192.168.25.110:9010/"
     var baseUrl = "http://120.76.40.63:9010/"
    //var baseUrl = "http://192.168.25.127/"
     // var baseUrl = "http://192.168.25.203:80/"
    }
}
