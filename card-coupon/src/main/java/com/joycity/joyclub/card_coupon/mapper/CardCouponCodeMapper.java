package com.joycity.joyclub.card_coupon.mapper;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.ShowCouponCodeInfo;
import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCodeExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardCouponCodeMapper extends BaseMapper<CardCouponCode, Long, CardCouponCodeExample> {

    List<ShowCouponCodeInfo> selectCardCouponCodeByFilter(@Param("filter") ShowCouponCodeFilter filter);
}
