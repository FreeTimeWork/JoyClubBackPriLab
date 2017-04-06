package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SaleActWithBLOBs;
import com.joycity.joyclub.apiback.modal.act.ActFormData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ActService {
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
     * @return data为 {@link ActFormData}
     */
    ResultData getActFormData(Long storeId);
}
