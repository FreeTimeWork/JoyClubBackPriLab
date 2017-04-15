package com.joycity.joyclub.apifront.mapper.manual;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.apifront.modal.client.Client;
import com.joycity.joyclub.apifront.modal.client.ClientExample;
import org.apache.ibatis.annotations.Select;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ClientUserMapper extends BaseMapper<Client, Long, ClientExample> {
    @Select("select id from client where tel=#{tel}")
    Long getIdByTel(String tel);
}
