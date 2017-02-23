package com.joycity.joyclub.api.mapper.manual;



import com.joycity.joyclub.api.entity.VipUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by amosc on 2017/2/20.
 */
@Repository
public interface VipUserMapper {
@Select("select id_ id,virprice_ point,card_no_ cardNum,vip_code_ vipNum,realname_ name from sys_user ")
    List<VipUser> getList();
}
