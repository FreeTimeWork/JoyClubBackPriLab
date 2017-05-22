package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.apifront.service.LoginFrontService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;
import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID_REQUEST_PARAM;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class LoginFrontController {
    @Autowired
    LoginFrontService loginFrontService;
    @Autowired
    KeChuanCrmService keChuanCrmService;

    /**
     * 微信登陆
     *
     * @param phone
     * @param authCode
     * @param openId    微信 openId
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/login/wechat", method = {RequestMethod.POST})
    public ResultData wechatLogin(@RequestParam String phone,
                                  @RequestParam  String authCode,
                                  @RequestParam  String openId,
                                  @RequestParam  String accessToken,
                                  @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) final Long projectId
    ) {
        return loginFrontService.wechatLogin( projectId, phone, authCode,openId,accessToken);
    }

    /**
     * 商业或者地产项目使用平台时的微信登陆
     *
     * @param phone
     * @param openId    微信 openId
     * @return
     */
    @RequestMapping(value = "/login/subproject/auto/wechat", method = {RequestMethod.POST})
    public ResultData wechatAutoLogin(
            @RequestParam String phone,
            @RequestParam String openId,
            @RequestParam String accessToken,
            @RequestParam Long subProjectId
    ) {
        return loginFrontService.subProjectWechatAutoLogin(subProjectId, phone, openId,accessToken);
    }
    /**
     * 商业或者地产项目使用平台时的微信登陆
     *
     * @return
     */
    @RequestMapping(value = "/login/subproject/auto/wap", method = {RequestMethod.POST})
    public ResultData wapAutoLogin(
            @RequestParam String phone,
            @RequestParam Long subProjectId
    ) {
        return loginFrontService.subProjectWapAutoLogin(subProjectId, phone);
    }
}
