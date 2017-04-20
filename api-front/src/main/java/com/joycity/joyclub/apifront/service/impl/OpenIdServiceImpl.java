package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.mapper.manual.WechatOpenIdMapper;
import com.joycity.joyclub.apifront.service.OpenIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
@Service
public class OpenIdServiceImpl implements OpenIdService {
    @Autowired
    WechatOpenIdMapper wechatOpenIdMapper;

    @Override
    public String getOpenId(Long projectId, Long clientId) {
        return wechatOpenIdMapper.getOpenId(projectId, clientId);
    }

    @Override
    public void saveOpenId(Long projectId, Long clientId, String openId) {
        wechatOpenIdMapper.saveOpenId(projectId, clientId, openId);
    }

    @Override
    public Boolean checkOpenIdExist(String openId) {
        return !wechatOpenIdMapper.countByOpenId(openId).equals(0);
    }
}
