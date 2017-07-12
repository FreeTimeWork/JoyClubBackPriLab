package com.joycity.joyclub.card_coupon.modal;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;

/**
 * Created by fangchen.chai on 2017/7/12.
 * 查询卡券的信息
 */
public class ShowCouponInfo extends CardCoupon{

    private Integer availableNum;   //可用库存

    private List<CardCouponLaunch> launchs;

    public Integer getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(Integer availableNum) {
        this.availableNum = availableNum;
    }

    public List<CardCouponLaunch> getLaunchs() {
        return launchs;
    }

    public void setLaunchs(List<CardCouponLaunch> launchs) {
        this.launchs = launchs;
    }
}
