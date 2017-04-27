package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.service.BenefitService;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.coupon.mapper.CouponMapper;
import com.joycity.joyclub.coupon.modal.CouponForClient;
import com.joycity.joyclub.coupon.modal.generated.Coupon;
import com.joycity.joyclub.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil.checkNull;
import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;

/**
 * Created by CallMeXYZ on 2017/4/21.
 */
@Service
public class BenefitServiceImpl implements BenefitService {
    @Autowired
    ClientUserMapper clientMapper;
    @Autowired
    CouponService couponService;
    @Autowired
    ClientService clientService;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    KeChuanCrmService keChuanCrmService;

    @Override
    public ResultData getCoupons(Long projectId, Long clientId, PageUtil pageUtil) {
        List<CouponForClient> list;
        if (clientId == null) {
            list = couponService.getCouponListForFront(projectId, pageUtil);
        } else {
            Client client = clientMapper.selectByPrimaryKey(clientId);
            checkNull(client, "会员不存在");
            list = couponService.getCouponListForFront(projectId, clientId, client.getVipCardGrade(), pageUtil);
        }
        return new ResultData(new ListResult(list));
    }

    @Override
    public ResultData getClientCoupons(Long clientId, PageUtil pageUtil) {

        return new ResultData(new ListResult(couponService.getCouponListForFrontClient(clientId, pageUtil)));

    }

    //// TODO: 2017/4/21
    private Integer getVipPoint(Long clientId) {
        String tel = clientMapper.getTel(clientId);
        Client client = keChuanCrmService.getMemberByTel(tel);
        if (client == null) {
            throw new BusinessException(DATA_NOT_EXIST, "无法获取会员信息");
        }
        return client.getVipPoint();
    }

    @Override
    public ResultData receiveCoupon(Long couponId, Long clientId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        if (coupon == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST, "卡券不存在");
        }
        if (coupon.getPointCost() > getVipPoint(clientId)) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST, "积分不足");
        }
        Integer pointCost;

        pointCost = couponService.clientReceiveCoupon(couponId, clientId);


        //减积分
        if (pointCost > 0) {
            clientService.addPoint(clientId, Double.valueOf(-pointCost));
        }
        return new ResultData();
    }

}
