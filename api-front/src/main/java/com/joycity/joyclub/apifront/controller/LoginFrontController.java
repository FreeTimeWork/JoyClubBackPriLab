package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.KeChuanCrmService;
import com.joycity.joyclub.apifront.service.LoginFrontService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;
import static com.joycity.joyclub.commons.constants.Global.PLATFORM_ID_REQUEST_PARAM;

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
     * @param openId  微信 openId
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/login/wechat", method = {RequestMethod.POST})
    public ResultData wechatLogin(@RequestParam String phone,
                                  @RequestParam final String authCode,
                                  @RequestParam final String openId,
                                  @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) final Long projectId
    ) {
        return loginFrontService.wechatLogin(openId, projectId, phone, authCode);
    }


}
