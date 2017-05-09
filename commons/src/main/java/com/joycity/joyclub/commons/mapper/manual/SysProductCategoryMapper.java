package com.joycity.joyclub.commons.mapper.manual;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.modal.base.IdName;
import com.joycity.joyclub.commons.modal.generated.SysProductCategory;
import com.joycity.joyclub.commons.modal.generated.SysProductCategoryExample;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SysProductCategoryMapper extends BaseMapper<SysProductCategory, Long, SysProductCategoryExample> {
    @Select("select id,name from sys_product_category where delete_flag=0 order by id desc")
    List<IdName> getSimpleList();
}
