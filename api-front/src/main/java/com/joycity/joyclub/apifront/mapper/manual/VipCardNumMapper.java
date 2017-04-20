package com.joycity.joyclub.apifront.mapper.manual;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 卡号管理
 * 对应 sys_vip_card_num
 * Created by CallMeXYZ on 2017/4/10.
 */
public interface VipCardNumMapper {
    @Select("select min(num) from sys_vip_card_num where project_id=#{projectId} and type=#{type} and status=1 and delete_flag=0")
    Long getMinCardNumNotUsed(@Param("projectId") Long projectId, @Param("type") String type);

    @Update("update sys_vip_card_num set status=#{status} where num=#{num}")
    int setCardStatus(@Param("num") Long num, @Param("status") Byte status);

    /**
     * 设置卡为使用状态，并且设置useTime为now
     * @param num
     * @return
     */
    @Update("update sys_vip_card_num set status=2,use_time=now() where num=#{num}")
    int setCardStatusUsed(@Param("num") Long num);
    /**
     * 设置卡为未使用状态
     * @param num
     * @return
     */
    @Update("update sys_vip_card_num set status=1,use_time=null where num=#{num}")
    int setCardStatusNOtUsed(@Param("num") Long num);
}
