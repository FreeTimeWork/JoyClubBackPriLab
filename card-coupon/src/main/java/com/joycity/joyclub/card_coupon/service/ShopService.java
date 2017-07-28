package com.joycity.joyclub.card_coupon.service;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.MallcooShop;
import com.joycity.joyclub.card_coupon.modal.generated.SysShop;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface ShopService {

    ResultData batchInsertOrUpdate(List<MallcooShop> shops, Long projectId);

    ResultData syncMallCooShop(Long projectId);

    ResultData getListByCodeAndSubCommercial(Long projectId, String code, String name, PageUtil pageUtil);

    ResultData getShopsGroupBySubCommercial(Long projectId);

    ResultData getAllShopByNameAndCode(Long projectId, String name, String code);

    SysShop getShopByProjectIdAndCode(Long projectId, String code);

}
