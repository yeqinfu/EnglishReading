/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * @author Administrator
 *
 */

public class CustomDialog extends Dialog {
	private Context context;
	private int gravity;
	private int widthPercent;//宽度占比
	public CustomDialog(Context context) {
		super(context);
	}

	/**
	 * 
	 * @param context
	 * @param theme
	 *            涓婚椋庢牸
	 */
	public CustomDialog(Context context, int theme, View contentView, int gravity, int widthPercent) {
		super(context, theme);
		this.context = context;
		this.gravity = gravity;
		this.widthPercent = widthPercent;
		setContentView(contentView);
		setAttr();
	}

	private void setAttr() {
		Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(gravity);
		DisplayMetrics dMetrics = context.getResources().getDisplayMetrics();
		int width = dMetrics.widthPixels;
		lp.width = width*widthPercent/5;
	}

}
