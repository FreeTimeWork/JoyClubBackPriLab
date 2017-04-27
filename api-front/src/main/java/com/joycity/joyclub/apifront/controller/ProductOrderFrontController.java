package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.ProductOrderFrontService;
import com.joycity.joyclub.apifront.util.WechatXmlUtil;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;
import static com.joycity.joyclub.commons.constant.Global.*;
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

    /**
     * @param type all:所有订单，notPayed:待支付订单，notSent:待发货，notReceived:待收货,completed:已完成（自提或者快递收货）
     * @return
     */
    @RequestMapping(value = "/orders", method = GET)
    public ResultData getOrders(
            @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
            @RequestParam Long clientId,
            @RequestParam String type,
            PageUtil pageUtil) {

        return productOrderService.getList(projectId, clientId, type, pageUtil);
    }

    /**
     * 提供商品属性相关数据
     *
     * @return
     */
    @RequestMapping(value = "/order/formdata", method = GET)
    public ResultData getListForPreOrder(@RequestParam Long clientId, @RequestParam String formData) {

        return productOrderService.getFormData(clientId, formData);
    }

    /**
     * 微信下单,
     * 如果需要支付金钱，返回微信支付参数
     * 如果不需要，扣分后直接返回，
     *
     * @param projectId
     * @param clientId
     * @param attrsJson     [{attrId,num,moneyOrPoint}]的json串
     * @param pickupOrPost
     * @param fromCart      是否是从购物车下的订单 如果是则需要对购物车减数量
     * @param postAddressId
     * @return
     */
    @RequestMapping(value = "/order/wechat", method = POST)
    public ResultData orderForWechat(
            @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
            @RequestParam Long clientId,
            @RequestParam String attrsJson,
            @RequestParam Boolean pickupOrPost,
            @RequestParam(required = false, defaultValue = "false") Boolean fromCart,
            @RequestParam(required = false) Long postAddressId) {
        return productOrderService.orderForWechat(projectId, clientId, attrsJson, pickupOrPost, postAddressId, fromCart);
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
    @RequestMapping(value = "/order/{id}/cancel", method = POST)
    public ResultData clientCancelOrder(
            @PathVariable Long id) {
        return productOrderService.clientCancelOrder(id);
    }
    @RequestMapping(value = "/order/pay/wechat/notify", method = {POST, GET})
    public void wechatPayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {

            Map<String, String> params = WechatXmlUtil.xmlToMap(request
                    .getInputStream());

            String tradeStatus = params.get("result_code");
            if (tradeStatus != null && tradeStatus.equals("SUCCESS")) {
                String tradeNo = params.get("transaction_id");
                String out_trade_no = params.get("out_trade_no");
                productOrderService.wechatNotifyPayed(tradeNo, out_trade_no);
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
}
