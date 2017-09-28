/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils.toast;

import android.content.Context;
import android.text.TextUtils;

public class ToastUtil {

	public static void toast(Context context,String toastMsg){
        if (!TextUtils.isEmpty(toastMsg)){
            ToastUtilAdapter.getInstance().setContext(context).toast(toastMsg);
        }
	}
	
	public static void toast(Context context,int resId){
		ToastUtilAdapter.getInstance().setContext(context).toast(resId);
	}


	
}
