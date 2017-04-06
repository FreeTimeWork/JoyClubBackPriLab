package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SaleProductAttr;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.apiback.service.ProductAttrService;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.apiback.constant.Global.URL_API_BACK;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ProductAttrController extends BaseUserSessionController {
    @Autowired
    private ProductAttrService productAttrService;
    @Autowired
    private ManagerService managerService;

    /**
     * 只有商户用户可以访问
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/product/attrs", method = RequestMethod.GET)
    public ResultData getList(@RequestParam(required = false) String productName, PageUtil pageUtil, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        return productAttrService.getListByStoreIdAndProductName(user.getInfoId(), productName, pageUtil);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/attr/{id}", method = RequestMethod.GET)
    public ResultData getProductAttr(@PathVariable Long id, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return productAttrService.getProductAttr(id);
    }


    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/attr/{id}", method = RequestMethod.POST)
    public ResultData updateProductAttr(@PathVariable Long id, SaleProductAttr productAttr, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        productAttr.setId(id);
        return productAttrService.updateProductAttr( productAttr);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param productAttr
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/attr", method = RequestMethod.POST)
    public ResultData createProductAttr(SaleProductAttr productAttr, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        return productAttrService.createProductAttr(productAttr);
    }

}
