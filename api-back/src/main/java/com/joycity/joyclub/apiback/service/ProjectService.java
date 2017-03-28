package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SysProject;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
public interface ProjectService {
    /**
     * @return data为按时间倒序的所有项目列表
     */
    ResultData getList();

    /**
     * 返回某个项目的具体信息
     * @param id
     * @return
     */
    ResultData getProject(Long id);
    ResultData updateProject(Long id,SysProject project);
    ResultData createProject(SysProject project);
}
