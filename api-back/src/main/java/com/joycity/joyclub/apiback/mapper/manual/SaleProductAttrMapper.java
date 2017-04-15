package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.apiback.modal.generated.SaleProductAttr;
import com.joycity.joyclub.apiback.modal.generated.SaleProductAttrExample;
import com.joycity.joyclub.apiback.modal.product.ProductWithCategoryAndDesignerName;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SaleProductAttrMapper extends BaseMapper<SaleProductAttr, Long, SaleProductAttrExample> {
    Long countByStoreIdAndProductName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    List<ProductWithCategoryAndDesignerName> selectByStoreIdAndProductName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

}
