/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.http;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.ppandroid.im.bean.BaseBody;
import com.ppandroid.im.utils.Contants;
import com.ppandroid.readenglish.bean.ErrorBody;
import com.ppandroid.readenglish.http.callback.StringCallback;
import com.ppandroid.readenglish.utils.DebugLog;
import com.ppandroid.readenglish.utils.Log2FileUtil;
import com.ppandroid.readenglish.utils.Utils_SharedPreferences;
import com.ppandroid.readenglish.utils.activitymanager.ActivityManager;
import com.ppandroid.readenglish.utils.info.Utils_UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by yeqinfu on 2017/7/31.
 */

public class Http {

	public static <T extends BaseBody> void get(final Context context, String url, final Class<T> tt, final MyCallBack<T> callBack) {
		final WeakReference<Context> mWeakContext = new WeakReference<Context>(context);
		url = addBaseParams(context, url);
		if (!url.startsWith("http")) {
			url = Contants.Companion.getBaseUrl() + url;
		}
		DebugLog.i("httpget==>" + url + "\n\n");
		OkHttpUtils.get().url(url).build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				e.printStackTrace();
				ErrorBody errorBody = new ErrorBody();
				errorBody.setMessage(e.getMessage());
				callBack.onError(errorBody);
			}

			@Override
			public void onResponse(String response, int id) {
				DebugLog.i("httpget==>" + response + "\n\n");
				if (isGoodJson(response)) {
					ErrorBody errorBody = parseErrorBody(response);
					if (errorBody == null) {
						Gson gson = new Gson();
						callBack.onResponse(gson.fromJson(response, tt));
					}
					else {
						callBack.onError(errorBody);
						parseError(mWeakContext.get(), errorBody);
					}

				}
				else {
					ErrorBody body = new ErrorBody();
					body.setMessage("json解析异常");
					callBack.onError(body);
				}
			}
		});
	}

	public static <T extends BaseBody> void post(final Context context, String url, Map<String, String> params, final Class<T> tt, final MyCallBack<T> callBack) {
		params = addBaseParams(context, params);
		final WeakReference<Context> mWeakContext = new WeakReference<Context>(context);
		if (!url.startsWith("http")) {
			url = Contants.Companion.getBaseUrl() + url;
		}
		DebugLog.i("httppost==>" + url + "\n\n" + params.toString() + "\n\n");
		OkHttpUtils.post().params(params).url(url).build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(String response, int id) {
				DebugLog.i("httpget==>" + response + "\n\n");
				if (isGoodJson(response)) {
					ErrorBody errorBody = parseErrorBody(response);
					if (errorBody == null) {
						Gson gson = new Gson();
						callBack.onResponse(gson.fromJson(response, tt));
					}
					else {
						callBack.onError(errorBody);
						parseError(mWeakContext.get(), errorBody);
					}

				}
				else {
					ErrorBody body = new ErrorBody();
					body.setMessage("json解析异常");
					callBack.onError(body);
				}

			}
		});
	}

	/**
	 * 错误码处理
	 *
	 * @param errorBody
	 */
	private static void parseError(Context context, ErrorBody errorBody) {
		if (errorBody.getErrorCode() == null) {
			return;
		}
		switch (errorBody.getErrorCode()) {
		case HttpConstant.SERVICE_ERROR://服务器异常
			//toast(context, R.string.service_error);
			break;
		case HttpConstant.TOKEN_BLANK_ERROR://TOKEN 为空
			//toast(context, R.string.token_invalid);
			clearInfoAndLogin(context);
			break;
		case HttpConstant.TOKEN_INVALID_ERROR://TOKEN失效 尝试MD5
			getTokenByMD5(context);
			break;
		case HttpConstant.RELOAAGIN_MD5_INVALID_ERROR://自动登录已过期，请重新登录
			//toast(context, errorBody.getMessage());
			clearInfoAndLogin(context);
			break;
		case HttpConstant.BLANK_ERROR://校验数据为空 比如验证码输入为空，评论输入为空
			//toast(context, errorBody.getMessage());
			break;
		case HttpConstant.CHECK_ERROR:
			//toast(context, errorBody.getMessage());
			break;
		default:
			if (!TextUtils.isEmpty(errorBody.getMessage())) {
				//toast(context, errorBody.getMessage());
			}
			break;

		}

	}

	private static void clearInfoAndLogin(Context context) {
		Utils_UserInfo.clearUserInfo(context);
	//	APP.Companion.toLogin();
		ActivityManager.getActivityManager().popAllActivity();
		((Activity) context).finish();
	}

	private static boolean isAutoCheck = false;

	/**
	 * 用md5获取新的token
	 */
	public static void getTokenByMD5(final Context context) {
		if (isAutoCheck) {
			return;
		}
		isAutoCheck = true;
		DebugLog.i("http", "====================== token 失效 尝试MD5 登录====================================");
		Utils_SharedPreferences sp = new Utils_SharedPreferences(context);
		String url = Contants.Companion.getBaseUrl() + "user/login/auto/check.json" + "?id=" + sp.getString("userID", null) + "&md5=" + sp.getString("MD5", null);
        url += "&appId=1"+"&timeStamp=" + getTimeStemp() ;
      //  url+="&sign=" + String2UTF8(Utils_Sign.signGet(url));
        DebugLog.d("http->md5登录" + url);
		Log2FileUtil.saveLog2Sdcard("++++++++++++++++++++++http->md5登录+++++++++++++++++++++++++++++++\n" + url);


	}

	@Nullable
	public static Object parseJson(String json, Object response) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
			boolean boolean1 = jsonObject.getBoolean("isSuccess");
			if (boolean1) {
				Gson gson = new Gson();
				response = gson.fromJson(json, response.getClass());
				return response;
			}
			else {
				DebugLog.e(jsonObject.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ErrorBody parseErrorBody(String json) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
			boolean boolean1 = jsonObject.getBoolean("isSuccess");
			if (!boolean1) {
				Gson gson = new Gson();
				ErrorBody errorBody = gson.fromJson(json, ErrorBody.class);
				if (errorBody != null) {
					return errorBody;
				}
				else {
					return null;
				}
			}
			else {
				return null;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isGoodJson(String json) {
		if ("null".equals(json)) {
			return false;
		}

		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			DebugLog.e("bad json: " + json);
			return false;
		}
	}

	/**
	 * 添加基本参数post
	 *
	 * @param params
	 * @return
	 */
	private static Map<String, String> addBaseParams(Context context, Map<String, String> params) {
		Utils_SharedPreferences sp = new Utils_SharedPreferences(context);
		if (!TextUtils.isEmpty(sp.getString("Token", ""))) {
			params.put("X-Auth-Token", sp.getString("Token", ""));
		}
		params.put("timeStamp", getTimeStemp());
		params.put("appId", Utils_UserInfo.getUserId(context));
     //   params.put("sign",  String2UTF8(Utils_Sign.signPost(params)));
		return params;
	}

	/**
	 * 添加基本参数get
	 *
	 * @param context
	 * @param url
	 * @return
	 */
	public static String addBaseParams(Context context, String url) {
		Utils_SharedPreferences sp = new Utils_SharedPreferences(context);
		if (!url.contains("X-Auth-Token=") && (!TextUtils.isEmpty(sp.getString("Token", "")))) {
			if (url.contains("?")) {
				url += "&X-Auth-Token=" + sp.getString("Token", "");
			}
			else {
				url += "?X-Auth-Token=" + sp.getString("Token", "");
			}
		}
		String split;
        if (url.contains("?")) {
            split="&";
        }else{
            split="?";
        }
        url += split+"appId=1"+"&timeStamp=" + getTimeStemp() ;

     //   url+="&sign=" + String2UTF8(Utils_Sign.signGet(url));
		return url;
	}

	private static String getTimeStemp() {
		return new Date().getTime()+"";
	}
    /**
     * 将string 数据转换成网络请求格式
     */
    protected static String String2UTF8(String string) {
        try {
            string = URLEncoder.encode(string, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }

}
