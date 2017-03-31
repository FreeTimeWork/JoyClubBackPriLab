package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SaleProductPrice;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.service.ManagerService;
import com.joycity.joyclub.apiback.service.ProductPriceService;
import com.joycity.joyclub.apiback.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.joycity.joyclub.apiback.constant.Global.URL_API_BACK;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class ProductPriceController extends BaseUserSessionController {
    @Autowired
    private ProductPriceService productPriceService;


    /**
     * 只有商户用户可以访问
     *
     * @return data为按创建时间倒序的所有项目列表
     */
    @RequestMapping(value = "/product/prices", method = RequestMethod.GET)
    public ResultData getList(@RequestParam(required = false) Integer reviewStatus, @RequestParam(required = false) String productName, PageUtil pageUtil, HttpSession httpSession) {
        //确保是商户用户
        SysUser user = checkStoreUser(httpSession);
        return productPriceService.getListByStoreIdAndProductName(user.getInfoId(), reviewStatus, productName, pageUtil);
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
    public ResultData updateProductPrice(@PathVariable Long id, SaleProductPrice productPrice, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        productPrice.setId(id);
        return productPriceService.updateProductPrice(productPrice);
    }
    /**
     * 只有商户用户可以访问
     * 下架
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
     * 只有商户用户可以访问
     *
     * @param productPrice
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/product/price", method = RequestMethod.POST)
    public ResultData createProductPrice(SaleProductPrice productPrice, HttpSession httpSession) {
        //确保是商户用户
        checkStoreUser(httpSession);
        return productPriceService.createProductPrice(productPrice);
    }

}
