package com.joycity.joyclub.api_async.controller;

import com.joycity.joyclub.api_async.modal.kechuan.KeChuanAsyncData;
import com.joycity.joyclub.api_async.modal.kechuan.KeChuanAsyncResult;
import com.joycity.joyclub.api_async.service.impl.KeChuanAsyncServiceImpl;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * AynscClientController
 *
 * @author CallMeXYZ
 * @date 2017/8/11
 */
@RestController
@RequestMapping("/api/async/kechuan")
public class KeChuanAynscController {
    @Autowired
    KeChuanAsyncServiceImpl keChuanAsyncService;
    private final Log logger = LogFactory.getLog(KeChuanAsyncServiceImpl.class);

    @Autowired
    ClientService clientService;

    @PostMapping("/client")
    public KeChuanAsyncResult asyncClient(@Valid @RequestBody KeChuanAsyncData data) {
        return keChuanAsyncService.ayncClinet(data);
    }

    @GetMapping("/crmId")
    public ResultData syncCrmId() {

        return clientService.syncCrmId();
    }
}
