package com.joycity.joyclub.api_async.controller;

import com.joycity.joyclub.api_async.modal.kechuan.KeChuanAsyncData;
import com.joycity.joyclub.api_async.modal.kechuan.KeChuanAsyncResult;
import com.joycity.joyclub.api_async.service.impl.KeChuanAsyncServiceImpl;
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

    @PostMapping("/client")
    public KeChuanAsyncResult asyncClient(@Valid @RequestBody KeChuanAsyncData data) {
        return keChuanAsyncService.ayncClinet(data);
    }
}
