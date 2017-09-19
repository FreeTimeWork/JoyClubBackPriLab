package com.joycity.joyclub.title_carousel.service.impl;

import java.util.List;

import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.title_carousel.mapper.ProductTitleCarouselMapper;
import com.joycity.joyclub.title_carousel.modal.generated.SaleProductTitleCarousel;
import com.joycity.joyclub.title_carousel.service.ProductTitleCarouselService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fangchen.chai on 2017/7/5.
 */
@Service
public class ProductTitleCarouselServiceImpl implements ProductTitleCarouselService {
    private final Log log = LogFactory.getLog(ProductTitleCarouselServiceImpl.class);

    @Autowired
    private ProductTitleCarouselMapper productTitleCarouselMapper;

    @Override
    public ResultData getAllProductTitleCarousel() {
        List<SaleProductTitleCarousel> saleTitleCarousels =  productTitleCarouselMapper.selectAllSaleProductTitleCarousel();
        return new ResultData(new ListResult(saleTitleCarousels));
    }

    @Override
    @Transactional
    public ResultData updateProductTitleCarousel(List<SaleProductTitleCarousel> saleTitleCarousels) {
        productTitleCarouselMapper.deleteAll();
        for (SaleProductTitleCarousel carousel : saleTitleCarousels) {
            productTitleCarouselMapper.insertSelective(carousel);
        }
        return new ResultData(new UpdateResult(saleTitleCarousels.size()));
    }

    @Override
    public ResultData createProductTitleCarousel(SaleProductTitleCarousel titleCarousel) {
        productTitleCarouselMapper.insert(titleCarousel);
        return new ResultData(new CreateResult(titleCarousel.getId()));
    }

    @Override
    public ResultData deleteProductTitleCarousel(Long id) {
        Integer num = productTitleCarouselMapper.deleteByPrimaryKey(id);
        return new ResultData(new UpdateResult(num));
    }

}
