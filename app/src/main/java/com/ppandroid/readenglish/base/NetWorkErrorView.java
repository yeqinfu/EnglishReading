/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.base;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ppandroid.readenglish.R;

/**
 * Created by yeqinfu on 2017/4/30.
 */

public class NetWorkErrorView extends RelativeLayout implements View.OnClickListener{

    public NetWorkErrorView(Context context) {
        super(context);
    }

    public NetWorkErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NetWorkErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        onCreate();
        /*默认整个页面是消失的*/
        setViewType(NORMAL_VIEW);
        setOnClickListener(this);
    }

    private ImageView iv_img;
    private TextView tv_msg;

    private void onCreate() {
        iv_img = (ImageView) findViewById(R.id.iv_img);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
    }

    /**
     * 网络异常
     */
    public static final int NETWORK_ERROR = 10001;
    /**
     * 暂无数据
     */
    public static final int NOT_DATA = 10002;
    /**
     * 消失
     */
    public static final int NORMAL_VIEW = 10000;

    /**默认是没有网络类型*/
    private int currentType=NOT_DATA;

    /**
     * 设置当前页面类型
     * @param type ：NETWORK_ERROR，NOT_DATA
     */
    public void setViewType(int type){
        currentType=type;
        if (currentType==NOT_DATA){//暂无数据
            setVisibility(VISIBLE);
            iv_img.setImageResource(R.drawable.no_data);
            tv_msg.setText("暂无数据");
        }else if (currentType==NETWORK_ERROR){//网络异常
            setVisibility(VISIBLE);
            iv_img.setImageResource(R.drawable.network_error);
            tv_msg.setText("网络不给力，点击屏幕重试~");
        }else{
            setVisibility(GONE);
            currentType=NOT_DATA;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==getId()){
            if (currentType==NETWORK_ERROR&&listener!=null){
                listener.clickRefresh();
            }
        }
    }

    public interface RefreshNetworkErrorListener{
        void clickRefresh();
    }
    RefreshNetworkErrorListener listener;

    public void setListener(RefreshNetworkErrorListener listener) {
        this.listener = listener;
    }
}
