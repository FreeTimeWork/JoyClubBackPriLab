package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.card_coupon.cache.CardCouponCodeCache;
import com.joycity.joyclub.card_coupon.constant.CouponLaunchPayType;
import com.joycity.joyclub.card_coupon.constant.CouponOrderConst;
import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponOrderMapper;
import com.joycity.joyclub.card_coupon.modal.CouponLaunchWithCoupon;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponOrder;
import com.joycity.joyclub.card_coupon.modal.order.PreCouponOrderResult;
import com.joycity.joyclub.card_coupon.service.CardCouponOrderService;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import com.joycity.joyclub.commons.modal.order.PreOrderResult;
import com.joycity.joyclub.we_chat.service.WxPayService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.joycity.joyclub.commons.constant.ResultCode.REQUEST_PARAMS_ERROR;

/**
 * Created by fangchen.chai on 2017/8/1
 */
@Service
public class CardCouponOrderServiceImpl implements CardCouponOrderService {
    /**
     * 微信支付
     */
    public static final Byte PAY_TYPE_WECHAT = 0;
    /**
     * 支付宝支付
     */
    public static final Byte PAY_TYPE_ALI = 1;

    @Value("${coupon.wxPay.notifyUrl}")
    private String WX_PAY_NOTIFY_URL;
    @Autowired
    private CardCouponLaunchMapper launchMapper;
    @Autowired
    private CardCouponCodeCache couponCodeCache;
    @Autowired
    private ClientUserMapper clientMapper;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardCouponOrderMapper couponOrderMapper;
    @Autowired
    private WxPayService wxPayService;

    @Override
    public ResultData orderForWeChat(Long clientId, Long couponLaunchId, Boolean moneyOrPoint) {
        return clientOrder(PAY_TYPE_WECHAT,clientId,couponLaunchId,moneyOrPoint);
    }

    @Override
    public ResultData orderForAli(Long clientId, Long couponLaunchId, Boolean moneyOrPoint) {
        return clientOrder(PAY_TYPE_ALI,clientId,couponLaunchId,moneyOrPoint);
    }

    @Override
    public void cancelOverTenMinsOrder() {

    }

    private ResultData clientOrder(Byte payType, Long clientId, Long launchId, Boolean moneyOrPoint) {
        CouponLaunchWithCoupon couponLaunchWithCoupon = launchMapper.selectLaunchWithCouponById(launchId);
        CardCouponOrder order = createOrder(payType, clientId, couponLaunchWithCoupon, moneyOrPoint);
        PreOrderResult preOrderResult = null;
        if (order.getMoneySum().compareTo(BigDecimal.ZERO) == 0) {
            if (payType.equals(PAY_TYPE_WECHAT)) {
                preOrderResult = wxPayService.getWechatPreOrderResult(couponLaunchWithCoupon.getProjectId(), clientId, order.getMoneySum().floatValue(), order.getCode(), WX_PAY_NOTIFY_URL);
            } else if (payType.equals(PAY_TYPE_ALI)) {
//                preOrderResult = getAliPreOrderResult(couponLaunchWithCoupon.getId(), order.getMoneySum(), order.getCode());
            }
        } else {
            //总金钱为0，积分处理
            if (order.getPointSum().compareTo(BigDecimal.ZERO) == 0) {
                //总积分也是0，为免费，直接完成订单
                couponOrderMapper.setOutPayCodeById(order.getId(), null);
            } else {
                clientService.addPoint(clientId, -order.getPointSum().doubleValue());
                couponOrderMapper.setOutPayCodeById(order.getId(), null);
            }
            // 订单已经算支付完成了
            preOrderResult = new PreOrderResult();
            preOrderResult.setIfUseMoney(false);
        }
        return new ResultData(preOrderResult);
    }



    /**
     * 生成支付宝相关参数
     * @param projectId
     * @param moneySum
     * @param code
     * @return
     */
    private PreCouponOrderResult getAliPreOrderResult(Long projectId, Float moneySum, String code) {
        return null;
    }
    private void setOrderPayed(String orderCode, String outOrderCode) {
        Long orderId = couponOrderMapper.selectIdByCode(orderCode);
        ThrowBusinessExceptionUtil.checkNull(orderId, "订单号不存在");
        couponOrderMapper.setPayedById(orderId);
        if (outOrderCode != null) {
            couponOrderMapper.setOutPayCodeById(orderId, outOrderCode);
        }

    }

    private CardCouponOrder createOrder(Byte payType, Long clientId, CouponLaunchWithCoupon couponLaunchWithCoupon, Boolean moneyOrPoint) {
        ThrowBusinessExceptionUtil.checkNull(couponLaunchWithCoupon,"该卡券不在投放期间或已下架");
        if (couponCodeCache.getInventory(couponLaunchWithCoupon.getId()) <= 0) {
            throw new BusinessException(REQUEST_PARAMS_ERROR, "卡券已售罄");
        }

        BigDecimal moneySum = BigDecimal.ZERO;
        BigDecimal pointSum = BigDecimal.ZERO;
        CardCouponOrder order = new CardCouponOrder();
        if (moneyOrPoint && couponLaunchWithCoupon.getPayType().equals(CouponLaunchPayType.MONEY)) {
            order.setPayType(payType);
            moneySum = couponLaunchWithCoupon.getPayAmount();
        } else if (!moneyOrPoint && couponLaunchWithCoupon.getPayType().equals(CouponLaunchPayType.POINT)){
            pointSum = couponLaunchWithCoupon.getPayAmount();
        } else {
            throw new BusinessException(REQUEST_PARAMS_ERROR, "支付类型不匹配");
        }
        //积分
        Integer nowPoint = clientMapper.getPoint(clientId);
        //积分不足，提前判断，避免生成订单
        if (pointSum.intValue() > nowPoint) {
            throw new BusinessException(ResultCode.VIP_POINT_NOT_ENOUGH, "积分不足");
        }

        order.setCode(createOrderCode());
        order.setProjectId(couponLaunchWithCoupon.getProjectId());
        order.setClientId(clientId);
        order.setLaunchId(couponLaunchWithCoupon.getId());
        order.setStatus(CouponOrderConst.STATUS_NOT_PAYED);
        order.setMoneySum(moneySum);
        order.setPointSum(pointSum);
        couponOrderMapper.insertSelective(order);
        return order;
    }


    private String createOrderCode(){
        String pix = String.valueOf(System.nanoTime());
        return pix + RandomStringUtils.random(8, "1234567890");
    }

    public static void main(String[] args) {
        String pix = String.valueOf(System.nanoTime());
        pix += RandomStringUtils.random(8, "1234567890");
        System.out.println(pix);
        method(-BigDecimal.ONE.doubleValue());
    }

    public static void method(Double d) {
        System.out.println(d);
    }
}
