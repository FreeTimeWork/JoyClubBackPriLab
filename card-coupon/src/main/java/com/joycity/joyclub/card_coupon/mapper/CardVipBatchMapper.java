package com.joycity.joyclub.card_coupon.mapper;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.generated.CardVipBatch;
import com.joycity.joyclub.card_coupon.modal.generated.CardVipBatchExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardVipBatchMapper extends BaseMapper<CardVipBatch, Long, CardVipBatchExample> {

    int batchInsertCardVipBatch(@Param("sql") String sql);

    @Select("select count(*) from card_vip_batch where batch = #{batch} and delete_flag = 0")
    Long countCardVipBatchByBatch(@Param("batch") String batch);

    @Select("select client_id from card_vip_batch where batch = #{batch} and delete_flag = 0")
    List<Long> selectClientIdByBatch(@Param("batch") String batch);

    /**
     * 清除与card_coupon_launch没有关联的数据
     * @return
     */
    @Delete("DELETE cvb.* from card_vip_batch cvb LEFT JOIN card_coupon_launch ccl ON ccl.vip_batch = cvb.batch WHERE ISNULL(ccl.id)")
    int clear();

}
