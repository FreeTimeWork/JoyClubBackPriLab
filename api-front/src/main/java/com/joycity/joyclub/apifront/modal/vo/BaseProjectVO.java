package com.joycity.joyclub.apifront.modal.vo;

import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class BaseProjectVO {
    private Long projectId = PLATFORM_ID;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
