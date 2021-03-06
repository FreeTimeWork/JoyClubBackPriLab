package com.joycity.joyclub.apiback.modal.vo.pos;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by fangchen.chai on 2017/8/14
 */
public class PosCheckVO {
    @NotNull
    private String vipCode;
    @NotNull
    private String shopCode;
    @NotNull
    private String couponCode;
    @NotNull
    private String orderCode;
    @NotNull
    private BigDecimal payable;

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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }
}
