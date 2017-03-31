package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SaleProductPrice;
import com.joycity.joyclub.apiback.util.PageUtil;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductPriceService {
    /**
     * @return data为按创建时间倒序的所有项目列表
     */
    ResultData getListByStoreIdAndProductName(Long storeId, Integer reviewStatus, String productName, PageUtil pageUtil);

    /**
     * 返回某个项目的具体信息
     *
     * @param id
     * @return
     */
    ResultData getProductPrice(Long id);

    ResultData updateProductPrice(SaleProductPrice price);

    ResultData forbidProductPrice(Long id);

    ResultData createProductPrice(SaleProductPrice price);

}
