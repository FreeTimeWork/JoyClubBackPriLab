package com.joycity.joyclub.apiback.modal.vo.pos;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by fangchen.chai on 2017/8/14
 */
public class PosRefundVO {
    @NotNull
    private String orderCode;
    @NotNull
    private BigDecimal refundAmount;
    private String shopCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
}
