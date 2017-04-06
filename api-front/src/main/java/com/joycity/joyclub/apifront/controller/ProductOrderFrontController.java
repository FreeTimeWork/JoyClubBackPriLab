package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;

/**
 * 商品订单
 * Createdby CallMeXYZ on 2017/4/5.
 */
@RestController
@RequestMapping(URL_API_FRONT + "/product")
public class ProductOrderFrontController {
    /**
     * 获取某个用户的订单
     * 可以提供订单状态来查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ResultData getOrdersByUserId(@RequestParam Long id, PageUtil pageUtil, @RequestParam(required = false) Byte status) {
        return null;
    }

    /**
     * 订单生成
     * @param id
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResultData createOrderFromCard(@RequestParam Long[] id) {
        return null;
    }
}
