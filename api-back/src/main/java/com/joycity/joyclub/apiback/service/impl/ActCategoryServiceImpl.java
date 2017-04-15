package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SysActCategoryMapper;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.DataListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SysActCategory;
import com.joycity.joyclub.apiback.modal.generated.SysActCategoryExample;
import com.joycity.joyclub.apiback.service.ActCategoryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joycity.joyclub.apiback.constant.ResultCode.DATA_NOT_EXIST;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class ActCategoryServiceImpl implements ActCategoryService {
    private final Log log = LogFactory.getLog(ActCategoryServiceImpl.class);
    @Autowired
    SysActCategoryMapper sysActCategoryMapper;

    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getList() {
        SysActCategoryExample example = new SysActCategoryExample();
        SysActCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo(false);
        return new ResultData(new DataListResult(sysActCategoryMapper.selectByExample(example)));
    }

    @Override
    public ResultData getActCategory(Long id) {
        SysActCategory sysActCategory = sysActCategoryMapper.selectByPrimaryKey(id);
        if (sysActCategory == null || sysActCategory.getDeleteFlag())
            throw new BusinessException(DATA_NOT_EXIST, "该分类不存在");
        return new ResultData(sysActCategory);
    }


    @Override
    public ResultData createActCategory(SysActCategory actCategory) {
       sysActCategoryMapper.insertSelective(actCategory);
        return new ResultData(new CreateResult(actCategory.getId()));
    }

    @Override
    public ResultData updateActCategory(Long id, SysActCategory actCategory) {
        return new ResultData(new UpdateResult(sysActCategoryMapper.updateByPrimaryKeySelective(actCategory)));
    }


}
