/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class NetworkUtils {
	/**
	 * @Description 判断网络是否可用
	 * @author luozheng
	 * @date 2015年3月2日 下午10:54:33
	 */
	public static boolean isNetworkAvaiable(Context context) {
		boolean isNetAvailable = false;
		if (null != context){
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo[] netInfos = cm.getAllNetworkInfo();
			if (null != netInfos) {
				for (int i = 0; i < netInfos.length; i++) {
					NetworkInfo netInfo = netInfos[i];
					if (netInfo != null && netInfo.getState() == State.CONNECTED) {
						isNetAvailable = true;
						break;
					}
				}
			}
			return isNetAvailable;
		}else{
			//极端情况，默认返回true
			return true;
		}
	}
}
