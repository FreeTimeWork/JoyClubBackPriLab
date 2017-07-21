package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.card_coupon.service.CardThirdCouponCodeService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by fangchen.chai on 2017/7/20.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class CardThirdCouponCodeController extends BaseUserSessionController{

    @Autowired
    private CardThirdCouponCodeService cardThirdCouponCodeService;

    @RequestMapping(value = "/card/third/coupon/codes", method = RequestMethod.GET)
    public ResultData getListByFilter(@RequestParam(required = false) String couponName,
                                      @RequestParam(required = false) String thirdPartyShopName,
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

        if (thirdPartyShopName != null)
            thirdPartyShopName = "%" + thirdPartyShopName + "%";
        filter.setThirdPartyName(thirdPartyShopName);

        if (couponLaunchName != null)
            couponLaunchName = "%" + couponLaunchName + "%";
        filter.setCouponLaunchName(couponLaunchName);

        filter.setCode(code);
        filter.setTel(tel);
        return cardThirdCouponCodeService.getListByFilter(sysUser.getInfoId(), filter, pageUtil);
    }

}
