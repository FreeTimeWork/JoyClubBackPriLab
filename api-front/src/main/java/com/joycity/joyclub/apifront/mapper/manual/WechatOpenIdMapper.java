package com.joycity.joyclub.apifront.mapper.manual;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
public interface WechatOpenIdMapper {
    @Select("select open_id,project_id,client_id from client_wx_openid where project_id=#{projectId} and client_id=#{clientId} limit 0,1")
    String getOpenId(@Param("projectId") Long projectId, @Param("clientId") Long clientId);


    @Insert("insert into client_wx_openid(open_id,project_id,client_id) values(#{openId} ,#{projectId},#{clientId})")
    void saveOpenId(@Param("projectId") Long projectId, @Param("clientId") Long clientId, @Param("openId") String openId);
    @Update("update  client_wx_openid set open_id = #{openId} where project_id=#{projectId} and client_id=#{clientId}")

    void updateOpenId(@Param("projectId") Long projectId, @Param("clientId") Long clientId, @Param("openId") String openId);
}
