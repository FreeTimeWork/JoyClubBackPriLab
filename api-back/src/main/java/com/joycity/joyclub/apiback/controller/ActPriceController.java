package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.act.modal.generated.SaleActPrice;
import com.joycity.joyclub.act.service.ActPriceService;
import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.apiback.constant.UserType.*;
import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;
import static com.joycity.joyclub.commons.constant.ResultCode.API_NO_PERMISSION_FOR_CURRENT_USER;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ActPriceController extends BaseUserSessionController {
    @Autowired
    private ActPriceService actPriceService;


    /**
     * 商户用户访问时返回list,
     * 平台或者项目用户返回的list多个storeName项，并且可以按商户名搜索
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/act/prices", method = RequestMethod.GET)
    public ResultData getList(@RequestParam(required = false) Long projectId,
                              @RequestParam(required = false) Integer reviewStatus,
                              @RequestParam(required = false) String storeName,
                              @RequestParam(required = false) String actName,
                              @RequestParam(required = false) Byte buyType,
                              PageUtil pageUtil, HttpSession httpSession) {
        SysUser user = checkUser(httpSession);
        Integer userType = user.getType();
        if (userType.equals(USER_TYPE_STORE))
            return actPriceService.getListForStore(user.getInfoId(), reviewStatus, actName, buyType, pageUtil);
        else if (userType.equals(USER_TYPE_PLATFORM) || userType.equals(USER_TYPE_PROJECT))
            return actPriceService.getListForProject(projectId, storeName, reviewStatus, actName, buyType, pageUtil);
        else throw new BusinessException(API_NO_PERMISSION_FOR_CURRENT_USER);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/price/{id}", method = RequestMethod.GET)
    public ResultData getActPrice(@PathVariable Long id, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return actPriceService.getActPrice(id);
    }


    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/price/{id}", method = RequestMethod.POST)
    public ResultData updateActPrice(@PathVariable Long id, @RequestBody SaleActPrice actPrice, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        actPrice.setId(id);
        return actPriceService.updateActPrice(actPrice);
    }

    /**
     * 只有商户用户可以访问
     * 下架
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/price/{id}/forbid", method = RequestMethod.POST)
    public ResultData forbidActPrice(@PathVariable Long id, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return actPriceService.forbidActPrice(id);
    }

    /**
     * 只有平台或项目用户可以访问
     * 审核通过
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/price/{id}/review/permit", method = RequestMethod.POST)
    public ResultData permitPrice(@PathVariable Long id, HttpSession httpSession) {
        //确保是商户用户
        checkPlatformOrProjectUser(httpSession);
        return actPriceService.permitActPrice(id);
    }

    /**
     * 只有平台或项目用户可以访问
     * 审核拒绝
     *
     * @param id
     * @param reviewInfo
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/price/{id}/review/reject", method = RequestMethod.POST)
    public ResultData rejectPrice(@PathVariable Long id, @RequestBody String reviewInfo, HttpSession httpSession) {
        //确保是商户用户
        checkPlatformOrProjectUser(httpSession);
        return actPriceService.rejectActPrice(id, reviewInfo);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param actPrice
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/act/price", method = RequestMethod.POST)
    public ResultData createActPrice(@RequestBody SaleActPrice actPrice, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return actPriceService.createActPrice(actPrice);
    }

}
