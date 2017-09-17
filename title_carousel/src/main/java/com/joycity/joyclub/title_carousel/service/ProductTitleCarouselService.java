package com.joycity.joyclub.title_carousel.service;

import java.util.List;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.title_carousel.modal.generated.SaleProductTitleCarousel;

/**
 * Created by fangchen.chai on 2017/7/5.
 */
public interface ProductTitleCarouselService {

    ResultData getAllProductTitleCarousel();

    ResultData updateProductTitleCarousel(List<SaleProductTitleCarousel> saleProductTitleCarousels);

    ResultData createProductTitleCarousel(SaleProductTitleCarousel productTitleCarousel);

    ResultData deleteProductTitleCarousel(Long id);
}
