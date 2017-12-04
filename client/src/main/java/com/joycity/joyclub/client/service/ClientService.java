package com.joycity.joyclub.client.service;

import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by Administrator on 2017/4/16.
 */
public interface ClientService {
    ResultData getPointRecord(Long id);

    ResultData getWechatPortraitAndName(Long id);

    ResultData getCardInfo(Long id);

    /**
     * 先从科传处获取积分，再更新本地积分
     */
    Integer getPoint(Long clientId);

    /**
     * 先更新科传处积分，再更新本地积分
     *
     * @param clientId
     * @param changeValue
     */
    void addPoint(Long clientId, Double changeValue);

    String getVipCodeById(Long id);

    ResultData getListForBack(/*String group13,*/ String cardType, Integer pointStart, Integer pointEnd, String vipNo, String cardNo, String phone, PageUtil pageUtil);

    /**
     * 从科传处更新会员信息，并且同步到本地
     * @param vipCode
     * @return 返回从科传处取得的值，以及在本地的id
     */
    Client fetchClientByVipCodeFromKechuanAndAynclocal(String vipCode);

    Boolean checkPoint(String tel,Double checkPoint);
}
