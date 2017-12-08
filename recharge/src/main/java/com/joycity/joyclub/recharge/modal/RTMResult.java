package com.joycity.joyclub.recharge.modal;

import java.math.BigDecimal;

/**
 * @auther fangchen.chai ON 2017/11/30
 */
public class RTMResult {

    private Boolean status = true;// 状态
    private String code = "200"  ;// 状态编码
    private String orderNum ;// 商户的订单号
    private String msg = "success"; //返回描述信息
    /**
    *
    *   订单状态
    *   0 异常订单
    *   1 积分增加成功
    *   2 积分扣减成功
    *   3 退款成功
    * */
    private Integer orderStatus;//订单状态

    /**
     * 积分
     */
    private BigDecimal balance;

    /**
     * 以下回调需传
     * @return
     */

    private String clientId;
    private String uid;
    private String userStatus;
    private String timestamp;
    private String sign;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
