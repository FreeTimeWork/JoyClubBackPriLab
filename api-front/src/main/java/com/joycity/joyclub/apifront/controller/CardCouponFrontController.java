package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.vo.card_coupon.CouponFreeGetVO;
import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.card_coupon.service.CardCouponLaunchService;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by fangchen.chai on 2017/8/3
 */
@RestController
@RequestMapping(URL_API_FRONT + "/card/coupon")
public class CardCouponFrontController {

    @Autowired
    private ClientTokenService clientTokenService;
    @Autowired
    private CardCouponLaunchService launchService;
    @Autowired
    private CardCouponCodeService couponCodeService;

    /**
     * 某个项目中，某个会员能领取的卡券
     * 按权重，卡券投放时间倒序
     *
     * @return
     */
    @GetMapping("/launches")
    public ResultData getCouponsClientCanReceive(
            @CookieValue(name = Global.COOKIE_TOKEN, required = false) String token,
            @RequestParam Long projectId,
            @RequestParam(required = false) Byte couponType,
            PageUtil pageUtil) {

        Long clientId = token == null ? null : clientTokenService.getId(token);
        if (clientId == null) {
            return launchService.getVisitorVisibleListByCouponType(projectId, couponType, pageUtil);
        } else {
            return launchService.getClientVisibleListByCouponType(projectId, clientId, couponType, pageUtil);
        }
    }

    /**
     * 免费领取
     */
    @PostMapping("/free/receive")
    public ResultData freeReceiveCoupon(@CookieValue(Global.COOKIE_TOKEN) String token,
                                        @Valid @RequestBody CouponFreeGetVO vo) {

        return couponCodeService.freeReceiveCoupon(clientTokenService.getIdOrThrow(token), vo.getLaunchId());
    }
}
