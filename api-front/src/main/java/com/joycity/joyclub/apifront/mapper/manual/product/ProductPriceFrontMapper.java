package com.joycity.joyclub.apifront.mapper.manual.product;

import com.joycity.joyclub.apifront.modal.product.price.ProductPrice;
import org.apache.ibatis.annotations.Select;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface ProductPriceFrontMapper {

    /**
     * 获得商品现在的价格，可能会不存在
     * @param productId
     * @return
     */
    @Select("select id,product_id,price,point_rate from sale_product_price where delete_flag=0 and  product_id=#{productId} and forbid_flag=false and review_status=1 and start_time<=now() and end_time>=now() limit 0,1 ")
    ProductPrice getNowPrice(Long productId);
}
