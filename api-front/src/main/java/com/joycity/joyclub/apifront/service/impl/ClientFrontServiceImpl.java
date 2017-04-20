package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.exception.BusinessException;
import com.joycity.joyclub.apifront.mapper.manual.ClientUserMapper;
import com.joycity.joyclub.apifront.modal.client.Client;
import com.joycity.joyclub.apifront.modal.client.VipCardInfo;
import com.joycity.joyclub.apifront.service.ClientFrontService;
import com.joycity.joyclub.apifront.service.KeChuanCrmService;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joycity.joyclub.apifront.constant.ResultCode.DATA_NOT_EXIST;

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
        return new ResultData(info);
    }

    @Override
    public Integer getPoint(Long id) {
        return clientUserMapper.getPoint(id);
    }

    @Override
    public void addPoint(Long id, Double changeValue) {
     String vipCode =    clientUserMapper.getVipCodeById(id);
        if(vipCode==null) {
            throw  new BusinessException(DATA_NOT_EXIST,"会员不存在");

        }
        keChuanCrmService.changePoint(vipCode,changeValue);
    }

    private String getVipCodeById(Long id) {
        String code = clientUserMapper.getVipCodeById(id);
        checkNullThrowUserNotExist(code);
        return code;
    }

    /**
     * 如果为空 抛出异常
     */
    private void checkNullThrowUserNotExist(Object o) {
        if (o == null) {
            throw new BusinessException(DATA_NOT_EXIST, "用户不存在");
        }
    }
}
