package com.joycity.joyclub.alipay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.*;
import com.alipay.api.request.*;
import com.joycity.joyclub.alipay.service.AliPayService;
import com.joycity.joyclub.alipay.service.modal.AliPayStoreInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CallMeXYZ on 2017/5/9.
 */
@Service
public class AliPayServiceImpl implements AliPayService {
    public final String GATE_WAY = "https://openapi.alipay.com/gateway.do";
    private final DecimalFormat decimalFormat = new DecimalFormat(".00");
    private final String PAY_TITLE = "悦客会";
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Value("${alipay.returnUrl}")
    private String returnUrl;

    public String wapPay(String tradeNo, String total, String title/*,String body*/, AliPayStoreInfo storeInfo) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        Map<String, String> map = new HashMap<String, String>();
        map.put("out_trade_no", tradeNo);
        map.put("total_amount", total);
        map.put("subject", title);
        //
        //map.put("seller_id", storeInfo.getPId());
        map.put("product_code", "QUICK_WAP_PAY");
//        map.put("body", body);
        String biz = paramToJson(map);
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);
        request.setBizContent(biz);
        AlipayClient client = getAliPayClient(storeInfo);
        String form = null;
        try {
            form = client.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return form;
    }

    @Override
    public String wapPay(String tradeNo, Float total, /*String title, String body,*/ AliPayStoreInfo storeInfo) {
        return wapPay(tradeNo, String.format("%.2f", Math.ceil(total*100)/100), PAY_TITLE, storeInfo);
    }

    /**
     * 条码支付
     */
    @Override
    public AlipayResponse barCodePay(/*String partner,*/ String out_trade_no, String auth_code,
                                     String total_amount, String subject, AliPayStoreInfo storeInfo) {
        Map<String, String> map = new HashMap<>();
        map.put("out_trade_no", out_trade_no);
        map.put("auth_code", auth_code);
        map.put("total_amount", total_amount);
        map.put("subject", subject);
        map.put("scene", "bar_code");
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.setBizContent(paramToJson(map));
        return execute(request, storeInfo);
    }

    /**
     * 查询
     */
    @Override
    public AlipayResponse query(String out_trade_no, AliPayStoreInfo storeInfo) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("out_trade_no", out_trade_no);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent(paramToJson(map));
        return execute(request, storeInfo);
    }

    /**
     * 退款
     */
    @Override
    public AlipayResponse refund(String trade_no, String out_request_no, String refund_amount, AliPayStoreInfo storeInfo) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("out_trade_no", trade_no);
        map.put("out_request_no", out_request_no);
        map.put("refund_amount", refund_amount);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(paramToJson(map));
        return execute(request, storeInfo);
    }

    /**
     * 预支付
     */
    @Override
    public AlipayResponse precreate(String partner, String out_trade_no, String total_amount,
                                    String subject, AliPayStoreInfo storeInfo) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("out_trade_no", out_trade_no);
        map.put("total_amount", total_amount);
        map.put("subject", subject);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent(paramToJson(map));
        return execute(request, storeInfo);
    }

    private String paramToJson(Map<String, String> map) {
        return JSON.toJSONString(map);
    }

    private AlipayResponse execute(AlipayRequest request, AliPayStoreInfo storeInfo) {
        AlipayClient client = getAliPayClient(storeInfo);
        AlipayResponse response = null;
        try {
            response = client.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

    private DefaultAlipayClient getAliPayClient(AliPayStoreInfo storeInfo) {
        return new DefaultAlipayClient(GATE_WAY,
                storeInfo.getAppId(), storeInfo.getPrivateKey(), "json",
                "utf-8", storeInfo.getPublicKey(),"RSA2");
    }
}