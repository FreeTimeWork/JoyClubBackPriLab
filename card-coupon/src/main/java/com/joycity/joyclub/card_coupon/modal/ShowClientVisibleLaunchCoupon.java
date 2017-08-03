package com.joycity.joyclub.card_coupon.modal;

import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;

import java.math.BigDecimal;

/**
 * Created by fangchen.chai on 2017/8/3
 * 前段用户可领取的列表
 */
public class ShowClientVisibleLaunchCoupon extends CardCouponLaunch{
    /**
     * 领取的状态 0,未领取  1，已领取
     */
    private Byte receiveStatus;
    private String couponName;
    private Byte couponType;
    /**
     * 卡券剩余数量，通过sql查询计算出来
     */
    private Integer remainNum;
    private String portrait;
    private String info;
    private BigDecimal amount;
    private BigDecimal subtractAmount;

    public Byte getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(Byte receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Byte getCouponType() {
        return couponType;
    }

    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSubtractAmount() {
        return subtractAmount;
    }

    public void setSubtractAmount(BigDecimal subtractAmount) {
        this.subtractAmount = subtractAmount;
    }
}
