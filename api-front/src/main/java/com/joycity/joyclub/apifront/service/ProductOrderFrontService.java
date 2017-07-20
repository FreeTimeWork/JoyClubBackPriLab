package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.apifront.modal.vo.product.order.ProductOrderVO;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */
public interface ProductOrderFrontService {
    ResultData getList(Long projectId, Long clientId, String type, PageUtil pageUtil);

    /**
     * 根据attr获取订单数据
     */
    ResultData getFormData(Long clientId, List<Long> attrIds);

    ResultData orderForWechat(Long clientId, ProductOrderVO vo);

    ResultData orderForAli(Long clientId, ProductOrderVO vo);

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
