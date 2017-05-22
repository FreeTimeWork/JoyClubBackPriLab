package com.joycity.joyclub.act.service;

import com.joycity.joyclub.act.modal.generated.SaleActWithBLOBs;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/5/5.
 * 活动的service
 */
public interface ActService {
    //////////////////////////api back//////////////////

    /**
     * @return data为按创建时间倒序的所有项目列表
     */
    ResultData getListByStoreIdAndName(Long storeId, String name, PageUtil pageUtil);

    /**
     * 返回某个项目的具体信息
     *
     * @param id
     * @return
     */
    ResultData getAct(Long id);

    /**
     * @param act 应该包含id
     * @return
     */
    ResultData updateAct(SaleActWithBLOBs act);

    ResultData createAct(SaleActWithBLOBs act);

    /**
     * 返回添加或者编辑项目所需的formData
     *
     * @param storeId
     * @return data为 {@link com.joycity.joyclub.act.modal.ActFormData}
     */
    ResultData getActFormData(Long storeId);

    ////////////////////////////////api front//////////////////////
    ResultData getInfo(Long id);

    /**
     * projectId,StoreId按顺序选择非空的值作为筛选条件
     * 如果projectId,StoreId,都空，搜索的所有
     */
    ResultData getList(Long projectId, Long storeId, PageUtil pageUtil);

    ResultData getAttrs(Long id);
}

