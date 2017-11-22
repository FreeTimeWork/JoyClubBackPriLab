package com.joycity.joyclub.apifront.modal;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @auther fangchen.chai ON 2017/11/21
 */
public class MitenoResult {

    String agentid ;
    String userToken ;
    String orderId;
    String amount ;
    String trxamount;
    String sid ;
    String returncode ;
    String sign;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTrxamount() {
        return trxamount;
    }

    public void setTrxamount(String trxamount) {
        this.trxamount = trxamount;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
