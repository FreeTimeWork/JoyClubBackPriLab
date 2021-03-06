package com.joycity.joyclub.we_chat.service.impl;

import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.apifront.mapper.manual.ProjectMapper;
import com.joycity.joyclub.apifront.modal.project.SysProject;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.we_chat.mapper.WechatOpenIdMapper;
import com.joycity.joyclub.we_chat.modal.AccessTokenAndOpenId;
import com.joycity.joyclub.we_chat.modal.WechatUserInfo;
import com.joycity.joyclub.we_chat.pay.wechat.WxPayConfig;
import com.joycity.joyclub.we_chat.service.WechatService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    @Autowired
    WechatOpenIdMapper wechatOpenIdMapper;

    @Autowired
    private ProjectMapper projectMapper;
    /**
     * 这个获取用户信息，如果没关注，只能获得openId,无法获取用户信息
     */
    private final String URL_SUBSCIBE_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    private final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**
     * accessToken是网页授权获取的，不是基础的那个
     */
    private final String URL_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    @Override
    public WechatUserInfo getUserInfo(String openid, String accessToken) {
        if (StringUtils.isBlank(openid)) {
            throw new BusinessException(WECHAT_ERROR, "获取微信用户信息失败，openId为空");
        }
        String url = URL_USER_INFO.replaceAll("ACCESS_TOKEN", accessToken).replaceAll("OPENID", openid);
        String result = restTemplate.getForObject(url, String.class);
        WechatUserInfo info = JSON.parseObject(result, WechatUserInfo.class);
        //如果超时的话，info里的值应该是空的，但是info却不空
        return info;
    }

    @Override
    public AccessTokenAndOpenId getAccessTokenAndOpenId(String code, Long projectId) {
        SysProject project = projectMapper.selectByPrimaryKey(projectId);
        // TODO: 2017/4/18 不同的项目appid不同
        String url = URL_ACCESS_TOKEN.replaceAll("APPID",project.getWechatAppId()).replaceAll("SECRET", project.getWechatAppSecret()).replace("CODE", code);
        String resultStr = restTemplate.getForObject(url, String.class);
        AccessTokenAndOpenId result = JSON.parseObject(resultStr, AccessTokenAndOpenId.class);
        logger.info("getAccessTokenAndOpenId: result="+ resultStr);
        if (result == null || result.getErrcode() != null || result.getOpenid() == null) {
            throw new BusinessException(WECHAT_ERROR, "获取微信openId失败");
        }
        return result;
    }

    @Override
    public String getOpenId(Long projectId, Long clientId) {
        return wechatOpenIdMapper.getOpenId(projectId, clientId);
    }

    @Override
    public void saveOrUpdateProjectOpenId(Long projectId, Long clientId, String openId) {
        String nowOpenId = wechatOpenIdMapper.getOpenId(projectId, clientId);
        //从未登陆，则新建
        if (nowOpenId == null) {
            wechatOpenIdMapper.saveOpenId(projectId, clientId, openId);
        }
        //用别的微信号登陆过，则替换
        else if (!nowOpenId.equals(openId)) {
            wechatOpenIdMapper.updateOpenId(projectId, clientId, openId);
        }
    }

    @Override
    public AccessTokenAndOpenId saveOrUpdateProjectOpenIdByCode(Long projectId, Long clientId, String code) {
        AccessTokenAndOpenId tokenAndOpenId = getAccessTokenAndOpenId(code, projectId);
        saveOrUpdateProjectOpenId(projectId, clientId, tokenAndOpenId.getOpenid());
        return tokenAndOpenId;
    }

}
