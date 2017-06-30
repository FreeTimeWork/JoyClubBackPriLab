package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
public interface LoginFrontService {
    /**
     *
     * @param accessToken 网页授权的accessToken
     */
    ResultData wechatLogin(HttpServletResponse response, Long projectId, String phone, String authCode, String wechatOpenId, String accessToken);

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
