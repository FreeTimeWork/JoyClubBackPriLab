package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by Administrator on 2017/4/20.
 */
public interface ActOrderFrontService {
  /**
   *
   * @param status 可以为null
   * @return
   */
  ResultData getList(Long projectId, Long clientId,Byte status,  PageUtil pageUtil);

    /**
     *
     * @return
     */
    ResultData getFormData(Long clientId, Long  attrId);

    /**
     * @param clientId
     * @param subProjectId 可空
     * @return
     */
    ResultData orderForWechat(Long projectId, Long subProjectId, Long clientId, Long attrId,Boolean moneyOrPoint);
    ResultData orderForAli(Long projectId, Long subProjectId, Long clientId, Long attrId,Boolean moneyOrPoint);

    ResultData reorderForWechat(Long orderId);
    ResultData reorderForAli(Long orderId);

    ResultData clientCancelOrder(Long orderId);

    /**
     * @param code    我方单号
     * @param outCode 对方单号
     */

    Boolean notifyPayed(String code,String outCode,Byte payType);

/**
     * 取消超过一小时的订单
     */
    void cancelOverOneHourOrder();
    ResultData checkOrder(Long orderId);

}
