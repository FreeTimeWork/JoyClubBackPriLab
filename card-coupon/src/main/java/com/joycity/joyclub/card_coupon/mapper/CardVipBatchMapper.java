package com.joycity.joyclub.card_coupon.mapper;

import com.joycity.joyclub.card_coupon.modal.generated.CardVipBatch;
import com.joycity.joyclub.card_coupon.modal.generated.CardVipBatchExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardVipBatchMapper extends BaseMapper<CardVipBatch, Long, CardVipBatchExample> {

    int batchInsertCardVipBatch(@Param("sql") String sql);

    @Select("select count(*) from card_vip_batch where batch = #{batch}")
    Long countCardVipBatchByBatch(@Param("batch") String batch);


}
