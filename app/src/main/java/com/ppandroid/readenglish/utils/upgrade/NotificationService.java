/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils.upgrade;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ppandroid.readenglish.R;
import com.ppandroid.readenglish.utils.NetworkUtils;
import com.ppandroid.readenglish.utils.Utils_DateFormat;
import com.ppandroid.readenglish.utils.Utils_File;

import java.io.File;
import java.math.BigDecimal;

/**
 * 下载新版本apk的服务（非绑定服务，在MainActivity销毁时关闭）
 * 
 * @author Administrator
 * 
 */
public class NotificationService extends Service {

	IntentFilter filter;
	Context context;
	private static final int NOTIFY_ID = 0;
	private NotificationManager mNotificationManager;
	Notification mNotification;
	private String apkFilePath;
	private String apkName;
	private DownloadTask task;
	private BN_VersionInfo versionInfo;
	private final static int threadNum = 1; // 开启下载的线程数量
	private final static int MSG_NOTIFICATION = 0x03;
	private final static int MSG_NOTIFICATION_CANCEL = 0x04;
	private boolean flagNetWorkStateChange;// 用于网络连接状态的瞬时变化
	
	private Handler mHandler = new Handler() {
		@SuppressWarnings("deprecation")
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case MSG_NOTIFICATION:
				int result2 = msg.getData().getInt("result");
				int fileTotalSize = msg.getData().getInt("fileTotalSize");

				if (versionInfo.isCompel()) {// 强制更新
					Intent intent = new Intent();
					intent.setAction("com.android.qiangzhi_update");
					intent.putExtra("result", result2);
					intent.putExtra("fileTotalSize", fileTotalSize);
					sendBroadcast(intent);
				}

				// 此处RemoteViews必须每次new新的，不能重用，否则导致手机反应慢或死机
				// RemoteViews contentview2 = mNotification.contentView;
				RemoteViews contentview2 = new RemoteViews(
						context.getPackageName(), R.layout.notification_version);
				mNotification.contentView = contentview2;// 指定个性化视图

				contentview2.setTextViewText(R.id.n_process_per, result2 + "%");//百分比
				contentview2.setProgressBar(R.id.n_progress, 100, result2,false);//进度条
				contentview2.setTextViewText(R.id.n_time, Utils_DateFormat.getTime());//时间
				
				//fileTotalSize是计算出来的大小，不是后台配置的值
				String fileSizeStr = Utils_File.formatFileSize(fileTotalSize);
				if (!TextUtils.isEmpty(fileSizeStr)) {
					double size = Double.parseDouble(fileSizeStr.substring(0,
							fileSizeStr.length() - 2));//去掉单位
					BigDecimal bg = new BigDecimal(size * result2 / 100.0);
					double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					contentview2.setTextViewText(R.id.n_process_per_total, f1
							+ "M/" + fileSizeStr);//已下载大小/总大小
				}

				// 如果进度达到了进度最大值，即下载完毕
				if (result2 == 100) {
					Toast.makeText(context, "下载完成", Toast.LENGTH_LONG).show();// 下载成功

					SharedPreferences mPreferences = context
							.getSharedPreferences("bzwAppInfo",
									Context.MODE_PRIVATE);
					Editor mEditor = mPreferences.edit();
					mEditor.putBoolean(
							context.getString(R.string.download_complete_flag),
							true);
					mEditor.commit();
					// 下载完毕后变换通知形式
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;// 点击后消失
					mNotification.contentView = null;
					// Intent intent = new
					// Intent(context,InstallApkActivity.class);
					Intent intent = new Intent();
					intent.putExtra("apkFilePath", apkFilePath);
					// 告知已完成
					// intent.putExtra("completed", "yes");
					// 更新参数,注意flags要使用FLAG_UPDATE_CURRENT
					PendingIntent contentIntent = PendingIntent.getActivity(
							context, 0, intent,
							PendingIntent.FLAG_UPDATE_CURRENT);
					/*mNotification.setLatestEventInfo(context, "保障网", "已下载完成",
							contentIntent);*/
                    mNotification=new Notification.Builder(getBaseContext())
                            .setContentTitle(getString(R.string.app_name))
                            .setContentText("下载完成！")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .build();
                    mNotification.deleteIntent=contentIntent;
					installApk(apkFilePath);
				}

				mNotificationManager.notify(NOTIFY_ID, mNotification);
				break;
			case MSG_NOTIFICATION_CANCEL:
				if (mNotificationManager != null) {
					mNotificationManager.cancel(NOTIFY_ID);
				}
				break;

			default:
				break;
			}
		};
	};

	@Override
	public void onCreate() {
		super.onCreate();
		context = NotificationService.this;
		filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(broadcastReceiver, filter);

	}

	@Override
	public IBinder onBind(Intent intent) {// 重写onBind方法
		return null;
	}

	@Override
	public int onStartCommand(Intent data, int flags, int startId) {

		SharedPreferences mPreferences = context.getSharedPreferences(
				"bzwAppInfo", Context.MODE_PRIVATE);
		boolean isComplete = mPreferences.getBoolean(
				context.getString(R.string.download_complete_flag), false);
		versionInfo = new BN_VersionInfo();
		
		versionInfo.setVersionString(mPreferences.getString("versionName_v", ""));
		versionInfo.setNote(mPreferences.getString("updateLog_v", ""));
		versionInfo.setDownloadUrl(mPreferences.getString("downloadUrl_v", ""));
		versionInfo.setCompel(mPreferences.getBoolean("updateInstall_v", false));
		versionInfo.setSize(mPreferences.getString("size_v", ""));

		String savePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/BZW_User/update/";
		apkName = versionInfo.getVersionString() + ".apk";
		File apkFile = new File(savePath + apkName);
		if (!isComplete || !apkFile.exists()) {
			if (NetworkUtils.isNetworkAvaiable(context)) {
				
				sendNotification();// 发送通知

				if (versionInfo.isCompel()) {// 强制更新
					Intent intent = new Intent(NotificationService.this,
							TranslucentActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 从非Activity组件启动Activity要加此句
					startActivity(intent);
				}

				String storageState = Environment.getExternalStorageState();
				if (storageState.equals(Environment.MEDIA_MOUNTED)) {
					File file = new File(savePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					apkFilePath = savePath + apkName;
					// 开始下载的相关操作
					task = new DownloadTask(versionInfo.getDownloadUrl(), file);
					new Thread(task).start();// 开辟子线程完成下载操作

					SharedPreferences mPreferences2 = context
							.getSharedPreferences("bzwAppInfo",
									Context.MODE_PRIVATE);
					Editor mEditor = mPreferences2.edit();
					mEditor.putBoolean(
							context.getString(R.string.download_complete_flag),
							false);
					mEditor.commit();

				} else {
					Toast.makeText(context, "没有内存卡", Toast.LENGTH_SHORT).show();
				}
			}
		}
		return super.onStartCommand(data, flags, startId);
	}

	@Override
	public void onDestroy() {// 重写onDestroy方法
		unregisterReceiver(broadcastReceiver);
		mHandler.sendEmptyMessageDelayed(MSG_NOTIFICATION_CANCEL, 600);//关闭通知栏
		super.onDestroy();
	}

	/**
	 * 发送通知
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void sendNotification() {

		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotification =new Notification.Builder(getBaseContext())
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(getString(R.string.app_name)+"下载中...")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();


        // 放置在"正在运行"栏目中
		// mNotification.flags = Notification.FLAG_ONGOING_EVENT;

		RemoteViews contentView = new RemoteViews(this.getPackageName(),
				R.layout.notification_version);
		mNotification.contentView = contentView;// 指定个性化视图

		Intent intent = new Intent();
		// 下面两句是 在按home后，点击通知栏，返回之前activity 状态;
		// 有下面两句的话，假如service还在后台下载， 在点击程序图片重新进入程序时，直接到下载界面，相当于把程序MAIN 入口改了 - -
		// intent.setAction(Intent.ACTION_MAIN);
		// intent.addCategory(Intent.CATEGORY_LAUNCHER);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		// mNotification.setLatestEventInfo(this, "title",
		// "text",contentIntent);
		// 指定内容意图
		mNotification.contentIntent = contentIntent;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
	}

	/**
	 * 安装apk
	 */
	private void installApk(String apkFilePath) {

		File apkfile = new File(apkFilePath);
		if (!apkfile.exists()) {
			return;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	private final class DownloadTask implements Runnable {

		private String path;// 下载路径
		private File saveDir;// 保存路径
		private FileDownloader loader;// 文件下载器

		public DownloadTask(String path, File saveDir) {
			this.path = path;
			this.saveDir = saveDir;
		}

		public void run() {

			try {
				loader = new FileDownloader(context, path, saveDir, threadNum,
						apkName);
				/**
				 * DownloadProgressListener是一个接口，onDownloadSize()为未实现的方法。
				 * onDownloadSize()方法会在download方法内部被动态赋值
				 * 监听下载数量的变化,如果不需要了解实时下载的数量,可以设置为null
				 */
				loader.download(new DownloadProgressListener() {
					public void onDownloadSize(int size) {

						float num = (float) size / (float) loader.getFileSize();
						final int result = (int) (num * 100);
						Message msg2 = new Message();
						msg2.what = MSG_NOTIFICATION;
						msg2.getData().putInt("result", result);
						msg2.getData().putInt("fileTotalSize",
								loader.getFileSize());
						mHandler.sendMessageDelayed(msg2, 500);

					}
				});

			} catch (Exception e) {
				e.printStackTrace();
				mHandler.sendMessage(mHandler.obtainMessage(-1));
			}

		}

		/**
		 * 退出下载
		 */
		public void exit() {
			if (loader != null)
				loader.exit();
		}
	}

	/*
	 * 
	 * 网络状态变化的广播接收器
	 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (NetworkUtils.isNetworkAvaiable(context)) {
				
				if (flagNetWorkStateChange) {
					flagNetWorkStateChange = false;

					// 网络重新连接后，继续下载，通知显示进度
					SharedPreferences mPreferences = context
							.getSharedPreferences("bzwAppInfo",
									Context.MODE_PRIVATE);
					boolean isComplete = mPreferences.getBoolean(
							context.getString(R.string.download_complete_flag),
							false);
					Log.i("", "是否下载完整：" + isComplete);
					if (!isComplete) {
						new Thread(task).start();// 开辟子线程完成下载操作
					}
				}

			} else {

				flagNetWorkStateChange = true;

				if (task != null) {
					task.exit();// 退出下载
				}
				// 网络断开时，停止下载，取消通知栏
				mHandler.sendEmptyMessageDelayed(MSG_NOTIFICATION_CANCEL, 1500);
			}
		}
	};
}
