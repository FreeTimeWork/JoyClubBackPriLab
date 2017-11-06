package com.joycity.joyclub.apiback.controller.card_coupon;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.card_coupon.service.CardCouponService;
import com.joycity.joyclub.card_coupon.service.CardThirdCouponCodeService;
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
public class CardThirdCouponCodeController extends BaseUserSessionController{

    @Autowired
    private CardThirdCouponCodeService cardThirdCouponCodeService;
    @Autowired
    private CardCouponCodeService cardCouponCodeService;

    /**
     * 所有该项目下的第三方卡券号
     * @param couponName
     * @param thirdPartyShopName
     * @param couponLaunchName
     * @param code
     * @param tel
     * @param pageUtil
     * @param session
     * @return
     */
    @RequestMapping(value = "/card/third/coupon/codes", method = RequestMethod.GET)
    public ResultData getListByFilter(@RequestParam(required = false) String couponName,
                                      @RequestParam(required = false) String thirdPartyShopName,
                                      @RequestParam(required = false) String couponLaunchName,
                                      @RequestParam(required = false) String code,
                                      @RequestParam(required = false) String tel,
                                      @RequestParam Long infoId,
                                      PageUtil pageUtil,
                                      HttpSession session){
//        SysUser sysUser = checkProjectUser(session);
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
        return cardThirdCouponCodeService.getListByFilter(infoId, filter, pageUtil);
    }

    /**
     * 该第三方商户自己的卡券列表
     * @param couponName
     * @param couponLaunchName
     * @param code
     * @param tel
     * @param pageUtil
     * @param session
     * @return
     */
    @RequestMapping(value = "/card/third/shop/coupon/codes", method = RequestMethod.GET)
    public ResultData getListByFilter(@RequestParam(required = false) String couponName,
                                      @RequestParam(required = false) String couponLaunchName,
                                      @RequestParam(required = false) String code,
                                      @RequestParam(required = false) String tel,
                                      @RequestParam Long infoId,
                                      PageUtil pageUtil,
                                      HttpSession session){
//        SysUser sysUser = checkThirdPartyShopUser(session);
        ShowCouponCodeFilter filter = new ShowCouponCodeFilter();

        if (couponName != null)
            couponName = "%" + couponName + "%";
        filter.setCouponName(couponName);

        if (couponLaunchName != null)
            couponLaunchName = "%" + couponLaunchName + "%";
        filter.setCouponLaunchName(couponLaunchName);

        filter.setCode(code);
        filter.setTel(tel);
        filter.setThirdPartyShopId(infoId);
        return cardThirdCouponCodeService.getListByFilter(null, filter, pageUtil);
    }

}
