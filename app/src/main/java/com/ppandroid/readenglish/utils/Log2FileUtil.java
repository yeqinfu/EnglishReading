/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;

import android.os.Environment;

import com.ppandroid.readenglish.APP;
import com.ppandroid.readenglish.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Log2FileUtil {

	public static final String LogFileDir = "fileLog";
	public static final String LogFileName = "Android_Log.txt";

    public static void saveError2Sdcard(Throwable e){
        StringBuffer sb = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        Throwable cause = e.getCause();
        // 循环着把所有的异常信息写入writer中
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.close();// 记得关闭
        String result = writer.toString();
        sb.append(result);
       saveLog2Sdcard("+++++++++++++++++++++++++++++++++++++++++++++++++++++\n" + sb.toString());
    }

	/**
	 * 日志保存到sdcard中
	 * @param log
	 */
	public static void saveLog2Sdcard(String log) {
		if (true) {
			try {

				File file = AppFileManager.getInstance(APP.Companion.getContext()).createFile(LogFileDir, LogFileName);
				DebugLog.v("#####-->" + file.getAbsolutePath());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				BufferedWriter out = null;
				try {
					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
					out.write(sdf.format(new Date()) + " : " + new String(log.getBytes(), "UTF-8") + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (out != null) {
							out.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void copyDBToAssert() {
		String DB_PATH = String.format("/data/data/%1$s/databases/medicine.sqlite",
				APP.Companion.getContext().getResources().getResourcePackageName(R.string.app_name));
		if (new File(DB_PATH).exists() && AppFileManager.getInstance(APP.Companion.getContext()).hasSDCard()) {
			try {
				// 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在
				File f = new File(DB_PATH);
				// 如 database 目录不存在，新建该目录
				if (!f.exists()) {
					f.mkdir();
				}
				FileInputStream fis = new FileInputStream(new File(DB_PATH));
				// 输出流
				OutputStream os = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "qwei.sqlite"));
				// 文件写入
				byte[] buffer = new byte[1024];
				int length;
				while ((length = fis.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				// 关闭文件流
				os.flush();
				os.close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
