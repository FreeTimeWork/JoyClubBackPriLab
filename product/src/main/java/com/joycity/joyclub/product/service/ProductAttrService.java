package com.joycity.joyclub.product.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.modal.generated.SaleProductAttr;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductAttrService {
    /**
     * @return data为按创建时间倒序的所有项目列表
     */
    ResultData getListByStoreIdAndProductName(Long storeId, String productName, PageUtil pageUtil);

    /**
     * 返回某个项目的具体信息
     *
     * @param id
     * @return
     */
    ResultData getProductAttr(Long id);

    ResultData updateProductAttr(SaleProductAttr attr);

    ResultData createProductAttr(SaleProductAttr attr);

}
