package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SaleStoreDesignerMapper;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.DataListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SaleStoreDesignerExample;
import com.joycity.joyclub.apiback.modal.generated.SaleStoreDesignerWithBLOBs;
import com.joycity.joyclub.apiback.service.DesignerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.joycity.joyclub.apiback.constant.ResultCode.DATA_NOT_EXIST;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class DesignerServiceImpl implements DesignerService {
    private final Log log = LogFactory.getLog(DesignerServiceImpl.class);
    @Autowired
    SaleStoreDesignerMapper designerMapper;


    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getListByStoreId(Long storeId) {
        DataListResult dataListResult = new DataListResult();
        SaleStoreDesignerExample example = new SaleStoreDesignerExample();
        SaleStoreDesignerExample.Criteria criteria = example.createCriteria();
        criteria.andStoreIdEqualTo(storeId);
        criteria.andDeleteFlagEqualTo(false);

        long sum = designerMapper.countByExample(example);
        ;
        if (sum == 0) {
            dataListResult.setList(new ArrayList());
        } else {
            example.setOrderByClause("id desc");
            dataListResult.setList(designerMapper.selectByExample(example));
        }
        return new ResultData(dataListResult);
    }

    @Override
    public ResultData getDesigner(Long id) {
        SaleStoreDesignerWithBLOBs designer = designerMapper.selectByPrimaryKey(id);
        if (designer == null || designer.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该商品不存在");
        return new ResultData(designer);
    }


    @Override
    public ResultData createDesigner(SaleStoreDesignerWithBLOBs designer) {
        designerMapper.insertSelective(designer);
        return new ResultData(new CreateResult(designer.getId()));
    }


    @Override
    public ResultData updateDesigner(SaleStoreDesignerWithBLOBs designer) {
        return new ResultData(new UpdateResult(designerMapper.updateByPrimaryKeySelective(designer)));
    }

}
