package com.joycity.joyclub.recharge.service;

import com.joycity.joyclub.recharge.modal.RTMResult;

import java.math.BigDecimal;

/**
 * @auther fangchen.chai ON 2017/11/30
 */
public interface RtmRechargeOrderService {

    RTMResult deletePoint(String uid, BigDecimal credits, String pointType , String rtmOrderNum);
    RTMResult backPoint(String uid,BigDecimal credits,String pointType ,String rtmOrderNum);
    RTMResult addPoint(String uid,BigDecimal credits,String pointType ,String rtmOrderNum);
    RTMResult getRtmOrderDetail(String rtmOrderNum);
    RTMResult getPoint(String uid);

}
