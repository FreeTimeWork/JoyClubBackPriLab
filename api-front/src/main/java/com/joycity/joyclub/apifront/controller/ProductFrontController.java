package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.service.ProductFrontService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;

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
    public ResultData getProducts(@RequestParam(required = false) Long projectId,
                                  @RequestParam(required = false) Long storeId,
                                  @RequestParam(required = false) Long designerId,
                                  PageUtil pageUtil
    ) {
        //projectId,store designer 取一个就好
        return productService.getList(projectId, storeId, designerId, pageUtil);
    }


    /**
     * 获得商品的属性
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/product/{id}/attrs", method = RequestMethod.GET)
    public ResultData getProductAttrs(@PathVariable Long id) {
        return productService.getAttrs(id);
    }

}
