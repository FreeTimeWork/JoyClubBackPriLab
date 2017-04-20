package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.apifront.modal.cart.ClientPostAddress;
import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by Administrator on 2017/4/19.
 */
public interface PostAddressService {
    ResultData getList(Long clientId);
    ResultData add(ClientPostAddress postAddress);
    ResultData remove(Long id);

    /**
     * 设置某个地址最后使用时间为now
     * @param id
     * @return
     */
    Integer setLastUseTimeNow(Long id);
}
