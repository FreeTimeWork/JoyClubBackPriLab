package com.joycity.joyclub.card_coupon.service.sendcardstrategy;

/**
 * @auther fangchen.chai ON 2018/3/12
 */
public class IncrementSingleStrategy implements SingleStrategy {

    @Override
    public Integer CouponNum(Float amount, Float criteriaAmount) {
        return new Float(amount / criteriaAmount).intValue();
    }
}
