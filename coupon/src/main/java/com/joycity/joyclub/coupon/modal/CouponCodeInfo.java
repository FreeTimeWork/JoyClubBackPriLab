package com.joycity.joyclub.coupon.modal;

import com.joycity.joyclub.coupon.modal.generated.CouponCode;

/**
 * Created by CallMeXYZ on 2017/4/12.
 */
public class CouponCodeInfo extends CouponCode {
    private String couponName;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }
}
