package com.joycity.joyclub.coupon.mapper;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.modal.base.IdName;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.coupon.modal.CouponForClient;
import com.joycity.joyclub.coupon.modal.generated.Coupon;
import com.joycity.joyclub.coupon.modal.generated.CouponExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/12.
 */
public interface CouponMapper extends BaseMapper<Coupon, Long, CouponExample> {

    Long countByFilter(@Param("projectId") Long projectId,
                       @Param("type") Integer type,
                       @Param("nameLike") String nameLike,
                       @Param("useFlag") Boolean useFlag,
                       @Param("pageUtil") PageUtil pageUtil);

    List<Coupon> selectByFilter(@Param("projectId") Long projectId,
                                @Param("type") Integer type,
                                @Param("nameLike") String nameLike,
                                @Param("useFlag") Boolean useFlag,
                                @Param("pageUtil") PageUtil pageUtil);

    List<CouponForClient> selectForFrontByFilter(

            @Param("projectId") Long projectId,
            @Param("clientId") Long clientId,
            @Param("cardType") String cardType,
            @Param("pageUtil") PageUtil pageUtil
    );

    List<CouponForClient> selectForFrontWithoutClientByFilter(

            @Param("projectId") Long projectId,
            @Param("pageUtil") PageUtil pageUtil
    );

    List<CouponForClient> selectClientCouponsByFilter(
            @Param("clientId") Long clientId,
            @Param("pageUtil") PageUtil pageUtil
    );

    @Update("update coupon set num=num+#{num} where id=#{id}")
    void addNum(@Param("id") Long id, @Param("num") Integer num);

    @Select("select id,name from coupon where use_flag=1 and forbid_flag=0 and now() between available_start_time and available_end_time  ")
    List<IdName> getAllSimpleList();

}
