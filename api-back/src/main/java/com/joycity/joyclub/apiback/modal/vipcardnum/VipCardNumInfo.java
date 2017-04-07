package com.joycity.joyclub.apiback.modal.vipcardnum;

import com.joycity.joyclub.apiback.modal.generated.SysVipCardNum;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
public class VipCardNumInfo extends SysVipCardNum {
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
