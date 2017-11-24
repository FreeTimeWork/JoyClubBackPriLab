package com.joycity.joyclub.title_carousel.service;

import java.util.List;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.title_carousel.modal.generated.SaleActTitleCarousel;

/**
 * Created by fangchen.chai on 2017/7/5.
 */
public interface ActTitleCarouselService {

    ResultData getAllActTitleCarousel(Long projectId);

    ResultData createActTitleCarousel(SaleActTitleCarousel actTitleCarousel);

    ResultData deleteActTitleCarousel(Long id);

}
