package com.ppandroid.readenglish;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ppandroid.readenglish.base.AC_Base;
import com.ppandroid.readenglish.utils.DebugLog;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyH5Activity extends AC_Base {
	private WebView		detilWeb;
	private ImageView	iv_share;
	private String		url;

	public static Intent createIntent(Context context, String title, String url) {
		Intent intent = new Intent();
		intent.putExtra("title", title);
		intent.putExtra("url", url);
		intent.setClass(context, MyH5Activity.class);
		return intent;
	}
	public static Intent createIntent(Context context,String url) {
		Intent intent = new Intent();
		intent.putExtra("url", url);
		intent.setClass(context, MyH5Activity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_h5);
		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		String title = intent.getStringExtra("title");

		DebugLog.d("http", url);

		detilWeb = (WebView) findViewById(R.id.webView1);
		WebSettings settings = detilWeb.getSettings();
		settings.setJavaScriptEnabled(true);// 设置支持js
		settings.setDomStorageEnabled(true);
		String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
		settings.setAppCachePath(appCachePath);
		settings.setAllowFileAccess(true);
		settings.setAppCacheEnabled(true);
		detilWeb.setWebViewClient(new MyWebViewClient());
		detilWeb.setWebChromeClient(new MyWebChromeClient());
		detilWeb.loadUrl(url);
	}

	class MyWebViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(final WebView view, String url) {
			// 获取上下文, H5PayDemoActivity为当前页面
			final Activity context = MyH5Activity.this;
			if (!(url.startsWith("http") || url.startsWith("https"))) {
				return true;
			}
			view.loadUrl(url);
			return true;
		}

		// 拦截非超链接
		@Override
		public void onLoadResource(WebView view, String url) {
			super.onLoadResource(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}
	}

	class MyWebChromeClient extends WebChromeClient {
		public void onProgressChanged(WebView view, int newProgress) {}

		@Override
		public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
			AlertDialog.Builder b = new AlertDialog.Builder(MyH5Activity.this);
			b.setTitle("来自网页消息");
			b.setMessage(message);
			b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					result.confirm();
					dialog.dismiss();
				}
			});
			b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					result.cancel();
					dialog.dismiss();
				}
			});
			b.create().show();
			return true;
		}
	}

	private Map<String, Target> methodCache = new LinkedHashMap<>();

	private class Target {
		Method	method;
		Object	owner;

		public Target(Object owner, Method method) {
			this.method = method;
			this.owner = owner;
		}
	}

}
