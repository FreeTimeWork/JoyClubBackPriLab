package com.joycity.joyclub.apifront.service;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
public interface WechatOpenIdService {
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

}
