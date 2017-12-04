package com.joycity.joyclub.recharge.modal.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @auther fangchen.chai ON 2017/12/1
 */
public class RtmParamVo {

    private String uid = "";
    private String credits = "" ;
    private String clientId = "";
    private String timestamp = "";
    private String description = "";
    private String rtmOrderNum  = "";
    private String pointType  = "";
    private String sign = "";

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRtmOrderNum() {
        return rtmOrderNum;
    }

    public void setRtmOrderNum(String rtmOrderNum) {
        this.rtmOrderNum = rtmOrderNum;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
