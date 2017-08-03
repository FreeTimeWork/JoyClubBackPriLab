package com.joycity.joyclub.apiback.controller.card_coupon;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.vo.pos.PosCheckVO;
import com.joycity.joyclub.apiback.util.CheckSecretKey;
import com.joycity.joyclub.card_coupon.service.CardPosService;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by fangchen.chai on 2017/7/24
 */
@RestController
@RequestMapping(URL_API_BACK+"/card/pos")
public class CardPosController extends BaseUserSessionController {

    @Value("${pos.secretKey}")
    private String secretKey;
    @Autowired
    private CardPosService cardPosService;

    /**
     * 得到某个会员在某个商户当前可用券
     */
    @RequestMapping(value = "/current/coupons", method = RequestMethod.GET)
    public ResultData getCardCoupons(@RequestHeader("sign") String sign,
                                     @RequestHeader("timestamp") Long timestamp,
                                     @RequestParam String vipCode,
                                     @RequestParam String shopCode) {

        //TODO: cfc  projectId根据秘钥对应，得到到底是哪个项目的商家
        Long projectId = 1L;
        CheckSecretKey.checkSecretKey(secretKey,sign,timestamp);
        return cardPosService.getCurrentCoupons(projectId, shopCode, vipCode);
    }


    /**
     * 查看券号
     */
    @GetMapping(value = "/coupon/examine")
    public ResultData posCheckCouponCode(@RequestHeader("sign") String sign,
                                         @RequestHeader("timestamp") Long timestamp,
                                         @RequestParam String vipCode, @RequestParam String shopCode, @RequestParam String couponCode) {
        CheckSecretKey.checkSecretKey(secretKey,sign,timestamp);
        return cardPosService.examineCouponCode(vipCode,shopCode,couponCode);
    }
    /**
     * 核销券号
     */
    @GetMapping(value = "/check")
    public ResultData posCheck(@RequestHeader("sign") String sign,
                               @RequestHeader("timestamp") Long timestamp,
                               @RequestParam String vipCode,
                               @RequestParam String shopCode,
                               @RequestParam String couponCode,
                               @RequestParam String orderCode,
                               @RequestParam BigDecimal payable) {
        CheckSecretKey.checkSecretKey(secretKey,sign,timestamp);
        return cardPosService.posCheck(vipCode, couponCode, orderCode, payable, shopCode);
    }

    /**
     * 取消核销
     */
    @GetMapping(value = "/check/cancel")
    public ResultData posCheckCancel(@RequestHeader("sign") String sign,
                                     @RequestHeader("timestamp") Long timestamp,
                                     @RequestParam String orderCode) {
        CheckSecretKey.checkSecretKey(secretKey,sign,timestamp);
        return cardPosService.posCheckCancel(orderCode);
    }

    /**
     * 订单结果通知后处理
     */
    @GetMapping(value = "/order/inform")
    public ResultData posOrderInform(@RequestHeader("sign") String sign,
                                     @RequestHeader("timestamp") Long timestamp,
                                     @RequestParam String vipCode,
                                     @RequestParam String orderCode,
                                     @RequestParam String shopCode,
                                     @RequestParam BigDecimal payable,
                                     @RequestParam BigDecimal payment) throws ParseException {
        //TODO: cfc  projectId根据秘钥对应，得到到底是哪个项目的商家
        Long projectId = 1L;
        CheckSecretKey.checkSecretKey(secretKey,sign,timestamp);
        return cardPosService.posOrderInform(projectId, vipCode, orderCode, shopCode, payable, payment);
    }

    /**
     * 验证是否可以退货
     */
    @RequestMapping(value = "/refund/verify", method = RequestMethod.GET)
    public ResultData refundVerification(@RequestHeader("sign") String sign,
                                         @RequestHeader("timestamp") Long timestamp,
                                         @RequestParam String orderCode,@RequestParam BigDecimal refundAmount, @RequestParam(required = false) String shopCode) {
        CheckSecretKey.checkSecretKey(secretKey,sign,timestamp);
        return cardPosService.refundVerification(orderCode,refundAmount);
    }

    /**
     * pos退货通知后处理
     */
    @RequestMapping(value = "/refund", method = RequestMethod.GET)
    public ResultData refund(@RequestHeader("sign") String sign,
                             @RequestHeader("timestamp") Long timestamp,
                             @RequestParam String orderCode, @RequestParam BigDecimal refundAmount, @RequestParam(required = false) String shopCode) {
        CheckSecretKey.checkSecretKey(secretKey,sign,timestamp);
        return cardPosService.refund(orderCode,refundAmount);
    }

}
