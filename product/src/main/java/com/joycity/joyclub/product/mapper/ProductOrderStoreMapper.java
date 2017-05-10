package com.joycity.joyclub.product.mapper;


import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.modal.*;
import com.joycity.joyclub.product.modal.generated.SaleProductOrderDetail;
import com.joycity.joyclub.product.modal.generated.SaleProductOrderStore;
import com.joycity.joyclub.product.modal.generated.SaleProductOrderStoreExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/27.
 */
public interface ProductOrderStoreMapper extends BaseMapper<SaleProductOrderStore,Long,SaleProductOrderStoreExample>{
    ///////////////////////////////// api back//////////////////////////////////////////////

    Long countByFilter(@Param("storeId") Long storeId,
                       @Param("receiveType") Byte receiveType,
                       @Param("status") Byte status,
                       @Param("code") String code,
                       @Param("name") String name,
                       @Param("phone") String phone,
                       @Param("pageUtil") PageUtil pageUtil);

    List<ProductOrderStoreInfo> selectByFilter(@Param("storeId") Long storeId,
                                               @Param("receiveType") Byte receiveType,
                                               @Param("status") Byte status,
                                               @Param("code") String code,
                                               @Param("name") String name,
                                               @Param("phone") String phone,
                                               @Param("pageUtil") PageUtil pageUtil);

    ProductOrderStoreInfo selectById(@Param("id") Long id);

    List<ProductOrderDetailInfo> getDetails(@Param("storeOrderId") Long storeOrderId);

    /**
     * @param storeOrderId
     * @param status          状态其实就是 {@link com.joycity.joyclub.commons.constant.OrderStatus} 里的已发货，为了后续更改方便，不写死，作为参数传递
     * @param deliveryCompany
     * @param deliveryCode
     * @return
     */
    @Update("update sale_product_order_store set status=#{status},delivery_time = now(),delivery_company=#{deliveryCompany},delivery_code=#{deliveryCode} where id = #{storeOrderId}")
    Integer setDeliveryInfo(@Param("storeOrderId") Long storeOrderId, @Param("status") Byte status, @Param("deliveryCompany") String deliveryCompany, @Param("deliveryCode") String deliveryCode);

    @Update("update sale_product_order_store set status=#{status},receive_time=now() where id = #{storeOrderId}")
    Integer setReceived(@Param("storeOrderId") Long storeOrderId, @Param("status") Byte status);

    ///////////////////////////////// api front//////////////////////////////////////////////

    @Update("update sale_product_order_store set status=#{status} where order_id=#{orderId}")
    Integer setOrderStatusByMainOrderId(@Param("orderId") Long orderId, @Param("status") Byte status);

    /**
     * 发货超过十天的订单设置为已收货
     * @param sentStatus
     * @param receivedStatus
     * @return
     */
    @Update("update sale_product_order_store set status=#{receivedStatus},receive_time=now() where status=#{sentStatus} and datediff(now(),delivery_time)>10")
    Integer setDeliveredTenDayReceived(@Param("sentStatus") Byte sentStatus, @Param("receivedStatus") Byte receivedStatus);

}
