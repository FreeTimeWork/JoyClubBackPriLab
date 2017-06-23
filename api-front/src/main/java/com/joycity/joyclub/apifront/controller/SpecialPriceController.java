package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID_REQUEST_PARAM;
import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/6/22.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class SpecialPriceController {
    @Autowired
    ProductService productService;
    /**
     * 获取某个特价活动的详情
     */
    @RequestMapping(value = "/specialprice/act/{id}", method = RequestMethod.GET)
    public ResultData getSpecialPriceProductsAct(
            @PathVariable Long id
    ) {

        return productService.getSpecialPriceAct(id);
    }


    /**
     * 获取当前的秒杀活动内容
     *
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/specialprice/act", method = RequestMethod.GET)
    public ResultData getSpecialPriceAct(@RequestParam(required = false, defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId) {
        //projectId,store designer 取一个就好
        return productService.getProjectLatestSpecialPriceAct(projectId);
    }

    /**
     * 获取当前的秒杀活动的所有商品
     *
     * @param projectId
     * @param storeId
     * @param designerId
     * @param pageUtil
     * @return
     */
    @RequestMapping(value = "/specialprice/products", method = RequestMethod.GET)
    public ResultData getSpecialPriceProducts(@RequestParam(required = false, defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
                                              @RequestParam(required = false) Long storeId,
                                              @RequestParam(required = false) Long designerId,
                                              PageUtil pageUtil
    ) {
        //projectId,store designer 取一个就好
        return productService.getSpecialPriceProductList(projectId, storeId, designerId, pageUtil);
    }
    /**
     * 获取某个特价活动的商品列表
     */
    @RequestMapping(value = "/specialprice/act/{id}/products", method = RequestMethod.GET)
    public ResultData getSpecialPriceProductsAct(
            @PathVariable Long id,
            PageUtil pageUtil
    ) {

        return productService.getSpecialPriceActProducts(id,pageUtil);
    }
}
