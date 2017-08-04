package com.joycity.joyclub.apiback.modal.vo.title_carousel;

import java.util.List;

import com.joycity.joyclub.title_carousel.modal.generated.SaleProductTitleCarousel;

/**
 * Created by fangchen.chai on 2017/7/19.
 */
public class SearchProductTitleCarouselsVO {
    List<SaleProductTitleCarousel> titleCarousels;

    public List<SaleProductTitleCarousel> getTitleCarousels() {
        return titleCarousels;
    }

    public void setTitleCarousels(List<SaleProductTitleCarousel> titleCarousels) {
        this.titleCarousels = titleCarousels;
    }
}
