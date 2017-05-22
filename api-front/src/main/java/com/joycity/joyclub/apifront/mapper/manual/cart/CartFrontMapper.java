package com.joycity.joyclub.apifront.mapper.manual.cart;

import com.joycity.joyclub.apifront.modal.cart.Cart;
import com.joycity.joyclub.apifront.modal.cart.CartExample;
import com.joycity.joyclub.apifront.modal.cart.CartInfo;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */
public interface CartFrontMapper extends BaseMapper<Cart, Long, CartExample> {
    @Update("update sale_cart set  delete_flag=1, delete_time = now() where id=#{id}")
    Integer deleteCart(@Param("id") Long id);

    /**
     * 修改num
     */
    @Update("update sale_cart set  num = #{num} where id=#{id}")
    Integer setCartNum(@Param("id") Long id, @Param("num") Integer num);

    @Select("select num from sale_cart where id = #{id}")
    Integer getNumById(@Param("id") Long id);
    @Select("select num from sale_cart where   project_id=#{projectId} and client_id=#{clientId} and attr_id = #{attrId}")
    Integer getNumByFilter(@Param("projectId") Long projectId,
                           @Param("clientId") Long clientId,
                           @Param("attrId") Long attrId);

    /**
     * 减去购物车数量
     *
     * @param projectId
     * @param clientId
     * @param attrId
     * @param subNum
     * @return
     */
    @Update("update sale_cart set  num = num - #{subNum} where project_id=#{projectId} and client_id=#{clientId} and attr_id = #{attrId}")
    Integer subCartNum(@Param("projectId") Long projectId,
                       @Param("clientId") Long clientId,
                       @Param("attrId") Long attrId,
                       @Param("subNum") Integer subNum);

    /**
     * 在原有num的基础上增加
     */
    @Update("update sale_cart set  num = num+#{num} where id=#{id}")
    Integer addCartNum(@Param("id") Long id, @Param("num") Integer num);

    /**
     * 查找未刪除的购物车id
     */
    @Select("select id from sale_cart where project_id = #{projectId} and client_id = #{clientId} and attr_id=#{attrId} and delete_flag=0")
    Long getIdByAttr(@Param("projectId") Long projectId, @Param("clientId") Long clientId, @Param("attrId") Long attrId);
//// TODO: 2017/5/10 还是应该分页
    /**
     * 按id倒序，最多返回50个
     *
     * @param projectId
     * @param clientId
     * @return
     */
    @Select("select c.id,c.attr_id,c.num, a.name as attrName,a.num as maxNum,p.id  productId,p.portrait,p.name  productName,s.id storeId,s.name storeName,price.start_time priceStartTime,price.end_time priceEndTime,price.special_price_flag specialPriceFlag,price.price,price.point_rate pointRate,p.base_price basePrice,cate.point_rate basePointRate " +
            "from sale_cart c  " +
            "join sale_product_attr a on a.id = c.attr_id " +
            "join sale_product p on p.id =a.product_id " +
            "join sys_product_category cate on p.category_id = cate.id " +
            "join sys_store s on s.id = p.store_id " +
            " join sale_product_price price on price.product_id=p.id and price.forbid_flag=0 and price.review_status=1 and now() between price.start_time and price.end_time " +
            "where c.delete_flag=0 and c.project_id = #{projectId} and c.client_id = #{clientId} order by c.id desc limit 0,50")
    List<CartInfo> getCartInfoList(@Param("projectId") Long projectId, @Param("clientId") Long clientId);


}
