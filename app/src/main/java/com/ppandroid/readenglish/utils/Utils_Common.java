/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by yeqinfu on 2017/5/29.
 */

public class Utils_Common {
   static String[] colors={
            "#FA6D6F",
            "#FE8D25",
            "#FFB338",
            "#6CC37E",
            "#2ED9A4"
    };
    public static int getRandomColor(){
       return  Color.parseColor(colors[(int) (Math.random()*100%colors.length)]);
    }

    public static double parseNumberString(String s){
        String d=findNumberFromStr(s);
        double result=paraseDouble(d);
        if (s.contains("万")){
            result=result*10000D;
        }
        return result;
    }
    public static String findNumberFromStr(String s){
        String regEx="[^0-9||/.]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.replaceAll("").trim();
    }
    public static double paraseDouble(String s){
        double result=0.0;
        try {
            result=Double.parseDouble(s);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public  static <T> T parseJson(String json,Class<T> tt){
        Gson gson=new Gson();
        try{
            return gson.fromJson(json,tt);
        }catch (Exception e){
           e.printStackTrace();
        }
        return null;
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            //	           return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                }
                else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
    //当你在项目中需要查下缓存大小，就使用getTotalCacheSize(Context)方法，清空缓存，就使用clearAllCache(Context)方法
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }


    public static Intent getIntent(Context paramContext) {
        StringBuilder localStringBuilder = new StringBuilder().append("market://details?id=");
        String str = paramContext.getPackageName();
        localStringBuilder.append(str);
        Uri localUri = Uri.parse(localStringBuilder.toString());
        return new Intent("android.intent.action.VIEW", localUri);
    }

    //直接跳转不判断是否存在市场应用
    public static void start(Context paramContext, String paramString) {
        Uri localUri = Uri.parse(paramString);
        Intent localIntent = new Intent("android.intent.action.VIEW", localUri);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        paramContext.startActivity(localIntent);
    }

    public static boolean judge(Context paramContext, Intent paramIntent) {
        List<ResolveInfo> localList = paramContext.getPackageManager().queryIntentActivities(paramIntent, PackageManager.GET_INTENT_FILTERS);
        if ((localList != null) && (localList.size() > 0)) {
            return false;
        } else {
            return true;
        }
    }

    public static  boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        else {
            return false;
        }
    }
}
