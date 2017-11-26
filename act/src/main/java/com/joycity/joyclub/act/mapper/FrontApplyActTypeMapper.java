package com.joycity.joyclub.act.mapper;

import com.joycity.joyclub.act.modal.generated.FrontApplyActType;
import com.joycity.joyclub.act.modal.generated.FrontApplyActTypeExample;
import com.joycity.joyclub.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/9/15
 */
public interface FrontApplyActTypeMapper extends BaseMapper<FrontApplyActType,Long,FrontApplyActTypeExample> {

    List<FrontApplyActType> selectList(@Param("projectId") Long projectId);
}
