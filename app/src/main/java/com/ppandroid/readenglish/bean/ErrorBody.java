/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.bean;

import com.google.gson.annotations.Expose;
import com.ppandroid.im.bean.BaseBody;

/**
 * Created by yeqinfu on 2017/4/24.
 */

public class ErrorBody extends BaseBody {
	@Expose
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
