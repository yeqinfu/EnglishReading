/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ryan
 * 
 * @creation 2014-2-24
 */
public class Utils_DateFormat {

	public static String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
	public static String YYYYMMDD = "yyyy-MM-dd";
	public static String MMDDHHMM = "MM-dd HH:mm";

	public static String timeFormatWithFormatStr(Date date,String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}

	public static Date formatYYYYMMToDate(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date formatYYYYMMddToDate(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String MMddDateFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(date);
	}

	public static String dateFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static Date dateFormat(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String timeFormat(Date date, Boolean is24) {
		String str = "";
		if (is24) {
			str = "yyyy-MM-dd HH:mm";
		} else {
			str = "yyyy-MM-dd hh:mm";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(date);
	}
	
	public static String timeFormatHHmm(Date date) {
		String str = "HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(date);
	}

	/**
	 * 获取当前时间
	 * 
	 * @param str
	 *            为空则默认yyyy-MM-dd
	 * @return
	 */
	public static String getDay(String str) {
		if (str.equals("")) {
			str = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		Date date = new Date(System.currentTimeMillis());
		return sdf.format(date);
	}
	public static String getTime() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = new Date(System.currentTimeMillis());
		return sdf.format(date);
	}
	
	
	public static String timeFormatYYYYMM(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		return sdf.format(date);
	}

	public static Date timeFormatYYYYMM(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String timeFormat2(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
		return sdf.format(date);
	}

	public static String timeFormatYYYYMMdd(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String YYYYMMTimeFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(date);
	}

	public static String getHaveTime(String intervalHaveTime, String str) {
		SimpleDateFormat ft = new SimpleDateFormat(str);
		SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = ft2.parse(intervalHaveTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ft.format(date);
	}

	public static String getHaveTime(String intervalHaveTime, String str,
			String oringalStr) {
		String time = "";
		SimpleDateFormat ft = new SimpleDateFormat(str);
		SimpleDateFormat ft2 = new SimpleDateFormat(oringalStr);
		Date date = null;
		try {
			date = ft2.parse(intervalHaveTime);
			time = ft.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 相差几天time1-time2
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int getQuot(String time1, String time2) {
		long quot = 0;
		float mfloat = 0.00f;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();

			mfloat = quot / 1000.0f / 60.0f / 60.0f / 24.0f;

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (mfloat == 0.0) {
			mfloat = 0.0f;
		} else if (mfloat < 0 && mfloat > -1.0) {
			mfloat = -1.0f;
		}

		return (int) mfloat;
	}

	/**
	 * 相差几天time1-time2
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int getQuot(long time1, String time2) {
		long quot = 0;
		Date date1 = new Date(time1);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date1 = ft.parse(ft.format(date1));
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) quot;
	}

	/**
	 * 判断date1是否大于date2
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDate1GreaterThanDate2(long date1, String date2) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		try {
			date = ft.parse(date2);

			if (date1 > date.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 判断date1是否大于date2
	 * 
	 * @return
	 */
	public static boolean isStrDate1GreaterThanDate2(String dateStr1, String dateStr2) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date1;
		Date date2;
		try {
			date1 = ft.parse(dateStr1);
			date2 = ft.parse(dateStr2);

			if (date1.getTime() >= date2.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * 两个年月日的比较
	 * @param dateStr1
	 * @param dateStr2
	 * @return
	 */
	public static boolean isDate1GreaterDate2(String dateStr1, String dateStr2) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Date date1;
		Date date2;
		try {
			date1 = ft.parse(dateStr1);
			date2 = ft.parse(dateStr2);

			if (date1.getTime() > date2.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

	}







	/**
	 * date1 当前小时
	 * 
	 * @param action
	 * @param time
	 * @return
	 */
	public static String isDate1GreaterThanDate2(String action, int time,
			int addTime) {
		Date dt = new Date();
		Date dt2 = new Date();
		Date dt24 = new Date();
		long time2;
		long time2c;
		long time24;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sd24 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dt = sdf.parse(sdf.format(System.currentTimeMillis()));
			dt2 = sdf.parse(action);
			time2 = dt2.getTime() + ((long) time * addTime * 1000 * 60 * 60);
			dt24 = sd24.parse(sd24.format(System.currentTimeMillis()));
			time24 = dt24.getTime();
			// time2+=time24;
			time2c = time2 + time24;
			dt24 = sdf.parse("23:59");
			time24 += dt24.getTime();
			System.out.println(time2 + ":" + time24);
			if (time2c > time24) {
				return "next";
			}
			if (time2 > dt.getTime()) {
				return sdf.format(time2);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "no";
	}

	/**
	 * 增加天数
	 * 
	 * @return
	 */
	public static String addDate(String date, int value) {
		String dateString = date;
		long mDate;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = null;
		try {
			newDate = ft.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		mDate = newDate.getTime() + ((long) value) * 1000 * 60 * 60 * 24;
		dateString = ft.format(new Date(mDate));
		return dateString;
	}
}
