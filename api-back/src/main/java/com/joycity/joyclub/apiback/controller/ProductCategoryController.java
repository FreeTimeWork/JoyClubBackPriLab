package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SysProductCategory;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.apiback.constant.Global.URL_API_BACK;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ProductCategoryController extends BaseUserSessionController {
    @Autowired
    private ProductCategoryService productCategoryService;


    /**
     * 只有平台用户可以访问
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/product/categories", method = RequestMethod.GET)
    public ResultData getList(HttpSession httpSession) {
        //确保是平台或项目用户
        SysUser user = checkPlatformOrProjectUser(httpSession);
        return productCategoryService.getList();
    }

    /**
     * 只有平台用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/category/{id}", method = RequestMethod.GET)
    public ResultData getProductCategory(@PathVariable Long id, HttpSession httpSession) {
        //确保是平台或项目用户
        checkPlatformOrProjectUser(httpSession);
        return productCategoryService.getProductCategory(id);
    }

    /**
     * 只有平台可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/category/{id}", method = RequestMethod.POST)
    public ResultData updateProductCategory(@PathVariable Long id, SysProductCategory productCategory, HttpSession httpSession) {
        //确保是平台或项目用户
        checkPlatformOrProjectUser(httpSession);
        return productCategoryService.updateProductCategory(id, productCategory);
    }

    /**
     * 只有平台可以访问
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/category", method = RequestMethod.POST)
    public ResultData updateProductCategory( SysProductCategory productCategory, HttpSession httpSession) {
        //确保是平台或项目用户
        checkPlatformOrProjectUser(httpSession);
        return productCategoryService.createProductCategory(productCategory);
    }
}
