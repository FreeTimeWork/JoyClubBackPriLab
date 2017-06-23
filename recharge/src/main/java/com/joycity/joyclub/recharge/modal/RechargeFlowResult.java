package com.joycity.joyclub.recharge.modal;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
public class RechargeFlowResult {
    /**
     * 交易指令码	2字节	固定值: CZ
     */
    private String action;
    /**
     * 商户账号	1-20字节	原值返回
     */
    private String agentAccount;
    /**
     * 定单号	1-20字节	原值返回
     */
    private String orderId;
    /**
     * 交易流水号	1-20字节
     */
    private String chargeId;
    /**
     * 错误代码	4字节
     */
    private String errorCode;
    /**
     * 错误描述
     */
    private String errorDesc;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAgentAccount() {
        return agentAccount;
    }

    public void setAgentAccount(String agentAccount) {
        this.agentAccount = agentAccount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
