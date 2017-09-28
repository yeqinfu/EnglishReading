/*
 * Created by yeqinfu on 17-9-27 上午9:35
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.bean;

import java.util.UUID;

/**
 * Created by yeqinfu on 2017/4/6.
 */

public class ET_Refresh extends ET_Base {

    /**小红点逻辑*/
    public static final int TASKID_REFRESH_RED_POINT = UUID.randomUUID().hashCode();
    /**刷新我的页面*/
    public static final int TASKID_REFRESH_MINE = UUID.randomUUID().hashCode();
    /**刷新*/
    public static final int TASKID_REFRESH = UUID.randomUUID().hashCode();
    /**登出*/
    public static final int TASKID_REFRESH_LOGINOUT = UUID.randomUUID().hashCode();
    /**刷新我的新家*/
    public static final int TASKID_REFRESH_MY_HOME = UUID.randomUUID().hashCode();
    /**刷新部门管理*/
    public static final int TASKID_REFRESH_TEAM_MANAGEMENT = UUID.randomUUID().hashCode();
    /**部门详情关闭*/
    public static final int TASKID_REFRESH_TEAM_DETAIL = UUID.randomUUID().hashCode();
    /**部门详情刷新*/
    public static final int TASKID_REFRESH_TEAM_DETAIL_REFRESH = UUID.randomUUID().hashCode();

    public ET_Refresh(int taskId) {
        super(taskId);
    }
}
