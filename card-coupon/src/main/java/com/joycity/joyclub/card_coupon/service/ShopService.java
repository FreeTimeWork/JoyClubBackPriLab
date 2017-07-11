package com.joycity.joyclub.card_coupon.service;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.MallcooShop;
import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface ShopService {

    ResultData batchInsertOrUpdate(List<MallcooShop> shops);

    ResultData syncMallCooShop(Long projectId);
}
