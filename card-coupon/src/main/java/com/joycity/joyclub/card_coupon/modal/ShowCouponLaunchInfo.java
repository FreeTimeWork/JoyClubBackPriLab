package com.joycity.joyclub.card_coupon.modal;

import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
public class ShowCouponLaunchInfo extends CardCouponLaunch {

    private String couponName;
    private Byte couponType;
    private Integer status;
    private String portrait;

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
