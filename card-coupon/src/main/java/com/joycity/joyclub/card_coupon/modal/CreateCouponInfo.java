package com.joycity.joyclub.card_coupon.modal;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponStoreScope;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponVipScope;

/**
 * Created by fangchen.chai on 2017/7/12.
 * 新建券页面信息
 */
public class CreateCouponInfo {
    private CardCoupon cardCoupon;
    private List<CardCouponStoreScope> storeScopes;
    private List<CardCouponVipScope> vipScopes;

    public CardCoupon getCardCoupon() {
        return cardCoupon;
    }

    public void setCardCoupon(CardCoupon cardCoupon) {
        this.cardCoupon = cardCoupon;
    }

    public List<CardCouponStoreScope> getStoreScopes() {
        return storeScopes;
    }

    public void setStoreScopes(List<CardCouponStoreScope> storeScopes) {
        this.storeScopes = storeScopes;
    }

    public List<CardCouponVipScope> getVipScopes() {
        return vipScopes;
    }

    public void setVipScopes(List<CardCouponVipScope> vipScopes) {
        this.vipScopes = vipScopes;
    }
}
