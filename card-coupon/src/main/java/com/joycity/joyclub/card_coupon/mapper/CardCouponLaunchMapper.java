package com.joycity.joyclub.card_coupon.mapper;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.ShowCouponLaunchInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunchExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardCouponLaunchMapper extends BaseMapper<CardCouponLaunch, Long, CardCouponLaunchExample> {

    Long countByCouponNameAndCouponTypeAndStatus(@Param("name") String name, @Param("type") Integer type, @Param("status") Integer status, @Param("pageUtil") PageUtil pageUtil);

    List<ShowCouponLaunchInfo> selectByCouponNameAndCouponTypeAndStatus(@Param("name") String name, @Param("type") Integer type, @Param("status") Integer status, @Param("pageUtil") PageUtil pageUtil);


}
