package com.joycity.joyclub.apifront.mapper.manual.product;

import com.joycity.joyclub.apifront.modal.product.attr.ProductAttrSimple;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Administrator on 2017/4/15.
 */
public interface ProductAttrFrontMapper {
    /**
     * 返回有库存的属性
     * 数据库默认排序，
     * @param productId
     * @return
     */
    @Select("select id,product_id,name,num from sale_product_attr where product_id=#{productId} and num>0")
    List<ProductAttrSimple> selectAvailableSimpleByProduct(Long productId);
    @Update("update sale_product_attr set num= num + #{addNum} where id = #{attrId}")
    Integer addNum(@Param("attrId")Long attrId,@Param("addNum") Integer addNum);
}
