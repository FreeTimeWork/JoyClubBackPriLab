package com.joycity.joyclub.apiback.modal.vo.coupon;

import com.joycity.joyclub.coupon.modal.generated.Coupon;

/**
 * Created by CallMeXYZ on 2017/7/19.
 */
public class CouponVO extends Coupon {

    private String[] cardTypes;


    public String[] getCardTypes() {
        return cardTypes;
    }

    public void setCardTypes(String[] cardTypes) {
        this.cardTypes = cardTypes;
    }
}
