package com.joycity.joyclub.apifront.controller;

import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.coupon.modal.generated.Coupon;
import com.joycity.joyclub.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/11.
 */
@RestController
@RequestMapping(URL_API_FRONT + "/test")
public class TestController {
    @Autowired
    CouponService couponService;

    @RequestMapping("/test")
    public void test(String[] test) {

        System.out.println(JSON.toJSONString(test));

    }
}
