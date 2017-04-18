package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface ProductFrontService {
    ResultData getInfo(Long id);
    ResultData getAttrs(Long id);

    /**
     * projectId,storeId, designerId知按顺序取不为空的作为筛选条件
     * @param projectId
     * @param storeId
     * @param designerId
     * @param pageUtil
     * @return
     */
    ResultData getList(Long projectId,Long storeId, Long designerId, PageUtil pageUtil);
}
