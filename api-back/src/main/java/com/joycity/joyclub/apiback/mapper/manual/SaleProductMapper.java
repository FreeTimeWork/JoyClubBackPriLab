package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.apiback.mapper.BaseMapperWithBLOBS;
import com.joycity.joyclub.apiback.modal.generated.SaleProduct;
import com.joycity.joyclub.apiback.modal.generated.SaleProductExample;
import com.joycity.joyclub.apiback.modal.generated.SaleProductWithBLOBs;
import com.joycity.joyclub.apiback.modal.product.ProductWithCategoryAndDesignerName;
import com.joycity.joyclub.apiback.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SaleProductMapper extends BaseMapperWithBLOBS<SaleProduct,SaleProductWithBLOBs, Long, SaleProductExample> {


    Long countByStoreIdAndName(@Param("storeId") Long storeId,@Param("nameLike") String nameLike,@Param("pageUtil") PageUtil pageUtil);

    List<ProductWithCategoryAndDesignerName> selectByStoreIdAndName(@Param("storeId") Long storeId,@Param("nameLike") String nameLike,@Param("pageUtil") PageUtil pageUtil);

}
