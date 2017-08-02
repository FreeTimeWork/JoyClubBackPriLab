package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by fangchen.chai on 2017/7/13.
 */
public interface CardCouponCodeService {

    void  batchCreateCouponCode(Long launchId);

    /**
     * 免费领取卡券
     * @param clientId
     * @param couponLaunchId
     * @param moneyOrPoint
     * @return
     */
    ResultData freeReceiveCoupon(Long clientId, Long couponLaunchId, Boolean moneyOrPoint);


    ResultData getListByFilter(Long projectId, ShowCouponCodeFilter filter, PageUtil pageUtil);

    /**
     * 核销卡券
     * 有orderCode，传入orderCode
     */
    ResultData checkCouponCode(Long id, String orderCode);

    /**
     * 卡券改为未使用
     */
    int updateNotUsedCouponCode(Long couponCodeId);



}
