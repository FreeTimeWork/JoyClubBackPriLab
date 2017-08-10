package com.joycity.joyclub.card_coupon.modal;

import com.joycity.joyclub.mallcoo.modal.result.data.CouponInfo;

/**
 * CouponInfoBag
 * 券包点击进入券详情
 * @author CallMeXYZ
 * @date 2017/8/10
 */
public class CouponInfoInBag extends CouponInfo {
    private Long launchId;
    private Long couponId;

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
}
