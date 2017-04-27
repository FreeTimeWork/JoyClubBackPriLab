package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.client.service.ClientFrontService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by Administrator on 2017/4/16.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class ClientFrontController {
    @Autowired
    ClientFrontService clientFrontService;

    @RequestMapping(value = "/client/{id}/point/records", method = RequestMethod.GET)
    public ResultData getPointRecords(@PathVariable Long id) {
        return clientFrontService.getPointRecord(id);
    }

    @RequestMapping(value = "/client/{id}/wechat/info", method = RequestMethod.GET)
    public ResultData getWechatInfo(@PathVariable Long id) {
        return clientFrontService.getWechatPortraitAndName(id);
    }
    @RequestMapping(value = "/client/{id}/card/info", method = RequestMethod.GET)
    public ResultData getCardBaseInfo(@PathVariable Long id) {
        return clientFrontService.getCardInfo(id);
    }

}
