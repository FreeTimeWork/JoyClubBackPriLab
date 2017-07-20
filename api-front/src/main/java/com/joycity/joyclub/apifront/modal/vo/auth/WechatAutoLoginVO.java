package com.joycity.joyclub.apifront.modal.vo.auth;

import com.joycity.joyclub.apifront.modal.vo.BaseProjectVO;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class WechatAutoLoginVO extends BaseProjectVO {
    @NotNull
    private String phone;
    @NotNull
    private String openId;
    @NotNull
    private String accessToken;
    private String from;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
