package com.joycity.joyclub.apiback.mapper.manual;

import com.joycity.joyclub.commons.mapper.BaseMapper;
import com.joycity.joyclub.apiback.modal.ProjectVipCardRangeInfo;
import com.joycity.joyclub.apiback.modal.generated.SysProjectVipCardRange;
import com.joycity.joyclub.apiback.modal.generated.SysProjectVipCardRangeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface SysProjectVipCardRangeMapper extends BaseMapper<SysProjectVipCardRange, Long, SysProjectVipCardRangeExample> {

    /**
     * 搜索号段重叠的数量
     *
     * @param min
     * @param max
     * @return
     */
    @Select("select count(*) from sys_project_vip_card_range where (min>=#{min} and min<=#{max}) or (max>=#{min} and max<=#{max})")
    long countByRangeOverlap(@Param("min") long min, @Param("max") long max);
    @Select("select id,project_id,type,case type when '01' then '缤纷卡' when '02' then '璀璨' when '74' then '电子卡' else '其他' end typeName,min,max from sys_project_vip_card_range  where project_id = #{projectId}")

    List<ProjectVipCardRangeInfo> selectByProjectId(long projectId);
}
