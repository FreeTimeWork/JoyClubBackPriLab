package com.joycity.joyclub;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.system.App;
import com.joycity.joyclub.title_carousel.service.ActTitleCarouselService;
import com.joycity.joyclub.title_carousel.service.ProductTitleCarouselService;
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
//    @Autowired
//    RechargeService rechargeService;
    @Autowired
    ProductTitleCarouselService productTitleCarouselService;

    @Autowired
    ActTitleCarouselService actTitleCarouselService;

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

        ResultData data = productTitleCarouselService.getAllProductTitleCarousel();
        System.out.println();
    }


}