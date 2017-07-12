package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.card_coupon.modal.CreateCouponInfo;
import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by fangchen.chai on 2017/7/11.
 */

public interface CardCouponService {

    /**
     *  创建优惠券
     * @param info
     * @return
     */
    ResultData createCardCoupon(CreateCouponInfo info);

    ResultData getListByNameAndType(String name, Integer type);
}
