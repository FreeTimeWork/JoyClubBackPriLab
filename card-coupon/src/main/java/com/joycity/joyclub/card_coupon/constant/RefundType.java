package com.joycity.joyclub.card_coupon.constant;

/**
 * Created by fangchen.chai on 2017/7/28
 */
public class RefundType {
    /**
     * 不可退款
     */
    public static final Byte FORBIT_REFUND = 1;

    /**
     * 只可以退全单
     */
    public static final Byte PERMIT_REFUND_FULL_ORDER = 2;

    /**
     *任意退款
     */
    public static final Byte PREMIT_REFUND = 3;
}
