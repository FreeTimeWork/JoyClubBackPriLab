package com.joycity.joyclub.apiback.modal.vo.pos;

import javax.validation.constraints.NotNull;

/**
 * Created by fangchen.chai on 2017/8/14
 */
public class PosCheckCancelVO {
    @NotNull
    private String orderCode;
    @NotNull
    private String couponCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
