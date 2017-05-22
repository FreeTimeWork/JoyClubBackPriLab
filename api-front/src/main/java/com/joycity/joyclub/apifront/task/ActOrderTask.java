package com.joycity.joyclub.apifront.task;

import com.joycity.joyclub.apifront.service.ActOrderFrontService;
import com.joycity.joyclub.apifront.service.ProductOrderFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by CallMeXYZ on 2017/4/28.
 */
@Component
public class ActOrderTask {
    @Autowired
    ActOrderFrontService orderService;
/*
    *//**
     * 每天12点 查找发货超过十天的store order，设置为已经收货
     *//*
    @Scheduled(cron = "0 0 12 * * ?")
    public void autoReceiveOrder() {
        orderService.receiveOverTenDayStoreOrder();
    }*/

    /**
     * 每隔五分钟， 查找
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void autoCancelOrder() {
        orderService.cancelOverOneHourOrder();
    }


}
