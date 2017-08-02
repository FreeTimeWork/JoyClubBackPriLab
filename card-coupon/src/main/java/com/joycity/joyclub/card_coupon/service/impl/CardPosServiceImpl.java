package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.card_coupon.constant.CouponCodeUseStatus;
import com.joycity.joyclub.card_coupon.constant.CouponType;
import com.joycity.joyclub.card_coupon.constant.RefundType;
import com.joycity.joyclub.card_coupon.mapper.CardCouponCodeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponStoreScopeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardPosSaleDetailMapper;
import com.joycity.joyclub.card_coupon.modal.*;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCodeExample;
import com.joycity.joyclub.card_coupon.modal.generated.PosSaleDetail;
import com.joycity.joyclub.card_coupon.modal.generated.SysShop;
import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.card_coupon.service.CardPosService;
import com.joycity.joyclub.card_coupon.service.ShopService;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/24
 */
@Service
public class CardPosServiceImpl implements CardPosService {

    @Autowired
    private CardPosSaleDetailMapper cardPosSaleDetailMapper;
    @Autowired
    private CardCouponCodeMapper cardCouponCodeMapper;
    @Autowired
    private CardCouponCodeService cardCouponCodeService;
    @Autowired
    private CardCouponStoreScopeMapper cardCouponStoreScopeMapper;
    @Autowired
    private ClientUserMapper clientUserMapper;
    @Autowired
    private ShopService shopService;

    @Override
    public ResultData getCurrentCoupons(Long projectId, String shopCode, String vipCode) {
        return new ResultData(new ListResult(cardCouponCodeMapper.selectCurrentCouponCode(projectId, shopCode, null, vipCode)));
    }

    @Override
    public ResultData examineCouponCode(String vipCode, String shopCode, String couponCode) {

        return new ResultData(preExamineCouponCode(vipCode, shopCode, couponCode));
    }

    private ShowPosCurrentCouponCodeInfo preExamineCouponCode(String vipCode, String shopCode, String couponCode){
        String  notUseInfo = null;
        Date date = new Date();
        ShowPosCurrentCouponCodeInfo info = cardCouponCodeMapper.selectByCode(couponCode, -1L);
        if (info == null) {
            notUseInfo = "卡券不存在";
            info.setUseFlag(false);
            info.setNotUseInfo(notUseInfo);
            return info;
        }

        if (!info.getVipCode().equals(vipCode)) {
            notUseInfo = "卡券不属于该会员";
        } else if (info.getUseStatus().equals(CouponCodeUseStatus.USED) || info.getUseStatus().equals(CouponCodeUseStatus.CANCELLED)) {
            notUseInfo = "卡券不能使用";
        } else if (date.before(info.getEffectiveStartTime()) || date.after(info.getEffectiveEndTime())) {
            notUseInfo = "卡券不在有效期内";
        }
        int num = cardCouponStoreScopeMapper.countByCouponIdAndShopCode(info.getCouponId(), shopCode);
        if (num == 0) {
            notUseInfo = "卡券不能再该店使用";
        }
        if (notUseInfo != null) {
            info.setUseFlag(false);
            info.setNotUseInfo(notUseInfo);
            return info;
        }
        info.setUseFlag(true);
        return info;
    }
    @Override
    public ResultData posCheck(String vipCode, String couponCode, String orderCode, BigDecimal orderAmount, String shopCode) {
        //验证卡券的存在性，可用性
        ShowPosCurrentCouponCodeInfo info = preExamineCouponCode(vipCode, shopCode, couponCode);
        Boolean checkResult = info.getUseFlag();
        String resultInfo = info.getNotUseInfo();

        if (checkResult) {
            List<CouponCodeWithCoupon> couponCodeWithCoupons = cardCouponCodeMapper.selectCouponCodeWithCouponByOrderCode(orderCode);
            if (CollectionUtils.isNotEmpty(couponCodeWithCoupons)) {

                for (CouponCodeWithCoupon item : couponCodeWithCoupons) {
                    if (info.getCouponType().equals(item.getCouponType())) {
                        checkResult = false;
                        resultInfo = "该订单已核销过相同类型的卡券";
                        break;
                    }
                }
            }

            if (orderAmount.compareTo(info.getAmount()) < 0) {
                checkResult = false;
                resultInfo = "消费金额未达到该卡券使用条件";
            }
        }

        if (checkResult) {
            resultInfo = "核销成功";
            orderAmount = orderAmount.subtract(info.getSubtractAmount());
            cardCouponCodeService.checkCouponCode(info.getCouponCodeId(), orderCode);
        }

        return new ResultData(new PosCheckResult(orderAmount, checkResult, resultInfo));
    }

    @Override
    @Transactional
    public ResultData posCheckCancel(String orderCode) {
        CardCouponCodeExample example = new CardCouponCodeExample();
        try {
            CardCouponCodeExample.Criteria criteria = example.createCriteria();
            criteria.andOrderCodeEqualTo(orderCode);
            List<CardCouponCode> cardCouponCodes = cardCouponCodeMapper.selectByExample(example);
            for (CardCouponCode cardCouponCode : cardCouponCodes) {
                cardCouponCodeService.updateNotUsedCouponCode(cardCouponCode.getId());
            }
        } catch (Exception e) {
            return new ResultData(new BooleanResult(false));
        }
        return new ResultData(new BooleanResult(true));
    }

    @Override
    public ResultData posOrderInform(Long projectId, String vipCode, String orderCode, String shopCode, BigDecimal payable, BigDecimal payment) {
        SysShop shop = shopService.getShopByProjectIdAndCode(projectId, shopCode);

        Long clientId = clientUserMapper.getIdByVipCode(vipCode);
        //记录流水，返回主键
        Long posSaleDetailId = createPosSaleDetail(shop.getId(), orderCode, clientId, payable, payment);
        //条件发放卡券业务

        return new ResultData(new CreateResult(posSaleDetailId));
    }

    @Override
    public ResultData refundVerification(String orderCode, BigDecimal refundAmount) {

        CouponLaunchBetweenInfo info = preRefundVerification(orderCode, refundAmount);
        if (info.getRefundType() != null) {
            return new ResultData(new PosRefundVerifyResult(info.getRefundType()));
        }

        if (CollectionUtils.isNotEmpty(info.getDetail().getCouponCodeIds())) {
            //应该代金券拥有量
            int subCouponNum = getSubCouponNum(info, info.getDetail().getPayment());
            //实际代金券拥有量
            int actualCouponNum = info.getNotUsedNum() + info.getUsedNum();
            if ((actualCouponNum - subCouponNum) > info.getNotUsedNum() ) {
                return new ResultData(new PosRefundVerifyResult(RefundType.FORBIT_REFUND));
            }
            return new ResultData(new PosRefundVerifyResult(RefundType.PERMIT_REFUND_FULL_ORDER));
        } else {
            //应该代金券拥有量
            int subCouponNum = getSubCouponNum(info, refundAmount);
            //实际代金券拥有量
            int actualCouponNum = info.getNotUsedNum() + info.getUsedNum();
            if ((actualCouponNum - subCouponNum) > info.getNotUsedNum() ) {
                return new ResultData(new PosRefundVerifyResult(RefundType.FORBIT_REFUND));
            }
            return new ResultData(new PosRefundVerifyResult(RefundType.PREMIT_REFUND));
        }

    }

    @Override
    @Transactional
    public ResultData refund(String orderCode, BigDecimal refundAmount) {
        CouponLaunchBetweenInfo info = preRefundVerification(orderCode, refundAmount);

        if (info.getRefundType() != null && info.getRefundType().equals(RefundType.FORBIT_REFUND)) {

            throw new BusinessException(ResultCode.FORBID_REFUND);
        }
        //该订单改为退货状态
        PosSaleDetail posSaleDetail = new PosSaleDetail();
        posSaleDetail.setId(info.getDetail().getId());
        posSaleDetail.setRefundTime(new Date());
        posSaleDetail.setBalance(info.getDetail().getPayment().subtract(refundAmount));
        int affectNum = cardPosSaleDetailMapper.updateByPrimaryKeySelective(posSaleDetail);
        //退款订单关联卡券改为未使用
        if (CollectionUtils.isNotEmpty(info.getDetail().getCouponCodeIds())) {
            for (Long couponCodeId : info.getDetail().getCouponCodeIds()) {
                cardCouponCodeService.updateNotUsedCouponCode(couponCodeId);
            }
        }
        //应该代金券拥有量
        int subCouponNum = getSubCouponNum(info, refundAmount);
        //实际代金券拥有量
        int actualCouponNum = info.getNotUsedNum() + info.getUsedNum();
        Integer num = actualCouponNum - subCouponNum;

        Date date = info.getDetail().getCreateTime();
        Long clientId = info.getDetail().getClientId();
        List<Long> couponCodeIds = cardCouponCodeMapper.selectNotUsedCouponCodeIdFromLaunchBetween(date, clientId, num);
        //未使用代金券废弃
        for (Long id : couponCodeIds) {
            CardCouponCode cardCouponCode = new CardCouponCode();
            cardCouponCode.setId(id);
            cardCouponCode.setUseStatus(CouponCodeUseStatus.CANCELLED);
            cardCouponCodeMapper.updateByPrimaryKeySelective(cardCouponCode);
        }


        return new ResultData(new UpdateResult(affectNum));
    }

    /**
     *     条件发放的判断逻辑,返回应发放代金券的数量
     */

    private int conditionSendCoupon(){

        return 0;
    }

    private Long createPosSaleDetail(Long shopId, String orderCode, Long clientId, BigDecimal payable, BigDecimal payment) {
        PosSaleDetail detail = new PosSaleDetail();
        detail.setOrderCode(orderCode);
        detail.setClientId(clientId);
        detail.setPayable(payable);
        detail.setPayment(payment);
        detail.setBalance(payment);
        detail.setShopId(shopId);
        cardPosSaleDetailMapper.insertSelective(detail);
        return detail.getId();
    }

    private CouponLaunchBetweenInfo preRefundVerification(String orderCode, BigDecimal refundAmount) {
        PosSaleDetailWithCouponCode detail = cardPosSaleDetailMapper.selectByOrderCode(orderCode);
        //找不到订单，不能退款
        if (detail == null) {
            CouponLaunchBetweenInfo info = new CouponLaunchBetweenInfo();
            info.setRefundType(RefundType.FORBIT_REFUND);
            return info;
        }
        if (refundAmount.compareTo(detail.getBalance()) > 0) {
            CouponLaunchBetweenInfo info = new CouponLaunchBetweenInfo();
            info.setRefundType(RefundType.FORBIT_REFUND);
            return info;
        }

        int forbidRefundNum = cardCouponCodeMapper.countForbidRefundByOrderCode(orderCode);
        if (forbidRefundNum > 0) {
            CouponLaunchBetweenInfo info = new CouponLaunchBetweenInfo();
            info.setRefundType(RefundType.FORBIT_REFUND);
            return info;
        }
        CouponLaunchBetweenInfo info = cardCouponCodeMapper.selectInfoFromLaunchBetween(detail.getCreateTime());
        //订单不再投放期间内可以退款
        if (info == null) {
            //如果该订单关联了卡券，只能全单退款
            info = new CouponLaunchBetweenInfo();
            if (CollectionUtils.isNotEmpty(detail.getCouponCodeIds())) {
                info.setRefundType(RefundType.PERMIT_REFUND_FULL_ORDER);
            } else {
                info.setRefundType(RefundType.PREMIT_REFUND);
            }
            return info;
        }
        info.setDetail(detail);
        //在条件投放期间内，卡券的使用和未使用的数量
        CouponLaunchBetweenInfo couponNumInfo = cardCouponCodeMapper.selectCouponNumFromLaunchBetween(detail.getCreateTime(),detail.getClientId());
        info.setUsedNum(couponNumInfo.getUsedNum());
        info.setNotUsedNum(couponNumInfo.getNotUsedNum());
        BigDecimal sumPaid = cardCouponCodeMapper.selectSumPaidFromLaunchBetween(detail.getCreateTime(),detail.getClientId());
        info.setSumPaid(sumPaid);

        return info;
    }

    private int getSubCouponNum(CouponLaunchBetweenInfo info, BigDecimal refundAmount) {
        return (info.getSumPaid().subtract(refundAmount).divide(info.getConditionAmount())).intValue();
    }

}
