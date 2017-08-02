package com.joycity.joyclub.card_coupon.mapper;

import com.joycity.joyclub.card_coupon.modal.generated.CardCouponExample;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponOrder;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/8/1
 */
public interface CardCouponOrderMapper extends BaseMapper<CardCouponOrder, Long, CardCouponExample> {

    /**
     * 积分支付和免费领取
     * @param id
     * @return
     */
    @Update("UPDATE card_coupon_order set status = 2, pay_time = now() where id = #{id}")
    Integer setPayedById(@Param("id") Long id);

    @Select("select id from card_coupon_order where code = #{code}")
    Long selectIdByCode(@Param("code") String code);

    @Update("update card_coupon_order set out_pay_code =#{outPayCode} where id=#{id}")
    Integer setOutPayCodeById(@Param("id") Long id, @Param("outPayCode") String outPayCode);

    @Update("update card_coupon_order set status = 1,cancel_time=now() where id=#{id}")
    Integer cancelOrder(@Param("id") Long id);

    @Select("select id, launch_id from card_coupon_order where status = 0 and MINUTE(timediff(now(),create_time))>=10")
    List<CardCouponOrder> getTenMinNotPayedOrder();

}
