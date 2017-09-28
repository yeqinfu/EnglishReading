/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by yeqinfu on 2017/8/23.
 */

public class DialogUtil {
	// 定义一个显示消息的对话框
	public static void showDialog(final Context ctx, String msg, boolean goHome) {
		// 创建一个AlertDialog.Builder对象
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(msg).setCancelable(false);
		if (goHome) {
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
		}
		else {
			builder.setPositiveButton("确定", null);
		}
		builder.create().show();
	}

	// 定义一个显示指定组件的对话框
	public static void showDialog(Context ctx, View view) {
		new AlertDialog.Builder(ctx).setView(view).setCancelable(false).setPositiveButton("确定", null).create().show();
	}
}
