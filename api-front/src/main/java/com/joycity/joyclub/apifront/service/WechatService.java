package com.joycity.joyclub.apifront.service;

        import com.joycity.joyclub.apifront.modal.wechat.AccessTokenAndOpenId;
        import com.joycity.joyclub.client.modal.WechatUserInfo;

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
// TODO: 2017/4/16 增加项目选项 
    /**
     *获取openid
     * @param code 微信网页跳转返回的code参数
     * @return
     */
    AccessTokenAndOpenId getAccessTokenAndOpenId(String code,Long projectId);

}
