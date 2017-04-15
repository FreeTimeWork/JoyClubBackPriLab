package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.WechatService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/14.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class WechatController {
    @Autowired
    WechatService wechatService;

    //todo token写死了
    @RequestMapping("/wechat/userinfo/{openId}")
    public ResultData getUserInfoForProject(@PathVariable String openId) {
        return new ResultData(wechatService.getUserInfo(openId, "http://bjmall.stcl365.com:20000/wechat/public/getToken"));
    }

}
