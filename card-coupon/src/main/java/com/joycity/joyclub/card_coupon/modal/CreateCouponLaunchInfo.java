package com.joycity.joyclub.card_coupon.modal;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;

/**
 * Created by fangchen.chai on 2017/7/17.
 */
public class CreateCouponLaunchInfo extends CardCouponLaunch {

    private List<CouponTriggerScopeWithShop> couponTriggerScopes;
    private Integer receiveNum; //领取量
    private Integer usedNum;

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public List<CouponTriggerScopeWithShop> getCouponTriggerScopes() {
        return couponTriggerScopes;
    }

    public void setCouponTriggerScopes(List<CouponTriggerScopeWithShop> couponTriggerScopes) {
        this.couponTriggerScopes = couponTriggerScopes;
    }
}
