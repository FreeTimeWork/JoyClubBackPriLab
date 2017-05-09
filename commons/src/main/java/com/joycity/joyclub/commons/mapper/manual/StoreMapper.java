package com.joycity.joyclub.commons.mapper.manual;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.modal.base.IdNamePortrait;
import com.joycity.joyclub.commons.modal.generated.SysStore;
import com.joycity.joyclub.commons.modal.generated.SysStoreExample;
import com.joycity.joyclub.commons.modal.store.StoreInfoPage;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public interface StoreMapper extends BaseMapper<SysStore, Long, SysStoreExample> {
    @Select("select id,name,contact_name,contact_phone,pickup_address,service_phone from sys_store where delete_flag=0 and project_id=#{id} order by id desc limit #{pageUtil.offset},#{pageUtil.pageSize}")
    List<SysStore> getListByProjectId(@Param("id") Long id, @Param("pageUtil")PageUtil pageUtil);

    @Select("select id,name,portrait from sys_store where id = #{id}")
    IdNamePortrait getSimpleInfo(Long id);

    @Select("select  id, project_id, head_img, portrait, intro, name, contact_name, contact_phone, pickup_address, service_phone from sys_store where id=#{id}")
    StoreInfoPage getInfo(Long id);

    List<IdNamePortrait> selectSimpleByFilter(@Param("projectId") Long projectId, @Param("pageUtil") PageUtil pageUtil);

}
