package com.joycity.joyclub.apifront.modal.vo.auth;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class SubProjectWechatAutoLoginVO extends WechatAutoLoginVO {
    @NotNull
    private Long subProjectId;

    public Long getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(Long subProjectId) {
        this.subProjectId = subProjectId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
