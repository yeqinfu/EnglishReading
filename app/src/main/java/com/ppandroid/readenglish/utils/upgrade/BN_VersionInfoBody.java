/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils.upgrade;

import com.google.gson.annotations.Expose;
import com.ppandroid.im.bean.BaseBody;


/**
 * Created by yeqinfu on 2017/3/28.
 */

public class BN_VersionInfoBody extends BaseBody {


    /**
     * message : {"version":6,"versionString":"1.0.0","note":"测试","size":"32Mb","compel":true,"downloadUrl":"http://118.178.57.101//attachs/apk/ZX_BZW_1.0.0_1490608394896.apk"}
     */

    @Expose
    private BN_VersionInfo message;

    public BN_VersionInfo getMessage() {
        return message;
    }

    public void setMessage(BN_VersionInfo message) {
        this.message = message;
    }


}
