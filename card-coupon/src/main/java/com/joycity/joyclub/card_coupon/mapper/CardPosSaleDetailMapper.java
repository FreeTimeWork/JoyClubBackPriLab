package com.joycity.joyclub.card_coupon.mapper;

import com.joycity.joyclub.card_coupon.modal.PosSaleDetailWithCouponCode;
import com.joycity.joyclub.card_coupon.modal.ShowPosCurrentCouponCodeInfo;
import com.joycity.joyclub.card_coupon.modal.generated.PosSaleDetail;
import com.joycity.joyclub.card_coupon.modal.generated.PosSaleDetailExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardPosSaleDetailMapper extends BaseMapper<PosSaleDetail, Long, PosSaleDetailExample> {

    /**
     * 根据订单号得到pos流水，如果该流水里用了系统券，那把couponCodeId返回来
     * @param orderCode
     * @return
     */
    PosSaleDetailWithCouponCode selectByOrderCode(@Param("orderCode") String orderCode);
}
