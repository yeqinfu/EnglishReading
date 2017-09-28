/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.base;


import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ppandroid.readenglish.widget.SwipeBackLayout;


/**
 * Created by yeqinfu on 2017/9/12.
 */

public class AC_SwipeBase extends AppCompatActivity implements SwipeBackLayout.SwipeBackListener {
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(getContainer());
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        swipeBackLayout.addView(view);
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this){

        };
        swipeBackLayout.setOnSwipeBackListener(this);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
    /*    ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.black_p50));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);*/
        container.addView(swipeBackLayout);
        return container;
    }


    public void setDragEdge(SwipeBackLayout.DragEdge dragEdge) {
        swipeBackLayout.setDragEdge(dragEdge);
    }

    public void setEnablePullToBack(boolean b){
        swipeBackLayout.setEnablePullToBack(b);
    }
    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }

    @Override
    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
     //   ivShadow.setAlpha(1 - fractionScreen);
    }
}
