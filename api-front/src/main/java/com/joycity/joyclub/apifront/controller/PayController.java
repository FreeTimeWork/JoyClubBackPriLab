package com.joycity.joyclub.apifront.controller;

import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.apifront.pay.wechat.PreOrder;
import com.joycity.joyclub.apifront.pay.wechat.SignUtils;
import com.joycity.joyclub.apifront.pay.wechat.WxPayService;
import com.joycity.joyclub.apifront.pay.wechat.WxPayConfig;
import com.joycity.joyclub.apifront.util.WechatXmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by CallMeXYZ on 2017/4/1.
 */
@RequestMapping("/api/front/wxpay")
@RestController
public class PayController {
    @Autowired
    WxPayService wxpay;
    @Autowired
    WxPayConfig wxpayConfig;
// TODO: 2017/4/6  
    @RequestMapping(value = "/receive", method = {RequestMethod.POST, RequestMethod.GET})
    public void wxRecevie(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = WechatXmlUtil.xmlToMap(request
                    .getInputStream());
            System.out.println(JSON.toJSONString(params));

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("return_code", "SUCCESS");
            map.put("return_msg", "OK");
            response.getWriter().write(WechatXmlUtil.mapToXml(map));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
    }

    // TODO: 2017/4/6  
    @RequestMapping(value = "/order")
    public String order() {
        PreOrder request = new PreOrder();
        request.setBody("测试");
        request.setOutTradeNo(UUID.randomUUID().toString().replace("-", ""));
        request.setTotalFee("0.1");
        request.setOpenId("oBDYawi1UjKoNXeDDeunHbqUn3As");
        String result = wxpay.precreate(request);
        return result;


    }

    /**
     * @param prepayId
     * @return
     */
    @RequestMapping(value = "/pay", method = {RequestMethod.GET})
    public String startPay(@RequestParam String prepayId) {

        Map<String, Object> param = new HashMap<>();
        param.put("appId", wxpayConfig.getAppid());
        param.put("timeStamp", String.valueOf(new Date().getTime()));
        param.put("nonceStr", "12345678");
        param.put("package", "prepay_id=" + prepayId);
        param.put("signType", "MD5");
        String sign = SignUtils.paySign(param, wxpayConfig.getSign());
        param.put("paySign", sign.toUpperCase());
        return JSON.toJSONString(param);
    }
}