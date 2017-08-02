package com.joycity.joyclub.apiback.controller.card_coupon;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by fangchen.chai on 2017/7/20.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class CardSysCouponCodeController extends BaseUserSessionController{

    @Autowired
    private CardCouponCodeService cardCouponCodeService;

    @RequestMapping(value = "/card/sys/coupon/codes", method = RequestMethod.GET)
    public ResultData getListByFilter(@RequestParam(required = false) String couponName,
                                      @RequestParam(required = false) Byte couponType,
                                      @RequestParam(required = false) String couponLaunchName,
                                      @RequestParam(required = false) String code,
                                      @RequestParam(required = false) String tel,
                                      PageUtil pageUtil,
                                      HttpSession session){
        SysUser sysUser = checkProjectUser(session);
        ShowCouponCodeFilter filter = new ShowCouponCodeFilter();
        if (couponName != null)
            couponName = "%" + couponName + "%";

        filter.setCouponName(couponName);
        filter.setCouponType(couponType);
        if (couponLaunchName != null)
            couponLaunchName = "%" + couponLaunchName + "%";

        filter.setCouponLaunchName(couponLaunchName);
        filter.setCode(code);
        filter.setTel(tel);
        return cardCouponCodeService.getListByFilter(sysUser.getInfoId(), filter, pageUtil);
    }

    @RequestMapping(value = "card/sys/coupon/{id}/check", method = RequestMethod.POST)
    public ResultData checkCouponCode(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponCodeService.checkCouponCode(id, null);
    }

}