package com.joycity.joyclub.commons.service.impl;


import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.mapper.manual.SysProductCategoryMapper;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.modal.generated.SysProductCategory;
import com.joycity.joyclub.commons.modal.generated.SysProductCategoryExample;
import com.joycity.joyclub.commons.service.ProductCategoryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final Log log = LogFactory.getLog(ProductCategoryServiceImpl.class);
    @Autowired
    SysProductCategoryMapper sysProductCategoryMapper;

    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getList() {
        SysProductCategoryExample example = new SysProductCategoryExample();
        SysProductCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo(false);
        return new ResultData(new ListResult(sysProductCategoryMapper.selectByExample(example)));
    }

    @Override
    public ResultData getProductCategory(Long id) {
        SysProductCategory sysProductCategory = sysProductCategoryMapper.selectByPrimaryKey(id);
        if (sysProductCategory == null || sysProductCategory.getDeleteFlag())
            throw new BusinessException(DATA_NOT_EXIST, "该分类不存在");
        return new ResultData(sysProductCategory);
    }


    @Override
    public ResultData createProductCategory(SysProductCategory productCategory) {
       sysProductCategoryMapper.insertSelective(productCategory);
        return new ResultData(new CreateResult(productCategory.getId()));
    }

    @Override
    public ResultData updateProductCategory(Long id, SysProductCategory productCategory) {
        return new ResultData(new UpdateResult(sysProductCategoryMapper.updateByPrimaryKeySelective(productCategory)));
    }


}
