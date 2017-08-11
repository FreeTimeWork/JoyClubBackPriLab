package com.joycity.joyclub.card_coupon.mapper;

import java.util.Date;
import java.util.List;

import com.joycity.joyclub.card_coupon.modal.*;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunch;
import com.joycity.joyclub.card_coupon.modal.generated.CardCouponLaunchExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by fangchen.chai on 2017/7/11.
 */
public interface CardCouponLaunchMapper extends BaseMapper<CardCouponLaunch, Long, CardCouponLaunchExample> {

    Long countByCouponNameAndCouponTypeAndStatus(@Param("couponName") String couponName, @Param("couponType") Integer couponType, @Param("name") String name, @Param("type") Integer type, @Param("status") Integer status, @Param("now") Date now, @Param("pageUtil") PageUtil pageUtil);

    List<ShowCouponLaunchInfo> selectByCouponNameAndCouponTypeAndStatus(@Param("couponName") String couponName, @Param("couponType") Integer couponType, @Param("name") String name, @Param("type") Integer type, @Param("status") Integer status, @Param("now") Date now, @Param("pageUtil") PageUtil pageUtil);

    @Update("update card_coupon_launch set delete_flag = 1, delete_time = now() where id = #{id}")
    int deleteCardCouponLaunchById(@Param("id") Long id);

    /**
     * 查找确认投放的数量
     * @param couponId
     * @return
     */
    @Select("select IFNULL(sum(launch_num),0) from card_coupon_launch where coupon_id = #{couponId} and confirm_flag = 1 and delete_flag = 0")
    int selectLaunchNumByCouponId(@Param("couponId") Long couponId);

    @Select("select count(*) from card_coupon_launch where coupon_id = #{couponId} and delete_flag = 0")
    int countCardCouponLaunchByCouponId(@Param("couponId") Long couponId);

    /**
     * 正在投放期间的卡券信息
     * @param id
     * @return
     */
    CouponLaunchWithCoupon selectLaunchWithCouponById(@Param("id") Long id);

    CreateCouponLaunchInfo selectCouponLaunchInfoById(@Param("id") Long id);

    /**
     * 条件投放期间没有重叠 返回0
     * @param launchStartTime
     * @param launchEndTime
     * @return
     */
    @Select("SELECT count(*) from card_coupon_launch where review_status = 1 AND confirm_flag = 1 AND type = 1 AND !((#{launchStartTime} > IFNULL(forbid_time,launch_end_time) AND #{launchEndTime} > IFNULL(forbid_time,launch_end_time)) OR ( #{launchStartTime} < launch_start_time AND #{launchEndTime} < launch_start_time)) ")
    int verifyConditionLaunch(@Param("launchStartTime") Date launchStartTime, @Param("launchEndTime") Date launchEndTime);

    /**
     * 根据 id 得到 该投放下应剩余的库存。
     */
    @Select("SELECT ccl.launch_num - count(ccc.id) FROM card_coupon_launch ccl INNER JOIN card_coupon_code ccc ON ccc.launch_id = ccl.id and ccc.delete_flag = 0 WHERE ccl.id = #{id} and ccl.delete_flag = 0")
    int selectInventoryNumById(@Param("id") Long id);

    //投放-商户承担金额列表页
    List<ShowShopRatio> selectShopRatioByLaunchId(@Param("launchId") Long launchId,@Param("shopName") String shopName, @Param("pageUtil") PageUtil pageUtil);
    Long countShopRatioByLaunchId(@Param("launchId") Long launchId,@Param("shopName") String shopName);



    // --------------------前段------------------------
    // 当前会员可领取列表
    Long countClientVisibleByCouponType(@Param("projectId") Long projectId, @Param("clientId") Long clientId, @Param("couponType") Byte couponType);
    List<ShowClientVisibleLaunchCoupon> selectClientVisibleByCouponType(@Param("projectId") Long projectId, @Param("clientId") Long clientId, @Param("couponType") Byte couponType, @Param("pageUtil") PageUtil pageUtil);

    //游客可以看到所有正在投放的列表
    Long countVisitorVisibleByCouponType(@Param("projectId") Long projectId, @Param("couponType") Byte couponType);
    List<ShowClientVisibleLaunchCoupon> selectVisitorVisibleByCouponType(@Param("projectId") Long projectId, @Param("couponType") Byte couponType, @Param("pageUtil") PageUtil pageUtil);

}
