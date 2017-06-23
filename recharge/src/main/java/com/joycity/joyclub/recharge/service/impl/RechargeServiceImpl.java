package com.joycity.joyclub.recharge.service.impl;

import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.recharge.RechargeConfig;
import com.joycity.joyclub.recharge.modal.RechargeFlowResult;
import com.joycity.joyclub.recharge.modal.RechargePostData;
import com.joycity.joyclub.recharge.service.RechargeService;
import com.sun.crypto.provider.HmacMD5;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
@Service
public class RechargeServiceImpl implements RechargeService {
    private final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddhhmmssSSS");
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RechargeConfig rechargeConfig;

    /**
     * 参考文档 http://wiki.madaikr.com/flow-api-doc/
     *
     * @param phone
     * @param flowSize 流量包大小，以M为单位
     */
    @Override
    public void rechargeFlow(String phone, int flowSize, boolean ifNationFlow) {
        Map<String, Object> businessBody = new HashedMap();
        businessBody.put("action", "CZ");
        businessBody.put("orderId", createOrderCode());
        businessBody.put("chargeAcct", phone);
        businessBody.put("chargeType", 1);
        businessBody.put("flowPackageType", ifNationFlow ? 0 : 1);
        businessBody.put("flowPackageSize", flowSize);
        // TODO: 2017/6/14 回调地址 
//        businessBody.put("retUrl", URLEncoder.encode("http://www.baidu.com?a=1&b=张三","UTF-8"));

        System.out.println(rechargeConfig.getUrl());
        System.out.println(JSON.toJSONString(getPostData("CZ", businessBody)));
        String result = restTemplate.postForObject(rechargeConfig.getUrl(), getPostData("CZ", businessBody), String.class);
        JSON.parseObject(result, RechargeFlowResult.class);


    }

    /**
     * 生成20位订单号
     * 当前日期+随机三位数
     *
     * @return
     */
    private String createOrderCode() {
        return SDF.format(new Date()) + RandomStringUtils.random(3, "123456789");
    }

    private RechargePostData getPostData(String action, Map businessBody) {
        RechargePostData postData = new RechargePostData();
        postData.setAgentAccount(rechargeConfig.getUser());
        postData.setBusiBody(businessBody);
        postData.setSign(DigestUtils.md5Hex(JSON.toJSONString(businessBody) + rechargeConfig.getKey()));
        return postData;
    }
}
