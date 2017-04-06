package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SaleActPrice;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ActPriceService {
    /**
     * 按增加时间倒序
     *
     * @param storeName
     * @param reviewStatus
     * @param actName
     * @param pageUtil
     * @return
     */
    ResultData getListForProject(String storeName, Integer reviewStatus, String actName, PageUtil pageUtil);

    /**
     * 按增加时间倒序
     *
     * @param storeId
     * @param reviewStatus
     * @param actName
     * @param pageUtil
     * @return
     */
    public ResultData getListForStore(Long storeId, Integer reviewStatus, String actName, PageUtil pageUtil);

    /**
     * 返回某个项目的具体信息
     *
     * @param id
     * @return
     */
    ResultData getActPrice(Long id);

    ResultData updateActPrice(SaleActPrice price);

    ResultData forbidActPrice(Long id);

    /**
     * 项目管理者通过审核
     *
     * @param id
     * @return
     */
    ResultData permitActPrice(Long id);

    /**
     * 项目管理者拒绝审核
     *
     * @param id
     * @param reviewInfo
     * @return
     */
    ResultData rejectActPrice(Long id, String reviewInfo);

    ResultData createActPrice(SaleActPrice price);

}
