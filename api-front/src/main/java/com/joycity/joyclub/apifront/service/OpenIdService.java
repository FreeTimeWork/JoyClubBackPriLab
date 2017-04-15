package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.apifront.modal.wechat.ClientWechatOpenId;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
public interface OpenIdService {
    String getOpenId(Long projectId,Long clientId);
    void saveOpenId(Long projectId,Long clientId,String openId);
    Boolean checkOpenIdExist(String openId);
}
