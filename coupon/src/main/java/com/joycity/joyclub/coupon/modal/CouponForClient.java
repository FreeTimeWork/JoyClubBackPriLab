package com.joycity.joyclub.coupon.modal;

import com.joycity.joyclub.coupon.modal.generated.Coupon;

import java.util.Date;

/**
 * Created by CallMeXYZ on 2017/4/21.
 * 用于手机端 用户的卡券
 */
public class CouponForClient extends Coupon {
private String code;
    /**
     * 使用状态
     */
    private Boolean clientUseStatus;
    private Date  clientUseTime;
    /**
     * 核销状态
     */
    private Boolean checkFlag;
    private Date checkTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getClientUseStatus() {
        return clientUseStatus;
    }

    public void setClientUseStatus(Boolean clientUseStatus) {
        this.clientUseStatus = clientUseStatus;
    }

    public Date getClientUseTime() {
        return clientUseTime;
    }

    public void setClientUseTime(Date clientUseTime) {
        this.clientUseTime = clientUseTime;
    }

    public Boolean getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
}
