package com.joycity.joyclub.apiback.mapper;

import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.modal.generated.SysUserExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public interface SysUserMapper extends BaseMapper<SysUser, Long, SysUserExample> {
    /**
     * 区分账户大小写
     * @param account
     * @return
     */
    @Select("select id,info_id,account,password,type,sub_type,project_type,forbid_flag from sys_user where binary account=#{account}")
    SysUser getByAccountAndPwd(String account);
}
