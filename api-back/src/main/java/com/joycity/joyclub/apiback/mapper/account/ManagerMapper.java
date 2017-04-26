package com.joycity.joyclub.apiback.mapper.account;

import com.joycity.joyclub.apiback.modal.account.Manager;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Repository
public interface ManagerMapper {
  /*  *//**
     * 根据sys_user 获取管理账户
     *
     * @param type   可以为null
     * @param infoId 可以为null
     * @return
     *//*
    List<Manager> getList(@Param("type") Integer type, @Param("infoId") Long infoId);*/

    @Select("select count(*) from sys_user where binary account=#{account}")
    Long getUserNumByAccount(@Param("account") String account);

}
