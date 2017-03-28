package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.apiback.mapper.BaseMapper;
import com.joycity.joyclub.apiback.modal.generated.SysProject;
import com.joycity.joyclub.apiback.modal.generated.SysProjectExample;
import com.joycity.joyclub.apiback.modal.generated.SysUser;
import com.joycity.joyclub.apiback.modal.generated.SysUserExample;
import com.joycity.joyclub.apiback.modal.project.ProjectInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public interface SysProjectMapper extends BaseMapper<SysProject, Long, SysProjectExample> {
    /**
     * @return 按时间倒序的所有项目列表
     */
    @Select("select id,name,case type when 1 then '商业项目' when 2 then '写字楼项目' when 3 then '地产项目' when 10 then '第三方合作项目' else '其他' end typeName,contact_name,contact_phone from sys_project where delete_flag=0 order by id desc")
    List<ProjectInfo> getList();
}
