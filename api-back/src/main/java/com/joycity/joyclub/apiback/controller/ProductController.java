package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.modal.generated.SaleProductWithBLOBs;
import com.joycity.joyclub.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ProductController extends BaseUserSessionController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ManagerService managerService;

    /**
     * 只有商户用户可以访问
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResultData getList(@RequestParam(required = false) String name, PageUtil pageUtil, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        return productService.getListByStoreIdAndName(user.getInfoId(), name, pageUtil);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResultData getProduct(@PathVariable Long id, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return productService.getProduct(id);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param httpSession
     * @return 返回商品新建或者修改所需的基础数据，主要是Select标签的数据
     */
    @RequestMapping(value = "/product/formdata", method = RequestMethod.GET)
    public ResultData getProduct(HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        return productService.getProductFormData(user.getInfoId());
    }

    /**
     * 只有商户用户可以访问
     *
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.POST)
    public ResultData updateProduct(@PathVariable Long id, SaleProductWithBLOBs product, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        product.setId(id);
        return productService.updateProduct( product);
    }

    /**
     * 只有商户用户可以访问
     *
     * @param product
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ResultData createProduct(SaleProductWithBLOBs product, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        product.setStoreId(user.getInfoId());
        return productService.createProduct(product);
    }

}
