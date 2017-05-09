package com.joycity.joyclub.commons.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.generated.SysStore;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface StoreService {
    /**
     * @return data为按创建时间倒序的所有项目列表
     */
    ResultData getList(Long projectId, PageUtil pageUtil);

    /**
     * 返回某个项目的具体信息
     *
     * @param id
     * @return
     */
    ResultData getStore(Long id);

    ResultData updateStore(SysStore store);

    ResultData createStore(SysStore store);
    ///////////////////api front/////////////////////////////////////
    ResultData getInfo(Long id);

    ResultData getSimpleList(Long projectId, PageUtil pageUtil);
}
