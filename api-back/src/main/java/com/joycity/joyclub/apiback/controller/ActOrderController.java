package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.act.service.ActOrderService;
import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.service.ProductStoreOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Administrator on 2017/2/20.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ActOrderController extends BaseUserSessionController {
    @Autowired
    ActOrderService actOrderService;

    /**
     * @param status      订单状态
     * @param pageUtil
     * @param session
     * @param actName 模糊查询
     * @return
     */
    @RequestMapping(value = "/act/orders",method = GET)
    public ResultData getList(
            @RequestParam(required = false) Byte status,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String actName,
            PageUtil pageUtil,
            HttpSession session
    ) {

        SysUser sysUser = checkStoreUser(session);
        return actOrderService.getList(sysUser.getInfoId(), status, code, name, phone,actName, pageUtil);
    }

  /*  @RequestMapping(value = "/act/order/{id}",method = GET)
    public ResultData getInfo(
            @PathVariable Long id,
            HttpSession session
    ) {

        checkStoreUser(session);
        return actOrderService.getInfo(id);
    }
*/

    /**
     * 商家发货
     *
     * @return
     */
    @RequestMapping( value = "/act/order/{id}/check",method = POST)
    public ResultData delivery(@PathVariable Long id,
                               HttpSession session) {

        checkStoreUser(session);
        return actOrderService.checkOrder(id);
    }
}
