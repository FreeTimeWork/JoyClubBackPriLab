package com.joycity.joyclub.api.entity.account;

import com.joycity.joyclub.api.entity.generated.SysUser;

import java.sql.Date;

/**
 * 管理端账号类
 * Created by CallMeXYZ on 2017/2/27.
 */
public class Manager extends SysUser {
    /**
     * 类型名
     */
    private String typeName;

    /**
     * 项目类型名
     */
    private String projectTypeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {

        this.typeName = typeName;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }
}
