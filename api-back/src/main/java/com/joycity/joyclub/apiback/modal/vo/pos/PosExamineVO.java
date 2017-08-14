package com.joycity.joyclub.apiback.modal.vo.pos;

import javax.validation.constraints.NotNull;

/**
 * Created by fangchen.chai on 2017/8/14
 */
public class PosExamineVO {
    @NotNull
    private String vipCode;
    @NotNull
    private String shopCode;
    @NotNull
    private String couponCode;

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
