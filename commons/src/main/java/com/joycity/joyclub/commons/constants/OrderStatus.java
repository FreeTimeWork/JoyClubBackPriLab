package com.joycity.joyclub.commons.constants;

/**
 * Created by CallMeXYZ on 2017/4/24.
 */
public class OrderStatus {
//   主订单状态
    /**
     * 未支付
     */
    public static final Byte MAIN_ORDER_STATUS_NOT_PAYED = 0;
    /**
     * 已支付
     */
    public static final Byte MAIN_ORDER_STATUS_PAYED = 1;
    /**
     * 已取消
     */
    public static final Byte MAIN_ORDER_STATUS_CANCELLED = 2;


//    店铺订单状态

    /**
     * 店铺订单 未支付 继承主订单
     */
    public static final Byte STORE_ORDER_STATUS_NOT_PAYED = -1;
    /**
     * 店铺订单 已支付 继承主订单
     */
    public static final Byte STORE_ORDER_STATUS_PAYED = 0;

    /**
     * 店铺订单 已发货
     */
    public static final Byte STORE_ORDER_STATUS_SENT = 1;
    /**
     * 店铺订单已收货（包括自提和快递收货）
     */
    public static final Byte STORE_ORDER_STATUS_RECEIVED = 2;
    /**
     * 店铺订单退款中
     */
    public static final Byte STORE_ORDER_STATUS_REFUNDING = 3;
    /**
     * 店铺订单已退款退款
     */
    public static final Byte STORE_ORDER_STATUS_REFUNDED = 4;
    /**
     * 自提
     */
    public static final Byte RECEIVE_TYPE_PICKUP = 0;
    /**
     * 快递
     */
    public static final Byte RECEIVE_TYPE_POST = 1;
}
