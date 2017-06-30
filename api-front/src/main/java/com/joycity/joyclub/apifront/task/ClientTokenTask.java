package com.joycity.joyclub.apifront.task;

import com.joycity.joyclub.client_token.service.ClientTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by CallMeXYZ on 2017/6/29.
 */
@Component
public class ClientTokenTask {
    @Autowired
    ClientTokenService clientTokenService;

    /**
     * 每天12点 查找发货超过十天的store order，设置为已经收货
     */
    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 12 * * ?")
    public void autoReceiveOrder() {
        clientTokenService.clearExpireToken();
    }

}
