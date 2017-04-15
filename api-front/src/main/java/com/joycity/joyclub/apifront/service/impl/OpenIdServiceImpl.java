package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.mapper.manual.OpenIdMapper;
import com.joycity.joyclub.apifront.service.OpenIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
@Service
public class OpenIdServiceImpl implements OpenIdService {
    @Autowired
    OpenIdMapper openIdMapper;

    @Override
    public String getOpenId(Long projectId, Long clientId) {
        return openIdMapper.getOpenId(projectId, clientId);
    }

    @Override
    public void saveOpenId(Long projectId, Long clientId, String openId) {
        openIdMapper.saveOpenId(projectId, clientId, openId);
    }

    @Override
    public Boolean checkOpenIdExist(String openId) {
        return !openIdMapper.countByOpenId(openId).equals(0);
    }
}
