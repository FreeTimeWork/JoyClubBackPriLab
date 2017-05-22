package com.joycity.joyclub.apifront.service.impl;

import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.apifront.modal.wechat.AccessTokenAndOpenId;
import com.joycity.joyclub.client.modal.WechatUserInfo;
import com.joycity.joyclub.apifront.pay.wechat.WxPayConfig;
import com.joycity.joyclub.apifront.service.WechatService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.joycity.joyclub.commons.constant.ResultCode.WECHAT_ERROR;

/**
 * Created by CallMeXYZ on 2017/4/7.
 */
@Service
public class WechatServiceImpl implements WechatService {
    private Log logger = LogFactory.getLog(WechatServiceImpl.class);
    @Autowired
    WxPayConfig wxpayConfig;
    @Autowired
    RestTemplate restTemplate;
    /**
     * 这个获取用户信息，如果没关注，只能获得openId,无法获取用户信息
     */
    private final String URL_SUBSCIBE_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    private final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**
     * accessToken是网页授权获取的，不是基础的那个
     */
    private final String URL_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /*   // TODO: 2017/5/12 appId应该根据project去取
       @Override
       public WechatUserInfo getUserInfo(String openid, String accessTokenUrl) {
           if (StringUtils.isBlank(openid)) {
               throw new BusinessException(WECHAT_ERROR, "获取微信用户信息失败，openId为空");
           }
           String url = URL_USER_INFO.replaceAll("ACCESS_TOKEN", getAccessToken(accessTokenUrl)).replaceAll("OPENID", openid);
           WechatUserInfo info = restTemplate.getForObject(url, WechatUserInfo.class);
           //基本上出错就是因为accessToken获取的原因
           if (info.getOpenid() == null) {
               logger.error("获取用户信息失败");
               throw new BusinessException(WECHAT_ERROR, "获取微信用户信息失败");
           }
           return info;
       }*/
    @Override
    public WechatUserInfo getUserInfo(String openid, String accessToken) {
        if (StringUtils.isBlank(openid)) {
            throw new BusinessException(WECHAT_ERROR, "获取微信用户信息失败，openId为空");
        }
        String url = URL_USER_INFO.replaceAll("ACCESS_TOKEN", accessToken).replaceAll("OPENID", openid);
        String result = restTemplate.getForObject(url, String.class);
        WechatUserInfo info = JSON.parseObject(result,WechatUserInfo.class);
        //如果超时的话，info里的值应该是空的，但是info却不空
        return info;
    }

    @Override
    public AccessTokenAndOpenId getAccessTokenAndOpenId(String code, Long projectId) {
        // TODO: 2017/4/18 不同的项目appid不同 
        String url = URL_ACCESS_TOKEN.replaceAll("APPID", wxpayConfig.getAppid()).replaceAll("SECRET", wxpayConfig.getAppsecret()).replace("CODE", code);
        String resultStr = restTemplate.getForObject(url, String.class);
        AccessTokenAndOpenId result = JSON.parseObject(resultStr, AccessTokenAndOpenId.class);
        if (result == null || result.getErrcode() != null || result.getOpenid() == null) {
            throw new BusinessException(WECHAT_ERROR, "获取微信openId失败");
        }
        return result;
    }


}
