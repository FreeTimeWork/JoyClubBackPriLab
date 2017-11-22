package com.joycity.joyclub.recharge.service;

import com.joycity.joyclub.recharge.modal.vo.FluxTemp;
import com.joycity.joyclub.recharge.modal.vo.RechargeVO;
import com.joycity.joyclub.recharge.modal.vo.SpecListModel;

import java.io.UnsupportedEncodingException;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
public interface RechargeService {
//    /**
//     * 手机、固话号等，固话和宽带需要带区号，但不要分隔符。
//     * @param phone
//     * @param flowSize 流量包大小，以M为单位
//     * @param ifNationFlow 是否是全国流量，如果否则代表省内流量
//     */
//    void rechargeFlow(String phone,int flowSize,boolean ifNationFlow);

    String rechargeMoney(RechargeVO vo,Long clientId) throws UnsupportedEncodingException;

    String rechargeFlux(RechargeVO vo, Long clientId) throws Exception;

    /**
     * 查询规格
     */
    SpecListModel getSpecList(String tel, FluxTemp temp) throws Exception;

}
