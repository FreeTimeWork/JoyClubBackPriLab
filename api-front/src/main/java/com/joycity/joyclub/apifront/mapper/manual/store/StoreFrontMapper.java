package com.joycity.joyclub.apifront.mapper.manual.store;

import com.joycity.joyclub.apifront.modal.base.IdNamePortrait;
import com.joycity.joyclub.apifront.modal.store.Store;
import com.joycity.joyclub.apifront.modal.store.StoreInfo;
import com.joycity.joyclub.apifront.modal.store.StoreInfoPage;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface StoreFrontMapper {
    @Select("select id,name,portrait from sys_store where id = #{id}")
    IdNamePortrait getSimpleInfo(Long id);

    @Select("select  id, project_id, head_img, portrait, intro, name, contact_name, contact_phone, pickup_address, service_phone from sys_store where id=#{id}")
    StoreInfoPage getInfo(Long id);

    List<IdNamePortrait> selectSimpleByFilter(@Param("projectId") Long projectId, @Param("pageUtil") PageUtil pageUtil);
}
