package com.joycity.joyclub.commons.service.impl;


import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.mapper.manual.SaleStoreDesignerMapper;
import com.joycity.joyclub.commons.mapper.manual.StoreMapper;
import com.joycity.joyclub.commons.modal.base.*;
import com.joycity.joyclub.commons.modal.designer.DesignerInfoPage;
import com.joycity.joyclub.commons.modal.designer.SimpleDesigner;
import com.joycity.joyclub.commons.modal.generated.SaleStoreDesignerExample;
import com.joycity.joyclub.commons.modal.generated.SaleStoreDesignerWithBLOBs;
import com.joycity.joyclub.commons.service.DesignerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;
import static com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil.checkNull;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@Service
public class DesignerServiceImpl implements DesignerService {
    private final Log log = LogFactory.getLog(DesignerServiceImpl.class);
    @Autowired
    SaleStoreDesignerMapper designerMapper;

    @Autowired
    StoreMapper storeMapper;

    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getListByStoreId(Long storeId) {
        ListResult listResult = new ListResult();
        SaleStoreDesignerExample example = new SaleStoreDesignerExample();
        SaleStoreDesignerExample.Criteria criteria = example.createCriteria();
        criteria.andStoreIdEqualTo(storeId);
        criteria.andDeleteFlagEqualTo(false);

        long sum = designerMapper.countByExample(example);
        ;
        if (sum == 0) {
            listResult.setList(new ArrayList());
        } else {
            example.setOrderByClause("id desc");
            listResult.setList(designerMapper.selectByExample(example));
        }
        return new ResultData(listResult);
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


    @Override
    public ResultData getInfo(Long id) {
        DesignerInfoPage info = designerMapper.getInfo(id);
        checkNull(info, "设计师不存在");

        IdNamePortrait store = storeMapper.getSimpleInfo(info.getStoreId());
        checkNull(store, "商户不存在");
        info.setStore(store);
        return new ResultData(info);
    }

    // TODO: 2017/4/13 待完善
    @Override
    public ResultData getList(Long projectId, Long storeId) {
        List<SimpleDesigner> list;
        if (projectId != null) {
            list = designerMapper.getSimpleListByProject(projectId);
        } else if (storeId != null) {
            list = designerMapper.getSimpleListByStore(storeId);
        } else {
            list = designerMapper.getSimpleList();
        }
        return new ResultData(new ListResult(list));
    }
}
