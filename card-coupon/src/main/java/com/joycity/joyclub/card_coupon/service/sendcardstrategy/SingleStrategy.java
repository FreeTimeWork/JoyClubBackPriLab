package com.joycity.joyclub.card_coupon.service.sendcardstrategy;

import java.math.BigDecimal;

public interface SingleStrategy {

    public Integer CouponNum(BigDecimal amount, BigDecimal criteriaAmount);
}
