package com.joycity.joyclub.apifront.modal.wechat;

/**
 * 项目openid关联对象
 * Created by CallMeXYZ on 2017/4/10.
 */
public class ClientWechatOpenId {
    private String openId;
    private Long clientId;
    private Long projectId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
