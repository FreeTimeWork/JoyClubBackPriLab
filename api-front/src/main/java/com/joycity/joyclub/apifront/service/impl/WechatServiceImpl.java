package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.exception.BusinessException;
import com.joycity.joyclub.apifront.modal.wechat.WechatUserInfo;
import com.joycity.joyclub.apifront.service.WechatService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.joycity.joyclub.apifront.constant.ResultCode.WECHAT_ERROR;

/**
 * Created by CallMeXYZ on 2017/4/7.
 */
@Service
public class WechatServiceImpl implements WechatService {
    private   Log logger = LogFactory.getLog(WechatServiceImpl.class);


    @Autowired
    RestTemplate restTemplate;
    private final String URL_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

   @Override
    public WechatUserInfo getUserInfo(String openid, String accessTokenUrl) {
        if (StringUtils.isBlank(openid)) {
            throw new BusinessException(WECHAT_ERROR, "获取微信用户信息的失败，openId为空");
        }
        String url = URL_USER_INFO.replaceAll("ACCESS_TOKEN", getAccessToken(accessTokenUrl)).replaceAll("OPENID", openid);
       WechatUserInfo info = restTemplate.getForObject(url, WechatUserInfo.class);
        //基本上出错就是因为accessToken获取的原因
        if (info.getOpenid() == null) {
            logger.error("获取用户信息失败");
            throw new BusinessException(WECHAT_ERROR, "获取微信用户信息失败");
        }
        return info;

    }

    /**
     * 获取accessToken
     * getToken
     * @return
     */
    public String getAccessToken(String url) {
        // TODO: 2017/4/10 将来不用的公众号很可能accessToken返回对象结构不用
        //目前悦客会是商通建立的
        Map<String, String> response = restTemplate.getForObject(url, Map.class);
        return response.get("data");
    }
}
