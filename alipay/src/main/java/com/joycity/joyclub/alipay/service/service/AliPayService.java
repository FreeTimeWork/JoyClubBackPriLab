package com.joycity.joyclub.alipay.service.service;

import com.alipay.api.AlipayResponse;
import com.joycity.joyclub.alipay.service.modal.AliPayStoreInfo;
import com.joycity.joyclub.commons.modal.order.PreOrderResult;

/**
 * Created by CallMeXYZ on 2017/5/9.
 */
public interface AliPayService {

    /**
     * 生成支付宝支付相关的参数
     *
     * @param projectId
     * @param code
     * @param moneySum
     * @return
     */
    public PreOrderResult getAliPreOrderResult(Long projectId, Float moneySum, String code, String wxPayNotifyUrl);

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
