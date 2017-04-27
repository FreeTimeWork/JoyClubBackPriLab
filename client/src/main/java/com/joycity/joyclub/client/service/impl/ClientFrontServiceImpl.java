package com.joycity.joyclub.client.service.impl;

import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client.modal.VipCardInfo;
import com.joycity.joyclub.client.service.ClientFrontService;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;

/**
 * Created by Administrator on 2017/4/16.
 */
@Service
public class ClientFrontServiceImpl implements ClientFrontService {
    @Autowired
    KeChuanCrmService keChuanCrmService;
    @Autowired
    ClientUserMapper clientUserMapper;

    @Override
    public ResultData getPointRecord(Long id) {
        String vipCode = getVipCodeById(id);
        return new ResultData(new ListResult(keChuanCrmService.getScoreRecord(vipCode)));
    }

    @Override
    public ResultData getWechatPortraitAndName(Long id) {
        return new ResultData(clientUserMapper.getWechatInfo(id));
    }

    @Override
    public ResultData getCardInfo(Long id) {
        Client client = clientUserMapper.selectByPrimaryKey(id);
        checkNullThrowUserNotExist(client);
        VipCardInfo info = new VipCardInfo();
        info.setCardType(client.getVipCardGrade());
        info.setVipPoint(client.getVipPoint());
        info.setPointRecords(keChuanCrmService.getScoreRecord(client.getVipCode()));
        info.setSaleRecords(keChuanCrmService.getSaleRecord(client.getVipCode()));
        return new ResultData(info);
    }

    @Override
    public Integer getPoint(Long clientId) {
        String tel = clientUserMapper.getTel(clientId);
        checkNullThrowUserNotExist(tel);
        Integer newPoint = keChuanCrmService.getMemberByTel(tel).getVipPoint();
        clientUserMapper.setPoint(clientId, newPoint);
        return newPoint;
    }

    @Override
    public void addPoint(Long clientId, Double changeValue) {
        String vipCode = clientUserMapper.getVipCodeById(clientId);
        Integer newPoint = keChuanCrmService.changePoint(vipCode, changeValue);
        if (newPoint != null) {
            clientUserMapper.setPoint(clientId, newPoint);
        }
    }

    @Override
    public String getVipCodeById(Long id) {
        String code = clientUserMapper.getVipCodeById(id);
        checkNullThrowUserNotExist(code);
        return code;
    }

    /**
     * 如果为空 抛出异常
     */
    private void checkNullThrowUserNotExist(Object o) {
        if (o == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST, "会员不存在");
        }
    }
}
