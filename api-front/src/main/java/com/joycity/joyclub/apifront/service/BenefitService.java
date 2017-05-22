package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/4/21.
 */
public interface BenefitService {
    ResultData getCoupons(Long projectId, Long clientId, PageUtil pageUtil);

    ResultData getClientCoupons(Long clientId, PageUtil pageUtil);

    /**
     * @param subProjectId 可以为null，特指商业地产等项目
     */
    ResultData receiveCoupon(Long couponId,Long clientId,Long subProjectId);
}
