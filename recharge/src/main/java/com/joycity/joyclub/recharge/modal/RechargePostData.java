package com.joycity.joyclub.recharge.modal;

import java.util.Map;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
public class RechargePostData {
    private String sign;
    private String agentAccount;
    private Map busiBody;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAgentAccount() {
        return agentAccount;
    }

    public void setAgentAccount(String agentAccount) {
        this.agentAccount = agentAccount;
    }

    public Map getBusiBody() {
        return busiBody;
    }

    public void setBusiBody(Map busiBody) {
        this.busiBody = busiBody;
    }
}
