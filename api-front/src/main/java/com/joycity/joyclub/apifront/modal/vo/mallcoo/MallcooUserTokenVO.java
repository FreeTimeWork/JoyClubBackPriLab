package com.joycity.joyclub.apifront.modal.vo.mallcoo;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class MallcooUserTokenVO {
    @NotNull
    private String ticket;
    @NotNull
    private Long projectId;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
