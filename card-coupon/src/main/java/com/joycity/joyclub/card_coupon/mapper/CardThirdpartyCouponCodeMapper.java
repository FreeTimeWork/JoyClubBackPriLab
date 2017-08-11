package com.joycity.joyclub.card_coupon.mapper;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.ThirdpartyCouponCodeGroupByBatch;
import com.joycity.joyclub.card_coupon.modal.generated.CardThirdpartyCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.CardThirdpartyCouponCodeExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Delete;
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
    int countByBatch(@Param("batch") String batch);

    @Select("select id, code, batch from card_thirdparty_coupon_code where batch = #{batch} and delete_flag = 0 limit #{pageUtil.offset},#{pageUtil.pageSize}")
    List<CardThirdpartyCouponCode> selectByBatch(@Param("batch") String thirdCodeBatch, @Param("pageUtil") PageUtil pageUtil);

    @Update("update card_thirdparty_coupon_code set delete_flag = 1, delete_time = now() where id = #{id} and delete_flag = 0")
    int deleteById(@Param("id") Long id);

    /**
     * 清除与card_coupon没有关联的数据
     *
     * @return
     */
    @Delete("DELETE ctcc.* from card_thirdparty_coupon_code ctcc LEFT JOIN card_coupon cc ON cc.batch = ctcc.batch WHERE ISNULL(cc.id)")
    int clear();

    List<ThirdpartyCouponCodeGroupByBatch> selectGroupByBatch();
}
