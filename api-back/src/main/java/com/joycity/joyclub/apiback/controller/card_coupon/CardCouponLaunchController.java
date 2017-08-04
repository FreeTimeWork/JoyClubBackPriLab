package com.joycity.joyclub.apiback.controller.card_coupon;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;
import static com.joycity.joyclub.commons.constant.ResultCode.ERR_IMPORT_EXCEL;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.vo.reviewInfoVO;
import com.joycity.joyclub.apiback.util.ExcelToBeanParser;
import com.joycity.joyclub.card_coupon.modal.CreateCouponLaunchInfo;
import com.joycity.joyclub.card_coupon.service.CardCouponLaunchService;
import com.joycity.joyclub.card_coupon.service.CardVipBatchService;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class CardCouponLaunchController extends BaseUserSessionController {

    @Autowired
    private CardCouponLaunchService cardCouponLaunchService;
    @Autowired
    private CardVipBatchService cardVipBatchService;

    @RequestMapping(value = "/card/coupon/launch", method = RequestMethod.POST)
    public ResultData createCardCoupon(@RequestBody CreateCouponLaunchInfo info, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.createCardCouponLaunch(info);
    }

    @RequestMapping(value = "/card/coupon/launch/{id}/delete", method = RequestMethod.POST)
    public ResultData deleteById(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.deleteCardCouponLaunch(id);
    }

    @RequestMapping(value = "/card/coupon/launch/{id}", method = RequestMethod.GET)
    public ResultData getCardCouponLaunche(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.getCardCouponLaunchById(id);
    }

    @RequestMapping(value = "/card/coupon/launches", method = RequestMethod.GET)
    public ResultData getCardCouponLaunches(@RequestParam(required = false) String couponName,
                                            @RequestParam(required = false) Integer couponType,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) Integer type,
                                            @RequestParam(required = false) Integer status,
                                            PageUtil pageUtil, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.getListByCouponNameAndCouponTypeAndStatus(couponName, couponType, name, type, status, pageUtil);
    }

    @RequestMapping(value = "/card/coupon/launch/{id}/review/permit", method = RequestMethod.POST)
    public ResultData permitLaunch(@PathVariable Long id, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.permitLaunch(id);
    }

    @RequestMapping(value = "/card/coupon/launch/{id}/review/reject", method = RequestMethod.POST)
    public ResultData rejectLaunch(@PathVariable Long id, @Validated @RequestBody reviewInfoVO vo, HttpSession session) {
        checkProjectUser(session);
        return cardCouponLaunchService.rejectLaunch(id, vo.getInfo());
    }

    @RequestMapping(value = "/card/coupon/launch/{id}/confirm", method = RequestMethod.POST)
    public ResultData confirmLaunch(@PathVariable Long id, HttpSession session) throws SchedulerException {
        checkProjectUser(session);
        return cardCouponLaunchService.confirmLaunch(id);
    }

    @RequestMapping(value = "/card/coupon/launch/{id}/forbid", method = RequestMethod.POST)
    public ResultData forbidLaunch(@PathVariable Long id, HttpSession session) throws SchedulerException {
        checkProjectUser(session);
        return cardCouponLaunchService.forbidLaunch(id);
    }

    @RequestMapping(value = "/card/coupon/launch/vip/codes/excel", method = {RequestMethod.POST})
    public ResultData importCodesFromExcel(@RequestParam("file") final MultipartFile file,
                                           HttpSession httpSession) {

        checkProjectUser(httpSession);
        List<List<String>> list;
        try {
            list = ExcelToBeanParser.loadDataFromExcel(file.getInputStream(), file.getOriginalFilename(), 0);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ERR_IMPORT_EXCEL, "导入excel失败");
        }

        return cardVipBatchService.createCardVipBatch(list);

    }
}
