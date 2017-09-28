/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.http.builder;


import com.ppandroid.readenglish.http.request.PostStringRequest;
import com.ppandroid.readenglish.http.request.RequestCall;

import okhttp3.MediaType;

/**
 * Created by zhy on 15/12/14.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }


}
