package com.joycity.joyclub.title_carousel.service.impl;

import java.util.List;

import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.title_carousel.mapper.TitleCarouselMapper;
import com.joycity.joyclub.title_carousel.modal.generated.SaleProductTitleCarousel;
import com.joycity.joyclub.title_carousel.modal.generated.SaleTitleCarousel;
import com.joycity.joyclub.title_carousel.service.TitleCarouselService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fangchen.chai on 2017/7/5.
 */
@Service
public class ProductTitleCarouselServiceImpl implements TitleCarouselService {
    private final Log log = LogFactory.getLog(ProductTitleCarouselServiceImpl.class);

    @Autowired
    TitleCarouselMapper titleCarouselMapper;

    @Override
    public ResultData getAllTitleCarousel() {
        List<SaleProductTitleCarousel> saleTitleCarousels =  titleCarouselMapper.selectAllSaleTitleCarousel();
        return new ResultData(saleTitleCarousels);
    }

    @Override
    @Transactional
    public ResultData updateTitleCarousel(List<SaleTitleCarousel> saleTitleCarousels) {
        for (SaleTitleCarousel carousel : saleTitleCarousels) {
            titleCarouselMapper.deleteByPrimaryKey(carousel.getId());
            titleCarouselMapper.insertSelective(carousel);
        }
        return new ResultData(new UpdateResult(saleTitleCarousels.size()));
    }

    @Override
    public ResultData createTitleCarousel(SaleTitleCarousel titleCarousel) {
        titleCarouselMapper.insert(titleCarousel);
        return new ResultData(new CreateResult(titleCarousel.getId()));
    }

}
