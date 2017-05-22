package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.WechatService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;
import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID_REQUEST_PARAM;

/**
 * Created by CallMeXYZ on 2017/4/14.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class WechatController {
    @Autowired
    WechatService wechatService;

 /*   //todo token写死了
    @RequestMapping("/wechat/userinfo/{openId}")
    public ResultData getUserInfoForProject(@PathVariable String openId) {
        return new ResultData(wechatService.getUserInfo(openId, "http://bjmall.stcl365.com:20000/wechat/public/getToken"));
    }*/

    /**
     * 用于在微信端登陆获取openId
     * 会返回openId accessToken等，accessToken在登陆时用于获取用户信息，accessToken2小时会过期，目前认为登录页不会待2小时
     * @param code
     * @param projectId
     * @return
     */
    @RequestMapping("/wechat/openid")
    public ResultData getAuthCode(@RequestParam String code, @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId) {
        return new ResultData(wechatService.getAccessTokenAndOpenId(code,projectId));
    }


}
