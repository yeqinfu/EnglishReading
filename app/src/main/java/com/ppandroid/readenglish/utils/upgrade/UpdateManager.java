/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils.upgrade;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ppandroid.readenglish.APP;
import com.ppandroid.readenglish.R;
import com.ppandroid.readenglish.bean.ErrorBody;
import com.ppandroid.readenglish.http.Http;
import com.ppandroid.readenglish.http.MyCallBack;
import com.ppandroid.readenglish.utils.Utils_DateFormat;
import com.ppandroid.readenglish.utils.toast.ToastUtil;
import com.ppandroid.readenglish.widget.CustomDialog;

import java.io.File;
import java.util.Calendar;


/**
 * Created by yeqinfu on 2017/3/24.
 * 升级管理类
 */

public class UpdateManager {
    private static Context context ;
    public static boolean isUpdate;//是否需要升级
    private boolean isMainpage ;//用来标示是否是首页进行版本检查：首页时为true,手动检查为false
    private static UpdateManager mUpdateManager ;
    public CustomDialog mNoticeDialog ;	//版本更新通知对话框
    private boolean isChecked;
    private SharedPreferences mPreferences ;
    private String currVersionName;
    public static UpdateManager getUpdateManager(Context ctx) {

        if (mUpdateManager == null) {
            mUpdateManager = new UpdateManager();
        }

        context = ctx;
        return mUpdateManager;

    }

    /**
     * 每次进入主页面，都要清除已经检查更新的标志，设置为未检查
     * 这样在AC_BASE 的onresume方法中根据这个标志会检查一次版本，检查完
     * 把这个标志设置为已经检查过
     * 保证了，打开app会检查一次版本情况，
     * 如果按照原来版本，在MainActivity请求一次更新接口，如果他进入这个页面先断网
     * 再打开网络，检查接口就会被跳过。而上诉方法可以避免这个情况
     */
    public static final String bzw_check_version="bzw_check_version";
    public void checkFlag(boolean isChecked ){
        this.mPreferences = context.getSharedPreferences("bzwAppInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPreferences.edit();
        mEditor.putBoolean(bzw_check_version,isChecked);
        mEditor.commit();
    }
    public boolean getCheckFlag(){
        this.mPreferences = context.getSharedPreferences("bzwAppInfo", Context.MODE_PRIVATE);
        return mPreferences.getBoolean(bzw_check_version,false);
    }
    /**
     * 主方法：检查app更新
     */
    public void checkAppUpdate(boolean isMainpage) {
        this.isMainpage = isMainpage;
        this.mPreferences = context.getSharedPreferences("bzwAppInfo", Context.MODE_PRIVATE);
        checkVersion();
    }
    /**
     * 请求---最新版本信息
     *
     */
    private void checkVersion(){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
             currVersionName = info.versionName;


            ApplicationInfo appInfo = APP.Companion.getContext().getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
         /*   String channel=appInfo.metaData.getString("UMENG_CHANNEL");
            DebugLog.d(" UMENG_CHANNEL == " + channel );*/
            String url=  "app/update/android.json?nowVersion="+info.versionCode+"&channel=bzw";
            Http.get(context, url, BN_VersionInfoBody.class, new MyCallBack<BN_VersionInfoBody>() {
                @Override
                public void onResponse(BN_VersionInfoBody response) {
                    /**检查更新完成后，把本地标志置为已检查*/
                    checkFlag(true);
                    isUpdate = true;
                    Common.setVersionInfo(response.getMessage());
                    if (Common.myVersionInfo!=null){
                        SharedPreferences.Editor mEditor = mPreferences.edit();
                        mEditor.putString("versionName_v",Common.myVersionInfo.getVersionString());
                        mEditor.putString("updateLog_v",Common.myVersionInfo.getNote() );
                        mEditor.putString("downloadUrl_v",Common.myVersionInfo.getDownloadUrl());
                        mEditor.putBoolean("updateInstall_v",Common.myVersionInfo.isCompel());
                        mEditor.putString("size_v",Common.myVersionInfo.getSize());
                        mEditor.commit();

                        if (isUpdate) {
                            if (mNoticeDialog == null || !mNoticeDialog.isShowing()) {
                                isShowNoticeDialog();
                            }
                        }
                    }
                }

                @Override
                public void onError(ErrorBody error) {
                    ToastUtil.toast(context,error.getMessage());
                }
            });

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断是否显示提示更新对话框
     */
    private void isShowNoticeDialog() {
        String saveDate = mPreferences.getString(
                context.getString(R.string.updateDate), "");
        String saveVersionName = mPreferences.getString(
                context.getString(R.string.versionName), "");
        Calendar currCalendar = Calendar.getInstance();
        String currDate = Utils_DateFormat.timeFormat(currCalendar.getTime(),
                true);
        Log.i("yeqinfu", "当前日期：" + currDate + "保存日期：" + saveDate);
        Log.i("yeqinfu", "当前获取的版本：" + Common.myVersionInfo.getVersionString() + "保存的版本："
                + saveVersionName);



        /** 强制更新或手动更新时----要弹出提示框 **/
        if (Common.myVersionInfo.isCompel() || !isMainpage) {
            NoticeDialog();
        }





        if (saveVersionName.equals("")){//说明第一次安装
            if (!isCurrVersionBigger(currVersionName,Common.myVersionInfo.getVersionString())){//如果第一次安装，但是不是最新版本则提示
                NoticeDialog();
            }else{//第一次安装，但是已经是最新版本，不做任何操作

            }
        }else{//不是第一次安装 出现不同更新包
            if (saveVersionName.equals(Common.myVersionInfo.getVersionString())){//

            }else  if (!isCurrVersionBigger(currVersionName,Common.myVersionInfo.getVersionString())) {
                NoticeDialog();
            }
        }
        if(saveDate.equals("")){//第一次安装
            if (!isCurrVersionBigger(currVersionName,Common.myVersionInfo.getVersionString())){
                NoticeDialog();
            }else{
                //do nothing
            }
        }else{
            boolean isBig = Utils_DateFormat.isStrDate1GreaterThanDate2(
                    currDate, saveDate);
            if (isBig) {
                NoticeDialog();
            }
        }
    }


    private boolean isCurrVersionBigger(String currVersionName, String versionString) {
        String str1=currVersionName.replaceAll("\\.","");
        String str2=versionString.replaceAll("\\.","");
        try {
            int a=Integer.parseInt(str1);
            int b=Integer.parseInt(str2);
            if (a>=b){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 提示更新对话框
     */
    private void NoticeDialog() {

        if (mNoticeDialog != null &&  mNoticeDialog.isShowing()) {
            mNoticeDialog.dismiss();
        }
        View mView = LayoutInflater.from(context).inflate(
                R.layout.dialog_update_notice, null);
        mNoticeDialog = new CustomDialog(context, R.style.family_dialog_theme,
                mView, Gravity.CENTER, 4);
        TextView versionNameTv = (TextView) mView
                .findViewById(R.id.versionNameTv);
        TextView logTv = (TextView) mView.findViewById(R.id.logTv);
        CheckBox checkBox = (CheckBox) mView.findViewById(R.id.checkBox);

        Button cancelBtn = (Button) mView.findViewById(R.id.cancelBtn);
        Button updateBtn = (Button) mView.findViewById(R.id.updateBtn);

        versionNameTv.setText("版本号：" + Common.myVersionInfo.getVersionString());
        logTv.setText(Html.fromHtml(Common.myVersionInfo.getNote()));
        if (Common.myVersionInfo.isCompel()) {// 强制更新
            checkBox.setVisibility(View.GONE);
            cancelBtn.setText("暂不升级");
            mNoticeDialog.setCanceledOnTouchOutside(false);
            mNoticeDialog.setCancelable(false);
            cancelBtn.setVisibility(View.GONE);
        } else {
            mNoticeDialog.setCanceledOnTouchOutside(true);
            cancelBtn.setText("以后再说");
        }

        if (!isMainpage) {// 手动检查版本
            checkBox.setVisibility(View.GONE);
        }

        isChecked = false;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                isChecked = arg1;
            }
        });


        /** "以后再说"按钮   **/
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mNoticeDialog.dismiss();

                if (!Common.myVersionInfo.isCompel()) {

                    if (isMainpage) {
                        String saveDate = "";
                        if (isChecked) {// 在首页当勾选了3天不提醒，保存当前日期
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.DATE, 3);// 保存3天后的日期
                            saveDate = Utils_DateFormat.timeFormat(
                                    calendar.getTime(), true);
                        }
                        SharedPreferences.Editor mEditor = mPreferences.edit();
                        mEditor.putString(
                                context.getString(R.string.updateDate),
                                saveDate);
                        mEditor.putString(
                                context.getString(R.string.versionName),
                                Common.myVersionInfo.getVersionString());
                        mEditor.commit();

                        if (isChecked) {
                            Toast.makeText(context, "3天内将不再提醒您更新！",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                } else {// 强制升级就关闭App
                    System.exit(0);
                }
            }
        });

        /** "立即更新"按钮   **/
        updateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mNoticeDialog.dismiss();
                // 判断是否有下载完整的安装包，安装包是否还存在
                String savePath = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/BZW_User/update/";
                String apkName = Common.myVersionInfo.getVersionString() + ".apk";
                File file = new File(savePath + apkName);

                boolean isComplete = mPreferences.getBoolean(
                        context.getString(R.string.download_complete_flag),
                        false);
                Log.i("", "是否下载完整：" + isComplete + " 文件是否存在：" + file.exists());
                if (file.exists() && isComplete) {
                    installApk(savePath + apkName);

                    if (Common.myVersionInfo.isCompel()) {
                        //MedicineApplication application = (MedicineApplication) ((Activity) context).getApplication();
                        // application.exit();// 退出应用
                        System.exit(0);
                    }
                } else {
                    // 开启服务：
                    Intent intent = new Intent(context,NotificationService.class);
                    context.startService(intent);
                }

            }
        });
        mNoticeDialog.show();
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

}
