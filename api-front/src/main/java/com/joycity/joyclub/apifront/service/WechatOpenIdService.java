package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.apifront.modal.wechat.ClientWechatOpenId;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
public interface WechatOpenIdService {
    String getOpenId(Long projectId,Long clientId);

    /**
     * 存在，则检查是否需要更新，
     * 不存在则插入
     * 保证一个项目下一个用户只有一个微信号被绑定
     * @param projectId
     * @param clientId
     * @param openId
     */
    void saveOrUpdateProjectOpenId(Long projectId,Long clientId,String openId);

}
