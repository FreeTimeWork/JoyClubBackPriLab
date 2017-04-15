package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.apiback.modal.generated.SysStore;
import com.joycity.joyclub.apiback.modal.generated.SysStoreExample;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public interface SysStoreMapper extends BaseMapper<SysStore, Long, SysStoreExample> {
    @Select("select id,name,contact_name,contact_phone,pickup_address,service_phone from sys_store where delete_flag=0 and project_id=#{id} order by id desc limit #{pageUtil.offset},#{pageUtil.pageSize}")
    List<SysStore> getListByProjectId(@Param("id") Long id, @Param("pageUtil")PageUtil pageUtil);
}
