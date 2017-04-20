package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by Administrator on 2017/4/20.
 */
public interface ProductOrderFrontService {
    /**
     * @param rawData  应该是｛attrId｝数组
     * @return
     */
    ResultData getFormData(Long clientId,String rawData);

    /**
     * @param clientId
     * @param jsonStr
     * @param pickUpOrPost 自提还是快递
     * @param postAddressId 如果快递 需要提供快递地址id
     * @return
     */
    ResultData orderToGetPayParams(Long projectId, Long clientId, String jsonStr, Boolean pickUpOrPost, Long postAddressId);

    /**
     * @param code 我方单号
     * @param outCode 对方单号
     */
    void wechatNotifyPayed(String code,String outCode);
}
