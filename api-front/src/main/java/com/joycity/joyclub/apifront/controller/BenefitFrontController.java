package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.BenefitService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.sun.org.apache.xml.internal.utils.SuballocatedByteVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;
import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID_REQUEST_PARAM;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 权益
 * Created by CallMeXYZ on 2017/4/5.
 */
@RestController
@RequestMapping(URL_API_FRONT + "/benefit")
public class BenefitFrontController {
    @Autowired
    BenefitService benefitService;

    /**
     * 某个项目中，某个会员能领取的卡券
     * 按卡券投放时间倒序
     *
     * @param clientId 如果不给clientId，就返回该项目所有的卡券，业务逻辑处理不同
     * @return
     */
    @RequestMapping(value = "/coupons", method = GET)
    public ResultData getCouponsClientCanReceive(
            @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
            @RequestParam(required = false) Long clientId,
            PageUtil pageUtil
    ) {
        return benefitService.getCoupons(projectId, clientId, pageUtil);
    }

    /**
     * 某个会员的领取过的、未核销的、能使用的卡券
     * 按领取时间倒序
     */
    @RequestMapping(value = "/coupons/client/{clientId}", method = GET)
    public ResultData getCouponsOfClient(
            @PathVariable Long clientId,
            PageUtil pageUtil
    ) {
        return benefitService.getClientCoupons(clientId, pageUtil);
    }

    @RequestMapping(value = "/coupon/{id}/receive", method = POST)
    public ResultData receiveCoupon(
            @PathVariable Long id,
            @RequestParam(required = false) Long subProjectId,
            @RequestParam Long clientId

    ) {
        return benefitService.receiveCoupon(id, clientId, subProjectId);
    }

}
