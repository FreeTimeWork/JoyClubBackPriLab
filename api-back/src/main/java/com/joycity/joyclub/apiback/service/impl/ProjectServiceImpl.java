package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SysProjectMapper;
import com.joycity.joyclub.apiback.modal.base.CreateResult;
import com.joycity.joyclub.apiback.modal.base.DataListResult;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SysProject;
import com.joycity.joyclub.apiback.service.ProjectService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joycity.joyclub.apiback.constant.ResultCode.DATA_NOT_EXIST;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private final Log log = LogFactory.getLog(ProjectServiceImpl.class);
    @Autowired
    SysProjectMapper sysProjectMapper;

    /**
     * @return resultData, data为按时间倒序的所有项目列表
     */
    @Override
    public ResultData getList() {
        return new ResultData(new DataListResult(sysProjectMapper.getList()));
    }

    @Override
    public ResultData getProject(Long id) {
        SysProject sysProject = sysProjectMapper.selectByPrimaryKey(id);
        if (sysProject == null || sysProject.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该项目信息不存在");
        return new ResultData(sysProject);
    }


    @Override
    public ResultData createProject(SysProject project) {
        long id = sysProjectMapper.insertSelective(project);
        return new ResultData(new CreateResult(id));
    }

    @Override
    public ResultData updateProject(Long id, SysProject project) {
        //防止不必要的数据更新
        project.setDeleteFlag(null);
        project.setLastUpdate(null);
        project.setCreateTime(null);
        project.setLastUpdate(null);
        return new ResultData(new UpdateResult(sysProjectMapper.updateByPrimaryKeySelective(project)));
    }

    /*@Override*/
    public ResultData getManagers(Long managerId) {
        return null;
    }
}
