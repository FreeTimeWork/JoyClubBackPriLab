package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.apifront.service.MsgAuthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class MsgAuthCodeController {
    @Autowired
    MsgAuthCodeService msgAuthCodeService;

    @RequestMapping(value = "/authcode/msg/{phone}", method = RequestMethod.POST)
    public ResultData getCode(@PathVariable String phone) {
        return msgAuthCodeService.getAndSendAuthCode(phone);
    }
}
