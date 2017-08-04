package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by fangchen.chai on 2017/8/4
 * 商户承担比
 */
public interface ShopRatioService {

    ResultData getListByLaunchId(Long launchId, String shopName, PageUtil pageUtil);
}
