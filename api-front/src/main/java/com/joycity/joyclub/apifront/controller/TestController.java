package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.mapper.manual.ClientUserMapper;
import com.joycity.joyclub.apifront.mapper.manual.MsgAuthCodeMapper;
import com.joycity.joyclub.apifront.modal.MsgAuthCode;
import com.joycity.joyclub.apifront.service.MsgAuthCodeService;
import com.joycity.joyclub.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/11.
 */
@RestController
@RequestMapping(URL_API_FRONT + "/test")
public class TestController {
    @Autowired
    CouponService couponService;
    @Autowired
    ClientUserMapper clientUserMapper;
    @Autowired
    MsgAuthCodeService msgAuthCodeService;
    @Autowired
    MsgAuthCodeMapper msgAuthCodeMapper;

    @RequestMapping("/test")
    public Long test(@RequestParam String phone, @RequestParam String code) {
 /*       msgAuthCodeService.checkAuthCode(phone, code);*/
       MsgAuthCode msgAuthCode = msgAuthCodeMapper.getLatestCodeByPhone(phone);
        return msgAuthCode.getCreateTime().getTime() ;
    }
}
