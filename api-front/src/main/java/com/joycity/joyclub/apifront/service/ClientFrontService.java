package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by Administrator on 2017/4/16.
 */
public interface ClientFrontService {
    ResultData getPointRecord(Long id);

    ResultData getWechatPortraitAndName(Long id);

    ResultData getCardInfo(Long id);

    /**
     * 从我方系统查询积分而不是从科传
     */
    Integer getPoint(Long id);

    /**
     * 可正可负
     * @param id
     * @param changeValue
     */
    void addPoint(Long id,Double changeValue);
}
