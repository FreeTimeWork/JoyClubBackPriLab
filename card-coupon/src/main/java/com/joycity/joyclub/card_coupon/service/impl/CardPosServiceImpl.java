package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.card_coupon.cache.CardCouponCodeCache;
import com.joycity.joyclub.card_coupon.constant.CouponCodeUseStatus;
import com.joycity.joyclub.card_coupon.constant.RefundType;
import com.joycity.joyclub.card_coupon.mapper.CardCouponCodeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponStoreScopeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardPosSaleDetailMapper;
import com.joycity.joyclub.card_coupon.modal.*;
import com.joycity.joyclub.card_coupon.modal.generated.*;
import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.card_coupon.service.CardPosService;
import com.joycity.joyclub.card_coupon.service.ShopService;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.*;
import com.joycity.joyclub.commons.utils.DateTimeUtil;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/24
 */
@Service
public class CardPosServiceImpl implements CardPosService {
    private Log logger = LogFactory.getLog(CardPosServiceImpl.class);

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
    @Autowired
    private CardCouponLaunchMapper launchMapper;
    @Autowired
    private CardCouponCodeCache couponCodeCache;
    @Autowired
    KeChuanCrmService keChuanCrmService;
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
            info = new ShowPosCurrentCouponCodeInfo();
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

        //减去优惠额为负数，重置为0
        if (orderAmount.compareTo(BigDecimal.ZERO) < 0) {
            orderAmount = BigDecimal.ZERO;
        }
        return new ResultData(new PosCheckResult(orderAmount, checkResult, resultInfo));
    }

    @Override
    @Transactional
    public ResultData posCheckCancel(String orderCode, String couponCode) {
        CardCouponCodeExample example = new CardCouponCodeExample();
        try {
            CardCouponCodeExample.Criteria criteria = example.createCriteria();
            criteria.andOrderCodeEqualTo(orderCode);
            criteria.andCodeEqualTo(couponCode);
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
    public ResultData posOrderInform(Long projectId, String vipCode, String orderCode, String shopCode, BigDecimal payable, BigDecimal payment) throws ParseException {
        logger.info("posOrderInform: projectId:" + projectId + " vipCode:" + vipCode + "orderCode: " + orderCode + " shopCode：" + shopCode + " payable:" + payable.toString() + " payment:" + payment.toString());
        payment = payable;
        SysShop shop = shopService.getShopByProjectIdAndCode(projectId, shopCode);
        ThrowBusinessExceptionUtil.checkNull(shop, "商户不存在");
        ThrowBusinessExceptionUtil.checkNull(vipCode, "会员号不存在");
        Long clientId = clientUserMapper.getIdByVipCode(vipCode);
        //如果clientId不存在，重新从科传同步一遍数据
        if (clientId == null) {
            Client client = keChuanCrmService.getMemberByVipCode(vipCode);
            //找不到会员，不处理
            if (client == null) {
                return new ResultData();
            }
            //没有手机号，不处理
            if (StringUtils.isBlank(client.getTel())) {
                return new ResultData();
            }
            clientUserMapper.insertSelective(client);
            clientId = client.getId();
        }
        //记录流水，返回主键
        Long posSaleDetailId = createPosSaleDetail(shop.getId(), orderCode, clientId, payable, payment);

        //条件发放卡券业务
        CouponLaunchBetweenInfo info = clientCouponNumAndSumPaidInfo(shop.getId(), new Date(), clientId);
        //如果该订单在条件投放期间
        if (info != null) {
            int receiveNum = receiveCashCouponNum(info, clientId);
            if (receiveNum > 0) {
                CardCouponLaunch launch = launchMapper.selectByPrimaryKey(info.getLaunchId());
                //发卡
                for (int i= 0 ; i < receiveNum ; i++) {
                    boolean result = couponCodeCache.sendCouponCode(info.getLaunchId());
                    if (result) {
                        cardCouponCodeService.sendCouponCode(clientId, info.getLaunchId(), launch.getCouponId());
                    }
                }
            }
        }
        return new ResultData(new CreateResult(posSaleDetailId));
    }

    @Override
    public ResultData refundVerification(String orderCode) {

        CouponLaunchBetweenInfo info = preRefundVerification(orderCode);
        return new ResultData(new PosRefundVerifyResult(info.getRefundType()));
    }

    @Override
    @Transactional
    public ResultData refund(String orderCode, BigDecimal refundAmount) {
        CouponLaunchBetweenInfo info = preRefundVerification(orderCode);
        logger.info("refund-CouponLaunchBetweenInfo:"+info);
        if (info.getRefundType() != null && info.getRefundType().equals(RefundType.FORBIT_REFUND)) {

            throw new BusinessException(ResultCode.COUPON_FORBID_REFUND);
        }

        //系统没有订单，直接返回成功
        if (info.getDetail() == null) {
            return new ResultData(new UpdateResult(0));
        }
//        // 如果 refundAmount = 0，直接 从订单里找退款金额
//        if (refundAmount != null && refundAmount.intValue() == 0) {
//            refundAmount = info.getDetail().getBalance();
//        }
        //应该代金券拥有量
        int subCouponNum = getSubCouponNum(info, refundAmount);
        Integer num = actualNumSubtractSubNum(info, subCouponNum);
        if (num > 0 && num <= info.getNotUsedNum()) {
            Date date = info.getDetail().getCreateTime();
            Long clientId = info.getDetail().getClientId();
            List<Long> couponCodeIds = cardCouponCodeMapper.selectNotUsedCashCouponCodeIdFromLaunchBetween(date, clientId, num);
            //未使用代金券废弃
            for (Long id : couponCodeIds) {
                CardCouponCode cardCouponCode = new CardCouponCode();
                cardCouponCode.setId(id);
                cardCouponCode.setUseStatus(CouponCodeUseStatus.CANCELLED);
                cardCouponCodeMapper.updateByPrimaryKeySelective(cardCouponCode);
            }
        } else if (num > 0 && num > info.getNotUsedNum()){
            throw new BusinessException(ResultCode.COUPON_FORBID_REFUND);
        }
        //退款订单关联卡券改为未使用
        if (CollectionUtils.isNotEmpty(info.getDetail().getCouponCodeIds())) {
            for (Long couponCodeId : info.getDetail().getCouponCodeIds()) {
                cardCouponCodeService.updateNotUsedCouponCode(couponCodeId);
            }
        }
        //该订单改为退货状态
        PosSaleDetail posSaleDetail = new PosSaleDetail();
        posSaleDetail.setId(info.getDetail().getId());
        posSaleDetail.setRefundTime(new Date());
        posSaleDetail.setBalance(info.getDetail().getBalance().subtract(refundAmount));
        int affectNum = cardPosSaleDetailMapper.updateByPrimaryKeySelective(posSaleDetail);

        return new ResultData(new UpdateResult(affectNum));
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

    private CouponLaunchBetweenInfo preRefundVerification(String orderCode) {
        PosSaleDetailWithCouponCode detail = cardPosSaleDetailMapper.selectByOrderCode(orderCode);
        //找不到订单，可以退款
        if (detail == null) {
            CouponLaunchBetweenInfo info = new CouponLaunchBetweenInfo();
            info.setRefundType(RefundType.PREMIT_REFUND);
            return info;
        }

        int forbidRefundNum = cardCouponCodeMapper.countForbidRefundByOrderCode(orderCode);
        if (forbidRefundNum > 0) {
            CouponLaunchBetweenInfo info = new CouponLaunchBetweenInfo();
            info.setRefundType(RefundType.FORBIT_REFUND);
            return info;
        }
        CouponLaunchBetweenInfo info = clientCouponNumAndSumPaidInfo(detail.getShopId(), detail.getCreateTime(), detail.getClientId());
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

        //应该代金券拥有量
        int subCouponNum = getSubCouponNum(info, info.getDetail().getBalance());
        int diff = actualNumSubtractSubNum(info, subCouponNum);
        if (diff > info.getNotUsedNum() ) {
            info.setRefundType(RefundType.FORBIT_REFUND);
            return info;
        }
        //订单是否关联卡券
        if (CollectionUtils.isNotEmpty(info.getDetail().getCouponCodeIds())) {
            info.setRefundType(RefundType.PERMIT_REFUND_FULL_ORDER);
        } else {
            info.setRefundType(RefundType.PREMIT_REFUND);
        }

        return info;
    }

    /**
     *
     * 返回一个用户在一个投放期间，订单的商户要在商户触发范围内，已使用代金券的和未使用代金券的数量，期间所有订单的总额,还有卡券的信息
     */

    private CouponLaunchBetweenInfo clientCouponNumAndSumPaidInfo(Long shopId,Date date, Long clientId) {
        CouponLaunchBetweenInfo info = cardCouponCodeMapper.selectInfoFromLaunchBetween(shopId, date);
        if (info == null) {
            return null;
        }
        BigDecimal sumPaid = cardCouponCodeMapper.selectSumPaidFromLaunchBetween(date, clientId);
        info.setSumPaid(sumPaid);
        //在条件投放期间内，卡券的使用和未使用的数量
        CouponLaunchBetweenInfo couponNumInfo = cardCouponCodeMapper.selectCouponNumFromLaunchBetween(date, clientId);
        info.setUsedNum(couponNumInfo.getUsedNum());
        info.setNotUsedNum(couponNumInfo.getNotUsedNum());
        return info;
    }

    /**
     * 会员实际拥有代金券与应该拥有代金券数量的差
     * @param info
     * @param subCouponNum 应该拥有卡券数量
     * @return
     */
    private int actualNumSubtractSubNum(CouponLaunchBetweenInfo info, int subCouponNum) {

        //实际代金券拥有量
        int actualCouponNum = info.getNotUsedNum() + info.getUsedNum();
        return actualCouponNum - subCouponNum;
    }
    /**
     * 算出应该给一个用户多少卡券。
     * 如果不是退款，新入订单的话，refundAmount = 0
     * @param info
     * @param refundAmount
     * @return
     */
    private int getSubCouponNum(CouponLaunchBetweenInfo info, BigDecimal refundAmount) {
        return (info.getSumPaid().subtract(refundAmount).divide(info.getConditionAmount())).intValue();
    }

    /**
     * 返回应发代金券的数量
     * @return
     */
    private int receiveCashCouponNum(CouponLaunchBetweenInfo info, Long clientId) throws ParseException {
        int receiveNum = 0;
        int subCouponNum= getSubCouponNum(info, BigDecimal.ZERO);
        //会员实际拥有代金券与应该拥有代金券数量的差
        int diff = actualNumSubtractSubNum(info, subCouponNum);
        //1.实际数量比应发数量小，应该发券
        //2.检查当天领取的代金券是否达到该投放限制的每日最大获取量
        if (diff < 0) {
            Date start = DateTimeUtil.parseYYYYMMDD(DateTimeUtil.formatYYYYMMDD(new Date()));
            Date end = DateTimeUtil.addDays(start, 1);
            int cashCouponNum = cardCouponCodeMapper.todayConditionCouponCodeNum(info.getLaunchId(), clientId, start, end);
            //每日限制数量 - 当日已领取代金券数量
            int todayLimitNum = info.getMaxReceive() - cashCouponNum;
            if (todayLimitNum > 0) {
                //发卡数量
                if ((todayLimitNum + diff) >= 0) {
                    return -diff;
                } else {
                    return todayLimitNum;
                }

            }
        }
        return receiveNum;
    }

    public static void main(String[] args) {
        BigDecimal b0 = BigDecimal.ZERO;
        BigDecimal b1 = new BigDecimal("0.0");
        System.out.println(b1.equals(b0));
    }

}
