package com.joycity.joyclub.apifront.mapper.manual.cart;

import com.joycity.joyclub.apifront.modal.cart.ClientPostAddress;
import com.joycity.joyclub.apifront.modal.cart.ClientPostAddressExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
public interface PostAddressMapper extends BaseMapper<ClientPostAddress, Long, ClientPostAddressExample> {
    @Select("select id ,client_id,name,address,postcode,phone,last_use_time from client_post_address where  client_id = #{clientId} and delete_flag=0 order by last_use_time desc")
    List<ClientPostAddress> getList(Long clientId);
    /**
     * 设置delete_flag并更新delete_time
     */
    @Update("update client_post_address set delete_flag=1,delete_time = now() where id = #{id}")
    int removeById(Long id);

    @Update("update client_post_address set last_use_time = now() where id = #{id}")
    int setLastUseTimeNow(Long id);
}

