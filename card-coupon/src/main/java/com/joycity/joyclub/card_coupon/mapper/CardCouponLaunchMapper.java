package com.joycity.joyclub.card_coupon.mapper;

import java.util.Date;
import java.util.List;

import com.joycity.joyclub.card_coupon.modal.CouponLaunchWithCouponInfo;
import com.joycity.joyclub.card_coupon.modal.CreateCouponLaunchInfo;
import com.joycity.joyclub.card_coupon.modal.ShowCouponLaunchInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunchExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardCouponLaunchMapper extends BaseMapper<CardCouponLaunch, Long, CardCouponLaunchExample> {

    Long countByCouponNameAndCouponTypeAndStatus(@Param("name") String name, @Param("type") Integer type, @Param("status") Integer status, @Param("now") Date now, @Param("pageUtil") PageUtil pageUtil);

    List<ShowCouponLaunchInfo> selectByCouponNameAndCouponTypeAndStatus(@Param("name") String name, @Param("type") Integer type, @Param("status") Integer status, @Param("now") Date now, @Param("pageUtil") PageUtil pageUtil);

    @Update("update card_coupon_launch set delete_flag = 1, delete_time = now() where id = #{id}")
    int deleteCardCouponLaunchById(@Param("id") Long id);

    @Select("select sum(launch_num) from card_coupon_launch where coupon_id = #{couponId} and delete_flag = 0")
    int selectlaunchNumByCouponId(@Param("couponId") Long couponId);

    @Select("select count(*) from card_coupon_launch where coupon_id = #{couponId} and delete_flag = 0")
    int countCardCouponLaunchByCouponId(@Param("couponId") Long couponId);

    CouponLaunchWithCouponInfo selectLaunchWithCouponByCouponId(@Param("couponId") Long couponId);

    CreateCouponLaunchInfo selectCouponLaunchInfoById(@Param("id") Long id);
}
