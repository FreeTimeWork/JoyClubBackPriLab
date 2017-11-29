package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.vo.product.order.ProductOrderFormDataVO;
import com.joycity.joyclub.apifront.modal.vo.product.order.ProductOrderVO;
import com.joycity.joyclub.apifront.service.ProductOrderFrontService;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.mapper.ProductOrderMapper;
import com.joycity.joyclub.we_chat.util.WechatXmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID_REQUEST_PARAM;
import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Administrator on 2017/4/20.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class ProductOrderFrontController {
    @Autowired
    ProductOrderFrontService productOrderService;
    @Autowired
    ProductOrderMapper productOrderMapper;
    @Autowired
    ClientTokenService clientTokenService;
    private Log logger = LogFactory.getLog(ProductOrderFrontController.class);

    /**
     * @param type all:所有订单，notPayed:待支付订单，notSent:待发货，notReceived:待收货,completed:已完成（自提或者快递收货）
     * @return
     */
    @RequestMapping(value = "/orders", method = GET)
    public ResultData getOrders(
            @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
//            @CookieValue(Global.COOKIE_TOKEN) String token,
            @RequestParam String type,
            PageUtil pageUtil) {

        return productOrderService.getList(projectId, /*clientTokenService.getIdOrThrow(token)*/1795L, type, pageUtil);
    }

    /**
     * 订单详情
     */
    @GetMapping("/order/{id}")
    public ResultData getOrder(@PathVariable("id") Long id) {

        return new ResultData(productOrderMapper.selectMyOrderDetail(id));
    }

    /**
     * 提供商品属性相关数据
     *
     * @return
     */
    @RequestMapping(value = "/order/formdata", method = POST)
    public ResultData getListForPreOrder(@CookieValue(Global.COOKIE_TOKEN) String token, @Valid @RequestBody ProductOrderFormDataVO vo) {

        return productOrderService.getFormData(clientTokenService.getIdOrThrow(token), vo.getAttrIds());
    }

    /**
     * 微信下单,
     * 如果需要支付金钱，返回微信支付参数
     * 如果不需要，扣分后直接返回，
     *
     * @return
     */
    @RequestMapping(value = "/order/wechat", method = POST)
    public ResultData orderForWechat(@CookieValue(Global.COOKIE_TOKEN) String token, @Valid @RequestBody ProductOrderVO vo) {
        return productOrderService.orderForWechat(clientTokenService.getIdOrThrow(token), vo);
    }

    /**
     * 支付宝下单 ，返回form参数 其他参照微信支付
     */
    @RequestMapping(value = "/order/ali", method = POST)
    public ResultData orderForAli(@CookieValue(Global.COOKIE_TOKEN) String token, @Valid @RequestBody ProductOrderVO vo) {
        return productOrderService.orderForAli(clientTokenService.getIdOrThrow(token), vo);
    }

    /**
     * 对已下单未成功支付订单进行重新支付
     *
     * @return
     */
    @RequestMapping(value = "/order/{id}/wechat/repay", method = POST)
    public ResultData reorderForWechat(
            @PathVariable Long id) {
        return productOrderService.reorderForWechat(id);
    }

    /**
     * 对已下单未成功支付订单进行重新支付
     *
     * @return
     */
    @RequestMapping(value = "/order/{id}/ali/repay", method = POST)
    public ResultData reorderForAli(
            @PathVariable Long id) {
        return productOrderService.reorderForAli(id);
    }

    @RequestMapping(value = "/order/{id}/cancel", method = POST)
    public ResultData clientCancelOrder(
            @PathVariable Long id) {
        return productOrderService.clientCancelOrder(id);
    }

    /**
     * 活动和商品订单使用一个回调
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/order/pay/wechat/notify", method = {POST, GET})
    public void wechatPayNotify(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(Global.LOGGER_HEADER + " wechat notify");
        try {

            Map<String, String> params = WechatXmlUtil.xmlToMap(request
                    .getInputStream());

            String tradeStatus = params.get("result_code");
            if (tradeStatus != null && tradeStatus.equals("SUCCESS")) {
                //注意out_trade_no 是商户的订单号，因为商户对于微信是out。
                //transaction_id 是微信订单号，对于我们来说是outCode
                String outCode = params.get("transaction_id");
                String code = params.get("out_trade_no");
                productOrderService.wechatNotifyPayed(code, outCode);
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
     * 活动和商品订单使用一个回调
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/order/pay/ali/notify", method = {POST, GET})
    public void aliNotify(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(Global.LOGGER_HEADER + " alipay notify");
        String tradeStatus = request.getParameter("trade_status");
        String refundStatus = request.getParameter("refund_status");
        String gmtRefund = request.getParameter("gmt_refund");
        if (tradeStatus.equals("TRADE_SUCCESS")) {
            if (StringUtils.isEmpty(gmtRefund)) {
                String outCode = request.getParameter("trade_no");
                String code = request.getParameter("out_trade_no");
                productOrderService.aliNotifyPayed(code, outCode);
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
