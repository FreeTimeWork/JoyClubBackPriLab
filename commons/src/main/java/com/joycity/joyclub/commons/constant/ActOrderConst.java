package com.joycity.joyclub.commons.constant;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class ActOrderConst {

    /**
     * 未支付
     */
    public static final Byte STATUS_NOT_PAYED = 0;

    /**
     * 已取消
     */
    public static final Byte STATUS_CANCELLED = 1;

    /**
     * 已支付
     */
    public static final Byte STATUS_PAYED = 2;
    /**
     * 已核销
     */
    public static final Byte STATUS_CHECKED = 4;
    public static final Byte STATUS_REFUNDING = 5;
    public static final Byte STATUS_REFUNDED =6;

    public static final Byte BUY_TYPE_BOTH = 1;
    public static final Byte BUY_TYPE_ONLY_MONEY = 2;
    public static final Byte BUY_TYPE_ONLY_POINT = 3;
}
