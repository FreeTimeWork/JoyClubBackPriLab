package com.joycity.joyclub.product.service;

import com.joycity.joyclub.product.modal.generated.SaleProductPrice;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductPriceService {
    /**
     * 按增加时间倒序
     *
     * @param storeName
     * @param reviewStatus
     * @param productName
     * @param pageUtil
     * @return
     */
    ResultData getListForProject(String storeName, Integer reviewStatus, String productName, PageUtil pageUtil);

    /**
     * 按增加时间倒序
     *
     * @param storeId
     * @param reviewStatus
     * @param productName
     * @param pageUtil
     * @return
     */
    public ResultData getListForStore(Long storeId, Integer reviewStatus, String productName, PageUtil pageUtil);

    /**
     * 返回某个项目的具体信息
     *
     * @param id
     * @return
     */
    ResultData getProductPrice(Long id);

    ResultData updateProductPrice(SaleProductPrice price);

    ResultData forbidProductPrice(Long id);

    /**
     * 项目管理者通过审核
     *
     * @param id
     * @return
     */
    ResultData permitProductPrice(Long id);

    /**
     * 项目管理者拒绝审核
     *
     * @param id
     * @param reviewInfo
     * @return
     */
    ResultData rejectProductPrice(Long id, String reviewInfo);

    ResultData createProductPrice(SaleProductPrice price);

}
