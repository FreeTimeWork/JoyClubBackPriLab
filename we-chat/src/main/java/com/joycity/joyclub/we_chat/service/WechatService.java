package com.joycity.joyclub.we_chat.service;

import com.joycity.joyclub.we_chat.modal.AccessTokenAndOpenId;
import com.joycity.joyclub.we_chat.modal.WechatUserInfo;

/**
 * Created by CallMeXYZ on 2017/4/7.
 */
public interface WechatService {
    /**
     * @param openid
     * @param accessToken 网页授权获取的accessToken,2小时会过期，目前不进行处理
     * @return
     */
    WechatUserInfo getUserInfo(String openid, String accessToken);

    /**
     * 获取openid
     *
     * @param code 微信网页跳转返回的code参数
     * @return
     */
    AccessTokenAndOpenId getAccessTokenAndOpenId(String code, Long projectId);

    /**
     *
     * @param wechatProjectId 这里是openId指的是所登陆的微信公众号的projectId,而不是悦客会的projectId,
     *                  比如商业项目使用平台悦客会，这个projectId应该是商业项目的，而不是平台项目的
     * @param clientId
     * @return
     */
    String getOpenId(Long wechatProjectId,Long clientId);

    /**
     * 存在，则检查是否需要更新，
     * 不存在则插入
     * 保证一个项目下一个用户只有一个微信号被绑定
     * @param wechatProjectId 这里是openId指的是所登陆的微信公众号的projectId,而不是悦客会的projectId,
     *                  比如商业项目使用平台悦客会，这个projectId应该是商业项目的，而不是平台项目的
     * @param clientId
     * @param openId
     */
    void saveOrUpdateProjectOpenId(Long wechatProjectId,Long clientId,String openId);

    /**
     * code只能使用一次，注意调用这个方法后code不能再被使用
     */
    AccessTokenAndOpenId saveOrUpdateProjectOpenIdByCode(Long projectId,Long clientId,String code);


}
