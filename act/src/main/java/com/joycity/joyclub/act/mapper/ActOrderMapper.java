package com.joycity.joyclub.act.mapper;

import com.joycity.joyclub.act.modal.ActOrderForBack;
import com.joycity.joyclub.act.modal.MyActOrder;
import com.joycity.joyclub.act.modal.generated.SaleActOrder;
import com.joycity.joyclub.act.modal.generated.SaleActOrderExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/5/17.
 */
public interface ActOrderMapper extends BaseMapper<SaleActOrder,Long,SaleActOrderExample> {
    Long countForStore(@Param("projectId") Long projectId,
                       @Param("status") Byte status,
                       @Param("code") String code,
                       @Param("name") String name,
                       @Param("phone") String phone,
                       @Param("actName") String actName,
                       @Param("pageUtil") PageUtil pageUtil);

    List<ActOrderForBack> selectForStore(@Param("projectId") Long projectId,
                                         @Param("status") Byte status,
                                         @Param("code") String code,
                                         @Param("name") String name,
                                         @Param("phone") String phone,
                                         @Param("actName") String actNameLike,
                                         @Param("pageUtil") PageUtil pageUtil);
    /////////////////////////////////////////////////////////
    @Select("select id from sale_act_order where code=#{code}")
    Long getIdByCode(String code);

    /**
     * 仅针对纯积分支付
     *
     * @param code
     * @return
     */
    @Update("update sale_act_order set status =2 , pay_time=now() where code=#{code}")
    Integer setPayedByCode(String code);

    @Update("update sale_act_order set out_pay_code =#{outPayCode} where code=#{code}")
    Integer setOutPayCodeByCode(@Param("code") String code, @Param("outPayCode") String outPayCode);

    @Update("update sale_act_order set status =2 , pay_time=now() where id=#{id}")
    Integer setPayedById(Long id);

    @Update("update sale_act_order set out_pay_code =#{outPayCode},pay_type=#{payType} where id=#{id}")
    Integer setOutPayCodeById(@Param("id") Long id, @Param("outPayCode") String outPayCode,@Param("payType") Byte payType);


    @Update("update sale_act_order set status = 1, canceler=#{cancelType},cancel_time=now() where id=#{id}")
    Integer cancelOrder(@Param("id") Long id, @Param("cancelType") Byte cancelType);
    @Update("update sale_act_order set status = 4,check_time=now() where id=#{id}")
    Integer checkOrder(@Param("id") Long id);

    @Select("select id from sale_act_order where status = 0 and hour(timediff(now(),create_time))>=1")
    List<Long> getOneHourNotPayedOrder();

    /**
     *
     * @param status 可以为null
     * @return
     */
    Long countMyActOrders(@Param("projectId") Long projectId,@Param("clientId") Long clientId,@Param("status") Byte status);

    /**
     *
     * @param status 可以为null
     * @return
     */
    List<MyActOrder> selectMyActOrders(@Param("projectId") Long projectId, @Param("clientId") Long clientId,@Param("status") Byte status, @Param("pageUtil")PageUtil pageUtil);

    /**
     * 根据 attrId 找到该活动已经申请多少人。
     */
    @Select("select sum(num) from sale_act_order where attr_id = #{attrId} and (status = 2 or status = 4)")
    Integer countActOrderByAttrId(@Param("attrId") Long attrId);

}
