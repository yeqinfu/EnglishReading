/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.http.utils;

import android.util.Log;

/**
 * Created by zhy on 15/11/6.
 */
public class L
{
    private static boolean debug = false;

    public static void e(String msg)
    {
        if (debug)
        {
            Log.e("OkHttp", msg);
        }
    }

}

