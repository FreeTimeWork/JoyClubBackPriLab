package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.product.service.ProductService;
import com.joycity.joyclub.title_carousel.service.ProductTitleCarouselService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID_REQUEST_PARAM;
import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class ProductFrontController {
    @Autowired
    ProductService productService;
    @Autowired
    private ProductTitleCarouselService productTitleCarouselService;

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResultData getProductInfo(@PathVariable Long id) {
        return productService.getInfo(id);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResultData getProducts(@RequestParam(required = false,defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId,
                                  @RequestParam(required = false) Long storeId,
                                  @RequestParam(required = false) Long designerId,
                                  @RequestParam(required = false) Long categoryId,
                                  PageUtil pageUtil
    ) {
        //projectId,store designer 取一个就好
        return productService.getProductList(projectId, categoryId, storeId, designerId, pageUtil);
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

    @GetMapping("/product/carousels")
    public ResultData getProductCarousel(){
        return productTitleCarouselService.getAllProductTitleCarousel();
    }

}
