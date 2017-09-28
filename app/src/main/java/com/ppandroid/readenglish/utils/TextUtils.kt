/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.im.utils

object TextUtils {
    fun isEmpty(vararg str: String): Boolean {
        for (string in str) {
            if (string == null || string.isEmpty()) {
                return true
            } else {
                continue
            }
        }
        return false

    }

}
