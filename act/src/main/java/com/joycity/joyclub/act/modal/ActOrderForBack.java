package com.joycity.joyclub.act.modal;

/**
 * 订单管理需要的
 * Created by CallMeXYZ on 2017/5/22.
 */
public class ActOrderForBack extends MyActOrder{
    private String clientName;
    private String clientPhone;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }
}
