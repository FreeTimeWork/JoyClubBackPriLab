package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.PointQRCodeService;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by Administrator on 2017/8/6.
 */
@RestController
@RequestMapping(URL_API_FRONT + "/qrcode/point")
public class PointQRCodeController {
    @Autowired
    PointQRCodeService service;
    @Autowired
    ClientTokenService clientTokenService;

    @GetMapping
    public ResultData getQRCode(@CookieValue(Global.COOKIE_TOKEN) String token) {
        return service.getQRCode(clientTokenService.getIdOrThrow(token));
    }

    /**
     * 平台没有猫酷账户的
     */
    @GetMapping("/mallcoo")
    public ResultData getQRCodeForMallcoo(@RequestParam String ticket, Long projectId) {
        return service.getQRCodeForMallcoo(projectId, ticket);
    }
}
