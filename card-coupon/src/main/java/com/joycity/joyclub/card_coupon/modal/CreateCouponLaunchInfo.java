package com.joycity.joyclub.card_coupon.modal;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/17.
 */
public class CreateCouponLaunchInfo extends ShowCouponLaunchInfo {

    private Integer receiveNum; //领取量
    private Integer usedNum;
    private List<Long> triggerScopeIds;
    private List<CouponTriggerScopeWithShop> couponTriggerScopes;

    public List<Long> getTriggerScopeIds() {
        return triggerScopeIds;
    }

    public void setTriggerScopeIds(List<Long> triggerScopeIds) {
        this.triggerScopeIds = triggerScopeIds;
    }

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
