/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.bean;

import android.graphics.Color;

/**
 * Created by yeqinfu on 2017/8/10.
 */

public class BN_Vertical {
	String	topText="";
	String	bottomText="";
	float	totalHeight=0.0f;
	float	realHeight=0.0f;
	int		lineColor	= Color.WHITE;

    float startX=0f;
    float startY=0f;
    float endX=0f;
    float endY=0f;

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

    public String getTopText() {

		return topText;
	}

	public void setTopText(String topText) {
		this.topText = topText;
	}

	public String getBottomText() {
		return bottomText;
	}

	public void setBottomText(String bottomText) {
		this.bottomText = bottomText;
	}

	public int getLineColor() {
		return lineColor;
	}

	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}

	public float getTotalHeight() {
		return totalHeight;
	}

	public void setTotalHeight(float totalHeight) {
		this.totalHeight = totalHeight;
	}

	public float getRealHeight() {
		return realHeight;
	}

	public void setRealHeight(float realHeight) {
		this.realHeight = realHeight;
	}
}
