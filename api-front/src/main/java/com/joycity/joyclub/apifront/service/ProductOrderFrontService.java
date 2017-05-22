package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by Administrator on 2017/4/20.
 */
public interface ProductOrderFrontService {
    ResultData getList(Long projectId, Long clientId, String type, PageUtil pageUtil);

    /**
     * @param rawData 应该是｛attrId｝数组
     * @return
     */
    ResultData getFormData(Long clientId, String rawData);

    /**
     * @param clientId
     * @param subProjectId 可空
     * @param jsonStr
     * @param pickUpOrPost  自提还是快递
     * @param postAddressId 如果快递 需要提供快递地址id
     * @return
     */
    ResultData orderForWechat(Long projectId, Long subProjectId,Long clientId, String jsonStr, Boolean pickUpOrPost, Long postAddressId, Boolean fromCart);
    ResultData orderForAli(Long projectId,Long subProjectId, Long clientId, String jsonStr, Boolean pickUpOrPost, Long postAddressId, Boolean fromCart);

    ResultData reorderForWechat(Long orderId);
    ResultData reorderForAli(Long orderId);

    ResultData clientCancelOrder(Long orderId);

    /**
     * @param code    我方单号
     * @param outCode 对方单号
     */
    void wechatNotifyPayed(String code, String outCode);
    void aliNotifyPayed(String code, String outCode);
    ////////////////////////////////////////////////
    void systemCancelOrder(Long orderId);

    /**
     * 取消超过一小时的订单
     */
    void cancelOverOneHourOrder();

    /**
     * 超过十天的已发货商户订单设置为已收货
     */
    void receiveOverTenDayStoreOrder();
}
