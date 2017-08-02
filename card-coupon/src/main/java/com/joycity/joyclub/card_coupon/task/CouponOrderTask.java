package com.joycity.joyclub.card_coupon.task;

import com.joycity.joyclub.card_coupon.service.CardCouponOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by fangchen.chai on 2017/8/2
 */
@Component
public class CouponOrderTask {

    @Autowired
    private CardCouponOrderService couponOrderService;

    /**
     * 每隔十分钟， 查找
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void autoCancelOrder() {
        couponOrderService.cancelOverTenMinOrder();
    }
}
