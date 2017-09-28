/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;

public class AppExceptionHandler implements UncaughtExceptionHandler {

	private static AppExceptionHandler instance = new AppExceptionHandler();
	private UncaughtExceptionHandler mDefaultHandler;
	private Context context;

	private AppExceptionHandler() {

	}

	public static AppExceptionHandler getInstance() {
		return instance;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		ex.printStackTrace();
		saveCrashInfo2File(ex);
		mDefaultHandler.uncaughtException(thread, ex);
	}

	public void init(Context context) {
		this.context = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器
		Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理器
	}

	/**
	 * 崩溃日志保存到sdcard目录下
	 * @param ex
	 */
	private void saveCrashInfo2File(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		ex.printStackTrace(pw);
		Throwable cause = ex.getCause();
		// 循环着把所有的异常信息写入writer中
		while (cause != null) {
			cause.printStackTrace(pw);
			cause = cause.getCause();
		}
		pw.close();// 记得关闭
		String result = writer.toString();
		sb.append(result);
		Log2FileUtil.saveLog2Sdcard("+++++++++++++++++++++++++++++++++++++++++++++++++++++\n" + sb.toString());
	}

}
