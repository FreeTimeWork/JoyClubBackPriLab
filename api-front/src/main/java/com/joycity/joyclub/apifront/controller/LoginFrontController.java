package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.LoginFrontService;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID_REQUEST_PARAM;
import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

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
                                  @RequestParam String authCode,
                                  @RequestParam String openId,
                                  @RequestParam String accessToken,
                                  @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
                                  HttpServletResponse response
    ) {

        return loginFrontService.wechatLogin(response, projectId, phone, authCode, openId, accessToken);
    }

    @RequestMapping(value = "/login/wap", method = {RequestMethod.POST})
    public ResultData wapLogin(@RequestParam String phone,
                               @RequestParam String authCode,
                               @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
                               HttpServletResponse response
    ) {
        return loginFrontService.wapLogin(response, projectId, phone, authCode);
    }

    /**
     * 商业或者地产项目使用平台时的微信登陆
     *
     * @param phone
     * @param openId 微信 openId
     * @return
     */
    @RequestMapping(value = "/login/auto/wechat", method = {RequestMethod.POST})
    public ResultData wechatAutoLogin(
            @RequestParam String phone,
            @RequestParam String openId,
            @RequestParam String accessToken,
            @RequestParam Long projectId,
            @RequestParam(required = false) String from,
            HttpServletResponse response
    ) {
        return loginFrontService.wechatAutoLogin(response, projectId, phone, openId, accessToken, from);
    }

    /**
     * 商业或者地产项目使用平台时的微信登陆
     *
     * @return
     */
    @RequestMapping(value = "/login/auto/wap", method = {RequestMethod.POST})
    public ResultData wapAutoLogin(
            @RequestParam String phone,
            @RequestParam Long projectId,
            @RequestParam(required = false) String from,
            HttpServletResponse response
    ) {
        return loginFrontService.wapAutoLogin(response, projectId, phone, from);
    }

    /**
     * 商业或者地产项目使用平台时的微信登陆
     *
     * @param phone
     * @param openId 微信 openId
     * @return
     */
    @RequestMapping(value = "/login/subproject/auto/wechat", method = {RequestMethod.POST})
    public ResultData subProjectWechatAutoLogin(
            @RequestParam String phone,
            @RequestParam String openId,
            @RequestParam String accessToken,
            @RequestParam Long subProjectId,
            @RequestParam(required = false) String from,
            HttpServletResponse response
    ) {
        return loginFrontService.subProjectWechatAutoLogin(response, subProjectId, phone, openId, accessToken, from);
    }

    /**
     * 商业或者地产项目使用平台时的微信登陆
     *
     * @return
     */
    @RequestMapping(value = "/login/subproject/auto/wap", method = {RequestMethod.POST})
    public ResultData subProjectWapAutoLogin(
            @RequestParam String phone,
            @RequestParam Long subProjectId,
            @RequestParam(required = false) String from,
            HttpServletResponse response
    ) {
        return loginFrontService.subProjectWapAutoLogin(response, subProjectId, phone, from);
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public ResultData logout(
            @CookieValue(Global.COOKIE_TOKEN) String token
    ) {
        return loginFrontService.logout(token);
    }
}
