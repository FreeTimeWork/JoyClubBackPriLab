package com.joycity.joyclub.card_coupon.mapper;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.CreateCouponInfo;
import com.joycity.joyclub.card_coupon.modal.ShowCouponInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardCouponMapper extends BaseMapper<CardCoupon, Long, CardCouponExample> {

    Long countCardCouponByNameAndType(@Param("name") String name, @Param("type") Integer type, @Param("pageUtil") PageUtil pageUtil);

    List<ShowCouponInfo> selectCardCouponByNameAndType(@Param("name") String name, @Param("type") Integer type, @Param("pageUtil") PageUtil pageUtil);

    @Update("update card_coupon set delete_flag = 1, delete_time = now() where id = #{id}")
    int deleteCardCouponById(@Param("id") Long id);

    List<CreateCouponInfo> selectCardCouponById(@Param("id") Long id);

}
