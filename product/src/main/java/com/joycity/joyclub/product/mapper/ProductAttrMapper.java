package com.joycity.joyclub.product.mapper;


import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.modal.ProductAttrSimple;
import com.joycity.joyclub.product.modal.ProductWithCategoryAndDesignerName;
import com.joycity.joyclub.product.modal.generated.SaleProductAttr;
import com.joycity.joyclub.product.modal.generated.SaleProductAttrExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductAttrMapper extends BaseMapper<SaleProductAttr, Long, SaleProductAttrExample> {
    ///////////////////////////////// api back//////////////////////////////////////////////
    Long countByStoreIdAndProductName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    List<ProductWithCategoryAndDesignerName> selectByStoreIdAndProductName(@Param("storeId") Long storeId, @Param("nameLike") String nameLike, @Param("pageUtil") PageUtil pageUtil);

    ///////////////////////////////// api front//////////////////////////////////////////////

    /**
     * 返回有库存的属性
     * 数据库默认排序，
     *
     * @param productId
     * @return
     */
    @Select("select id,product_id,name,num from sale_product_attr where product_id=#{productId} and num>0")
    List<ProductAttrSimple> selectAvailableSimpleByProduct(Long productId);

    @Update("update sale_product_attr set num= num + #{addNum} where id = #{attrId}")
    Integer addNum(@Param("attrId") Long attrId, @Param("addNum") Integer addNum);
}
