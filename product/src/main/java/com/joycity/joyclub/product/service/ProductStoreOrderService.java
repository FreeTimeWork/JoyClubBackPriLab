package com.joycity.joyclub.product.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/4/27.
 */
public interface ProductStoreOrderService {
    ResultData getList(Long storeId, Byte receiveType, Byte status, String code, String name, String phone, PageUtil pageUtil);
    ResultData getInfo(Long storeOrderId);

    /**自提完成
     * @param storeOrderId
     * @return
     */
    ResultData completeSelfFetch(Long storeOrderId);

    /**
     * 发货完成

     */
    ResultData completeDelivery(Long storeOrderId, String deliveryCompany, String deliveryCode);
}
