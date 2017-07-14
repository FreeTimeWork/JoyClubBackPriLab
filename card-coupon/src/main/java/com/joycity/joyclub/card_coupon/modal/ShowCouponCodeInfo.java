package com.joycity.joyclub.card_coupon.modal;

import com.joycity.joyclub.coupon.modal.generated.CouponCode;

/**
 * Created by fangchen.chai on 2017/7/14.
 */
public class ShowCouponCodeInfo extends CouponCode{
    private String couponName;
    private Byte couponType;
    private String couponLaunchName;

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

    public String getCouponLaunchName() {
        return couponLaunchName;
    }

    public void setCouponLaunchName(String couponLaunchName) {
        this.couponLaunchName = couponLaunchName;
    }
}
