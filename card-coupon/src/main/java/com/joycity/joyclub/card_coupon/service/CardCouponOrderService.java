package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by fangchen.chai on 2017/8/1
 */
public interface CardCouponOrderService {

    ResultData orderForWeChat(Long clientId, Long couponLaunchId, Boolean moneyOrPoint);
    ResultData orderForAli(Long clientId, Long couponLaunchId,Boolean moneyOrPoint);

    /**
     * 取消超过十分钟订单
     */
    void cancelOverTenMinsOrder();
}
