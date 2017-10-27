package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.vo.cart.CartNumVO;
import com.joycity.joyclub.apifront.modal.vo.cart.CartVO;
import com.joycity.joyclub.apifront.service.CartFrontService;
import com.joycity.joyclub.client.service.ClientService;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID_REQUEST_PARAM;
import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by CallMeXYZ on 2017/4/5.
 */
// TODO: 2017/4/6
@RestController
@RequestMapping(URL_API_FRONT)
public class
CartFrontController {
    @Autowired
    CartFrontService cartFrontService;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientTokenService clientTokenService;
    /**
     * 获取某个客户的购物车列表
     */
    @RequestMapping(value = "/carts", method = GET)
    public ResultData getCartsByUserId(@CookieValue(Global.COOKIE_TOKEN) String token,
                                       @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId) {
        Long clientId = clientTokenService.getIdOrThrow(token);
        Map<String,Object> result = new HashMap<>();
        result.put("list",cartFrontService.getCartList(projectId,clientId));
        result.put("point", clientService.getPoint(clientId));
        return new ResultData(result);
    }

    /**
     * 添加商品属性至购物车，如果没有则新建
     */
    @RequestMapping(value = "/cart", method = POST)
    public ResultData addToCart(@CookieValue(Global.COOKIE_TOKEN) String token,
                                @RequestBody @Valid  CartVO cartVO) {
        return cartFrontService.addToCart(cartVO.getProjectId(), clientTokenService.getIdOrThrow(token), cartVO.getAttrId(), cartVO.getNum());
    }


    /**
     * 更改某个购物车商品的数量
     */
    @RequestMapping(value = "/cart/{cardId}", method = POST)
    public ResultData changeCartNum(@PathVariable Long cardId,
                                    @RequestBody CartNumVO vo) {
        // TODO: 2017/4/5 增加购物车是否是当前用户的验证
        return cartFrontService.setCartNum(cardId,vo.getNum());
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
