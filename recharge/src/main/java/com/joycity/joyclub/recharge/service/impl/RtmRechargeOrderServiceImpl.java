package com.joycity.joyclub.recharge.service.impl;

import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.recharge.mapper.RtmRechargeOrderDetailMapper;
import com.joycity.joyclub.recharge.modal.RTMResult;
import com.joycity.joyclub.recharge.modal.generated.RtmRechargeOrderDetail;
import com.joycity.joyclub.recharge.service.RtmRechargeOrderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @auther fangchen.chai ON 2017/11/30
 */
@Service
public class RtmRechargeOrderServiceImpl implements RtmRechargeOrderService {

    //订单状态: 0 异常订单 1 积分增加成功 2 积分扣减成功 3 退款成功

    private static final String ADD = "add";
    private static final String DELETE = "delete";

    @Autowired
    private ClientUserMapper clientUserMapper;
    @Autowired
    private ClientService clientService;
    @Autowired
    private RtmRechargeOrderDetailMapper rtmRechargeOrderDetailMapper;

    @Override
    public RTMResult deletePoint(String uid, BigDecimal credits, String pointType, String rtmOrderNum) {
        RTMResult rtmResult = new RTMResult();
        try {
            Boolean result = clientService.checkPoint(uid, credits.doubleValue());
            RtmRechargeOrderDetail detail = null;
            if (result) {
                detail = createRtmOrder(uid, credits.negate(), pointType, rtmOrderNum, DELETE,2);
                clientService.addPoint(detail.getClientId(),-credits.doubleValue());
            } else {
                rtmResult.setMsg("积分不足");
                rtmResult.setStatus(false);
                rtmResult.setCode(ResultCode.VIP_POINT_NOT_ENOUGH+"");
            }
            rtmResult.setOrderNum(detail.getOrderCode());
        } catch (Exception e) {
            rtmResult.setStatus(false);
            rtmResult.setCode("500");
            rtmResult.setMsg(e.toString());
        }

        return rtmResult;
    }

    @Override
    public RTMResult backPoint(String uid, BigDecimal credits, String pointType, String rtmOrderNum) {
        RTMResult result = new RTMResult();
        RtmRechargeOrderDetail detail = rtmRechargeOrderDetailMapper.selectRtmByRtmOrderCode(rtmOrderNum);
        if (detail == null) {
            result.setCode(ResultCode.DATA_NOT_EXIST + "");
            result.setMsg("订单不存在");
            result.setStatus(false);
            return result;
        }
        detail.setPoint(detail.getPoint().add(credits));
        detail.setStatus(3);
        rtmRechargeOrderDetailMapper.updateByPrimaryKeySelective(detail);
        clientService.addPoint(detail.getClientId(), credits.doubleValue());
        result.setOrderNum(detail.getOrderCode());
        return result;
    }

    @Override
    public RTMResult addPoint(String uid, BigDecimal credits, String pointType, String rtmOrderNum) {
        RTMResult result = new RTMResult();
        try {
            RtmRechargeOrderDetail detail = createRtmOrder(uid, credits, pointType, rtmOrderNum, ADD,1);
            clientService.addPoint(detail.getClientId(), credits.doubleValue());
            result.setOrderNum(detail.getOrderCode());
        } catch (Exception e) {
            result.setStatus(false);
            result.setCode("500");
        }
        return result;
    }

    @Override
    public RTMResult getRtmOrderDetail(String rtmOrderNum) {
        RTMResult result = new RTMResult();
        RtmRechargeOrderDetail detail = rtmRechargeOrderDetailMapper.selectRtmByRtmOrderCode(rtmOrderNum);
        if (detail == null) {
            result.setCode(ResultCode.DATA_NOT_EXIST + "");
            result.setStatus(false);
            result.setMsg("订单不存在");
        }
        result.setOrderStatus(detail.getStatus());
        return result;
    }

    @Override
    public RTMResult getPoint(String uid) {
        RTMResult result = new RTMResult();
        Long clientId = clientUserMapper.getIdByTel(uid);
        if (clientId == null) {
            result.setCode(ResultCode.DATA_NOT_EXIST+"");
            result.setMsg("会员不存在");
            result.setStatus(false);
        }
        Integer point = clientService.getPoint(clientId);
        if (point == null) {
            result.setCode(ResultCode.DATA_NOT_EXIST+"");
            result.setMsg("积分获取失败");
            result.setStatus(false);
        }
        result.setBalance(new BigDecimal(point));
        return result;
    }

    private RtmRechargeOrderDetail createRtmOrder(String uid, BigDecimal credits, String pointType, String rtmOrderNum,String operateType,Integer orderStatus) {
        RtmRechargeOrderDetail detail = new RtmRechargeOrderDetail();
        Long clientId = clientUserMapper.getIdByTel(uid);
        detail.setClientId(clientId);
        detail.setOperateType(operateType);
        detail.setOrderCode(createOrderCode());
        detail.setOutOrder(rtmOrderNum);
        detail.setPoint(credits);
        detail.setType(pointType);
        detail.setStatus(orderStatus);
        rtmRechargeOrderDetailMapper.insertSelective(detail);
        return detail;
    }

    private String createOrderCode(){
        return System.currentTimeMillis()+ RandomStringUtils.random(8,"1234567890");
    }

    public static void main(String[] args) {
        BigDecimal  b= new BigDecimal("1");
        System.out.println(b.negate());
    }
}
