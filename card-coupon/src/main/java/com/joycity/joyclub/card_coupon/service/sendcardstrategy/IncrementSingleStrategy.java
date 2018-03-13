package com.joycity.joyclub.card_coupon.service.sendcardstrategy;

import java.math.BigDecimal;

/**
 * @auther fangchen.chai ON 2018/3/12
 */
public class IncrementSingleStrategy implements SingleStrategy {

    @Override
    public Integer CouponNum(BigDecimal amount, BigDecimal criteriaAmount) {
        return (amount.divide( criteriaAmount)).intValue();
    }
}
