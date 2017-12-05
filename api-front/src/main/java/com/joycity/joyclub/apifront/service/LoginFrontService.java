package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.apifront.modal.login.LoginMethodParam;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.commons.modal.base.ResultData;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
public interface LoginFrontService {


    /**
     * 猫酷联合登录
     */
    ResultData mallcooAutoLogin(Long projectId, String ticket,HttpServletResponse response);
    /**
     * 同步猫酷，crm和自己
     */
    Client mallcooSysn(Long projectId, String ticket);
    /**
     * 登录，会同步crm
     * @param params
     * @param response
     * @return
     */
    Long clientLogin(LoginMethodParam params, HttpServletResponse response);
    /**
     *
     * @param accessToken 网页授权的accessToken
     */
    ResultData wechatLogin(HttpServletResponse response, Long projectId, String phone, String authCode, String wechatOpenId, String accessToken);

    ResultData wapLogin(HttpServletResponse response, Long projectId, String phone, String authCode);

    /**
     * @param from 记录登陆来源
     * @return
     */
    ResultData wapAutoLogin(HttpServletResponse response, Long projectId, String phone, String from);
    /**
     *
     * @param accessToken 网页授权的accessToken
     */
    ResultData wechatAutoLogin(HttpServletResponse response, Long projectId, String phone, String openId, String accessToken, String from);

    /**
     * 商业项目都是使用平台的悦客会
     * @param subProjectId 商业项目id，或者说subProjectId
     */
    ResultData subProjectWapAutoLogin(HttpServletResponse response, Long subProjectId, String phone, String from);
    /**
     *
     * @param accessToken 网页授权的accessToken
     */
    ResultData subProjectWechatAutoLogin(HttpServletResponse response, Long subProjectId, String phone, String openId, String accessToken, String from);

    ResultData logout(String token);

}
