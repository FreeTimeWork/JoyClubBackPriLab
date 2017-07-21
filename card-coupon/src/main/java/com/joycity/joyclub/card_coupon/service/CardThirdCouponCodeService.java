package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by fangchen.chai on 2017/7/13.
 */
public interface CardThirdCouponCodeService {

    ResultData getListByFilter(Long projectId, ShowCouponCodeFilter filter, PageUtil pageUtil);

}
