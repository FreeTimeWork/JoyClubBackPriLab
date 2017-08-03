package com.joycity.joyclub.card_coupon.service.impl;

import com.joycity.joyclub.alipay.service.service.AliPayService;
import com.joycity.joyclub.card_coupon.cache.CardCouponCodeCache;
import com.joycity.joyclub.card_coupon.constant.CouponLaunchPayType;
import com.joycity.joyclub.card_coupon.constant.CouponLaunchType;
import com.joycity.joyclub.card_coupon.constant.CouponOrderConst;
import com.joycity.joyclub.card_coupon.mapper.CardCouponCodeMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponLaunchMapper;
import com.joycity.joyclub.card_coupon.mapper.CardCouponOrderMapper;
import com.joycity.joyclub.card_coupon.modal.CouponLaunchWithCoupon;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponOrder;
import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.card_coupon.service.CardCouponOrderService;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.commons.constant.LogConst;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import com.joycity.joyclub.commons.modal.order.PreOrderResult;
import com.joycity.joyclub.we_chat.service.WxPayService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.joycity.joyclub.commons.constant.ResultCode.REQUEST_PARAMS_ERROR;

/**
 * Created by fangchen.chai on 2017/8/1
 */
@Service
public class CardCouponOrderServiceImpl implements CardCouponOrderService {

    private Log logger = LogFactory.getLog(CardCouponOrderServiceImpl.class);
    private Log taskLogger = LogFactory.getLog(LogConst.LOG_TASK);

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
    @Value("${coupon.aliPay.notifyUrl}")
    private String ALI_PAY_NOTIFY_URL;
    @Autowired
    private CardCouponLaunchMapper launchMapper;
    @Autowired
    private CardCouponCodeMapper couponCodeMapper;
    @Autowired
    private CardCouponCodeService couponCodeService;
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
    @Autowired
    private AliPayService aliPayService;

    @Override
    public ResultData orderForWeChat(Long clientId, Long couponLaunchId, Boolean moneyOrPoint) {
        return clientOrder(PAY_TYPE_WECHAT,clientId,couponLaunchId,moneyOrPoint);
    }

    @Override
    public ResultData orderForAli(Long clientId, Long couponLaunchId, Boolean moneyOrPoint) {
        return clientOrder(PAY_TYPE_ALI,clientId,couponLaunchId,moneyOrPoint);
    }

    @Override
    public Boolean notifyPayed(String code, String outCode) {
        Long id = couponOrderMapper.selectIdByCode(code);
        if (id == null) {
            logger.error("卡券订单回调时发现我方单号不存在，返回值为：我方单号：" + code + " 对方单号：" + outCode);
            return false;
        }
        CardCouponOrder order = couponOrderMapper.selectByPrimaryKey(id);
        setOrderPayed(code, outCode);
        CardCouponLaunch launch = launchMapper.selectByPrimaryKey(order.getLaunchId());
        couponCodeService.sendCouponCode(order.getClientId(), order.getLaunchId(), launch.getCouponId());
        return true;
    }

    @Override
    public void cancelOverTenMinOrder() {
        // TODO: 2017/8/2 cfc 如果十分钟没有处理完，下一个十分钟定时器执行，会造成多加两次库存
        List<CardCouponOrder> orders = couponOrderMapper.getTenMinNotPayedOrder();
        for (CardCouponOrder order : orders) {
            couponOrderMapper.cancelOrder(order.getId());
            //cache库存恢复
            couponCodeCache.changeInventory(order.getLaunchId(), 1);
        }
        if (orders.size() > 0) {
            taskLogger.info("取消超时未支付卡券订单 " + orders.size() + " 个");
        }
    }

    private ResultData clientOrder(Byte payType, Long clientId, Long launchId, Boolean moneyOrPoint) {
        CouponLaunchWithCoupon couponLaunchWithCoupon = launchMapper.selectLaunchWithCouponById(launchId);

        if (couponCodeMapper.checkOnlineCouponCodeNum(launchId, clientId) > 0) {
            throw new BusinessException(REQUEST_PARAMS_ERROR, "卡券已领取");
        }

        //cache层发券
        if (!couponCodeCache.sendCouponCode(launchId)) {
            throw new BusinessException(REQUEST_PARAMS_ERROR, "卡券已售罄");
        }

        CardCouponOrder order;
        try {
            order = createOrder(payType, clientId, couponLaunchWithCoupon, moneyOrPoint);
        } catch (Exception e) {
            //创建订单失败，恢复库存。
            couponCodeCache.changeInventory(launchId,1);
            throw new BusinessException(REQUEST_PARAMS_ERROR, e.getMessage());
        }
        PreOrderResult preOrderResult = null;
        if (order.getMoneySum().compareTo(BigDecimal.ZERO) == 0) {
            if (payType.equals(PAY_TYPE_WECHAT)) {
                preOrderResult = wxPayService.getWechatPreOrderResult(couponLaunchWithCoupon.getProjectId(), clientId, order.getMoneySum().floatValue(), order.getCode(), WX_PAY_NOTIFY_URL);
            } else if (payType.equals(PAY_TYPE_ALI)) {
                preOrderResult = aliPayService.getAliPreOrderResult(couponLaunchWithCoupon.getId(), order.getMoneySum().floatValue(), order.getCode(), ALI_PAY_NOTIFY_URL);
            }
        } else {
            //总金钱为0，积分处理
            if (order.getPointSum().compareTo(BigDecimal.ZERO) > 0) {
                clientService.addPoint(clientId, -order.getPointSum().doubleValue());
            }
            couponOrderMapper.setPayedById(order.getId());
            // 订单已经算支付完成了
            preOrderResult = new PreOrderResult();
            preOrderResult.setIfUseMoney(false);
        }
        return new ResultData(preOrderResult);
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

}
