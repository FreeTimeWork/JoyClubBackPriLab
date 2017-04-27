package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.mapper.manual.WechatOpenIdMapper;
import com.joycity.joyclub.apifront.service.WechatOpenIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
@Service
public class WechatOpenIdServiceImpl implements WechatOpenIdService {
    @Autowired
    WechatOpenIdMapper wechatOpenIdMapper;

    @Override
    public String getOpenId(Long projectId, Long clientId) {
        return wechatOpenIdMapper.getOpenId(projectId, clientId);
    }

    @Override
    public void saveOrUpdateProjectOpenId(Long projectId, Long clientId, String openId) {
       String nowOpenId = wechatOpenIdMapper.getOpenId(projectId,clientId);
        //从未登陆，则新建
        if(nowOpenId==null) {
            wechatOpenIdMapper.saveOpenId(projectId, clientId, openId);
        }
        //用别的微信号登陆过，则替换
        else if(!nowOpenId.equals(openId)){
            wechatOpenIdMapper.updateOpenId(projectId, clientId, openId);
        }
    }



}
