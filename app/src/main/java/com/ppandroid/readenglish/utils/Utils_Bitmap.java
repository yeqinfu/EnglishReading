/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.util.Base64;

/**
 * Created by yeqinfu on 2017/5/29.
 */

public class Utils_Bitmap {

    public static String bitmapToBase64(Bitmap bitmap) {
        String result = "";
        ByteArrayOutputStream bos = null;
        try {
            if (null != bitmap) {
                bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);// 将bitmap放入字节数组流中
                bos.flush();// 将bos流缓存在内存中的数据全部输出，清空缓存
                bos.close();
                byte[] bitmapByte = bos.toByteArray();

                result = Base64.encodeToString(bitmapByte, Base64.DEFAULT);
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            if (null != null) {

                try {

                    bos.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }
            }
        }
        return result;

    }

}
