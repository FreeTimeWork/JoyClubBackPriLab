package com.joycity.joyclub.coupon.mapper;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.coupon.modal.CouponCodeInfo;
import com.joycity.joyclub.coupon.modal.generated.CouponCode;
import com.joycity.joyclub.coupon.modal.generated.CouponCodeExample;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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


    @Update("update coupon_code set check_flag=true , check_time = now(),checker_id=#{managerId} where id=#{couponId}")
    Integer setCodeChecked(@Param("couponId") Long couponId,@Param("managerId") Long managerId);

    @Select("select id,coupon_id,code,use_status,use_time,client_id,check_flag,check_time from coupon_code where code=#{code} and coupon_id=#{couponId} limit 0,1")
    CouponCode getCodeByCodeAndCouponId(@Param("couponId") Long couponId, @Param("code") String code);

    /**
     * 获得某个卡券下未领取的最小id的卡券
     * 包含codeId,couponId,
     */
    @Select("select min(id) id,coupon_id from coupon_code where coupon_id=#{couponId}  and use_status=0 and delete_flag=0")
    CouponCode getMinCodeIdOfCoupon(Long couponId);

    /**
     * 会员领取某个卡券号
     */
    @Select("update coupon_code set use_status=1 , client_id = #{clientId} , use_time=now() where id=#{codeId}")
    Integer setCodeUsed(@Param("codeId") Long codeId,@Param("clientId") Long clientId);

    /**
     * 记录在商业或者地产项目领取卡券
     * @param codeId
     * @param subProjectId
     */
    @Insert("insert into coupon_code_subproject(coupon_code_id,sub_project_id) values(#{codeId},#{subProjectId})")
    void addCouponCodeSubProject(@Param("codeId") Long codeId,@Param("subProjectId") Long subProjectId);
}
