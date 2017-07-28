package com.joycity.joyclub.card_coupon.mapper;

import com.joycity.joyclub.card_coupon.modal.generated.CardCouponStoreScope;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponStoreScopeExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardCouponStoreScopeMapper extends BaseMapper<CardCouponStoreScope, Long, CardCouponStoreScopeExample> {

    @Select("select count(ccss.coupon_id) from card_coupon_store_scope ccss inner join sys_shop ss on ss.id = ccss.store_id and ss.delete_flag = 0 where ss.code = #{shopCode} and ccss.coupon_id = #{couponId} and ccss.delete_flag = 0")
    int countByCouponIdAndShopCode(@Param("couponId") Long couponId, @Param("shopCode") String shopCode);

}
