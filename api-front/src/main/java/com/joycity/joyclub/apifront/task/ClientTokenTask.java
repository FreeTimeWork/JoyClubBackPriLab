package com.joycity.joyclub.apifront.task;

import com.joycity.joyclub.client_token.service.ClientTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by CallMeXYZ on 2017/6/29.
 */
@Component
public class ClientTokenTask {
    @Autowired
    ClientTokenService clientTokenService;

    /**
     * 每天12点 查找过期30天的token并清空
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void autoReceiveOrder() {
        clientTokenService.clearExpireToken();
    }

}
