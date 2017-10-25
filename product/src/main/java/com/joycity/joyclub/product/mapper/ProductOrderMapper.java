package com.joycity.joyclub.product.mapper;


import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.modal.ProductMainOrderWithStores;
import com.joycity.joyclub.product.modal.ProductOrderFormDataItem;
import com.joycity.joyclub.product.modal.ProductStoreOrderWithDetailsAndMainOrder;
import com.joycity.joyclub.product.modal.generated.SaleProductOrder;
import com.joycity.joyclub.product.modal.generated.SaleProductOrderDetail;
import com.joycity.joyclub.product.modal.generated.SaleProductOrderExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductOrderMapper extends BaseMapper<SaleProductOrder, Long, SaleProductOrderExample> {

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
     * 获取我的订单详情
     *
     * @return
     */
    ProductMainOrderWithStores selectMyOrderDetail(@Param("orderId") Long orderId);


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
    List<ProductStoreOrderWithDetailsAndMainOrder> selectMyNotReceivedStoreOrders(@Param("projectId") Long projectId, @Param("clientId") Long clientId, @Param("status") Byte status, @Param("receiveType") Byte receiveType, @Param("pageUtil") PageUtil pageUtil);



    @Select("select  a.id attrId,a.name as attrName,a.num as maxNum,p.id  productId,p.portrait,p.name  productName,s.id storeId,s.name storeName,price.price,price.point_rate pointRate,p.base_price basePrice,price.special_price_flag specialPriceFlag,cate.point_rate basePointRate " +
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

    @Update("update sale_product_order set out_pay_code =#{outPayCode},pay_type=#{payType} where id=#{id}")
    Integer setOutPayCodeById(@Param("id") Long id, @Param("outPayCode") String outPayCode,@Param("payType") Byte payType);


    @Update("update sale_product_order set status = 1, canceler=#{cancelType},cancel_time=now() where id=#{id}")
    Integer cancelOrder(@Param("id") Long id, @Param("cancelType") Byte cancelType);

    /**
     * 获得某个订单下所有的商品订单
     * @param orderId
     * @return 返回的ProductOrderDetail只包含id,product_attr,num
     */
    @Select("select d.id,d.product_attr,d.num from sale_product_order_store so " +
            "left join sale_product_order_detail d on d.store_order_id = so.id " +
            "where so.order_id = #{orderId}")
    List<SaleProductOrderDetail> getAllSimpleOrderDetails(@Param("orderId")Long orderId);
    @Select("select id from sale_product_order where status = 0 and hour(timediff(now(),create_time))>=1")
    List<Long> getOneHourNotPayedOrder();

    /**
     * 重新支付的时候重新生成新的订单号
     * @param id
     * @param code
     * @return
     */
    @Update("update sale_product_order set code = #{code} where id =#{id}")
    Integer setNewCode(@Param("id")Long id,@Param("code") String code);
}
