package com.joycity.joyclub.apiback.controller.card_coupon;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.card_coupon.constant.CouponCodeUseStatus;
import com.joycity.joyclub.card_coupon.mapper.CardCouponCodeMapper;
import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCode;
import com.joycity.joyclub.card_coupon.service.CardCouponCodeService;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
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
    @Autowired
    private CardCouponCodeMapper couponCodeMapper;

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

    @RequestMapping(value = "card/coupon/code/{id}/check", method = RequestMethod.POST)
    public ResultData checkCouponCode(@PathVariable Long id, HttpSession session) {

        CardCouponCode cardCouponCodeDb = couponCodeMapper.selectByPrimaryKey(id);
        String errorText = null;
        if (cardCouponCodeDb == null) {
            errorText = "该卡券号不存在";
        } else if (!cardCouponCodeDb.getUseStatus().equals(CouponCodeUseStatus.NOT_USED)) {
            errorText = "该卡券号已使用或已作废";
        }
        if (errorText != null) {
            throw new BusinessException(ResultCode.COUPON_CHECK_ERROR, errorText);
        }

        if (cardCouponCodeDb.getBelong().equals(-1L)) {
            checkProjectUser(session);
        } else {
            checkThirdPartyShopUser(session);
        }
        return cardCouponCodeService.checkCouponCode(id, null);
    }

}
