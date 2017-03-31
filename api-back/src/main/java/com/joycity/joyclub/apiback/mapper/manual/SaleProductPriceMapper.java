package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.apiback.mapper.BaseMapper;
import com.joycity.joyclub.apiback.modal.generated.SaleProductPrice;
import com.joycity.joyclub.apiback.modal.generated.SaleProductPriceExample;
import com.joycity.joyclub.apiback.modal.product.ProductWithCategoryAndDesignerName;
import com.joycity.joyclub.apiback.util.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SaleProductPriceMapper extends BaseMapper<SaleProductPrice, Long, SaleProductPriceExample> {
    Long countByStoreIdAndProductNameAndReviewStatus(@Param("storeId") Long storeId, @Param("reviewStatus") Integer reviewStatus, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    List<ProductWithCategoryAndDesignerName> selectByStoreIdAndProductNameAndReviewStatus(@Param("storeId") Long storeId, @Param("reviewStatus") Integer reviewStatus, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    Long countPriceTimeOverlap(@Param("startTime") Date statTime, @Param("endTime") Date endTime, @Param("id") Long id);
}
