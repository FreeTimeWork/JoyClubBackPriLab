package com.joycity.joyclub.product.service;


import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.modal.generated.SaleProductWithBLOBs;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductService {
    ////////////////////////////////api back/////////////////////////////////////////////

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
    ResultData getProduct(Long id);

    /**
     * @param product 应该包含id
     * @return
     */
    ResultData updateProduct(SaleProductWithBLOBs product);

    ResultData createProduct(SaleProductWithBLOBs product);

    /**
     * 返回添加或者编辑项目所需的formData
     *
     * @param storeId
     * @return data为 {@link com.joycity.joyclub.product.modal.ProductFormData}
     */
    ResultData getProductFormData(Long storeId);

    ////////////////////////////////api font/////////////////////////////////////////////

    ResultData getInfo(Long id);

    ResultData getAttrs(Long id);

    /**
     * projectId,storeId, designerId知按顺序取不为空的作为筛选条件
     *
     * @param projectId
     * @param storeId
     * @param designerId
     * @param pageUtil
     * @return
     */
    ResultData getProductList(Long projectId, Long storeId, Long designerId, PageUtil pageUtil);

    ResultData getSpecialPriceProductList(Long projectId, Long storeId, Long designerId, PageUtil pageUtil);
    ResultData getSpecialPriceAct(Long id);
    ResultData getSpecialPriceActProducts(Long id,PageUtil pageUtil);


}
