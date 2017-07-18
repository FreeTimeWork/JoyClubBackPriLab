package com.joycity.joyclub.card_coupon.mapper;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.generated.CardThirdpartyCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.CardThirdpartyCouponCodeExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by fangchen.chai on 2017/7/13.
 */
public interface CardThirdpartyCouponCodeMapper extends BaseMapper<CardThirdpartyCouponCode, Long, CardThirdpartyCouponCodeExample> {

    int batchInsertCardThirdpartyCouponCode(@Param("sql") String sql);

    @Select("select count(*) from card_thirdparty_coupon_code where batch = #{batch}")
    Long countIncludeDeleteByBatch(@Param("batch") String batch);

    @Select("select count(*) from card_thirdparty_coupon_code where batch = #{batch} and delete_flag = 0")
    Long countByBatch(@Param("batch") String batch);

    @Select("select id, code, batch from card_thirdparty_coupon_code where batch = #{batch} and delete_flag = 0")
    List<CardThirdpartyCouponCode> selectByBatch(@Param("batch") String batch);

    @Update("update card_thirdparty_coupon_code set delete_flag = 1, delete_time = now() where id = #{id} and delete_flag = 0")
    int deleteById(@Param("id") Long id);
}
