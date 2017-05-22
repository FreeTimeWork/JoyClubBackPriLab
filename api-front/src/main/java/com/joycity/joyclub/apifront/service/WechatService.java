package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.apifront.modal.wechat.AccessTokenAndOpenId;
import com.joycity.joyclub.client.modal.WechatUserInfo;

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

}
