package com.joycity.joyclub.commons.service.impl;

import com.joycity.joyclub.commons.exception.BusinessException;

import com.joycity.joyclub.commons.mapper.manual.SaleStoreDesignerMapper;
import com.joycity.joyclub.commons.mapper.manual.StoreMapper;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import com.joycity.joyclub.commons.modal.designer.SimpleDesigner;
import com.joycity.joyclub.commons.modal.generated.SysStore;
import com.joycity.joyclub.commons.modal.generated.SysStoreExample;
import com.joycity.joyclub.commons.modal.store.StoreInfoPage;
import com.joycity.joyclub.commons.service.StoreService;
import com.joycity.joyclub.commons.utils.PageUtil;
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
public class StoreServiceImpl implements StoreService {
    private final Log log = LogFactory.getLog(StoreServiceImpl.class);
    @Autowired
    StoreMapper storeMapper;
@Autowired
    SaleStoreDesignerMapper designerMapper;
    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getList(Long projectId, PageUtil pageUtil) {
        ListResult listResult = new ListResult();
        listResult.setPage(pageUtil.getPage());
        listResult.setPageSize(pageUtil.getPageSize());
        long sum = getStoreSumByProjectId(projectId);
        listResult.setSum(sum);
        if (sum == 0) {
            listResult.setList(new ArrayList());
        } else {
            listResult.setList(storeMapper.getListByProjectId(projectId, pageUtil));
        }
        return new ResultData(listResult);
    }

    @Override
    public ResultData getStore(Long id) {
        SysStore sysStore = storeMapper.selectByPrimaryKey(id);
        if (sysStore == null || sysStore.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该商户信息不存在");
        return new ResultData(sysStore);
    }


    @Override
    public ResultData createStore(SysStore store) {
        storeMapper.insertSelective(store);
        return new ResultData(new CreateResult(store.getId()));
    }

    @Override
    public ResultData updateStore(SysStore store) {

        return new ResultData(new UpdateResult(storeMapper.updateByPrimaryKeySelective(store)));
    }

    /**
     * @return 返回所有未删除的store数目
     */
    private Long getStoreSumByProjectId(long projectId) {
        SysStoreExample example = new SysStoreExample();
        SysStoreExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo(false);
        criteria.andProjectIdEqualTo(projectId);
        return storeMapper.countByExample(example);
    }


    @Override
    public ResultData getInfo(Long id) {
        StoreInfoPage info= storeMapper.getInfo(id);
        checkNull(info, "商户不存在");
        List<SimpleDesigner> designers= designerMapper.getSimpleListByStore(id);
        info.setDesigners(designers);
        return new ResultData(info);
    }

    @Override
    public ResultData getSimpleList(Long projectId, PageUtil pageUtil) {
        ListResult listResult = new ListResult(storeMapper.selectSimpleByFilter(projectId, pageUtil));
        listResult.setByPageUtil(pageUtil);
        return new ResultData(listResult);
    }

    // TODO: 2017/4/13 待完善
   /* @Override
    public ResultData getList(Long storeId) {
        return new ResultData(new ListResult(productMapper.selectByStore(storeId)));
    }*/

}
