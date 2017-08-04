package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.card_coupon.service.CardCouponOrderService;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.we_chat.util.WechatXmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by fangchen.chai on 2017/8/2
 */
@RestController
@RequestMapping(URL_API_FRONT + "/card/coupon/")
public class CardCouponOrderController {

    @Autowired
    private CardCouponOrderService couponOrderService;
    @Autowired
    private CardCouponCodeService couponCodeService;
    @Autowired
    ClientTokenService clientTokenService;
    private Log logger = LogFactory.getLog(ProductOrderFrontController.class);

    /**
     * 微信下单,
     * 如果需要支付金钱，返回微信支付参数
     * 如果不需要，扣分后直接返回，
     *
     * @return
     */
    @GetMapping(value = "/order/we_chat")
    public ResultData orderForWeChat(@CookieValue(Global.COOKIE_TOKEN) String token,
                                     @RequestParam Long launchId,
                                     @RequestParam Boolean moneyOrPoint) {
        return couponOrderService.orderForWeChat(clientTokenService.getIdOrThrow(token), launchId, moneyOrPoint);
    }

    /**
     * 支付宝下单 ，返回form参数 其他参照微信支付
     */
    @GetMapping(value = "/order/ali")
    public ResultData orderForAli(@CookieValue(Global.COOKIE_TOKEN) String token,
                                  @RequestParam Long launchId,
                                  @RequestParam Boolean moneyOrPoint) {
        return couponOrderService.orderForAli(clientTokenService.getIdOrThrow(token), launchId, moneyOrPoint);
    }

    /**
     * 微信回调
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "pay/we_chat/notify", method = {POST, GET})
    public void weChatPayNotify(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(Global.LOGGER_HEADER + " weChat notify");
        try {

            Map<String, String> params = WechatXmlUtil.xmlToMap(request
                    .getInputStream());

            String tradeStatus = params.get("result_code");
            if (tradeStatus != null && tradeStatus.equals("SUCCESS")) {
                //注意out_trade_no 是商户的订单号，因为商户对于微信是out。
                //transaction_id 是微信订单号，对于我们来说是outCode
                String outCode = params.get("transaction_id");
                String code = params.get("out_trade_no");
                couponOrderService.notifyPayed(code, outCode);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("return_code", "SUCCESS");
            map.put("return_msg", "OK");
            response.getWriter().write(WechatXmlUtil.mapToXml(map));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * ali回调
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "pay/ali/notify", method = {POST, GET})
    public void aliNotify(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(Global.LOGGER_HEADER + " aliPay notify");
        String tradeStatus = request.getParameter("trade_status");
        String refundStatus = request.getParameter("refund_status");
        String gmtRefund = request.getParameter("gmt_refund");
        if (tradeStatus.equals("TRADE_SUCCESS")) {
            if (StringUtils.isEmpty(gmtRefund)) {
                String outCode = request.getParameter("trade_no");
                String code = request.getParameter("out_trade_no");
                couponOrderService.notifyPayed(code, outCode);
            }
        }
        try {
            response.getWriter().write("success");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
