package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.constant.ResultCode;
import com.joycity.joyclub.apifront.modal.base.ResultData;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by CallMeXYZ on 2017/4/5.
 */
// TODO: 2017/4/6
@RestController
@RequestMapping(URL_API_FRONT)
public class CartController {
    /**
     * 获取某个客户的购物车列表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/carts", method = GET)
    public ResultData getCartsByUserId(@RequestParam Long id) {
        return null;
    }


    /**
     * 更改某个购物车商品的数量
     *
     * @param cardId
     * @param num
     * @return
     */
    @RequestMapping(value = "/cart/{cardId}", method = POST)
    public ResultData changeCartNum(@PathVariable Long cardId, @RequestParam int num) {
        // TODO: 2017/4/5 增加购物车是否是当前用户的验证
        return null;
    }
}
