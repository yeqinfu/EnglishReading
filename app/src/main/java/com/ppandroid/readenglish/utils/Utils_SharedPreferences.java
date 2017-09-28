/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

/**
 * @Description 保存一些简单的数据
 * @author jxb
 * @date 2015年3月14日 下午15:19:40
 */
package com.ppandroid.readenglish.utils;

import java.lang.ref.WeakReference;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

@SuppressLint("CommitPrefEdits")
public class Utils_SharedPreferences {
	
	private WeakReference<Context> mWeakReference;
	private SharedPreferences mKV;
	private Editor mEditor;
	
	/**
	 * 构造方法。
	 * @param context
	 *            打开的模式。值为Context.MODE_APPEND, Context.MODE_PRIVATE,
	 *            Context.WORLD_READABLE, Context.WORLD_WRITEABLE.
	 */
	public Utils_SharedPreferences(Context context) {
        mWeakReference=new WeakReference<Context>(context);
		mKV = mWeakReference.get().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		mEditor = mKV.edit();

	}
	
	public Utils_SharedPreferences(Context context, String fileName) {
        mWeakReference=new WeakReference<>(context);
		mKV = mWeakReference.get().getSharedPreferences(fileName, Context.MODE_PRIVATE);
		mEditor = mKV.edit();

	}
	
	/**
	 * 获取保存着的boolean对象。
	 *
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public boolean getBoolean(String key, boolean defValue) {
		return mKV.getBoolean(key, defValue);
	}
	/**
	 * 获取保存着的int对象。
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public int getInt(String key, int defValue) {
		return mKV.getInt(key, defValue);
	}
	/**
	 * 获取保存着的long对象。
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public long getLong(String key, long defValue) {
		return mKV.getLong(key, defValue);
	}
	/**
	 * 获取保存着的float对象。
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public float getFloat(String key, float defValue) {
		return mKV.getFloat(key, defValue);
	}
	/**
	 * 获取保存着的String对象。
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public String getString(String key, String defValue) {
		return mKV.getString(key, defValue);
	}
	/**
	 * 获取所有键值对。
	 * @return 获取到的所胡键值对。
	 */
	public Map<String, ?> getAll() {
		return mKV.getAll();
	}
	/**
	 * 设置一个键值对，它将在{@linkplain #commit()}被调用时保存。<br/>
	 * 注意：当保存的value不是boolean, byte(会被转换成int保存),int, long, float,
	 * String等类型时将调用它的toString()方法进行值的保存。
	 * @param key
	 *            键名称。
	 * @param value
	 *            值。
	 * @return 引用的KV对象。
	 */
	public Utils_SharedPreferences put(String key, Object value) {
		if (value instanceof Boolean) {
			mEditor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Integer || value instanceof Byte) {
			mEditor.putInt(key, (Integer) value);
		} else if (value instanceof Long) {
			mEditor.putLong(key, (Long) value);
		} else if (value instanceof Float) {
			mEditor.putFloat(key, (Float) value);
		} else if (value instanceof String) {
			mEditor.putString(key, (String) value);
		} else if (value == null) {
			mEditor.putString(key, "");
		} else {
			mEditor.putString(key, value.toString());
		}
		mEditor.commit();
		return this;
	}
	/**
	 * 清除所有键值对。
	 * @return 引用的KV对象。
	 */
	public Utils_SharedPreferences clear() {
		mEditor.clear();
		mEditor.commit();
		return this;
	}
	/**
	 * 是否包含某个键。
	 * @param key
	 *            查询的键名称。
	 * @return 当且仅当包含该键时返回true, 否则返回false.
	 */
	public boolean contains(String key) {
		return mKV.contains(key);
	}
}
