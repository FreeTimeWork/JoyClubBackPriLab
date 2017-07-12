package com.joycity.joyclub.card_coupon.mapper;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.ShowCouponInfo;
import com.joycity.joyclub.card_coupon.modal.generated.CardCoupon;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardCouponMapper extends BaseMapper<CardCoupon, Long, CardCouponExample> {

    Long countCardCouponByNameAndType(@Param("name") String name, @Param("type") Integer type, @Param("pageUtil") PageUtil pageUtil);

    List<ShowCouponInfo> selectCardCouponByNameAndType(@Param("name") String name, @Param("type") Integer type, @Param("pageUtil") PageUtil pageUtil);

    @Update("update card_coupon set delete_flag = 1, delete_time = now() where id = #{id}")
    int deleteCardCouponById(@Param("id") Long id);

    @Select(" select cc.id, cc.project_id, cc.name, cc.type, cc.portrait, cc.num, cc.amount, cc.subtract_amount, cc.max_receive," +
            "        cc.support_refund_flag, cc.info, cc.create_time, cc.last_update, cc.delete_flag, cc.delete_time from card_coupon where id = #{id}")
    CardCoupon selectCardCouponById(@Param("id") Long id);

}
