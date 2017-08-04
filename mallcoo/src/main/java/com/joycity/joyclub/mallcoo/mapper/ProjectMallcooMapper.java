package com.joycity.joyclub.mallcoo.mapper;

import com.joycity.joyclub.mallcoo.modal.ProjectMallcoo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by CallMeXYZ on 2017/6/6.
 */

public interface ProjectMallcooMapper {
    @Select("select id,project_id,app_id,public_key,private_key from sys_project_mallcoo where project_id = #{projectId} and delete_flag=0 limit 0,1")
    ProjectMallcoo getProjectMallcooInfo(@Param("projectId") Long projectId);
}
