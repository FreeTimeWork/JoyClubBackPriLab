package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.vo.auth.*;
import com.joycity.joyclub.apifront.service.LoginFrontService;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.mallcoo.modal.result.data.UserAdvancedInfo;
import com.joycity.joyclub.mallcoo.service.MallCooService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class LoginFrontController {
    private Log logger = LogFactory.getLog(LoginFrontController.class);
    @Autowired
    LoginFrontService loginFrontService;
    @Autowired
    KeChuanCrmService keChuanCrmService;
    @Autowired
    MallCooService mallCooService;

    /**
     * 微信登陆
     *
     * @return
     */
    @RequestMapping(value = "/login/wechat", method = {RequestMethod.POST})
    public ResultData wechatLogin(@Valid @RequestBody WechatLoginVO vo, HttpServletResponse response) {
        logger.info("/login/wechat" + vo);
        return loginFrontService.wechatLogin(response, vo.getProjectId(), vo.getPhone(), vo.getAuthCode(), vo.getOpenId(), vo.getAccessToken());
    }

    @RequestMapping(value = "/login/wap", method = {RequestMethod.POST})
    public ResultData wapLogin(@Valid @RequestBody WapLoginVO vo, HttpServletResponse response) {
        return loginFrontService.wapLogin(response, vo.getProjectId(), vo.getPhone(), vo.getAuthCode());
    }

    /**
     * 商业或者地产项目使用平台时的微信登陆
     *
     * @return
     */
    @RequestMapping(value = "/login/auto/wechat", method = {RequestMethod.POST})
    public ResultData wechatAutoLogin(
            @Valid @RequestBody WechatAutoLoginVO vo, HttpServletResponse response) {
        logger.info("/login/auto/wechat = "+vo);
        return loginFrontService.wechatAutoLogin(response, vo.getProjectId(), vo.getPhone(), vo.getOpenId(), vo.getAccessToken(), vo.getFrom());
    }

    /**
     * 网页登陆
     *
     * @return
     */
    @RequestMapping(value = "/login/auto/wap", method = {RequestMethod.POST})
    public ResultData wapAutoLogin(
            @Valid @RequestBody WapAutoLoginVO vo, HttpServletResponse response) {
        return loginFrontService.wapAutoLogin(response, vo.getProjectId(), vo.getPhone(), vo.getFrom());
    }

    /**
     * 商业或者地产项目使用平台时的微信登陆
     */
    @RequestMapping(value = "/login/subproject/auto/wechat", method = {RequestMethod.POST})
    public ResultData subProjectWechatAutoLogin(@Valid @RequestBody SubProjectWechatAutoLoginVO vo, HttpServletResponse response) {
       logger.info("/login/subproject/auto/wechat = "+ vo);
        return loginFrontService.subProjectWechatAutoLogin(response, vo.getSubProjectId(), vo.getPhone(), vo.getOpenId(), vo.getAccessToken(), vo.getFrom());
    }

    /**
     * 商业或者地产项目使用平台时的微信登陆
     */
    @RequestMapping(value = "/login/subproject/auto/wap", method = {RequestMethod.POST})
    public ResultData subProjectWapAutoLogin(
            @Valid @RequestBody SubProjectWapAutoLoginVO vo, HttpServletResponse response) {
        return loginFrontService.subProjectWapAutoLogin(response, vo.getSubProjectId(), vo.getPhone(), vo.getFrom());
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public ResultData logout(
            @CookieValue(Global.COOKIE_TOKEN) String token
    ) {
        return loginFrontService.logout(token);
    }

    /**
     * 猫酷联合登录
     *
     * @return
     */
    @RequestMapping(value = "/login/auto/mallcoo", method = {RequestMethod.GET})
    public ResultData mallcooAutoLogin(
            @RequestParam String ticket,@RequestParam Long projectId, HttpServletResponse response) {
        logger.info("/login/auto/mallcoo ticket= "+ticket);
        return loginFrontService.mallcooAutoLogin(projectId, ticket,response);

    }
}
