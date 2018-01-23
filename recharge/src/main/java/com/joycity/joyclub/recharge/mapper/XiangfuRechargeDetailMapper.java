package com.joycity.joyclub.recharge.mapper;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.recharge.modal.filter.XiangfuRechargeDetailFilter;
import com.joycity.joyclub.recharge.modal.generated.XiangfuRechargeDetail;
import com.joycity.joyclub.recharge.modal.generated.XiangfuRechargeDetailExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @auther fangchen.chai ON 2017/11/21
 */
public interface XiangfuRechargeDetailMapper extends BaseMapper<XiangfuRechargeDetail, Long, XiangfuRechargeDetailExample> {

    @Select("select id from xiangfu_recharge_detail where order_code = #{orderCode}")
    Long selectIdByXiangfuOrderCode(@Param("orderCode") String orderCode);

    Integer selectRechargeNumOnMonth(@Param("filter") XiangfuRechargeDetailFilter filter);

}
