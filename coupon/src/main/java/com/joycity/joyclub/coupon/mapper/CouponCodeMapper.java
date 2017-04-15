package com.joycity.joyclub.coupon.mapper;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.coupon.modal.CouponCodeInfo;
import com.joycity.joyclub.coupon.modal.generated.Coupon;
import com.joycity.joyclub.coupon.modal.generated.CouponCode;
import com.joycity.joyclub.coupon.modal.generated.CouponCodeExample;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/12.
 */
public interface CouponCodeMapper extends BaseMapper<CouponCode, Long, CouponCodeExample> {
    /**
     * 使用<code>${}</code>语法，可能会导致sql注入
     *
     * @param values (coupon_id,code),字符串，多个值用逗号分隔
     * @return
     */
    @Insert("insert into coupon_code(coupon_id,code) values ${values}")
    Integer insertCodeWithValuesSql(@Param("values") String values);

    /**
     * @param type     卡券类型
     * @param code     某个卡券号码（严格相等）
     * @param phone    用户手机号
     * @param nameLike 卡券名称
     * @param pageUtil
     * @return
     */
    Long countByFilter(@Param("type") String type,
                       @Param("code") String code,
                       @Param("phone") String phone,
                       @Param("nameLike") String nameLike,
                       @Param("pageUtil") PageUtil pageUtil);

    /**
     * @param type     卡券类型
     * @param code     某个卡券号码（严格相等）
     * @param phone    用户手机号
     * @param nameLike 卡券名称
     * @param pageUtil
     * @return
     */
    List<CouponCodeInfo> selectByFilter(@Param("type") String type,
                                        @Param("code") String code,
                                        @Param("phone") String phone,
                                        @Param("nameLike") String nameLike,
                                        @Param("pageUtil") PageUtil pageUtil);

}
