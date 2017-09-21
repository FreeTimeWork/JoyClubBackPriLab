package com.joycity.joyclub.client.service.impl;

import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client.modal.VipCardInfo;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.AbstractGetListData;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/16.
 */
@Service
public class ClientServiceImpl implements ClientService {
    private static final Log logger = LogFactory.getLog(ClientServiceImpl.class);
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
    public ResultData getListForBack(/*final String group13,*/
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
                return clientUserMapper.countForBack(/*group13,*/ cardType, pointStart, pointEnd, vipNo, cardNo, phone, pageUtil);
            }

            @Override
            public List<Client> selectByFilter() {
                return clientUserMapper.getListForBack(/*group13,*/ cardType, pointStart, pointEnd, vipNo, cardNo, phone, pageUtil);
            }
        }.getList(pageUtil);
    }

    @Override
    public Client fetchClientByVipCodeFromKechuanAndAynclocal(String vipCode) {
        Client client = keChuanCrmService.getMemberByVipCode(vipCode);
        if (client != null) {
            Long clientId = clientUserMapper.getIdByVipCode(vipCode);
            if (clientId == null) {
                Long id = null;
                try {
                     id = clientUserMapper.insertSelective(client);
                } catch (Exception e) {
                    logger.info("有问题的crmId：" + client.getVipCode());
                }
                client.setId(id);
            } else {
                client.setId(clientId);
                clientUserMapper.updateByPrimaryKeySelective(client);
            }

        }
        return client;
    }

    @Override
    public ResultData syncCrmId() {
        int num = 0;
        int page = 1;
        //一次取5万条
        PageUtil pageUtil = new PageUtil(page,50000);
        List<String> crmIds = clientUserMapper.getCrmId(pageUtil);
        while (CollectionUtils.isNotEmpty(crmIds)) {
            for (int i = 0; i < crmIds.size(); i++) {
                fetchClientByVipCodeFromKechuanAndAynclocal(crmIds.get(i));
            }
            num += crmIds.size();
            page ++ ;
            pageUtil.setPage(page);
            crmIds = clientUserMapper.getCrmId(pageUtil);
            logger.info("同步会员数据个数num：" + num);
        }
        Map map = new HashMap<String, Integer>();
        map.put("num", num);
        return new ResultData(map);
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
