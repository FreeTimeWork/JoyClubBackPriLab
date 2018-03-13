package com.joycity.joyclub.card_coupon.mapper;

import com.joycity.joyclub.card_coupon.modal.generated.CardCouponTriggerScope;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponTriggerScopeExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardCouponTriggerScopeMapper extends BaseMapper<CardCouponTriggerScope, Long, CardCouponTriggerScopeExample> {

    @Select({"select * from card_coupon_trigger_scope where launch_id = #{launchId}"})
    List<CardCouponTriggerScope> selectCardCouponTriggerScopesByLaunchId(@Param("launchId") Long launchId);

}
