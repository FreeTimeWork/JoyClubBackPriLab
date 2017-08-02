package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by fangchen.chai on 2017/8/1
 */
public interface CardCouponOrderService {

    ResultData orderForWeChat(Long clientId, Long couponLaunchId, Boolean moneyOrPoint);
    ResultData orderForAli(Long clientId, Long couponLaunchId,Boolean moneyOrPoint);

    /**
     * 支付成功返回回调，进行订单支付成功业务处理
     * @param code    我方单号
     * @param outCode 对方单号
     */

    Boolean notifyPayed(String code,String outCode);
    /**
     * 取消超过十分钟订单
     */
    void cancelOverTenMinOrder();
}
