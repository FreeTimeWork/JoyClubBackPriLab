package com.joycity.joyclub.title_carousel.mapper;

import java.util.List;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.title_carousel.modal.generated.SaleTitleCarousel;
import com.joycity.joyclub.title_carousel.modal.generated.SaleTitleCarouselExample;
import org.apache.ibatis.annotations.Select;

/**
 * Created by fangchen.chai on 2017/7/5.
 */
public interface TitleCarouselMapper extends BaseMapper<SaleTitleCarousel, Long, SaleTitleCarouselExample> {

    @Select("select id, carousel, goal_address from sale_title_carousel ")
    List<SaleTitleCarousel> selectAllSaleTitleCarousel();



}
