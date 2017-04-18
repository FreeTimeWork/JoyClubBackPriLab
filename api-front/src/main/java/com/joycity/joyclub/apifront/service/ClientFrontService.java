package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by Administrator on 2017/4/16.
 */
public interface ClientFrontService {
ResultData getPointRecord(Long id);
ResultData getWechatPortraitAndName(Long id);
    ResultData getCardInfo(Long id);
}
