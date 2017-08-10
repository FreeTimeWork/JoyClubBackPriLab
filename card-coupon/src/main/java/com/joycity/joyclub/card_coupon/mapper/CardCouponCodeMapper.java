package com.joycity.joyclub.card_coupon.mapper;

import com.joycity.joyclub.card_coupon.modal.*;
import com.joycity.joyclub.card_coupon.modal.filter.ShowCouponCodeFilter;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCode;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponCodeExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import com.joycity.joyclub.mallcoo.modal.result.data.JoinShop;
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

    @Select("select ccc.id,cc.type as coupon_type  from card_coupon_code ccc inner join card_coupon_launch ccl on ccl.id = ccc.launch_id and ccl.delete_flag = 0 " +
            "inner join card_coupon cc on cc.id = ccl.coupon_id and cc.delete_flag = 0 where ccc.order_code = #{orderCode} and ccc.delete_flag = 0")
    List<CouponCodeWithCoupon> selectCouponCodeWithCouponByOrderCode(@Param("orderCode") String orderCode);

    /**
     * 某个用户已领过该投放的卡券数量(线上投放类型)。
     */
    @Select("select count(*) from card_coupon_code ccc INNER JOIN card_coupon_launch ccl ON ccl.id = ccc.launch_id AND ccl.delete_flag = 0 WHERE ccc.launch_id = #{launchId} and ccc.client_id = #{clientId} AND ccl.type = 2 AND ccc.delete_flag = 0")
    int checkOnlineCouponCodeNum(@Param("launchId") Long launchId, @Param("clientId") Long clientId);

    /**
     * 某个用户一段时间已领过该投放的卡券数量(条件投放类型)。
     */
    @Select("select count(*) from card_coupon_code ccc INNER JOIN card_coupon_launch ccl ON ccl.id = ccc.launch_id AND ccl.delete_flag = 0 WHERE ccc.launch_id = #{launchId} and ccc.client_id = #{clientId} and ccc.receive_time > #{startTime} and ccc.receive_time < #{endTime}  AND ccl.type = 1 AND ccc.delete_flag = 0")
    int todayConditionCouponCodeNum(@Param("launchId") Long launchId, @Param("clientId") Long clientId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

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
     * @params tel
     * @param vipCode
     * @return
     */
    List<ShowPosCurrentCouponCodeInfo> selectCurrentCouponCode(@Param("projectId") Long projectId, @Param("shopCode") String shopCode, @Param("tel") String tel, @Param("vipCode") String vipCode);

    /**
     * 在条件投放期间内，正在投放中，在触发的商户范围内,卡券的基本信息
     */
    @Select(
            " SELECT ccl.id as launch_id, ccl.condition_amount, ccl.max_receive, cc.subtract_amount   " +
            " FROM card_coupon_launch ccl" +
            " inner join card_coupon cc on cc.id = ccl.coupon_id and cc.type != 3 and cc.delete_flag = 0 " +
            " INNER JOIN card_coupon_trigger_scope ccts ON ccts.launch_id = ccl.id "+
            " WHERE #{now} > ccl.launch_start_time AND #{now} < IFNULL(ccl.forbid_time,ccl.launch_end_time) and ccts.store_id = #{shopId} and ccl.type = 1 and ccl.confirm_flag = 1 and ccl.review_status = 1 and ccl.delete_flag = 0")
    CouponLaunchBetweenInfo selectInfoFromLaunchBetween(@Param("shopId")Long shopId,@Param("now") Date now);

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
    List<Long> selectNotUsedCashCouponCodeIdFromLaunchBetween(@Param("now") Date now, @Param("clientId") Long clientId, @Param("num") Integer num);

    /**
     * 在条件投放的期间内，触发的店铺范围内,该会员，所有订单的最终实际付款总额
     * @return
     */
    @Select(" SELECT	sum(psd.balance) FROM	pos_sale_detail psd"+
            " INNER JOIN ("+
            "       SELECT ccl.id,ccl.launch_start_time,IFNULL(ccl.forbid_time,ccl.launch_end_time) as launch_end_time " +
            "       FROM card_coupon_launch ccl " +
            "       INNER JOIN card_coupon cc ON cc.id = ccl.coupon_id  " +
            "       WHERE #{now} > ccl.launch_start_time AND #{now} < IFNULL(ccl.forbid_time,ccl.launch_end_time) and ccl.type = 1  " +
            "       and ccl.confirm_flag = 1 and ccl.review_status = 1 and ccl.delete_flag = 0"+
            "        ) launch_time ON psd.create_time > launch_time.launch_start_time"+
            " AND psd.create_time < launch_time.launch_end_time"+
            " INNER JOIN card_coupon_trigger_scope ccts ON ccts.launch_id = launch_time.id"+
            "        WHERE "+
            " psd.client_id = #{clientId} AND ccts.store_id = psd.shop_id and psd.delete_flag = 0")
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

    // 只查第三方券
    Long countCardThirdCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter);
    List<ShowCouponCodeInfo> selectCardThirdCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter, @Param("pageUtil") PageUtil pageUtil);

    // 只查系统券
    Long countSysCardCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter);
    List<ShowCouponCodeInfo> selectSysCardCouponCodeByFilter(@Param("projectId") Long projectId, @Param("filter") ShowCouponCodeFilter filter, @Param("pageUtil") PageUtil pageUtil);

    // 用户券包
    List<ClientCouponBag> selectCurrentClientCouponUsable(@Param("clientId") Long clientId);
    List<CouponSimpleInfoInBag> selectClientAvailableCoupon(@Param("clientId") Long clientId);
    CouponInfoInBag selectCouponInfoByCodeId(@Param("id") Long id);
    @Select("select s.name Name,s.id ID from card_coupon_store_scope c join sys_shop s on c.store_id=s.id where s.coupon_id = #{id}")
    List<JoinShop> selectJoinShopsByCouponId(@Param("id") Long id);

}
