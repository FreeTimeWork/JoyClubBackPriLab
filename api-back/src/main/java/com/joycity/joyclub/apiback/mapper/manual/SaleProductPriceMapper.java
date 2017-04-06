package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.apiback.mapper.BaseMapper;
import com.joycity.joyclub.apiback.modal.generated.SaleProductPrice;
import com.joycity.joyclub.apiback.modal.generated.SaleProductPriceExample;
import com.joycity.joyclub.apiback.modal.product.ProductWithCategoryAndDesignerName;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SaleProductPriceMapper extends BaseMapper<SaleProductPrice, Long, SaleProductPriceExample> {

    /**
     * 为商户用户计算总数
     * By StoreId,ProductName,ReviewStatus
     *
     * @param storeId
     * @param reviewStatus
     * @param productNameLike
     * @param pageUtil
     * @return
     */
    Long countForStore(@Param("storeId") Long storeId, @Param("reviewStatus") Integer reviewStatus, @Param("productNameLike") String productNameLike, @Param("pageUtil") PageUtil pageUtil);

    /**
     * 为商户用户获取list
     * By StoreId,ProductName,ReviewStatus
     *
     * @param storeId
     * @param reviewStatus
     * @param productNameLike
     * @param pageUtil
     * @return
     */
    List<ProductWithCategoryAndDesignerName> selectForStore(@Param("storeId") Long storeId, @Param("reviewStatus") Integer reviewStatus, @Param("productNameLike") String productNameLike, @Param("pageUtil") PageUtil pageUtil);

    /**
     * 为商户用户计算总数
     * By StoreName,ProductName,ReviewStatus
     *
     * @param reviewStatus
     * @param productNameLike
     * @param pageUtil
     * @return
     */
    Long countForProject(@Param("storeNameLike") String storeNameLike,@Param("reviewStatus") Integer reviewStatus, @Param("productNameLike") String productNameLike, @Param("pageUtil") PageUtil pageUtil);

    /**
     * 为商户用户获取list
     * By projectName,ProductName,ReviewStatus
     *
     * @param storeNameLike
     * @param reviewStatus
     * @param productNameLike
     * @param pageUtil
     * @return
     */
    List<ProductWithCategoryAndDesignerName> selectForProject(@Param("storeNameLike") String storeNameLike, @Param("reviewStatus") Integer reviewStatus, @Param("productNameLike") String productNameLike, @Param("pageUtil") PageUtil pageUtil);

    Long countPriceTimeOverlap(@Param("startTime") Date statTime, @Param("endTime") Date endTime, @Param("id") Long id);
}
