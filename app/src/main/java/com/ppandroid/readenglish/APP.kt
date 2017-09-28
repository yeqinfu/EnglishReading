/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish
import android.app.Application
import android.content.Context
import com.ppandroid.readenglish.utils.AppExceptionHandler
import com.ppandroid.readenglish.utils.DebugLog
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.squareup.leakcanary.LeakCanary




/**
 * Created by yeqinfu on 2017/8/1.
 */

class APP : Application() {


    companion object {
        var context: Application? = null
        fun getContext(): Context? {
            return context
        }

        fun toLogin() {
         /*   var it = Intent()
           // it.setClass(context, AC_Login::class.java)
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(it)*/
        }
    }

    override fun onCreate() {
        super.onCreate()

        //持久化cookie
     /*   val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(this))
        val okHttpClient = OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build()
        OkHttpUtils.initClient(okHttpClient)*/







        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)


        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
            ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate)//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater { context, layout ->
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate)
        }

        context = this
        DebugLog.d("+++++++++++++++++++++++++++++++++++++++++++context is" + context)
        /*****************************************************************
         * 闪退处理
         *****************************************************************/
        val mReportAppError = AppExceptionHandler.getInstance()
        mReportAppError.init(this)
        /* if (!EventBus.getDefault().isRegistered(this)) {
             EventBus.getDefault().register(this)
         }*/

    }


}
