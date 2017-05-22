package com.joycity.joyclub.act.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/5/22.
 */
public interface ActOrderService {
    ResultData getList(Long storeId,Byte status, String code, String name, String phone,String actName, PageUtil pageUtil);
   /* ResultData getInfo(Long orderId);*/

    ResultData checkOrder(Long orderId);

}
