package com.joycity.joyclub.client.service.impl;

import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client.modal.VipCardInfo;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.coupon.modal.generated.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/4/16.
 */
@Service
public class ClientServiceImpl implements ClientService {
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
        info.setVipPoint(getPoint(id));
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

    @Override
    public ResultData getListForBack(final String group13,
                                     final String cardType,
                                     final Integer pointStart,
                                     final Integer pointEnd,
                                     final String vipNo,
                                     final String cardNo,
                                     final String phone,
                                     final PageUtil pageUtil) {
        return new AbstractGetListData<Client>() {
            @Override
            public Long countByFilter() {
                return clientUserMapper.countForBack(group13, cardType, pointStart, pointEnd, vipNo, cardNo, phone, pageUtil);
            }

            @Override
            public List<Client> selectByFilter() {
                return clientUserMapper.getListForBack(group13, cardType, pointStart, pointEnd, vipNo, cardNo, phone, pageUtil);
            }
        }.getList(pageUtil);
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
