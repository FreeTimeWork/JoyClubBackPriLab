package com.joycity.joyclub.apiback.controller;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

import java.util.List;
import javax.servlet.http.HttpSession;

import com.joycity.joyclub.apiback.controller.base.BaseUserSessionController;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.title_carousel.modal.generated.SaleTitleCarousel;
import com.joycity.joyclub.title_carousel.service.TitleCarouselService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TitleCarouselService titleCarouselService;

    @RequestMapping(value = "/system/product/carousels", method = RequestMethod.POST)
    public ResultData updateTitleCarousel(List<SaleTitleCarousel> titleCarousels, HttpSession session) {
        checkProjectUser(session);
        return titleCarouselService.updateTitleCarousel(titleCarousels);
    }

    @RequestMapping(value = "/system/product/carousel", method = RequestMethod.POST)
    public ResultData createTitleCarousel(SaleTitleCarousel titleCarousel, HttpSession session) {
        checkProjectUser(session);
        return titleCarouselService.createTitleCarousel(titleCarousel);
    }

    @RequestMapping(value = "/system/product/carousels", method = RequestMethod.GET)
    public ResultData getAllTitleCarousel(HttpSession session) {
        checkProjectUser(session);
        return titleCarouselService.getAllTitleCarousel();
    }
}
