package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.apifront.modal.cart.CartInfo;
import com.joycity.joyclub.commons.modal.base.ResultData;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */
public interface CartFrontService {
    ResultData addToCart(Long projectId,Long clientId,Long attrId,Integer num);
    ResultData setCartNum(Long id,Integer num);
    List<CartInfo> getCartList(Long projectId, Long clientId);
    ResultData deleteCart(Long id);
}
