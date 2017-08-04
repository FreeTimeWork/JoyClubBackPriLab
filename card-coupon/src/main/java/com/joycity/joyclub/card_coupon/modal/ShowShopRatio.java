package com.joycity.joyclub.card_coupon.modal;

import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;

import java.math.BigDecimal;

/**
 * Created by fangchen.chai on 2017/8/4
 */
public class ShowShopRatio extends CardCouponLaunch{

    private String shopName;
    private BigDecimal ratio;
    /**
     * 各商家有效用券人数
     */
    private Integer effectiveClientNum;
    /**
     * 各商家有效用券数
     */
    private Integer effectiveCouponNum;
    /**
     * 商户承担金额
     */
    private BigDecimal shopRatioAmount;
    /**
     * 商场承担金额
     */
    private BigDecimal landlordRatioAmount;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public Integer getEffectiveCouponNum() {
        return effectiveCouponNum;
    }

    public void setEffectiveCouponNum(Integer effectiveCouponNum) {
        this.effectiveCouponNum = effectiveCouponNum;
    }

    public Integer getEffectiveClientNum() {
        return effectiveClientNum;
    }

    public void setEffectiveClientNum(Integer effectiveClientNum) {
        this.effectiveClientNum = effectiveClientNum;
    }

    public BigDecimal getShopRatioAmount() {
        return shopRatioAmount;
    }

    public void setShopRatioAmount(BigDecimal shopRatioAmount) {
        this.shopRatioAmount = shopRatioAmount;
    }

    public BigDecimal getLandlordRatioAmount() {
        return landlordRatioAmount;
    }

    public void setLandlordRatioAmount(BigDecimal landlordRatioAmount) {
        this.landlordRatioAmount = landlordRatioAmount;
    }
}
