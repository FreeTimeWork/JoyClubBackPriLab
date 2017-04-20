package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.CartFrontService;
import com.joycity.joyclub.apifront.service.ClientFrontService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;
import static com.joycity.joyclub.commons.constants.Global.PLATFORM_ID_REQUEST_PARAM;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by CallMeXYZ on 2017/4/5.
 */
// TODO: 2017/4/6
@RestController
@RequestMapping(URL_API_FRONT)
public class CartFrontController {
    @Autowired
    CartFrontService cartFrontService;
    @Autowired
    ClientFrontService clientFrontService;
    /**
     * 获取某个客户的购物车列表
     */
    @RequestMapping(value = "/carts", method = GET)
    public ResultData getCartsByUserId(@RequestParam Long clientId,
                                       @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId) {
        Map<String,Object> result = new HashMap<>();
        result.put("list",cartFrontService.getCartList(projectId,clientId));
        result.put("point",clientFrontService.getPoint(clientId));
        return new ResultData(result);
    }

    /**
     * 添加商品属性至购物车，如果没有则新建
     */
    @RequestMapping(value = "/cart", method = POST)
    public ResultData addToCart(@RequestParam Long clientId,
                                    @RequestParam Long attrId,
                                    @RequestParam Integer num,
                                    @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId) {
        return cartFrontService.addToCart(projectId,clientId,attrId,num);
    }


    /**
     * 更改某个购物车商品的数量
     */
    @RequestMapping(value = "/cart/{cardId}", method = POST)
    public ResultData changeCartNum(@PathVariable Long cardId, @RequestParam int num) {
        // TODO: 2017/4/5 增加购物车是否是当前用户的验证
        return cartFrontService.setCartNum(cardId,num);
    }
    /**
     * 更改某个购物车商品的数量
     */
    @RequestMapping(value = "/cart/{cardId}/delete", method = POST)
    public ResultData deleteCart(@PathVariable Long cardId) {
        // TODO: 2017/4/5 增加购物车是否是当前用户的验证
        return cartFrontService.deleteCart(cardId);
    }
}
