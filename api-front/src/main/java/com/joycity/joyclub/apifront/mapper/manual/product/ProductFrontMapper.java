package com.joycity.joyclub.apifront.mapper.manual.product;

import com.joycity.joyclub.apifront.modal.product.ProductInfoPage;
import com.joycity.joyclub.apifront.modal.product.ProductSimple;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface ProductFrontMapper {
    ProductInfoPage getInfo(Long id);

    List<ProductSimple> selectByFilter(
            @Param("storeId") Long storeId,
            @Param("designerId") Long designerId,
            @Param("pageUtil") PageUtil pageUtil
    );

    List<ProductSimple> selectByProject(
            @Param("projectId") Long selectByProject,
            @Param("pageUtil") PageUtil pageUtil
    );

}
