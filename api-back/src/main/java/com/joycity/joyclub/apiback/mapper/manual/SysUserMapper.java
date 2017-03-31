package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.apiback.mapper.BaseMapper;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.modal.generated.SysUserExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public interface SysUserMapper extends BaseMapper<SysUser, Long, SysUserExample> {
    /**
     * 区分账户大小写
     * @param account
     * @return
     */
    @Select("select id,info_id,account,password,type,sub_type,auth_type,forbid_flag from sys_user where binary account=#{account} and delete_flag=0 limit 0,1")
    SysUser getByAccount(String account);

    @Select("select id,info_id,account,type,sub_type,auth_type,forbid_flag,forbid_time,remark from sys_user where  type=#{type} and info_id=#{infoId} and delete_flag=0 order by id desc")
    List<SysUser> getManagersByUserTypeAndInfoId(@Param("type") Integer type,@Param("infoId") Long infoId);
}
