package com.joycity.joyclub.apifront.mapper.manual;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/4/16.
 */
public interface ClientLoginLogMapper {
    @Insert("insert into client_login_log(client_id,project_id,`from`) values(#{clientId},#{projectId},#{from})")
    void addLog(@Param("clientId") Long clientId, @Param("projectId") Long projectId, @Param("from") String from);

    @Insert("insert into client_login_log(client_id,project_id,sub_project_id,`from`) values(#{clientId},#{projectId},#{subProjectId},#{from})")
    void addLogWithSubProject(@Param("clientId") Long clientId, @Param("projectId") Long projectId, @Param("subProjectId") Long subProjectId, @Param("from") String from);
}
