package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.BenefitService;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID_REQUEST_PARAM;
import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;
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
    @Autowired
    ClientTokenService clientTokenService;

    /**
     * 某个项目中，某个会员能领取的卡券
     * 按卡券投放时间倒序
     *
     * @param token 如果不给token，就返回该项目所有的卡券，业务逻辑处理不同
     * @return
     */
    @RequestMapping(value = "/coupons", method = GET)
    public ResultData getCouponsClientCanReceive(
            @CookieValue(name = Global.COOKIE_TOKEN, required = false) String token,
            @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
            PageUtil pageUtil
    ) {
        Long clientId = token == null ? null : clientTokenService.getId(token);
        return benefitService.getCoupons(projectId, clientId, pageUtil);
    }

    /**
     * 某个会员的领取过的、未核销的、能使用的卡券
     * 按领取时间倒序
     */
    @RequestMapping(value = "/coupons/client", method = GET)
    public ResultData getCouponsOfClient(
            @CookieValue(Global.COOKIE_TOKEN) String token,
            PageUtil pageUtil
    ) {
        return benefitService.getClientCoupons(clientTokenService.getIdOrThrow(token), pageUtil);
    }

    @RequestMapping(value = "/coupon/{id}/receive", method = POST)
    public ResultData receiveCoupon(
            @CookieValue(Global.COOKIE_TOKEN) String token,
            @PathVariable Long id,
            @RequestParam(required = false) Long subProjectId

    ) {
        return benefitService.receiveCoupon(id, clientTokenService.getIdOrThrow(token), subProjectId);
    }

}
