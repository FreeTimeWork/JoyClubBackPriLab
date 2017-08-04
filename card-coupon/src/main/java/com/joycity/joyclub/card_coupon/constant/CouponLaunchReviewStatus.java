package com.joycity.joyclub.card_coupon.constant;

/**
 * 注意数据库里存储的是tinyint 4=>byte
 * Created by CallMeXYZ on 2017/3/30.
 */
public class CouponLaunchReviewStatus {

    public static final byte STATUS_NOT_REVIEW = 0;
    public static final byte STATUS_REVIEW_PERMIT = 1;
    public static final byte STATUS_REVIEW_REJECT = 2;
}
