package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SysProjectMapper;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SysProject;
import com.joycity.joyclub.apiback.service.ProjectService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private final Log log = LogFactory.getLog(ProjectServiceImpl.class);
    @Autowired
    SysProjectMapper sysProjectMapper;

    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getList() {
        return new ResultData(new ListResult(sysProjectMapper.getList()));
    }

    @Override
    public ResultData getProject(Long id) {
        SysProject sysProject = sysProjectMapper.selectByPrimaryKey(id);
        if (sysProject == null || sysProject.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该项目信息不存在");
        return new ResultData(sysProject);
    }


    @Override
    public ResultData createProject(SysProject project) {
        sysProjectMapper.insertSelective(project);
        return new ResultData(new CreateResult(project.getId()));
    }

    @Override
    public ResultData updateProject(SysProject project) {
        return new ResultData(new UpdateResult(sysProjectMapper.updateByPrimaryKeySelective(project)));
    }

}
