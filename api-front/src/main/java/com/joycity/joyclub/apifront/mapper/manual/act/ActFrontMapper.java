package com.joycity.joyclub.apifront.mapper.manual.act;

import com.joycity.joyclub.apifront.modal.act.ActInfoPage;
import com.joycity.joyclub.apifront.modal.act.ActSimpleWithAddress;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface ActFrontMapper {
    @Select("select  id, store_id,category_id, name, base_price, portrait, carousel, refund_forbid_flag, reserve_need_flag, delivery_flag,html_content from sale_act where id =#{id}")
    ActInfoPage getInfo(Long id);

    /**
     * projectId,storeId都可以为空
     * 都空是查询所有
     */
    List<ActSimpleWithAddress> selectByProjectOrStore(@Param("projectId") Long projectId, @Param("storeId") Long storeId, @Param("pageUtil") PageUtil pageUtil);

}
