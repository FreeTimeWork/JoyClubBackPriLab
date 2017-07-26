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
    ResultData getCurrentCoupons(Long projectId, String shopCode, String tel, String vipCode);

    /**
     * pos核销
     */
    ResultData posCheck(Long projectId, List<String> couponCodes, String orderCode, BigDecimal orderAmount, String shopCode);

    /**
     * pos 退货验证
     */
    ResultData refundVerification(String orderCode);

    /**
     * pos退货通知后的处理
     * @param orderCode
     * @return
     */
    ResultData refund(String orderCode);

    Long  createPosSaleDetail(String orderCode, Long clientId, BigDecimal paid);
}
