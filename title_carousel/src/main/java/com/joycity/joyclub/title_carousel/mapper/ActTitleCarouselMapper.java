package com.joycity.joyclub.title_carousel.mapper;

import java.util.List;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.title_carousel.modal.generated.SaleActTitleCarousel;
import com.joycity.joyclub.title_carousel.modal.generated.SaleActTitleCarouselExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by fangchen.chai on 2017/7/5.
 */
public interface ActTitleCarouselMapper extends BaseMapper<SaleActTitleCarousel, Long, SaleActTitleCarouselExample> {

    List<SaleActTitleCarousel> selectAllActTitleCarousel(@Param("projectId") Long projectId);



}
