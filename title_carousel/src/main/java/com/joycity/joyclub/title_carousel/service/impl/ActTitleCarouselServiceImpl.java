package com.joycity.joyclub.title_carousel.service.impl;

import java.util.List;

import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.title_carousel.mapper.ActTitleCarouselMapper;
import com.joycity.joyclub.title_carousel.modal.generated.SaleActTitleCarousel;
import com.joycity.joyclub.title_carousel.service.ActTitleCarouselService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fangchen.chai on 2017/7/5.
 */
@Service
public class ActTitleCarouselServiceImpl implements ActTitleCarouselService {
    private final Log log = LogFactory.getLog(ActTitleCarouselServiceImpl.class);

    @Autowired
    private ActTitleCarouselMapper actTitleCarouselMapper;

    @Override
    public ResultData getAllActTitleCarousel() {
        List<SaleActTitleCarousel> saleTitleCarousels =  actTitleCarouselMapper.selectAllActTitleCarousel();
        return new ResultData(saleTitleCarousels);
    }

    @Override
    @Transactional
    public ResultData updateActTitleCarousel(List<SaleActTitleCarousel> actTitleCarousels) {
        for (SaleActTitleCarousel carousel : actTitleCarousels) {
            actTitleCarouselMapper.deleteByPrimaryKey(carousel.getId());
            actTitleCarouselMapper.insertSelective(carousel);
        }
        return new ResultData(new UpdateResult(actTitleCarousels.size()));
    }

    @Override
    public ResultData createActTitleCarousel(SaleActTitleCarousel actTitleCarousel) {
        actTitleCarouselMapper.insert(actTitleCarousel);
        return new ResultData(new CreateResult(actTitleCarousel.getId()));
    }

}
