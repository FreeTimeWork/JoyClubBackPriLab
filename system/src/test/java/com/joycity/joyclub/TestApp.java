package com.joycity.joyclub;

import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.act.service.ActService;
import com.joycity.joyclub.card_coupon.cache.CardCouponCodeCache;
import com.joycity.joyclub.card_coupon.constant.CouponCodeUseStatus;
import com.joycity.joyclub.card_coupon.mapper.CardCouponCodeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponMapper;
import com.joycity.joyclub.card_coupon.mapper.CardPosSaleDetailMapper;
import com.joycity.joyclub.card_coupon.modal.PosSaleDetailWithCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.PosSaleDetail;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.system.App;
import com.joycity.joyclub.title_carousel.service.ActTitleCarouselService;
import com.joycity.joyclub.title_carousel.service.ProductTitleCarouselService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.Launcher;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles(value = "development")
public class TestApp {

    @Value("${coupon.alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${coupon.alipay.returnUrl}")
    private String returnUrl;
    @Value("${coupon.wxpay.notifyUrl}")
    private String wxNotifyUrl;

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
    @Autowired
    CardCouponLaunchMapper launchMapper;
    @Autowired
    CardCouponCodeMapper cardCouponCodeMapper;
    @Autowired
    RedisTemplate redisTemplate;

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
//        PosSaleDetailWithCouponCode data = cardPosSaleDetailMapper.selectByOrderCode("2");
//        System.out.println(data);
        PosSaleDetail detail = new PosSaleDetail();
        detail.setId(1L);
        detail.setBalance(BigDecimal.ONE);
        detail.setPayment(BigDecimal.ONE);
        detail.setPayable(BigDecimal.ONE);
        detail.setRefundTime(new Date());
        detail.setClientId(1L);
        detail.setOrderCode("1111");
        cardPosSaleDetailMapper.insertSelective(detail);
        detail.setRefundTime(null);
        cardPosSaleDetailMapper.updateByPrimaryKeySelective(detail);
    }

    @Test
    public void Test(){
        CardCouponCode cardCouponCode = new CardCouponCode();
        cardCouponCode.setOrderCode("123");
        cardCouponCode.setUseStatus(CouponCodeUseStatus.NOT_USED);
        cardCouponCode.setUseTime(new Date());
        cardCouponCode.setBelong(-1L);
        cardCouponCode.setClientId(1L);
        cardCouponCode.setCode("127");
        cardCouponCode.setLaunchId(3L);
        cardCouponCode.setReceiveTime(new Date());

        cardCouponCodeMapper.insertSelective(cardCouponCode);
    }

    @Test
    public void RedisTest(){
        BoundHashOperations<String, String, String> test = redisTemplate.boundHashOps("test");
        test.put("h1", "h1");
        test.put("h1", "h1");
        System.out.println(test.get("h1"));
        test.put("h1", "h2");
        System.out.println(test.get("h1"));
    }

}