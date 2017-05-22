package com.joycity.joyclub.apifront.controller;

import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.alipay.service.AliPayService;
import com.joycity.joyclub.alipay.service.modal.AliPayStoreInfo;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client.modal.WechatUserInfo;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/11.
 */
@RestController
@RequestMapping(URL_API_FRONT + "/test")
public class TestController {
    @Value("${alipay.appId}")
    String appId;
    @Value("${alipay.privateKey}")
    String privateKey;
    @Value("${alipay.publicKey}")
    String publicKey;
    @Autowired
    AliPayService aliPayService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    KeChuanCrmService keChuanCrmService;
    @RequestMapping("/test")
    public String test(HttpServletResponse httpResponse) throws IOException {
        Client client =keChuanCrmService.getMemberByTel("15001060933");
        return "";
    }

}
