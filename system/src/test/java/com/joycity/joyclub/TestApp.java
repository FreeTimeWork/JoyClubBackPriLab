package com.joycity.joyclub;


import com.joycity.joyclub.recharge.service.RechargeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApp {
    @Autowired
    RechargeService rechargeService;

    @Test
    public void testRechargeFlow() {
//        rechargeService.rechargeFlow("17600690739",1,true);

    }
}