package com.joycity.joyclub.apiback.modal.vo.pos;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by fangchen.chai on 2017/8/14
 */
public class PosOrderInformVO {
    @NotNull
    private String vipCode;
    @NotNull
    private String orderCode;
    @NotNull
    private String shopCode;
    @NotNull
    private BigDecimal payable;
    @NotNull
    private BigDecimal payment;

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }
}
