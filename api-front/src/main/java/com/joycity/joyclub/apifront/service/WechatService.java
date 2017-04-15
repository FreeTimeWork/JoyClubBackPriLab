package com.joycity.joyclub.apifront.service;

        import com.joycity.joyclub.apifront.modal.wechat.WechatUserInfo;

/**
 * Created by CallMeXYZ on 2017/4/7.
 */
public interface WechatService {
    /**
     * @param openid
     * @param accessTokenUrl 公众号accessToken的获取url
     * @return
     */
    WechatUserInfo getUserInfo(String openid, String accessTokenUrl);
}
