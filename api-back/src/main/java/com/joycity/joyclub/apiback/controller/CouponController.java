package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.util.ExcelToBeanParser;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.coupon.exception.CouponException;
import com.joycity.joyclub.coupon.modal.generated.Coupon;
import com.joycity.joyclub.coupon.service.CouponService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.joycity.joyclub.apiback.constant.Global.URL_API_BACK;
import static com.joycity.joyclub.apiback.constant.ResultCode.COUPON_CHECK_ERROR;
import static com.joycity.joyclub.apiback.constant.ResultCode.DATA_NOT_EXIST;
import static com.joycity.joyclub.apiback.constant.ResultCode.ERR_IMPORT_EXCEL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by CallMeXYZ on 2017/4/12.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class CouponController extends BaseUserSessionController {
    @Autowired
    CouponService couponService;

    /**{id,name}list
     * @return
     */
    @RequestMapping(value = "/coupons/simple", method = GET)
    public ResultData getCouponList() {
        return couponService.getSimpleCouponList();
    }

    @RequestMapping(value = "/coupons", method = GET)
    public ResultData getCouponList(@RequestParam(required = false) String name,

                                    @RequestParam(required = false) Integer type,
                                    @RequestParam(required = false) Boolean useFlag,
                                    PageUtil pageUtil,
                                    HttpSession httpSession) {
        //平台或者项目成员才能访问
        SysUser user = checkPlatformOrProjectUser(httpSession);
        return couponService.getCouponList(user.getInfoId(), type, name, useFlag, pageUtil);
    }

    @RequestMapping(value = "/coupon/codes", method = GET)
    public ResultData getCouponCodeList(@RequestParam(required = false) String type,
                                        @RequestParam(required = false) String code,
                                        @RequestParam(required = false) String phone,
                                        @RequestParam(required = false) String name,
                                        PageUtil pageUtil,
                                        HttpSession httpSession) {
        //平台或者项目成员才能访问
        checkPlatformOrProjectUser(httpSession);
        return couponService.getCouponCodeList(type, code, phone, name, pageUtil);
    }
    @RequestMapping(value = "/coupon/{id}/cardtypes", method = GET)
    public ResultData getCouponCodeList(@PathVariable Long id,
                                        HttpSession httpSession) {
        //平台或者项目成员才能访问
        checkPlatformOrProjectUser(httpSession);
        return new ResultData(new ListResult(couponService.getCardTypes(id)));
    }
    /**
     * 开始投放卡券
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/coupon/{id}/startuse", method = POST)
    public ResultData startUserCoupon(
            @PathVariable Long id,
            HttpSession httpSession) {
        //平台或者项目成员才能访问
        checkPlatformOrProjectUser(httpSession);
        return couponService.startUse(id);
    }

    /**
     * 卡券强行停止发放
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/coupon/{id}/forbid", method = POST)
    public ResultData forbid(
            @PathVariable Long id,
            HttpSession httpSession) {
        //平台或者项目成员才能访问
        checkPlatformOrProjectUser(httpSession);
        return couponService.forbid(id);
    }

    /**
     * 更新卡券，目前只能更新info
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/coupon/{id}", method = POST)
    public ResultData updateInfo(
            @PathVariable Long id,
            @RequestParam String info,
            String[] cardTypes,
            HttpSession httpSession) {
        //平台或者项目成员才能访问
        checkPlatformOrProjectUser(httpSession);
        return couponService.updateInfo(id, info, cardTypes);
    }

    /**
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/coupon", method = POST)
    public ResultData add(
            Coupon coupon,
            String[] cardTypes,
            HttpSession httpSession) {
        //平台或者项目成员才能访问
        SysUser user = checkPlatformOrProjectUser(httpSession);
        coupon.setProjectId(user.getInfoId());

        return couponService.insert(coupon, cardTypes);
    }

    @RequestMapping(value = "/coupon/{id}/codes/excel", method = {RequestMethod.POST})
    public ResultData importCodesFromExcel(@RequestParam("file") final MultipartFile file,
                                           @PathVariable Long id,
                                           HttpSession httpSession
    ) {
        checkPlatformOrProjectUser(httpSession);
        if (couponService.getById(id) == null) {
            throw new BusinessException(DATA_NOT_EXIST, "该卡券不存在");
        }
        List<List<String>> list;
        try {
            list = ExcelToBeanParser.loadDataFromExcel(file.getInputStream(), file.getOriginalFilename(), 0);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ERR_IMPORT_EXCEL, "导入excel失败");
        }
        List<String> cardNoList = new ArrayList<>();
        List<String> rows;
        for (int i = 0; i < list.size(); i++) {
            rows = list.get(i);
            if (rows != null && rows.size() > 0) {
                String cardNo = rows.get(0);
                if (!StringUtils.isBlank(cardNo)) {
                    cardNoList.add(cardNo);
                }
            }

        }

        return couponService.addCodes(id, cardNoList);


    }

    //核销
    @RequestMapping(value = "/coupon/{id}/check", method = POST)
    public ResultData checkCode(
            HttpSession httpSession,
            @PathVariable Long id,
            @RequestParam String code) {
        SysUser user = checkUser(httpSession);
        try {
            return couponService.checkCode(id,user.getId(), code);
        } catch (CouponException e) {
          throw new BusinessException(COUPON_CHECK_ERROR,e.getMessage());
        }
    }

}
