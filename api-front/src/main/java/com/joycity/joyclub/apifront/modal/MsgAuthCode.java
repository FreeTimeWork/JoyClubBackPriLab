package com.joycity.joyclub.apifront.modal;

import java.util.Date;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
public class MsgAuthCode {
    private String phone;
    private String code;
    private Date createTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
