package com.joycity.joyclub.apifront.mapper.manual.act;

import com.joycity.joyclub.apifront.modal.act.ActInfoPage;
import com.joycity.joyclub.apifront.modal.act.ActSimpleWithAddress;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface ActFrontMapper {
    @Select("select  id, store_id,category_id, name, base_price, portrait, carousel, refund_forbid_flag, reserve_need_flag, delivery_flag,html_content from sale_act where id =#{id}")
    ActInfoPage getInfo(Long id);

    // TODO: 2017/4/13 项目搜索未加进去！！！
    List<ActSimpleWithAddress> selectByStore(@Param("storeId") Long storeId);

}
