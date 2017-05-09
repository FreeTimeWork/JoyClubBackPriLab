package com.joycity.joyclub.commons.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.generated.SaleStoreDesignerWithBLOBs;

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
    /////////////////////////////api front/////////////////////////////////
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
