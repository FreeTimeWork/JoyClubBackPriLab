package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface ActFrontService {
    ResultData getInfo(Long id);

    /**
     * projectId,StoreId按顺序选择非空的值作为筛选条件
     * 如果projectId,StoreId,都空，搜索的所有
     */
    ResultData getList(Long projectId, Long storeId, PageUtil pageUtil);
}
