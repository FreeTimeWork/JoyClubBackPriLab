package com.joycity.joyclub.coupon.mapper;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.coupon.modal.generated.CouponCardType;
import com.joycity.joyclub.coupon.modal.generated.CouponCardTypeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/12.
 */
public interface CouponCardTypeMapper extends BaseMapper<CouponCardType, Long, CouponCardTypeExample> {
    @Select("select count(*) from coupon_card_type where coupon_id =#{id} and card_type=#{type}")
    int countByIdAndType(@Param("id") Long id, @Param("type") String type);
    @Select("select * from coupon_card_type where coupon_id =#{couponId}")
    List<CouponCardType> getByCouponId(Long couponId);
}
