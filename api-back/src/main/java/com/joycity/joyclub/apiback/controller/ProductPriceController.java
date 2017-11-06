package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.modal.vo.act.PriceReviewRejectVO;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.modal.generated.SaleProductPrice;
import com.joycity.joyclub.product.service.ProductPriceService;
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
public class ProductPriceController extends BaseUserSessionController {
    @Autowired
    private ProductPriceService productPriceService;


    /**
     * 商户用户访问时返回list,
     * 平台或者项目用户返回的list多个storeName项，并且可以按商户名搜索
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/product/prices", method = RequestMethod.GET)
    public ResultData getList(
                              @RequestParam(required = false) Boolean specialPriceFlag,
                              @RequestParam(required = false) Integer reviewStatus,
                              @RequestParam(required = false) String storeName,
                              @RequestParam(required = false) String productName,
                              @RequestParam Integer type,
                              @RequestParam Long  infoId,
                              PageUtil pageUtil, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkPlatformOrProjectOrStoreUser(httpSession);
        Integer userType = type;
        if (userType.equals(USER_TYPE_STORE))
            return productPriceService.getListForStore(infoId,specialPriceFlag, reviewStatus, productName, pageUtil);
        else if(userType.equals(USER_TYPE_PLATFORM)||userType.equals(USER_TYPE_PROJECT))
            return productPriceService.getListForProject(storeName,specialPriceFlag, reviewStatus, productName, pageUtil);
        else throw new BusinessException(API_NO_PERMISSION_FOR_CURRENT_USER);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/price/{id}", method = RequestMethod.GET)
    public ResultData getProductPrice(@PathVariable Long id, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return productPriceService.getProductPrice(id);
    }


    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/price/{id}", method = RequestMethod.POST)
    public ResultData updateProductPrice(@PathVariable Long id, @RequestBody SaleProductPrice productPrice, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        productPrice.setId(id);
        return productPriceService.updateProductPrice(productPrice);
    }

    /**
     * 只有商户用户可以访问
     * 下架
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/price/{id}/forbid", method = RequestMethod.POST)
    public ResultData forbidProductPrice(@PathVariable Long id, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return productPriceService.forbidProductPrice(id);
    }
    /**
     * 只有平台或项目用户可以访问
     * 审核通过
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/price/{id}/review/permit", method = RequestMethod.POST)
    public ResultData permitPrice(@PathVariable Long id, HttpSession httpSession) {
        //确保是商户用户
        checkPlatformOrProjectUser(httpSession);
        return productPriceService.permitProductPrice(id);
    }
    /**
     * 只有平台或项目用户可以访问
     * 审核拒绝
     *
     * @param id
     * @param rejectVO
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/price/{id}/review/reject", method = RequestMethod.POST)
    public ResultData rejectPrice(@PathVariable Long id, @RequestBody PriceReviewRejectVO rejectVO, HttpSession httpSession) {
        //确保是商户用户
        checkPlatformOrProjectUser(httpSession);
        return productPriceService.rejectProductPrice(id, rejectVO.getReviewInfo());
    }
    /**
     * 只有商户用户可以访问
     *
     * @param productPrice
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/price", method = RequestMethod.POST)
    public ResultData createProductPrice(@RequestBody SaleProductPrice productPrice, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return productPriceService.createProductPrice(productPrice);
    }

}
