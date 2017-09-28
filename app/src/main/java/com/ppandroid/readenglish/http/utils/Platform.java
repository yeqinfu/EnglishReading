/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */
package com.ppandroid.readenglish.http.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

public class Platform
{
    private static final Platform PLATFORM = findPlatform();

    public static Platform get()
    {
        L.e(PLATFORM.getClass().toString());
        return PLATFORM;
    }

    private static Platform findPlatform()
    {
        try
        {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0)
            {
                return new Android();
            }
        } catch (ClassNotFoundException ignored)
        {
        }
        return new Platform();
    }

    public Executor defaultCallbackExecutor()
    {
        return Executors.newCachedThreadPool();
    }

    public void execute(Runnable runnable)
    {
        defaultCallbackExecutor().execute(runnable);
    }


    static class Android extends Platform
    {
        @Override
        public Executor defaultCallbackExecutor()
        {
            return new MainThreadExecutor();
        }

        static class MainThreadExecutor implements Executor
        {
            private final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable r)
            {
                handler.post(r);
            }
        }
    }


}
