/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils.upgrade;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ppandroid.readenglish.R;
import com.ppandroid.readenglish.widget.CustomDialog;


/**
 * 强制升级时的下载进度对话框 Dialog是依附于activity存在的,Service无法直接启动Dialog.
 * 解决办法：在dialog的背后加一个透明的activity。即先显示一个透明的activity，
 * 再使用activity的context显示dialog。需要注意的是，
 * activity在destroy的时候一定要把dialog给dismiss掉，否则activity消失但dialog还在， 会crash。
 * 
 * @author Administrator
 * 
 */
public class TranslucentActivity extends Activity {

	ProgressBar dialog_bar;
	TextView dialog_percent;
	CustomDialog mDownloadDialog;
	//MedicineApplication application;
	private final static int MSG_NOTIFICATION = 0x03;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case MSG_NOTIFICATION:
				int result2 = msg.getData().getInt("result");
				int fileTotalSize = msg.getData().getInt("fileTotalSize");

				dialog_bar.setProgress(result2);
				dialog_percent.setText(result2 + "%");

				// 如果进度达到了进度最大值，即下载完毕
				if (result2 == 100) {
					mDownloadDialog.dismiss();
					//application.exit();// 退出应用
					finish();
					System.exit(0);
				}

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// MedicineApplication application = (MedicineApplication) this.getApplication();
		//application.addActivity(this);
		
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.android.qiangzhi_update");// 接收下载

		registerReceiver(broadcastReceiver, filter);
		showDownloadDialog();
	}

	/**
	 * 显示下载进度对话框
	 */
	private void showDownloadDialog() {
		if (mDownloadDialog != null) {
			mDownloadDialog.dismiss();
		}

		View mView = LayoutInflater.from(this).inflate(
				R.layout.dialog_download, null);

		dialog_bar = (ProgressBar) mView.findViewById(R.id.n_progress);
		dialog_percent = (TextView) mView.findViewById(R.id.n_process_per);

		mDownloadDialog = new CustomDialog(this, R.style.family_dialog_theme,
				mView, Gravity.CENTER, 4);
		mDownloadDialog.setCanceledOnTouchOutside(false);
		mDownloadDialog.setCancelable(false);
		mDownloadDialog.show();
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int result2 = intent.getIntExtra("result", 0);
			int fileTotalSize = intent.getIntExtra("fileTotalSize", 0);

			Message msg2 = new Message();
			msg2.what = MSG_NOTIFICATION;
			msg2.getData().putInt("result", result2);
			msg2.getData().putInt("fileTotalSize", fileTotalSize);
			mHandler.sendMessageDelayed(msg2, 0);
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDownloadDialog.dismiss();
		unregisterReceiver(broadcastReceiver);

	}


}
