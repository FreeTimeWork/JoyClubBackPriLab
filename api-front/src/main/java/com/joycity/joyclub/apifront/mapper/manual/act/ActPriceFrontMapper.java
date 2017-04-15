package com.joycity.joyclub.apifront.mapper.manual.act;

import org.apache.ibatis.annotations.Select;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface ActPriceFrontMapper {

    /**
     * 获得商品现在的价格，可能会不存在
     * @param actId
     * @return
     */
    @Select("select price from sale_act_price where delete_flag=0 and  act_id=#{actId} and forbid_flag=false and review_status=1 and start_time<=now() and end_time>=now() limit 0,1 ")
    Integer getNowPrice(Long actId);
}
