package com.joycity.joyclub.apiback.controller.card_coupon;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;
import static com.joycity.joyclub.commons.constant.ResultCode.ERR_IMPORT_EXCEL;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.modal.vo.card_coupon.updateCardCouponVO;
import com.joycity.joyclub.apiback.util.ExcelToBeanParser;
import com.joycity.joyclub.card_coupon.modal.CreateCouponInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;
import com.joycity.joyclub.card_coupon.service.CardCouponService;
import com.joycity.joyclub.card_coupon.service.ThirdpartyCouponCodeService;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class CardCouponController extends BaseUserSessionController {

    @Autowired
    private CardCouponService cardCouponService;
    @Autowired
    private ThirdpartyCouponCodeService thirdpartyCouponCodeService;

    @RequestMapping(value = "/card/coupon/{id}", method = RequestMethod.GET)
    public ResultData getCardCoupon(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponService.getCardCouponById( id);
    }

    @RequestMapping(value = "/card/coupon/{id}", method = RequestMethod.POST)
    public ResultData updateCardCoupon(@PathVariable Long id, @RequestBody updateCardCouponVO vo, HttpSession session) {
        checkProjectUser(session);
        CardCoupon cardCoupon = new CardCoupon();
        cardCoupon.setId(id);
        cardCoupon.setName(vo.getName());
        cardCoupon.setInfo(vo.getInfo());
        cardCoupon.setDisplayWeight(vo.getDisplayWeight());
        cardCoupon.setPortrait(vo.getPortrait());
        return cardCouponService.updateCardCoupon(cardCoupon);
    }

    @RequestMapping(value = "/card/coupons", method = RequestMethod.GET)
    public ResultData getCardCoupons(@RequestParam(required = false) String name, @RequestParam(required = false) Integer type, PageUtil pageUtil, HttpSession session) {
        SysUser sysUser = checkProjectUser(session);
        return cardCouponService.getListByNameAndType(sysUser.getInfoId(), name, type, pageUtil);
    }

    @RequestMapping(value = "/card/coupon/{id}/delete", method = RequestMethod.POST)
    public ResultData deleteById(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponService.deleteCardCoupon(id);
    }

    @RequestMapping(value = "/card/coupon", method = RequestMethod.POST)
        public ResultData createCardCoupon(@Validated @RequestBody CreateCouponInfo info, HttpSession session) {
        SysUser user = checkProjectUser(session);
        info.setProjectId(user.getInfoId());
        return cardCouponService.createCardCoupon(info);
    }

    @RequestMapping(value = "/card/thirdparty/coupon/codes/excel", method = {RequestMethod.POST})
    public ResultData importCodesFromExcel(@RequestParam final MultipartFile file,
                                           @RequestParam Long thirdpartyShopId,
                                           HttpSession httpSession) {

        checkProjectUser(httpSession);
        List<List<String>> list;
        try {
            list = ExcelToBeanParser.loadDataFromExcel(file.getInputStream(), file.getOriginalFilename(), 0);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ERR_IMPORT_EXCEL, "导入excel失败");
        }

        return thirdpartyCouponCodeService.createThirdpartyCouponCode(list, thirdpartyShopId);

    }
}
