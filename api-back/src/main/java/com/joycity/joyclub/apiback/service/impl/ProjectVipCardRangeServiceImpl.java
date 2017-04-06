package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.constant.ResultCode;
import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SysProjectVipCardRangeMapper;
import com.joycity.joyclub.apiback.modal.base.CreateResult;
import com.joycity.joyclub.apiback.modal.base.DataListResult;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SysProjectVipCardRange;
import com.joycity.joyclub.apiback.modal.generated.SysProjectVipCardRangeExample;
import com.joycity.joyclub.apiback.service.ProjectVipCardRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
@Service
public class ProjectVipCardRangeServiceImpl implements ProjectVipCardRangeService {
    @Autowired
    SysProjectVipCardRangeMapper vipCardRangeMapper;

    @Override
    public ResultData getRanges(long projectId) {
        return new ResultData(new DataListResult(vipCardRangeMapper.selectByProjectId(projectId)));
    }

    @Override
    public ResultData addRange(long projectId, String type, long min, long max) {
        if(min>=max) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "号段范围出错");
        }
        //先查看对应项目和卡类型的号段有没有被设置
        SysProjectVipCardRangeExample example = new SysProjectVipCardRangeExample();
        SysProjectVipCardRangeExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        criteria.andTypeEqualTo(type);
        vipCardRangeMapper.countByExample(example);
        long existNum = vipCardRangeMapper.countByExample(example);
        if (existNum != 0) {
            throw new BusinessException(ResultCode.DATA_ALREADY_EXIST, "已经为该卡类型设置过号段");
        }
        //再看号段有没有重叠
        long overlapNum = vipCardRangeMapper.countByRangeOverlap(min, max);
        if (overlapNum != 0) {
            throw new BusinessException(ResultCode.DATA_NOT_PERMIT, "该号段与已有的号段重叠");
        }
        // TODO: 2017/4/6 考虑是否应该验证projectId的存在性和type的合法性
        SysProjectVipCardRange range = new SysProjectVipCardRange();
        range.setProjectId(projectId);
        range.setType(type);
        range.setMin(min);
        range.setMax(max);
        vipCardRangeMapper.insertSelective(range);
        return new ResultData(new CreateResult(range.getId()));
    }

    @Override
    public ResultData updateRange(long id, long min, long max) {
        SysProjectVipCardRangeExample example = new SysProjectVipCardRangeExample();
        SysProjectVipCardRangeExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andDeleteFlagEqualTo(false);
        long existNum = vipCardRangeMapper.countByExample(example);
        if (existNum != 1) {
            throw new BusinessException(ResultCode.DATA_ALREADY_EXIST, "该号段不存在");

        }
        SysProjectVipCardRange range = new SysProjectVipCardRange();
        range.setId(id);
        range.setMin(min);
        range.setMax(max);
        return new ResultData(new UpdateResult(vipCardRangeMapper.updateByPrimaryKeySelective(range)));
    }
}
