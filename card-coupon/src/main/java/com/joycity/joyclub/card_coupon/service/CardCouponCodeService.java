package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by fangchen.chai on 2017/7/13.
 */
public interface CardCouponCodeService {

    void  batchCreateCouponCode(Long launchId);

    ResultData getListByFilter(Long projectId, ShowCouponCodeFilter filter, PageUtil pageUtil);

    /**
     * 核销卡券
     * 有订单流水号传入流水，没有传入null
     */
    ResultData checkCouponCode(Long id, Long posSaleDetailId);

    /**
     * 卡券改为未使用
     */
    int updateNotUsedCouponCode(Long couponCodeId);


}
