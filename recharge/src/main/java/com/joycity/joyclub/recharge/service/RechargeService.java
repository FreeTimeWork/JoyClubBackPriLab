package com.joycity.joyclub.recharge.service;

import com.joycity.joyclub.recharge.modal.vo.FluxTemp;
import com.joycity.joyclub.recharge.modal.vo.RechargeVO;
import com.joycity.joyclub.recharge.modal.vo.SpecListModel;
import com.joycity.joyclub.recharge.modal.vo.TelOperatorResult;

import java.io.UnsupportedEncodingException;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
public interface RechargeService {

    String rechargeMoney(RechargeVO vo,Long clientId) throws UnsupportedEncodingException;

    String rechargeFlux(RechargeVO vo, Long clientId) throws Exception;

    /**
     * 查询规格
     */
    SpecListModel getSpecList(String tel) throws Exception;

    /**
     * 获得手机号运营商
     */
    TelOperatorResult getTelOperator(String tel);

}
