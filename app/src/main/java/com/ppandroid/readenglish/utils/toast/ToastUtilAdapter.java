/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils.toast;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtilAdapter {
	private static ToastUtilAdapter instance;
	
	private Toast toast;
	private WeakReference<Context> context;
	private Handler handler;
	public ToastUtilAdapter setContext(Context context){
        this.context=new WeakReference(context);
        return instance;
    }
	private ToastUtilAdapter (){
		handler = new Handler();
	}
	
	public static ToastUtilAdapter getInstance(){
		if(instance == null){
			synchronized (ToastUtilAdapter.class) {
				if(instance == null){
					instance = new ToastUtilAdapter();
				}
			}
		}
		return instance;
	}

	public void toast(final String toastMsg){
		if(context== null){
			return ;
		}
		if (TextUtils.isEmpty(toastMsg)){
            return;
        }
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (toast == null) {  
					toast = Toast.makeText(context.get(), toastMsg, Toast.LENGTH_SHORT);
		        } else {  
		        	toast.setText(toastMsg);  
		        	toast.setDuration( Toast.LENGTH_SHORT);  
		        }  
				toast.show();  
			}
		});
	}
	
	public void toast(int resId){
		if(context == null){
			return ;
		}
		toast( context.get().getResources().getString(resId));
	}
	
}
