package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by Administrator on 2017/4/17.
 */
public interface CartFrontService {
    ResultData addToCart(Long projectId,Long clientId,Long attrId,Integer num);
    ResultData setCartNum(Long id,Integer num);
    ResultData getCardList(Long projectId,Long clientId);
    ResultData deleteCart(Long id);
}
