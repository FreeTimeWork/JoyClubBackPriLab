package com.joycity.joyclub;

import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.act.service.ActService;
import com.joycity.joyclub.card_coupon.mapper.CardCouponCodeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardPosSaleDetailMapper;
import com.joycity.joyclub.card_coupon.modal.PosSaleDetailWithCouponCode;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.system.App;
import com.joycity.joyclub.title_carousel.service.ActTitleCarouselService;
import com.joycity.joyclub.title_carousel.service.ProductTitleCarouselService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles(value = "development")
public class TestApp {
//    @Autowired
//    RechargeService rechargeService;
    @Autowired
    ProductTitleCarouselService productTitleCarouselService;

    @Autowired
    ActTitleCarouselService actTitleCarouselService;

    @Autowired
    ActService actService;
    @Autowired
    KeChuanCrmService keChuanCrmService;
    @Autowired
    CardPosSaleDetailMapper cardPosSaleDetailMapper;

//    @Test
//    public void testRechargeFlow() {
////        rechargeService.rechargeFlow("17600690739",1,true);
//
//    }

//    @Test
//    public void test(){
//        ResultData data = productService.getListByProductNameAndStoreName("商品", "哈", new PageUtil(1, 10));
//        System.out.println(data);
//    }

    @Test
    public void testTitleCarousel() {
//        ResultData data = actTitleCarouselService.getAllActTitleCarousel();
//        System.out.println();

//        ResultData data = productTitleCarouselService.getAllProductTitleCarousel();
//        System.out.println();

        ResultData data = actService.getListByActNameAndStoreName(new Integer(1).longValue(), "test", "哈哈", new PageUtil(1, 10));
        System.out.println();
    }

    @Test
    public void RefundTest(){
        System.out.println(JSON.toJSONString(keChuanCrmService.getMemberByTel("15001060933")));

    }

    @Test
    public void CollectionTest(){
        PosSaleDetailWithCouponCode data = cardPosSaleDetailMapper.selectByOrderCode("2");
        System.out.println(data);
    }

}