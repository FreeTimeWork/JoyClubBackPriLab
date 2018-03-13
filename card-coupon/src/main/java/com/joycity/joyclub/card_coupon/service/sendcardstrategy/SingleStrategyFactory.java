package com.joycity.joyclub.card_coupon.service.sendcardstrategy;

import com.joycity.joyclub.card_coupon.constant.CouponCalculateType;

/**
 * @auther fangchen.chai ON 2018/3/12
 */
public class SingleStrategyFactory {
    private SingleStrategy singleStrategy;

    public SingleStrategyFactory(SingleStrategy singleStrategy) {
        this.singleStrategy = singleStrategy;
    }

    public SingleStrategyFactory(CouponCalculateType couponCalculateType) {
        if (couponCalculateType.equals(CouponCalculateType.INCREMENT_SINGLE)) {
            singleStrategy =  new IncrementSingleStrategy();
        } else if (couponCalculateType.equals(CouponCalculateType.NONINCREMENT_SINGLE)) {
            singleStrategy =  new NonincrementSingleStrategy();
        } else if(couponCalculateType.equals(CouponCalculateType.INCREMENT_MANY)){
            singleStrategy =  new IncrementSingleStrategy();
        }
    }

    public SingleStrategy getSingleStrategy() {
        return singleStrategy;
    }
}
