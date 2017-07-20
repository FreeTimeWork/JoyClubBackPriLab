package com.joycity.joyclub.apifront.modal.vo.auth;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class WechatLoginVO extends WechatAutoLoginVO {
    @NotNull
    private String authCode;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
