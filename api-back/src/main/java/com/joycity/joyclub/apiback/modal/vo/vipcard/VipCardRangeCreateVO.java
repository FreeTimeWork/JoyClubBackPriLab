package com.joycity.joyclub.apiback.modal.vo.vipcard;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/19.
 */
public class VipCardRangeCreateVO extends VipCardRangeBaseVO {
    @NotNull
    private String type;
    @NotNull
    private Long projectId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
