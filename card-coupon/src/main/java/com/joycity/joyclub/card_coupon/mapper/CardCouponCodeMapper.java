package com.joycity.joyclub.card_coupon.mapper;

import com.joycity.joyclub.card_coupon.modal.ShowCouponCodeInfo;
import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCodeExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardCouponCodeMapper extends BaseMapper<CardCouponCode, Long, CardCouponCodeExample> {

    @Select("select count(*) from card_coupon_code where launch_id = #{launchId} and use_status = #{useStatus} and delete_flag = 0")
    int countByLaunchIdAndUseStatus(@Param("launchId") Long launchId, @Param("useStatus") Byte useStatus);

    @Select("select count(*) from card_coupon_code where launch_id = #{launchId} and delete_flag = 0")
    int countByLaunchId(@Param("launchId") Long launchId);

    Long countCardCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter);

    List<ShowCouponCodeInfo> selectCardCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter, @Param("pageUtil") PageUtil pageUtil);


    // thirdPartyMapper
    Long countCardThirdCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter);

    List<ShowCouponCodeInfo> selectCardThirdCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter, @Param("pageUtil") PageUtil pageUtil);


}
