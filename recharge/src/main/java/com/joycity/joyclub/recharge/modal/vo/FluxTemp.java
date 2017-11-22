package com.joycity.joyclub.recharge.modal.vo;

/**
 * @auther fangchen.chai ON 2017/11/21
 */
public class FluxTemp {
    /**
     * 运营商类型1移动2联通3电信
     */
    private String operatorType;
    private String province;
    private String scope;
    private String timeStamp;

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
