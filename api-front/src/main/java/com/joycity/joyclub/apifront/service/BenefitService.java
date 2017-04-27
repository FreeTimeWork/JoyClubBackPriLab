package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/4/21.
 */
public interface BenefitService {
    ResultData getCoupons(Long projectId, Long clientId, PageUtil pageUtil);

    ResultData getClientCoupons(Long clientId, PageUtil pageUtil);
    ResultData receiveCoupon(Long couponId,Long clientId);
}
