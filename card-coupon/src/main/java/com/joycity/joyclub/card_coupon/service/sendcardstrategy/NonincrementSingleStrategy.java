package com.joycity.joyclub.card_coupon.service.sendcardstrategy;

/**
 * @auther fangchen.chai ON 2018/3/12
 */
public class NonincrementSingleStrategy implements SingleStrategy {

    @Override
    public Integer CouponNum(Float amount, Float criteriaAmount) {
        if ((amount / criteriaAmount) > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
