package com.joycity.joyclub.title_carousel.service;

import java.util.List;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.title_carousel.modal.generated.SaleActTitleCarousel;

/**
 * Created by fangchen.chai on 2017/7/5.
 */
public interface ActTitleCarouselService {

    ResultData getAllActTitleCarousel();

    ResultData updateActTitleCarousel(List<SaleActTitleCarousel> actTitleCarousels);

    ResultData createActTitleCarousel(SaleActTitleCarousel actTitleCarousel);

}
