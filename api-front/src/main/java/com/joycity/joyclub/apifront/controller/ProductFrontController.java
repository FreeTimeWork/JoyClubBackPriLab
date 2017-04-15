package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.ProductFrontService;
import com.joycity.joyclub.apifront.service.ProductFrontService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class ProductFrontController {
    @Autowired
    ProductFrontService productService;

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResultData getProductInfo(@PathVariable Long id) {
        return productService.getInfo(id);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResultData getProducts(/*@RequestParam Long projectId*/
                                  @RequestParam(required = false) Long storeId,
                                  @RequestParam(required = false) Long designerId,
                                  PageUtil pageUtil
    ) {
        //store designer 取一个就好
        return productService.getList(storeId, designerId, pageUtil);
    }
   /* @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResultData getProducts(@RequestParam(required = false) Long storeId) {

        return productService.getList(storeId);
    }
        *//**
     * 获得项目的商品
     *
     * @param projectId
     * @return
     *//*
    @RequestMapping(value = "/products", method = GET)
    public ResultData getProducts(@RequestParam Long projectId, PageUtil pageUtil) {
        return null;
    }

    *//**
     * 获得商户的商品
     *
     * @param id
     * @param pageUtil
     * @return
     *//*
    @RequestMapping(value = "/store/{id}/products", method = GET)
    public ResultData getStoreProducts(@PathVariable Long id, PageUtil pageUtil) {
        return null;
    }

    *//**
     * 获得设计师的商品
     *
     * @param id
     * @param pageUtil
     * @return
     *//*
    @RequestMapping(value = "/designer/{id}/products", method = GET)
    public ResultData getDesignerProducts(@PathVariable Long id, PageUtil pageUtil) {
        return null;
    }
*/

}
