package com.joycity.joyclub.apiback.modal.vo.pos;

import javax.validation.constraints.NotNull;

/**
 * Created by fangchen.chai on 2017/8/14
 */
public class PosRefundVerificationVO {
    @NotNull
    private String orderCode;
    private String shopCode;

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
}
