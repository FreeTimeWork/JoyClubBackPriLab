package com.joycity.joyclub;

import com.joycity.joyclub.product.service.ProductService;
import com.joycity.joyclub.system.App;
import com.joycity.joyclub.title_carousel.service.TitleCarouselService;
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
    ProductService productService;

    @Autowired
    TitleCarouselService titleCarouselService;

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
//        ResultData data = titleCarouselService.getAllTitleCarousel();
        System.out.println();
    }
}