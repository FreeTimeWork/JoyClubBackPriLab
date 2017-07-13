package com.joycity.joyclub.card_coupon.constant;

/**
 *
 * 投放状态，1. 未审核，2 未投放，3 未开始，4 投放中，5 投放已过期，6 投放已中止
 */
public class CouponLaunchStatus {

    public static final int NOT_REVIEW = 1;
    public static final int NOT_LAUNCH = 2;
    public static final int NOT_START = 3;
    public static final int LAUNCHING = 4;
    public static final int LAUNCHED = 5;
    public static final int STOP_LAUNCH = 6;
}
