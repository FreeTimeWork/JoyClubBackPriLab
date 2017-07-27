package com.joycity.joyclub.card_coupon.service;

import com.joycity.joyclub.card_coupon.modal.generated.CardThirdPartyShop;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by fangchen.chai on 2017/7/26
 */
public interface CardThirdPartyShopService {

    ResultData createThirdPartyShop(CardThirdPartyShop thirdPartyShop);

    ResultData getListByName(String name, PageUtil pageUtil);


}
