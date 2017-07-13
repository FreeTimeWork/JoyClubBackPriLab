package com.joycity.joyclub.card_coupon.service;

import java.util.Set;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by fangchen.chai on 2017/7/13.
 */
public interface ThirdpartyCouponCodeService {

    ResultData createThirdpartyCouponCode(Set<String> cardNos);

}
