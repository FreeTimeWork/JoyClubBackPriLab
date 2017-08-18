package com.joycity.joyclub.apifront.modal.vo;

import javax.validation.constraints.NotNull;

/**
 * WechatNotifyVO
 *
 * @author CallMeXYZ
 * @date 2017/8/18
 */
public class WechatNotifyVO {
    @NotNull
    private Long projectId;
    @NotNull
    private String code;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
