package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.apiback.modal.order.ProductOrderDetailInfo;
import com.joycity.joyclub.apiback.modal.order.ProductOrderStoreInfo;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/27.
 */
public interface ProductStoreOrderMapper {

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
    Integer setDelieveryInfo(@Param("storeOrderId") Long storeOrderId, @Param("status") Byte status, @Param("deliveryCompany") String deliveryCompany, @Param("deliveryCode") String deliveryCode);

    @Update("update sale_product_order_store set status=#{status},receive_time=now() where id = #{storeOrderId}")
    Integer setReceived(@Param("storeOrderId") Long storeOrderId, @Param("status") Byte status);

}
