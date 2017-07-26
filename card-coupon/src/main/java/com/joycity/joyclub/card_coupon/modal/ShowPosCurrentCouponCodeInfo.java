package com.joycity.joyclub.card_coupon.modal;

import org.springframework.core.io.ByteArrayResource;

import java.math.BigDecimal;

/**
 * Created by fangchen.chai on 2017/7/24
 */
public class ShowPosCurrentCouponCodeInfo {

    private String couponName;
    private Byte couponType;
    private BigDecimal amount;
    private BigDecimal subtractAmount;
    private String launchName;
    private Byte launchType;
    private Long couponCodeId;    //卡券号表的id
    private String code;
    private Byte useStatus;
    private String shopName;

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

    public String getLaunchName() {
        return launchName;
    }

    public void setLaunchName(String launchName) {
        this.launchName = launchName;
    }

    public Byte getLaunchType() {
        return launchType;
    }

    public void setLaunchType(Byte launchType) {
        this.launchType = launchType;
    }

    public Long getCouponCodeId() {
        return couponCodeId;
    }

    public void setCouponCodeId(Long couponCodeId) {
        this.couponCodeId = couponCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Byte useStatus) {
        this.useStatus = useStatus;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

}
