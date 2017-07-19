package com.joycity.joyclub.apiback.controller;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

import java.util.List;
import javax.servlet.http.HttpSession;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.title_carousel.modal.generated.SaleActTitleCarousel;
import com.joycity.joyclub.title_carousel.modal.generated.SaleProductTitleCarousel;
import com.joycity.joyclub.title_carousel.service.ActTitleCarouselService;
import com.joycity.joyclub.title_carousel.service.ProductTitleCarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fangchen.chai on 2017/7/5.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class TitleCarouselController extends BaseUserSessionController {

    @Autowired
    private ProductTitleCarouselService productTitleCarouselService;
    @Autowired
    private ActTitleCarouselService actTitleCarouselService;

    //TODO: cfc  如何传list
    @RequestMapping(value = "/system/product/carousels", method = RequestMethod.POST)
    public ResultData updateProductTitleCarousel(@RequestBody List<SaleProductTitleCarousel> titleCarousels, HttpSession session) {
        checkProjectUser(session);
//        productTitleCarouselService.updateProductTitleCarousel(Arrays.asList(titleCarousels))
        return null;
    }

    @RequestMapping(value = "/system/product/carousels", method = RequestMethod.GET)
    public ResultData getAllProductTitleCarousel(HttpSession session) {
        checkProjectUser(session);
        return productTitleCarouselService.getAllProductTitleCarousel();
    }

    @RequestMapping(value = "/system/act/carousels", method = RequestMethod.POST)
    public ResultData updateActTitleCarousel(List<SaleActTitleCarousel> titleCarousels, HttpSession session) {
        checkProjectUser(session);
        return actTitleCarouselService.updateActTitleCarousel(titleCarousels);
    }

    @RequestMapping(value = "/system/act/carousels", method = RequestMethod.GET)
    public ResultData getAllActTitleCarousel(HttpSession session) {
        checkProjectUser(session);
        return actTitleCarouselService.getAllActTitleCarousel();
    }
}
