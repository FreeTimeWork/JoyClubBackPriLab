package com.joycity.joyclub.apiback.modal.vo.title_carousel;

import java.util.List;

import com.joycity.joyclub.title_carousel.modal.generated.SaleActTitleCarousel;

/**
 * Created by fangchen.chai on 2017/7/19.
 */
public class SearchActTitleCarouselsVO {
    List<SaleActTitleCarousel> titleCarousels;

    public List<SaleActTitleCarousel> getTitleCarousels() {
        return titleCarousels;
    }

    public void setTitleCarousels(List<SaleActTitleCarousel> titleCarousels) {
        this.titleCarousels = titleCarousels;
    }
}
