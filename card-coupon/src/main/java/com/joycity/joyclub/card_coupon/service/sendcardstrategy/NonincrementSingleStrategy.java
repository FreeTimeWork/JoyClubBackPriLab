package com.joycity.joyclub.card_coupon.service.sendcardstrategy;

import java.math.BigDecimal;

/**
 * @auther fangchen.chai ON 2018/3/12
 */
public class NonincrementSingleStrategy implements SingleStrategy {

    @Override
    public Integer CouponNum(BigDecimal amount, BigDecimal criteriaAmount) {
        if ((amount.divide(criteriaAmount) ).compareTo(BigDecimal.ZERO) > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
