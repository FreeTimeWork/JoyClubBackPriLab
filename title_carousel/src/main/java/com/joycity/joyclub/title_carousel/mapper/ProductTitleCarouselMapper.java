package com.joycity.joyclub.title_carousel.mapper;

import java.util.List;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.title_carousel.modal.generated.SaleProductTitleCarousel;
import com.joycity.joyclub.title_carousel.modal.generated.SaleProductTitleCarouselExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * Created by fangchen.chai on 2017/7/5.
 */
public interface ProductTitleCarouselMapper extends BaseMapper<SaleProductTitleCarousel, Long, SaleProductTitleCarouselExample> {

    @Select("select id, picture, target_address from sale_product_title_carousel ")
    List<SaleProductTitleCarousel> selectAllSaleProductTitleCarousel();

    @Delete("delete from sale_product_title_carousel")
    void deleteAll();



}
