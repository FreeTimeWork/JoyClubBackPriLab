package com.joycity.joyclub.apifront.mapper.manual.order;


import com.joycity.joyclub.apifront.modal.order.ProductOrder;
import com.joycity.joyclub.apifront.modal.order.ProductOrderExample;
import com.joycity.joyclub.apifront.modal.order.ProductOrderFormDataItem;
import com.joycity.joyclub.apifront.modal.order.list.ProductMainOrderWithStores;
import com.joycity.joyclub.apifront.modal.order.list.ProductStoreOrderWithDetailsAndMainOrder;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductOrderMapper extends BaseMapper<ProductOrder, Long, ProductOrderExample> {

    /**
     * 获取我的订单
     *
     * @param projectId
     * @param clientId
     * @param status    null则返回所有我的订单
     * @param pageUtil
     * @return
     */
    List<ProductMainOrderWithStores> selectMyOrders(@Param("projectId") Long projectId, @Param("clientId") Long clientId, @Param("status") Byte status, @Param("pageUtil") PageUtil pageUtil);

    /**
     * 获取我的待发货 待收货 已完成 订单
     *
     * @param projectId
     * @param clientId
     * @param status
     * @param receiveType 主订单收货类型 可以为空
     * @param pageUtil
     * @return
     */
    List<ProductStoreOrderWithDetailsAndMainOrder> selectMyStoreOrders(@Param("projectId") Long projectId, @Param("clientId") Long clientId, @Param("status") Byte status, @Param("receiveType") Byte receiveType, @Param("pageUtil") PageUtil pageUtil);



    @Select("select  a.id attrId,a.name as attrName,a.num as maxNum,p.id  productId,p.portrait,p.name  productName,s.id storeId,s.name storeName,price.price,price.point_rate pointRate,p.base_price basePrice,cate.point_rate basePointRate " +
            "from sale_product_attr a " +
            "join sale_product p on p.id =a.product_id " +
            "join sys_product_category cate on p.category_id = cate.id " +
            "join sys_store s on s.id = p.store_id " +
            " join sale_product_price price on price.product_id=p.id and price.forbid_flag=0 and price.review_status=1 and now() between price.start_time and price.end_time " +
            "where a.id = #{attrId} ")
    ProductOrderFormDataItem getOrderRawDataItem(@Param("attrId") Long attrId);

    @Select("select id from sale_product_order where code=#{code}")
    Long getIdByCode(String code);

    /**
     * 仅针对纯积分支付
     *
     * @param code
     * @return
     */
    @Update("update sale_product_order set status =2 , pay_time=now() where code=#{code}")
    Integer setPayedByCode(String code);

    @Update("update sale_product_order set out_pay_code =#{outPayCode} where code=#{code}")
    Integer setOutPayCodeByCode(@Param("code") String code, @Param("outPayCode") String outPayCode);

    @Update("update sale_product_order set status =2 , pay_time=now() where id=#{id}")
    Integer setPayedById(Long id);

    @Update("update sale_product_order set out_pay_code =#{outPayCode} where id=#{id}")
    Integer setOutPayCodeById(@Param("id") Long id, @Param("outPayCode") String outPayCode);


    @Update("update sale_product_order set status = 1, canceler=#{cancelType},cancel_time=now() where id=#{id}")
    Integer cancelOrder(@Param("id") Long id, @Param("cancelType") Byte cancelType);
}
