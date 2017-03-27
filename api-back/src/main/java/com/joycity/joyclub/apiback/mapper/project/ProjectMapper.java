package com.joycity.joyclub.apiback.mapper.project;

import com.joycity.joyclub.apiback.mapper.BaseMapper;
import com.joycity.joyclub.apiback.modal.generated.SysProject;
import com.joycity.joyclub.apiback.modal.generated.SysProjectExample;
import org.springframework.stereotype.Repository;

/**
 * Created by CallMeXYZ on 2017/3/7.
 */
@Repository
public interface ProjectMapper extends BaseMapper<SysProject,Long,SysProjectExample> {
}
