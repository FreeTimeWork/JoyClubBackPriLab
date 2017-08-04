package com.joycity.joyclub.card_coupon.modal;

import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;

/**
 * Created by fangchen.chai on 2017/7/12.
 * 查询卡券的信息
 */
public class ShowCouponInfo extends CardCoupon{

    private Integer availableNum;   //可用库存
    private Integer sumLaunchNum;   //投放总量
    private String thirdpartyShopName;

    public String getThirdpartyShopName() {
        return thirdpartyShopName;
    }

    public void setThirdpartyShopName(String thirdpartyShopName) {
        this.thirdpartyShopName = thirdpartyShopName;
    }

    public Integer getSumLaunchNum() {
        return sumLaunchNum;
    }

    public void setSumLaunchNum(Integer sumLaunchNum) {
        this.sumLaunchNum = sumLaunchNum;
    }

    public Integer getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(Integer availableNum) {
        this.availableNum = availableNum;
    }

}
