package com.joycity.joyclub.card_coupon.mapper;

import com.joycity.joyclub.card_coupon.modal.CouponCodeWithCoupon;
import com.joycity.joyclub.card_coupon.modal.CouponLaunchBetweenInfo;
import com.joycity.joyclub.card_coupon.modal.ShowCouponCodeInfo;
import com.joycity.joyclub.card_coupon.modal.ShowPosCurrentCouponCodeInfo;
import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCodeExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardCouponCodeMapper extends BaseMapper<CardCouponCode, Long, CardCouponCodeExample> {

    @Select("select count(*) from card_coupon_code where launch_id = #{launchId} and use_status = #{useStatus} and delete_flag = 0")
    int countByLaunchIdAndUseStatus(@Param("launchId") Long launchId, @Param("useStatus") Byte useStatus);

    @Select("select count(*) from card_coupon_code where launch_id = #{launchId} and delete_flag = 0")
    int countByLaunchId(@Param("launchId") Long launchId);

    Long countCardCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter);

    List<ShowCouponCodeInfo> selectCardCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter, @Param("pageUtil") PageUtil pageUtil);

    @Select("select ccc.id,cc.type as coupon_type  from card_coupon_code ccc inner join card_coupon_launch ccl on ccl.id = ccc.launch_id and ccl.delete_flag = 0 " +
            "inner join card_coupon cc on cc.id = ccl.coupon_id and cc.delete_flag = 0 where ccc.order_code = #{orderCode} and ccc.delete_flag = 0")
    List<CouponCodeWithCoupon> selectCouponCodeWithCouponByOrderCode(@Param("orderCode") String orderCode);

    /**
     * 根据卡券code，查找卡券的信息，
     * belong -1 为系统券，其他为第三方商户id
     * @return
     */
    ShowPosCurrentCouponCodeInfo selectByCode(@Param("code") String code, @Param("belong") Long belong);


    /**
     *  查找该商户，该会员当前可用券，只查系统券,卡券未使用，时间在有效期内
     * @param projectId
     * @param shopCode
     * @param tel
     * @param vipCode
     * @return
     */
    List<ShowPosCurrentCouponCodeInfo> selectCurrentCouponCode(@Param("projectId") Long projectId, @Param("shopCode") String shopCode, @Param("tel") String tel, @Param("vipCode") String vipCode);


    /**
     * 查找未使用的卡券，在有效期内
     *
     * @param couponCode 卡券code
     * @param onlySys     是否只查询系统券
     * @return
     */
    CouponCodeWithCoupon selectCardCouponCodesByCodes(@Param("projectId") Long projectId, @Param("list") String couponCode, @Param("shopCode") String shopCode ,@Param("onlySys") boolean onlySys);

    /**
     * 在条件投放期间内，卡券的基本信息
     */
    @Select(
            " SELECT ccl.condition_amount, cc.subtract_amount   " +
            " FROM card_coupon_launch ccl" +
            " inner join card_coupon cc on cc.id = ccl.coupon_id and cc.type != 3 and cc.delete_flag = 0 " +
            " WHERE #{now} > launch_start_time AND #{now} < launch_end_time and ccl.type = 1 and ccl.confirm_flag = 1 and ccl.forbid_flag = 0 and ccl.review_status = 1 and ccl.delete_flag = 0")
    CouponLaunchBetweenInfo selectInfoFromLaunchBetween(@Param("now") Date now);

    /**
     * 在条件投放的期间内，该会员 已使用卡券的数量和未使用卡券的数量
     *
     * @param now
     * @return
     */
    CouponLaunchBetweenInfo selectCouponNumFromLaunchBetween(@Param("now") Date now, @Param("clientId") Long clientId);

    /**
     * 条件投放期间内，从该会员未使用的代金券中找到num个couponCodeId
     * 注意要验证 list.size == num
     */
    List<Long> selectNotUsedCouponCodeIdFromLaunchBetween(@Param("now") Date now, @Param("clientId") Long clientId, @Param("num") Integer num);

    /**
     * 在条件投放的期间内,该会员，所有订单的最终实际付款总额
     * @return
     */
    @Select("SELECT sum(psd.balance) " +
            "FROM pos_sale_detail psd " +
            "INNER JOIN ( " +
            "   SELECT launch_start_time, launch_end_time FROM card_coupon_launch WHERE #{now} > launch_start_time AND #{now} < launch_end_time and type = 1 and confirm_flag = 1 and forbid_flag = 0 and review_status = 1 and delete_flag = 0" +
            ") launch_time ON psd.create_time > launch_time.launch_start_time " +
            "AND psd.create_time < launch_time.launch_end_time" +
            " where psd.client_id = #{clientId} and psd.delete_flag = 0")
    BigDecimal selectSumPaidFromLaunchBetween(@Param("now") Date now, @Param("clientId") Long clientId);

    /**
     * 订单存在禁止退款的卡券
     * @param orderCode
     * @return
     */
    @Select("select count(*) " +
            "FROM pos_sale_detail psd " +
            "INNER JOIN card_coupon_code ccc ON ccc.order_code = psd.order_code AND ccc.delete_flag = 0 " +
            "INNER JOIN card_coupon_launch ccl ON ccl.id = ccc.launch_id AND ccl.delete_flag = 0 " +
            "INNER JOIN card_coupon cc ON  cc.id = ccl.coupon_id AND cc.delete_flag = 0 " +
            " where psd.order_code = #{orderCode} and cc.support_refund_flag = 0 and  psd.delete_flag = 0 ")
    int countForbidRefundByOrderCode(@Param("orderCode") String orderCode);

    // 第三方列表需要的sql
    Long countCardThirdCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter);

    List<ShowCouponCodeInfo> selectCardThirdCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter, @Param("pageUtil") PageUtil pageUtil);


}
