/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.http;

import com.ppandroid.im.bean.BaseBody;
import com.ppandroid.readenglish.bean.ErrorBody;

/**
 * Created by yeqinfu on 2017/7/31.
 */

public interface MyCallBack<T extends BaseBody> {
	void onResponse(T response);
	void  onError(ErrorBody error);
}
