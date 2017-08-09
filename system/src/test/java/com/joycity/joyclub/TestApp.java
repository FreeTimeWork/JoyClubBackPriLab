package com.joycity.joyclub;

import com.joycity.joyclub.mallcoo.service.MallCooService;
import com.joycity.joyclub.system.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles(value = "development")
public class TestApp {
    @Autowired
    MallCooService mallCooService;

    @Test
    public void test1() {
        mallCooService.getCouponInfo(1L, "0500307360");
    }

    @Test
    public void test2() {
        mallCooService.getCoupons(1L, "0500307360");
    }
}