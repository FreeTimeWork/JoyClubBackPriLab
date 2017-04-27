package com.joycity.joyclub.apifront.mapper.manual.order;


        import com.joycity.joyclub.apifront.modal.order.ProductOrderStore;
        import com.joycity.joyclub.apifront.modal.order.ProductOrderStoreExample;
        import com.joycity.joyclub.commons.mapper.BaseMapper;
        import org.apache.ibatis.annotations.Param;
        import org.apache.ibatis.annotations.Update;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductOrderStoreMapper extends BaseMapper<ProductOrderStore, Long, ProductOrderStoreExample> {
    @Update("update sale_product_order_store set status=#{status} where order_id=#{orderId}")
    Integer setOrderStatusByMainOrderId(@Param("orderId") Long orderId,@Param("status") Byte status);
}
