package com.joycity.joyclub.apifront.modal.vo.auth;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class SubProjectWapAutoLoginVO extends WapAutoLoginVO {
    @NotNull
    private Long subProjectId;

    public Long getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(Long subProjectId) {
        this.subProjectId = subProjectId;
    }
}
