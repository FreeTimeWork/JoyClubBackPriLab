package com.joycity.joyclub.card_coupon.modal;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponStoreScope;

/**
 * Created by fangchen.chai on 2017/7/12.
 * 新建券页面信息
 */
public class CreateCouponInfo extends CardCoupon{
    private List<CardCouponStoreScope> storeScopes;
    private List<String> vipScopes;

    public List<CardCouponStoreScope> getStoreScopes() {
        return storeScopes;
    }

    public void setStoreScopes(List<CardCouponStoreScope> storeScopes) {
        this.storeScopes = storeScopes;
    }

    public List<String> getVipScopes() {
        return vipScopes;
    }

    public void setVipScopes(List<String> vipScopes) {
        this.vipScopes = vipScopes;
    }
}
