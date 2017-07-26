package com.joycity.joyclub.card_coupon.modal;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/24
 *  pos核销结果
 */
public class PosCheckResult {

    private BigDecimal payable;  //应付款
    private String orderCode;
    private List<RatioDetail> ratioDetails;

    public class RatioDetail {
        private String couponCode;      //卡券号
        private BigDecimal ratio;       //商户分担比
        private BigDecimal discountAmount;    //优惠额

        public RatioDetail() {
        }

        public RatioDetail(String couponCode, BigDecimal ratio, BigDecimal discountAmount) {
            this.couponCode = couponCode;
            this.ratio = ratio;
            this.discountAmount = discountAmount;
        }

        public BigDecimal getRatio() {
            return ratio;
        }

        public void setRatio(BigDecimal ratio) {
            this.ratio = ratio;
        }

        public BigDecimal getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public List<RatioDetail> getRatioDetails() {
        return ratioDetails;
    }

    public void setRatioDetails(List<RatioDetail> ratioDetails) {
        this.ratioDetails = ratioDetails;
    }

}
