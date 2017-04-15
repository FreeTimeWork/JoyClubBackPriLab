package com.joycity.joyclub.apifront.mapper.manual.product;

import com.joycity.joyclub.apifront.modal.act.ActInfoPage;
import com.joycity.joyclub.apifront.modal.act.ActSimpleWithAddress;
import com.joycity.joyclub.apifront.modal.product.ProductInfoPage;
import com.joycity.joyclub.apifront.modal.product.ProductSimple;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface ProductFrontMapper {
    ProductInfoPage getInfo(Long id);

    // TODO: 2017/4/13 项目搜索未加进去！！！
    List<ProductSimple> selectByFilter(@Param("storeId") Long storeId,
                                       @Param("designerId") Long designerId,
                                       @Param("pageUtil") PageUtil pageUtil
    );

}
