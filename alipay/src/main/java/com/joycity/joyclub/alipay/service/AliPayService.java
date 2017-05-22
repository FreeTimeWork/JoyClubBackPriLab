package com.joycity.joyclub.alipay.service;

import com.alipay.api.AlipayResponse;
import com.joycity.joyclub.alipay.service.modal.AliPayStoreInfo;

/**
 * Created by CallMeXYZ on 2017/5/9.
 */
public interface AliPayService {
    /**
     * 手机网站支付，返回的是页面对应的html
     */
    String wapPay(String tradeNo, Float total,/*  String title,String body,*/ AliPayStoreInfo storeInfo);

    /**
     * 条码支付
     */
    AlipayResponse barCodePay(/*String partner,*/ String out_trade_no, String auth_code,
                              String total_amount, String subject, AliPayStoreInfo storeInfo);

    /**
     * 查询
     */
    AlipayResponse query(String out_trade_no, AliPayStoreInfo storeInfo);

    /**
     * 退款
     */
    AlipayResponse refund(String trade_no, String out_request_no, String refund_amount, AliPayStoreInfo storeInfo);


    /**
     * 预支付
     */
    AlipayResponse precreate(String partner, String out_trade_no, String total_amount,
                             String subject, AliPayStoreInfo storeInfo);

}
