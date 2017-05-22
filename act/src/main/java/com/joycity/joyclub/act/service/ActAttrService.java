package com.joycity.joyclub.act.service;

import com.joycity.joyclub.act.modal.generated.SaleActAttr;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ActAttrService {
    /**
     * @return data为按创建时间倒序的所有项目列表
     */
    ResultData getListByStoreIdAndActName(Long storeId, String actName, PageUtil pageUtil);

    /**
     * 返回某个项目的具体信息
     *
     * @param id
     * @return
     */
    ResultData getActAttr(Long id);

    ResultData updateActAttr(SaleActAttr attr);

    ResultData createActAttr(SaleActAttr attr);

}
