package com.joycity.joyclub.apiback.controller;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

import javax.servlet.http.HttpSession;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.card_coupon.service.CardCouponLaunchService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class CardCouponLaunchController extends BaseUserSessionController {

    @Autowired
    private CardCouponLaunchService cardCouponLaunchService;

    @RequestMapping(value = "/card/coupon/launch/{id}", method = RequestMethod.GET)
    public ResultData getCardCoupon(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return null;
    }

    @RequestMapping(value = "/card/coupon/launchs", method = RequestMethod.GET)
    public ResultData getCardCoupons(@RequestParam(required = false) String couponName,
                                     @RequestParam(required = false) Integer couponType,
                                     @RequestParam(required = false) Integer status,
                                     PageUtil pageUtil, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.getListByCouponNameAndCouponTypeAndStatus(couponName, couponType, status, pageUtil);
    }

    @RequestMapping(value = "/card/coupon/launch/{id}/delete", method = RequestMethod.POST)
    public ResultData deleteById(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.deleteCardCouponLaunch(id);
    }

    @RequestMapping(value = "/card/coupon/create", method = RequestMethod.POST)
    public ResultData createCardCoupon(CardCouponLaunch info, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.createCardCouponLaunch(info);
    }

    @RequestMapping(value = "/card/coupon/launch/{id}/review/permit", method = RequestMethod.POST)
    public ResultData permitLaunch(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.permitLaunch(id);
    }

    @RequestMapping(value = "/card/coupon/launch/{id}/review/reject", method = RequestMethod.POST)
    public ResultData rejectLaunch(@PathVariable Long id, @RequestParam String reviewInfo, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.rejectLaunch(id, reviewInfo);
    }

    @RequestMapping(value = "/card/coupon/launch/{id}/confirm", method = RequestMethod.POST)
    public ResultData confirmLaunch(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.confirmLaunch(id);
    }

    @RequestMapping(value = "/card/coupon/launch/{id}/forbid", method = RequestMethod.POST)
    public ResultData forbidLaunch(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.forbidLaunch(id);
    }
}
