/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils.upgrade;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.ppandroid.im.bean.BaseBody;


public class BN_VersionInfo extends BaseBody implements Serializable{

    @Expose
	private String version; // 版本名称。形如 2.1.7,
    @Expose
    private String note; // 版本描述,
    @Expose
    private String size; //版本文件大小。形如 615M,
    @Expose
    private boolean compel;//该版本是否强制升级为最高版本,
    @Expose
    private String downloadUrl; // 最新版本下载地址
    @Expose
    private String versionString; //版本名称

    public String getDownloadUrl() {
		return downloadUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isCompel() {
		return compel;
	}

	public void setCompel(boolean compel) {
		this.compel = compel;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}


	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

    public String getVersionString() {
        return versionString;
    }

    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }
}
