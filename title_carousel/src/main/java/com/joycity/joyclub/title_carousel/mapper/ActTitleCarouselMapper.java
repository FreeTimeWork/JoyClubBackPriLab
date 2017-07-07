package com.joycity.joyclub.title_carousel.mapper;

import java.util.List;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.title_carousel.modal.generated.SaleActTitleCarousel;
import com.joycity.joyclub.title_carousel.modal.generated.SaleActTitleCarouselExample;
import org.apache.ibatis.annotations.Select;

/**
 * Created by fangchen.chai on 2017/7/5.
 */
public interface ActTitleCarouselMapper extends BaseMapper<SaleActTitleCarousel, Long, SaleActTitleCarouselExample> {

    @Select("select id, picture, target_address from sale_act_title_carousel ")
    List<SaleActTitleCarousel> selectAllActTitleCarousel();



}
