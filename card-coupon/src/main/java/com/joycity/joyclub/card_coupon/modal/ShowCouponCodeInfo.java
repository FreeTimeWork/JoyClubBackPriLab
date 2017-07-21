package com.joycity.joyclub.card_coupon.modal;

import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCode;

/**
 * Created by fangchen.chai on 2017/7/14.
 */
public class ShowCouponCodeInfo extends CardCouponCode{
    private String couponName;
    private String thirdPartyShopName;
    private Byte couponType;
    private String couponLaunchName;
    private String vipCode;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getThirdPartyShopName() {
        return thirdPartyShopName;
    }

    public void setThirdPartyShopName(String thirdPartyShopName) {
        this.thirdPartyShopName = thirdPartyShopName;
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

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }
}
