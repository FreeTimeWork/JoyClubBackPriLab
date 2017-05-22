package com.joycity.joyclub.commons.mapper.manual;

import com.joycity.joyclub.commons.mapper.BaseMapper;

import com.joycity.joyclub.commons.modal.base.IdName;
import com.joycity.joyclub.commons.modal.generated.SysActCategory;
import com.joycity.joyclub.commons.modal.generated.SysActCategoryExample;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SysActCategoryMapper extends BaseMapper<SysActCategory, Long, SysActCategoryExample> {
    @Select("select id,name from sys_act_category where delete_flag=0 order by id desc")
    List<IdName> getSimpleList();
}
