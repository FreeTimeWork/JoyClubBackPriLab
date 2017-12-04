package com.joycity.joyclub.recharge.mapper;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.recharge.modal.generated.RtmRechargeOrderDetail;
import com.joycity.joyclub.recharge.modal.generated.RtmRechargeOrderDetailExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RtmRechargeOrderDetailMapper extends BaseMapper<RtmRechargeOrderDetail,Long,RtmRechargeOrderDetailExample>{

    @Select("select * from rtm_recharge_order_detail where out_order = #{rtmOrderCode}")
    RtmRechargeOrderDetail selectRtmByRtmOrderCode(@Param("rtmOrderCode") String rtmOrderCode);

}
