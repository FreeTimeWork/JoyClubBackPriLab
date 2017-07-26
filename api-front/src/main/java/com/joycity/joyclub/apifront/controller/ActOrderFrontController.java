package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.vo.act.ActOrderVO;
import com.joycity.joyclub.apifront.service.ActOrderFrontService;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @Autowired
    ActOrderFrontService orderService;
    @Autowired
    ClientTokenService clientTokenService;
    private Log logger = LogFactory.getLog(ActOrderFrontController.class);
    
    @RequestMapping(value = "/orders/act", method = GET)
    public ResultData getOrders(
            @CookieValue(Global.COOKIE_TOKEN) String token,
            @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
            @RequestParam(required = false) Byte status,
            PageUtil pageUtil) {

        return orderService.getList(projectId, clientTokenService.getIdOrThrow(token), status, pageUtil);
    }

    /**
     * 提供商品属性相关数据
     *
     * @return
     */
    @RequestMapping(value = "/order/act/formdata", method = GET)
    public ResultData getListForPreOrder(@CookieValue(Global.COOKIE_TOKEN) String token,
                                         @RequestParam Long attrId) {

        return orderService.getFormData(clientTokenService.getIdOrThrow(token), attrId);
    }

    /**
     * 微信下单,
     * 如果需要支付金钱，返回微信支付参数
     * 如果不需要，扣分后直接返回，
     */
    @RequestMapping(value = "/order/act/wechat", method = POST)
    public ResultData orderForWechat(
            @CookieValue(Global.COOKIE_TOKEN) String token,
            @Valid @RequestBody ActOrderVO orderVO
    )
 {
     return orderService.orderForWechat(orderVO.getProjectId(), orderVO.getSubProjectId(), clientTokenService.getIdOrThrow(token), orderVO.getAttrId(), orderVO.getMoneyOrPoint());
    }

    /**
     * 支付宝下单 ，返回form参数 其他参照微信支付
     */
    @RequestMapping(value = "/order/act/ali", method = POST)
    public ResultData orderForAli(
            @CookieValue(Global.COOKIE_TOKEN) String token,
            @Valid @RequestBody ActOrderVO orderVO) {
        return orderService.orderForAli(orderVO.getProjectId(), orderVO.getSubProjectId(), clientTokenService.getIdOrThrow(token), orderVO.getAttrId(), orderVO.getMoneyOrPoint());
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
