package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface DesignerFrontService {
    ResultData getInfo(Long id);

    /**
     * 二者都可空
     * 最好选一个为空
     * 最大选择一个筛选条件
     * 目前没有分页
     * 至少搜索projectId的时候要分页
     * @param projectId
     * @param storeId
     * @return
     */
    // TODO: 2017/4/14 分页 
    ResultData getList(Long projectId,Long storeId);
}
