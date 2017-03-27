package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SysProjectExample;
import com.joycity.joyclub.apiback.mapper.account.ManagerMapper;
import com.joycity.joyclub.apiback.mapper.generated.SysProjectMapper;
import com.joycity.joyclub.apiback.service.ProjectService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joycity.joyclub.apiback.constant.ResultCode.SUCCESS;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    private final Log log= LogFactory.getLog(ProjectServiceImpl.class);
    @Autowired
    SysProjectMapper sysProjectMapper;
    @Autowired
    ManagerMapper managerMapper;

    @Override
    public ResultData getList() {

        ResultData result = new ResultData(SUCCESS);
        SysProjectExample example = new SysProjectExample();
        SysProjectExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo(false);
        result.setData(sysProjectMapper.selectByExample(example));
        return result;
    }

    /*@Override*/
    public ResultData getManagers(Long managerId) {
        return null;
    }
}
