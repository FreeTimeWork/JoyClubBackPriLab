package com.joycity.joyclub.apiback.controller;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

import javax.servlet.http.HttpSession;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.apiback.modal.vo.title_carousel.SearchActTitleCarouselsVO;
import com.joycity.joyclub.apiback.modal.vo.title_carousel.SearchProductTitleCarouselsVO;
import com.joycity.joyclub.commons.modal.base.ResultData;
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

    @RequestMapping(value = "/system/product/carousels", method = RequestMethod.POST)
    public ResultData updateProductTitleCarousel(@RequestBody SearchProductTitleCarouselsVO vo, HttpSession session) {
        checkProjectUser(session);
        return productTitleCarouselService.updateProductTitleCarousel(vo.getTitleCarousels());
    }

    @RequestMapping(value = "/system/product/carousels", method = RequestMethod.GET)
    public ResultData getAllProductTitleCarousel(HttpSession session) {
        checkProjectUser(session);
        return productTitleCarouselService.getAllProductTitleCarousel();
    }

    @RequestMapping(value = "/system/act/carousels", method = RequestMethod.POST)
    public ResultData updateActTitleCarousel(@RequestBody SearchActTitleCarouselsVO vo, HttpSession session) {
        checkProjectUser(session);
        return actTitleCarouselService.updateActTitleCarousel(vo.getTitleCarousels());
    }

    @RequestMapping(value = "/system/act/carousels", method = RequestMethod.GET)
    public ResultData getAllActTitleCarousel(HttpSession session) {
        checkProjectUser(session);
        return actTitleCarouselService.getAllActTitleCarousel();
    }
}
