package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.card_coupon.constant.CouponCodeUseStatus;
import com.joycity.joyclub.card_coupon.constant.CouponType;
import com.joycity.joyclub.card_coupon.mapper.CardCouponCodeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardPosSaleDetailMapper;
import com.joycity.joyclub.card_coupon.modal.CouponCodeWithCoupon;
import com.joycity.joyclub.card_coupon.modal.CouponLaunchBetweenInfo;
import com.joycity.joyclub.card_coupon.modal.PosCheckResult;
import com.joycity.joyclub.card_coupon.modal.PosSaleDetailWithCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.PosSaleDetail;
import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.card_coupon.service.CardPosService;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.BooleanResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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

    @Override
    public ResultData getCurrentCoupons(Long projectId, String shopCode, String tel, String vipCode) {
        return new ResultData(new ListResult(cardCouponCodeMapper.selectCurrentCouponCode(projectId, shopCode, tel, vipCode)));
    }

    @Override
    public ResultData posCheck(Long projectId, List<String> couponCodes, String orderCode, BigDecimal orderAmount, String shopCode) {
        List<CouponCodeWithCoupon> couponCodeWithCoupons = cardCouponCodeMapper.selectCardCouponCodesByCodes(projectId, couponCodes, shopCode, true);
        Long clientId = null;
        //验证卡券的存在性，可用性
        Map<Byte, CouponCodeWithCoupon> couponCodeWithCouponMap = preCheck(couponCodes, orderAmount, couponCodeWithCoupons);
        //先核销满减，再核销代金
        CouponCodeWithCoupon deductionCouponCode = couponCodeWithCouponMap.get(CouponType.DEDUCTION_COUPON);
        CouponCodeWithCoupon cashCouponCode = couponCodeWithCouponMap.get(CouponType.CASH_COUPON);

        //算实际支付金额，取clientId
        if (deductionCouponCode != null) {
            orderAmount = orderAmount.subtract(deductionCouponCode.getSubtractAmount());
            clientId = deductionCouponCode.getClientId();
        }
        if (cashCouponCode != null) {
            orderAmount = orderAmount.subtract(cashCouponCode.getSubtractAmount());
            clientId = cashCouponCode.getClientId();
        }

        //记录流水，返回主键
        Long  posSaleDetailId = createPosSaleDetail(orderCode, clientId, orderAmount);


        if (deductionCouponCode != null) {
            cardCouponCodeService.checkCouponCode(deductionCouponCode.getId(), posSaleDetailId);
        }
        if (cashCouponCode != null) {
            cardCouponCodeService.checkCouponCode(cashCouponCode.getId(), posSaleDetailId);
        }

        //返回应付值+每个卡券在该商户的分担比
        PosCheckResult posCheckResult = new PosCheckResult();
        List<PosCheckResult.RatioDetail> details = new ArrayList<>();
        PosCheckResult.RatioDetail deductionRatioDetail = posCheckResult.new RatioDetail(deductionCouponCode.getCode(), deductionCouponCode.getRatio(), deductionCouponCode.getSubtractAmount());
        PosCheckResult.RatioDetail cashRatioDetail = posCheckResult.new RatioDetail(cashCouponCode.getCode(), cashCouponCode.getRatio(), cashCouponCode.getSubtractAmount());
        details.add(deductionRatioDetail);
        details.add(cashRatioDetail);
        posCheckResult.setPayable(orderAmount);
        posCheckResult.setOrderCode(orderCode);
        posCheckResult.setRatioDetails(details);
        return new ResultData(posCheckResult);
    }

    @Override
    public ResultData refundVerification(String orderCode) {

        CouponLaunchBetweenInfo info = preRefundVerification(orderCode);
        // 该订单不在投放期间内，可以退款
        if (info == null) {
            return new ResultData(new BooleanResult(true));
        }
        //应该代金券拥有量
        int subCouponNum = getSubCouponNum(info);
        //实际代金券拥有量
        int actualCouponNum = info.getNotUsedNum() + info.getUsedNum();
        if ((actualCouponNum - subCouponNum) > info.getNotUsedNum() ) {
            throw new BusinessException(ResultCode.FORBID_REFUND);
        }

        return new ResultData(new BooleanResult(true));
    }

    @Override
    public ResultData refund(String orderCode) {

        CouponLaunchBetweenInfo info = preRefundVerification(orderCode);

        //该订单改为退货状态
        PosSaleDetail posSaleDetail = new PosSaleDetail();
        posSaleDetail.setId(info.getDetail().getId());
        posSaleDetail.setRefundFlag(true);
        posSaleDetail.setRefundTime(new Date());
        int affectNum = cardPosSaleDetailMapper.updateByPrimaryKeySelective(posSaleDetail);
        //退款订单关联卡券改为未使用
        if (CollectionUtils.isNotEmpty(info.getDetail().getCouponCodeIds())) {
            for (Long id : info.getDetail().getCouponCodeIds()) {
                cardCouponCodeService.updateNotUsedCouponCode(id);
            }
        }
        //应该代金券拥有量s
        int subCouponNum = getSubCouponNum(info);
        //实际代金券拥有量
        int actualCouponNum = info.getNotUsedNum() + info.getUsedNum();
        Integer num = actualCouponNum - subCouponNum;
        if (num <= info.getNotUsedNum()) {
            Date date = info.getDetail().getCreateTime();
            Long clientId = info.getDetail().getClientId();
            List<Long> couponCodeIds =  cardCouponCodeMapper.selectNotUsedCouponCodeIdFromLaunchBetween(date, clientId, num);
            //未使用代金券废弃
            for (Long id : couponCodeIds) {
                CardCouponCode cardCouponCode = new CardCouponCode();
                cardCouponCode.setId(id);
                cardCouponCode.setUseStatus(CouponCodeUseStatus.CANCELLED);
                cardCouponCodeMapper.updateByPrimaryKeySelective(cardCouponCode);
            }
        }

        return new ResultData(new UpdateResult(affectNum));
    }

    @Override
    public Long createPosSaleDetail(String orderCode, Long clientId, BigDecimal paid) {
        PosSaleDetail detail = new PosSaleDetail();
        detail.setOrderCode(orderCode);
        detail.setClientId(clientId);
        detail.setPaid(paid);
        cardPosSaleDetailMapper.insertSelective(detail);
        return detail.getId();
    }

    private CouponLaunchBetweenInfo preRefundVerification(String orderCode) {
        int forbidRefundNum = cardCouponCodeMapper.countForbidRefundByOrderCode(orderCode);
        if (forbidRefundNum > 0) {
            throw new BusinessException(ResultCode.FORBID_REFUND);
        }

        PosSaleDetailWithCouponCode detail = cardPosSaleDetailMapper.selectByOrderCode(orderCode);
        if (detail == null) {
            throw new BusinessException(ResultCode.ORDER_NOT_FIND);
        }
        CouponLaunchBetweenInfo info = cardCouponCodeMapper.selectInfoFromLaunchBetween(detail.getCreateTime());
        if (info == null) {
            return null;
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

    private int getSubCouponNum(CouponLaunchBetweenInfo info) {
        return (info.getSumPaid().subtract(info.getDetail().getPaid()).divide(info.getConditionAmount())).intValue();
    }

    private Map<Byte, CouponCodeWithCoupon> preCheck(List<String> couponCodes, BigDecimal orderAmount, List<CouponCodeWithCoupon> couponCodeWithCoupons) {
        if (CollectionUtils.isEmpty(couponCodeWithCoupons) || couponCodes.size() != couponCodeWithCoupons.size()) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "其中有卡券不可用");
        }

        Map<Byte, CouponCodeWithCoupon> couponCodeWithCouponMap = new HashMap<>();
        for (CouponCodeWithCoupon item : couponCodeWithCoupons) {
            if (item.getCouponType().equals(CouponType.DEDUCTION_COUPON)) {
                if (couponCodeWithCouponMap.get(CouponType.DEDUCTION_COUPON) != null){

                    throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "满减券只能有一个");
                }
                couponCodeWithCouponMap.put(CouponType.DEDUCTION_COUPON, item);
            }

            if (item.getCouponType().equals(CouponType.CASH_COUPON)) {
                if (couponCodeWithCouponMap.get(CouponType.CASH_COUPON) != null){

                    throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "代金券只能有一个");
                }
                couponCodeWithCouponMap.put(CouponType.CASH_COUPON, item);
            }
        }

        CouponCodeWithCoupon deductionCouponCode = couponCodeWithCouponMap.get(CouponType.DEDUCTION_COUPON);
        if (deductionCouponCode != null) {
            if (orderAmount.compareTo(deductionCouponCode.getAmount()) < 0) {
                throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "满减券没有达到满额条件");
            }
            orderAmount = orderAmount.subtract(deductionCouponCode.getSubtractAmount());
        }

        CouponCodeWithCoupon cashCouponCode = couponCodeWithCouponMap.get(CouponType.CASH_COUPON);
        if (cashCouponCode != null) {
            if (orderAmount.compareTo(cashCouponCode.getAmount()) < 0) {
                throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "代金券没有达到满额条件");
            }
        }

        return couponCodeWithCouponMap;
    }

    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal(10);
        System.out.println(amount.subtract(BigDecimal.ONE));
    }

    public static void method(BigDecimal amount) {
        amount.subtract(BigDecimal.ONE);
    }

}
