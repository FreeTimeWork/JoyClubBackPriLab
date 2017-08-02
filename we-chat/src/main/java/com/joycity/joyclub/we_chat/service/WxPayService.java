package com.joycity.joyclub.we_chat.service;

import com.joycity.joyclub.commons.modal.order.PreOrderResult;

/**
 * Created by fangchen.chai on 2017/8/2
 */
public interface WxPayService {
    /**
     * 生成微信支付相关的参数
     *
     * @param projectId
     * @param clientId
     * @param code
     * @param moneySum
     * @param wxPayNotifyUrl 微信的回调地址
     * @return
     */
    public PreOrderResult getWechatPreOrderResult(Long projectId, Long clientId, Float moneySum, String code, String wxPayNotifyUrl);
}
