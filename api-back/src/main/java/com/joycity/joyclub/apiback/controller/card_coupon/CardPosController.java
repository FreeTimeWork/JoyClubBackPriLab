package com.joycity.joyclub.apiback.controller.card_coupon;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.vo.pos.*;
import com.joycity.joyclub.apiback.util.CheckSecretKey;
import com.joycity.joyclub.card_coupon.service.CardPosService;
import com.joycity.joyclub.card_coupon.service.impl.CardPosServiceImpl;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by fangchen.chai on 2017/7/24
 */
@RestController
@RequestMapping(URL_API_BACK+"/card/pos")
public class CardPosController extends BaseUserSessionController {
    private Log logger = LogFactory.getLog(CardPosController.class);
    @Value("${pos.secretKey}")
    private String secretKey;
    @Autowired
    private CardPosService cardPosService;

    /**
     * 得到某个会员在某个商户当前可用券
     */
    @PostMapping("/current/coupons")
    public ResultData getCardCoupons(@RequestHeader("sign") String sign,
                                     @RequestHeader("timestamp") Long timestamp,
                                     @RequestBody @Validated PosCurrentVO vo) {

        //TODO: cfc  projectId根据秘钥对应，得到到底是哪个项目的商家
        Long projectId = 1L;
        CheckSecretKey.checkSecretKey(vo,secretKey,sign,timestamp);
        return cardPosService.getCurrentCoupons(projectId, vo.getShopCode(), vo.getVipCode());
    }


    /**
     * 查看券号
     */
    @PostMapping("/coupon/examine")
    public ResultData posCheckCouponCode(@RequestHeader("sign") String sign,
                                         @RequestHeader("timestamp") Long timestamp,
                                         @RequestBody @Validated PosExamineVO vo) {
        CheckSecretKey.checkSecretKey(vo,secretKey,sign,timestamp);
        return cardPosService.examineCouponCode(vo.getVipCode(),vo.getShopCode(),vo.getCouponCode());
    }
    /**
     * 核销券号
     */
    @PostMapping("/check")
    public ResultData posCheck(@RequestHeader("sign") String sign,
                               @RequestHeader("timestamp") Long timestamp,
                               @RequestBody @Validated PosCheckVO vo) {
        CheckSecretKey.checkSecretKey(vo,secretKey,sign,timestamp);
        return cardPosService.posCheck(vo.getVipCode(), vo.getCouponCode(), vo.getOrderCode(), vo.getPayable(), vo.getShopCode());
    }

    /**
     * 取消核销
     */
    @PostMapping("/check/cancel")
    public ResultData posCheckCancel(@RequestHeader("sign") String sign,
                                     @RequestHeader("timestamp") Long timestamp,
                                     @RequestBody @Validated PosCheckCancelVO vo) {
        CheckSecretKey.checkSecretKey(vo,secretKey,sign,timestamp);
        return cardPosService.posCheckCancel(vo.getOrderCode(), vo.getCouponCode());
    }

    /**
     * 订单结果通知后处理
     */
    @PostMapping("/order/inform")
    public ResultData posOrderInform(@RequestHeader("sign") String sign,
                                     @RequestHeader("timestamp") Long timestamp,
                                     @RequestBody @Validated PosOrderInformVO vo) throws ParseException {
        //TODO: cfc  projectId根据秘钥对应，得到到底是哪个项目的商家
        Long projectId = 1L;
        CheckSecretKey.checkSecretKey(vo,secretKey,sign,timestamp);
        return cardPosService.posOrderInform(projectId, vo.getVipCode(), vo.getOrderCode(), vo.getShopCode(), vo.getPayable(), vo.getPayment());
    }

    /**
     * 验证是否可以退货
     */
    @PostMapping("/refund/verify")
    public ResultData refundVerification(@RequestHeader("sign") String sign,
                                         @RequestHeader("timestamp") Long timestamp,
                                         @RequestBody @Validated PosRefundVerificationVO vo) throws ParseException {
        logger.info("refund-verify: "+vo);
        CheckSecretKey.checkSecretKey(vo,secretKey,sign,timestamp);
        return cardPosService.refundVerification(vo.getOrderCode());
    }

    /**
     * pos退货通知后处理
     */
    @PostMapping(value = "/refund")
    public ResultData refund(@RequestHeader("sign") String sign,
                             @RequestHeader("timestamp") Long timestamp,
                             @RequestBody @Validated PosRefundVO vo) {

        logger.info("refund: "+vo);
        CheckSecretKey.checkSecretKey(vo,secretKey,sign,timestamp);
        return cardPosService.refund(vo.getOrderCode(), vo.getRefundAmount());
    }

}
