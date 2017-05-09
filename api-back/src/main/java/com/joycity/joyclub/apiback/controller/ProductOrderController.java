package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.service.ProductStoreOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Administrator on 2017/2/20.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ProductOrderController extends BaseUserSessionController {
    @Autowired
    ProductStoreOrderService productStoreOrderService;

    /**
     * @param receiveType 自提还是快递
     * @param status      订单状态
     * @param pageUtil
     * @param session
     * @return
     */
    @RequestMapping(value = "/product/orders",method = GET)
    public ResultData getList(
            @RequestParam(required = false) Byte receiveType,
            @RequestParam(required = false) Byte status,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            PageUtil pageUtil,
            HttpSession session
    ) {

        SysUser sysUser = checkStoreUser(session);
        return productStoreOrderService.getList(sysUser.getInfoId(), receiveType, status, code, name, phone, pageUtil);
    }

    @RequestMapping(value = "/product/order/{id}",method = GET)
    public ResultData getList(
            @PathVariable Long id,
            HttpSession session
    ) {

        checkStoreUser(session);
        return productStoreOrderService.getInfo(id);
    }

    /**
     * 商家确认自提
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "/product/order/{id}/selffetch",method = POST)
    public ResultData selfFetch(@PathVariable Long id, HttpSession session) {

        checkStoreUser(session);
        return productStoreOrderService.completeSelfFetch(id);
    }

    /**
     * 商家发货
     *
     * @return
     */
    @RequestMapping( value = "/product/order/{id}/delivery",method = POST)
    public ResultData delivery(@PathVariable Long id,
                               @RequestParam String deliveryCompany,
                               @RequestParam String deliveryCode,

                               HttpSession session) {

        checkStoreUser(session);
        return productStoreOrderService.completeDelivery(id, deliveryCompany, deliveryCode);
    }
}
