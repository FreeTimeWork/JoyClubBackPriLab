package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
public interface VipCardNumService {
    ResultData getList(Long projectId, String batch, String type, Byte status, PageUtil pageUtil);

    /**
     * 批量制卡
     * @param projectId
     * @param batch
     * @param type
     * @param num
     * @return
     */
    ResultData createCardNum(Long projectId, String batch, String type, Integer num);

    /**
     * 获得筛选框所需的数据
     * @return
     */
    ResultData getFormData(Long projectId);
}
