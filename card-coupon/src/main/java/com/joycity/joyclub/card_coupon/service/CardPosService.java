package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/24
 */
public interface CardPosService {

    /**
     * 得到某个用户在某个商户的当前可用券
     */
    ResultData getCurrentCoupons(Long projectId, String shopCode, String vipCode);

    /**
     * 查看卡券
     */
    ResultData examineCouponCode(String vipCode, String shopCode, String couponCode);

    /**
     * 核销卡券
     */
    ResultData posCheck(String vipCode, String couponCode, String orderCode, BigDecimal orderAmount, String shopCode);

    /**
     * 取消核销
     */
    ResultData posCheckCancel(String orderCode);

    /**
     * 订单结果通知
     */
    ResultData posOrderInform(Long projectId, String vipCode, String orderCode, String shopCode, BigDecimal payable, BigDecimal payment);


    /**
     * pos 退货验证
     */
    ResultData refundVerification(String orderCode, BigDecimal refundAmount);

    /**
     * pos退货通知后的处理
     * @param orderCode
     * @return
     */
    ResultData refund(String orderCode, BigDecimal refundAmount);

}
