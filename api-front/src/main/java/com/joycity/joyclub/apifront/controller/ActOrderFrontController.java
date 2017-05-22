package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.ActOrderFrontService;
import com.joycity.joyclub.apifront.service.ProductOrderFrontService;
import com.joycity.joyclub.apifront.util.WechatXmlUtil;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID_REQUEST_PARAM;
import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 * Created by Administrator on 2017/4/20.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class ActOrderFrontController {
    private Log logger = LogFactory.getLog(ActOrderFrontController.class);
    @Autowired
    ActOrderFrontService orderService;

 /**
     * @param
     * @return
     */
    @RequestMapping(value = "/orders/act", method = GET)
    public ResultData getOrders(
            @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
            @RequestParam Long clientId,
            @RequestParam(required = false) Byte status,
            PageUtil pageUtil) {

        return orderService.getList(projectId, clientId,status,  pageUtil);
    }

    /**
     * 提供商品属性相关数据
     *
     * @return
     */
    @RequestMapping(value = "/order/act/formdata", method = GET)
    public ResultData getListForPreOrder(@RequestParam Long clientId, @RequestParam Long attrId) {

        return orderService.getFormData(clientId, attrId);
    }

    /**
     * 微信下单,
     * 如果需要支付金钱，返回微信支付参数
     * 如果不需要，扣分后直接返回，
     *
     * @param projectId
     * @param clientId
     * @return
     */
    @RequestMapping(value = "/order/act/wechat", method = POST)
    public ResultData orderForWechat(
            @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
            @RequestParam(required = false) Long subProjectId,
            @RequestParam Long clientId,
            @RequestParam Long attrId,@RequestParam Boolean moneyOrPoint)
 {
        return orderService.orderForWechat(projectId,subProjectId, clientId, attrId,moneyOrPoint);
    }

    /**
     * 支付宝下单 ，返回form参数 其他参照微信支付
     */
    @RequestMapping(value = "/order/act/ali", method = POST)
    public ResultData orderForAli(
            @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
            @RequestParam(required = false) Long subProjectId,
            @RequestParam Long clientId,
            @RequestParam Long attrId,@RequestParam Boolean moneyOrPoint){
        return orderService.orderForAli(projectId,subProjectId, clientId,attrId,moneyOrPoint);
    }

/**
     * 对已下单未成功支付订单进行重新支付
     *
     * @return
     */
    @RequestMapping(value = "/order/act/{id}/wechat/repay", method = POST)
    public ResultData reorderForWechat(
            @PathVariable Long id) {
        return orderService.reorderForWechat(id);
    }
    /**
     * 对已下单未成功支付订单进行重新支付
     *
     * @return
     */
    @RequestMapping(value = "/order/act/{id}/ali/repay", method = POST)
    public ResultData reorderForAli(
            @PathVariable Long id) {
        return orderService.reorderForAli(id);
    }

    @RequestMapping(value = "/order/act/{id}/cancel", method = POST)
    public ResultData clientCancelOrder(
            @PathVariable Long id) {
        return orderService.clientCancelOrder(id);
    }
}
