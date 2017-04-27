package com.joycity.joyclub.apifront.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/11.
 */
@RestController
@RequestMapping(URL_API_FRONT + "/test")
public class TestController {
/*@Autowired
    KeChuanCrmService keChuanCrmService;
    @RequestMapping("/test")
    public Object test(){
        return keChuanCrmService.getSaleRecord("9801012830");
    }*/
}
