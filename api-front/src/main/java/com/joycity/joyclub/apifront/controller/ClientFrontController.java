package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by Administrator on 2017/4/16.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class ClientFrontController {
    @Autowired
    ClientService clientService;
    @Autowired
    ClientTokenService clientTokenService;

    @RequestMapping(value = "/client/point/records", method = RequestMethod.GET)
    public ResultData getPointRecords(@CookieValue(Global.COOKIE_TOKEN) String token) {
        return clientService.getPointRecord(clientTokenService.getIdOrThrow(token));
    }

    @RequestMapping(value = "/client/wechat/info", method = RequestMethod.GET)
    public ResultData getWechatInfo(@CookieValue(Global.COOKIE_TOKEN) String token) {
        return clientService.getWechatPortraitAndName(clientTokenService.getIdOrThrow(token));
    }

    @RequestMapping(value = "/client/card/info", method = RequestMethod.GET)
    public ResultData getCardBaseInfo(@CookieValue(Global.COOKIE_TOKEN) String token) {
        return clientService.getCardInfo(clientTokenService.getIdOrThrow(token));
    }

}
