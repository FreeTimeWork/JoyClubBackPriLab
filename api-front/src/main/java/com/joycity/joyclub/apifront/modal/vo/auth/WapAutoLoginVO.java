package com.joycity.joyclub.apifront.modal.vo.auth;

import com.joycity.joyclub.apifront.modal.vo.BaseProjectVO;

import javax.validation.constraints.NotNull;

/**
 * Created by CallMeXYZ on 2017/7/20.
 */
public class WapAutoLoginVO extends BaseProjectVO {
    @NotNull
    private String phone;
    private String from;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
