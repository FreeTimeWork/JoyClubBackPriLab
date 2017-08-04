package com.joycity.joyclub.apiback.modal.vo.pos;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/24
 */
public class PosCheckVO {

    @NotNull
    private List<String> couponCodes;
    @NotNull
    private String orderCode;           //订单号
    @NotNull
    private BigDecimal orderAmount;     //订单金额
    @NotNull
    private String shopCode;            //商户号

    public List<String> getCouponCodes() {
        return couponCodes;
    }

    public void setCouponCodes(List<String> couponCodes) {
        this.couponCodes = couponCodes;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
}
