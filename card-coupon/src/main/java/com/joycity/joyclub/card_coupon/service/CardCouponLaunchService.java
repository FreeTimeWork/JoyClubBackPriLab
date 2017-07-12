package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
public interface CardCouponLaunchService {

    ResultData createCardCouponLaunch(CardCouponLaunch launch);

    ResultData getListByCouponNameAndCouponTypeAndStatus(String name, Integer type, Integer status, PageUtil pageUtil);

}
