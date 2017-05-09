package com.joycity.joyclub.product.mapper;

import com.joycity.joyclub.product.modal.ProductInfoPage;
import com.joycity.joyclub.product.modal.ProductSimple;
import com.joycity.joyclub.product.modal.generated.SaleProduct;
import com.joycity.joyclub.product.modal.generated.SaleProductExample;
import com.joycity.joyclub.product.modal.generated.SaleProductWithBLOBs;
import com.joycity.joyclub.product.modal.ProductWithCategoryAndDesignerName;
import com.joycity.joyclub.commons.mapper.BaseMapperWithBLOBS;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductMapper extends BaseMapperWithBLOBS<SaleProduct, SaleProductWithBLOBs, Long, SaleProductExample> {
    ///////////////////////////////// api back//////////////////////////////////////////////


    Long countByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    List<ProductWithCategoryAndDesignerName> selectByStoreIdAndName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    ///////////////////////////////// api front//////////////////////////////////////////////

    ProductInfoPage getInfo(Long id);

    /**
     * @param storeId
     * @param designerId
     * @param specialPriceFlag 可以为null
     * @param pageUtil
     * @return
     */
    List<ProductSimple> selectByFilter(
            @Param("storeId") Long storeId,
            @Param("designerId") Long designerId,
            @Param("specialPriceFlag") Boolean specialPriceFlag,
            @Param("pageUtil") PageUtil pageUtil
    );

    /**
     * @param selectByProject
     * @param specialPriceFlag 可以为null
     * @param pageUtil
     * @return
     */
    List<ProductSimple> selectByProject(
            @Param("projectId") Long selectByProject,
            @Param("specialPriceFlag") Boolean specialPriceFlag,
            @Param("pageUtil") PageUtil pageUtil
    );


}
