package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
public interface LoginFrontService {
    ResultData wechatLogin(String openId, Long projectId, String phone, String authCode);
}
