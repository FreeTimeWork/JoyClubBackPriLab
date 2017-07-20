package com.joycity.joyclub.apiback.modal.vo.auth;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/19.
 */
public class BackLoginVO {
    @NotNull
    private String account;
    @NotNull
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
