package com.joycity.joyclub.commons.modal.order;

import java.util.Map;

/**
 * Created by CallMeXYZ on 2017/5/19.
 */
public class PreOrderResult {
    //是否要金钱支付
    private Boolean ifUseMoney;
    //微信支付的参数
    private Map<String, Object> payParam;
    //支付宝支付的返回参数，form表单的string
    private String formString;

    public Boolean getIfUseMoney() {
        return ifUseMoney;
    }

    public void setIfUseMoney(Boolean ifUseMoney) {
        this.ifUseMoney = ifUseMoney;
    }

    public Map<String, Object> getPayParam() {
        return payParam;
    }

    public void setPayParam(Map<String, Object> payParam) {
        this.payParam = payParam;
    }

    public String getFormString() {
        return formString;
    }

    public void setFormString(String formString) {
        this.formString = formString;
    }
}
