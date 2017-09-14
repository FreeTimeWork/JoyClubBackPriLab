package com.joycity.joyclub.act.mapper;

import com.joycity.joyclub.act.modal.ActTypeWithAct;
import com.joycity.joyclub.act.modal.generated.SaleActType;
import com.joycity.joyclub.act.modal.generated.SaleActTypeExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/9/14
 */
public interface SaleActTypeMapper extends BaseMapper<SaleActType,Long,SaleActTypeExample> {

    Long countList();
    List<ActTypeWithAct> selectList(@Param("limit") Boolean limit, @Param("pageUtil") PageUtil pageUtil);

}
