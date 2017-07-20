package com.joycity.joyclub.apiback.modal.vo.auth;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/19.
 */
public class ResetPasswordVO {
    @NotNull
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
