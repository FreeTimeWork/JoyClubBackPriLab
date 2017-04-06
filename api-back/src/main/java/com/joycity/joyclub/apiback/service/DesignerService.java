package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SaleStoreDesignerWithBLOBs;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface DesignerService {
    /**
     * @return data为按创建时间倒序的所有项目列表
     */
    ResultData getListByStoreId(Long storeId);

    /**
     * 返回某个项目的具体信息
     *
     * @param id
     * @return
     */
    ResultData getDesigner(Long id);

    /**
     * @param product 应该包含id
     * @return
     */
    ResultData updateDesigner(SaleStoreDesignerWithBLOBs product);

    ResultData createDesigner(SaleStoreDesignerWithBLOBs product);

   
}
