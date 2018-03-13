package com.joycity.joyclub.card_coupon.service.sendcardstrategy;

/**
 * @auther fangchen.chai ON 2018/3/12
 */
public class SingleStrategyFactory {
    private SingleStrategy singleStrategy;

    public SingleStrategyFactory(SingleStrategy singleStrategy) {
        this.singleStrategy = singleStrategy;
    }

    public SingleStrategy getSingleStrategy() {
        return singleStrategy;
    }
}
