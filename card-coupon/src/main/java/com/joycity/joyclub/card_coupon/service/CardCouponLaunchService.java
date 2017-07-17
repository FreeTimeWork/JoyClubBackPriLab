package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.card_coupon.modal.CreateCouponLaunchInfo;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
public interface CardCouponLaunchService {

    ResultData createCardCouponLaunch(CreateCouponLaunchInfo launch);

    ResultData getListByCouponNameAndCouponTypeAndStatus(String name, Integer type, Integer status, PageUtil pageUtil);

    ResultData getCardCouponLaunchById(Long id);

    ResultData confirmLaunch(Long id);

    ResultData forbidLaunch(Long id);
    /**
     * 通过审核
     *
     * @param id
     * @return
     */
    ResultData permitLaunch(Long id);

    /**
     * 拒绝审核
     *
     * @param id
     * @param reviewInfo
     * @return
     */
    ResultData rejectLaunch(Long id, String reviewInfo);

    ResultData deleteCardCouponLaunch(Long id);

}
