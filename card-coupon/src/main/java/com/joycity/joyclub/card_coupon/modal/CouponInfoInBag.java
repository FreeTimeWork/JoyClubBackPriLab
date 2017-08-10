package com.joycity.joyclub.card_coupon.modal;

import com.joycity.joyclub.mallcoo.modal.result.data.CouponInfo;

import java.util.Date;

/**
 * CouponInfoBag
 * 券包点击进入券详情
 *
 * @author CallMeXYZ
 * @date 2017/8/10
 */
public class CouponInfoInBag extends CouponInfo {
    private Long launchId;
    private Long couponId;
    /* 是否是我方系统的券 */
    private Boolean ifSystem;
    private String sysCouponPortrait;
    /* 系统券类型 */
    private Float sysCouponType;
    /* 系统券的使用门槛 */
    private Float sysConditionMoney;
    /*系统券的优惠金额 */
    private Float sysReduceMoney;
    private Date sysCouponReceiveTime;
    private Integer sysUseStatus;
    public Long getLaunchId() {
        return launchId;
    }

    public void setLaunchId(Long launchId) {
        this.launchId = launchId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Boolean getIfSystem() {
        return ifSystem;
    }

    public void setIfSystem(Boolean ifSystem) {
        this.ifSystem = ifSystem;
    }

    public String getSysCouponPortrait() {
        return sysCouponPortrait;
    }

    public void setSysCouponPortrait(String sysCouponPortrait) {
        this.sysCouponPortrait = sysCouponPortrait;
    }

    public Float getSysCouponType() {
        return sysCouponType;
    }

    public void setSysCouponType(Float sysCouponType) {
        this.sysCouponType = sysCouponType;
    }

    public Float getSysConditionMoney() {
        return sysConditionMoney;
    }

    public void setSysConditionMoney(Float sysConditionMoney) {
        this.sysConditionMoney = sysConditionMoney;
    }

    public Float getSysReduceMoney() {
        return sysReduceMoney;
    }

    public void setSysReduceMoney(Float sysReduceMoney) {
        this.sysReduceMoney = sysReduceMoney;
    }

    public Date getSysCouponReceiveTime() {
        return sysCouponReceiveTime;
    }

    public void setSysCouponReceiveTime(Date sysCouponReceiveTime) {
        this.sysCouponReceiveTime = sysCouponReceiveTime;
    }

    public Integer getSysUseStatus() {
        return sysUseStatus;
    }

    public void setSysUseStatus(Integer sysUseStatus) {
        this.sysUseStatus = sysUseStatus;
    }
}
