package com.joycity.joyclub.apiback.mapper.manual;


import com.joycity.joyclub.apiback.modal.VipCard;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by amosc on 2017/2/20.
 */
@Repository
public interface VipCardMapper {
@Select("select id_ id,batch_ batch,status_ cardStatus,cardnum_ cardNum,type_ cardStatus,storeid_ storeId from sys_vip_cardnum ")
    List<VipCard> getList();
}
