/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils.activitymanager;

import android.app.Activity;

import com.ppandroid.readenglish.utils.DebugLog;

import java.util.Stack;

/**
 * Created by yeqinfu on 2017/8/14.
 * activity管理类，因为本app是只有登录状态下才能访问页面，当token失效
 * 所有的activity栈必须被清除
 */

public class ActivityManager {
    private static final String TAG = "ActivityManager";
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;
    private ActivityManager() {
    }

    public static ActivityManager getActivityManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }


    //退出栈顶Activity
    public void popActivity(Activity activity) {
        if(activity == null || activityStack == null) {
            return;
        }
        if(activityStack.contains(activity)) {
            activityStack.remove(activity);
            DebugLog.i("ActivityManager======remove++++++=======>");

        }
        DebugLog.i("ActivityManager======popActivity=======>"+activityStack.size());
        //activity.finish();

        //activity = null;
    }

    public void destoryActivity(Activity activity) {
        if(activity == null ) {
            return;
        }
        activity.finish();
        if(activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
        activity = null;
        DebugLog.d(TAG, "destoryActivity=================");
    }

    //获得当前栈顶Activity
    public Activity currentActivity() {
        if(activityStack == null ||activityStack.empty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    //将当前Activity推入栈中
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if (!activityStack.contains(activity)){
            activityStack.add(activity);
            DebugLog.i("ActivityManager======pushActivity=======>"+activityStack.size());
        }

    }

    //退出栈中除指定的Activity外的所有
    public void popAllActivityExceptOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            destoryActivity(activity);
        }
    }

    //退出栈中所有Activity
    public void popAllActivity() {
        popAllActivityExceptOne(null);
    }


    public int getActivityStackSize()
    {
        int size = 0;
        if (activityStack != null)
        {
            size = activityStack.size();
        }
        return size;
    }
}
