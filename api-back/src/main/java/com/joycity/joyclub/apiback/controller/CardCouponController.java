package com.joycity.joyclub.apiback.controller;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

import javax.servlet.http.HttpSession;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.card_coupon.modal.CreateCouponInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;
import com.joycity.joyclub.card_coupon.service.CardCouponService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class CardCouponController extends BaseUserSessionController {

    @Autowired
    private CardCouponService cardCouponService;

    @RequestMapping(value = "/card/coupon/{id}", method = RequestMethod.GET)
    public ResultData getCardCoupon(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponService.getCardCouponById(id);
    }

    @RequestMapping(value = "/card/coupon/{id}", method = RequestMethod.POST)
    public ResultData updateCardCoupons(@PathVariable Long id, CardCoupon cardCoupon, HttpSession session) {
        checkProjectUser(session);
        cardCoupon.setId(id);
        return cardCouponService.updateCardCoupon(cardCoupon);
    }

    @RequestMapping(value = "/card/coupons", method = RequestMethod.GET)
    public ResultData getCardCoupons(@RequestParam(required = false) String name, @RequestParam(required = false) Integer type, PageUtil pageUtil, HttpSession session) {
        checkProjectUser(session);
        return cardCouponService.getListByNameAndType(name, type, pageUtil);
    }

    @RequestMapping(value = "/card/coupon/{id}/delete", method = RequestMethod.POST)
    public ResultData deleteById(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponService.deleteCardCoupon(id);
    }

    @RequestMapping(value = "/card/coupon", method = RequestMethod.POST)
    public ResultData createCardCoupon(CreateCouponInfo info, HttpSession session) {
        SysUser user = checkProjectUser(session);
        info.setProjectId(user.getInfoId());
        return cardCouponService.createCardCoupon(info);
    }
}
