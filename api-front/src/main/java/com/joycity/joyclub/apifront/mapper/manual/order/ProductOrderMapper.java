package com.joycity.joyclub.apifront.mapper.manual.order;


import com.joycity.joyclub.apifront.modal.order.ProductOrder;
import com.joycity.joyclub.apifront.modal.order.ProductOrderExample;
import com.joycity.joyclub.apifront.modal.order.ProductOrderFormDataItem;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductOrderMapper extends BaseMapper<ProductOrder, Long, ProductOrderExample> {
    @Select("select  a.id attrId,a.name as attrName,a.num as maxNum,p.id  productId,p.portrait,p.name  productName,s.id storeId,s.name storeName,price.price,price.point_rate pointRate,p.base_price basePrice,cate.point_rate basePointRate " +
            "from sale_product_attr a " +
            "join sale_product p on p.id =a.product_id " +
            "join sys_product_category cate on p.category_id = cate.id " +
            "join sys_store s on s.id = p.store_id " +
            " join sale_product_price price on price.product_id=p.id and price.forbid_flag=0 and price.review_status=1 and now() between price.start_time and price.end_time " +
            "where a.id = #{attrId} ")
    ProductOrderFormDataItem getOrderRawDataItem(@Param("attrId") Long attrId);

    @Update("update sale_product_order set status =2 , pay_time=now where code=#{code}")
    Integer setPayedByCode(String code);

    @Update("update sale_product_order set out_pay_code =#{outPayCode} where code=#{code}")
    Integer setOutPayCodeByCode(@Param("code") String code, @Param("outPayCode") String outPayCode);

}
