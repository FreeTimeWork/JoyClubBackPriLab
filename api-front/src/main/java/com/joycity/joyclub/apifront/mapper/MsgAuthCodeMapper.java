package com.joycity.joyclub.apifront.mapper;

import com.joycity.joyclub.apifront.modal.MsgAuthCode;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
public interface MsgAuthCodeMapper {
    @Select("select count(*) from sys_msg_auth_code where phone=#{phone} and Date(create_time)=CurDate()")
    int getTodayNumByPhone(String phone);

    @Insert("insert into sys_msg_auth_code(phone,code) values(#{phone},#{code})")
    void addCode(@Param("phone") String phone, @Param("code") String code);

    @Select("select phone,code,create_time form sys_msg_auth_code where phone=#{phone} order by create_time desc limit 0,1")
    MsgAuthCode getLatestCodeByPhone(String phone);
}
