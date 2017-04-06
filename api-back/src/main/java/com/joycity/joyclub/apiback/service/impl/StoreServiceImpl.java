package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.mapper.manual.SysStoreMapper;
import com.joycity.joyclub.apiback.modal.base.CreateResult;
import com.joycity.joyclub.apiback.modal.base.DataListResult;
import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.base.UpdateResult;
import com.joycity.joyclub.apiback.modal.generated.SysStore;
import com.joycity.joyclub.apiback.modal.generated.SysStoreExample;
import com.joycity.joyclub.apiback.service.StoreService;
import com.joycity.joyclub.commons.utils.PageUtil;
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
public class StoreServiceImpl implements StoreService {
    private final Log log = LogFactory.getLog(StoreServiceImpl.class);
    @Autowired
    SysStoreMapper sysStoreMapper;

    /**
     * @return resultData, data为按创建时间倒序的所有项目列表
     */
    @Override
    public ResultData getList(Long projectId, PageUtil pageUtil) {
        DataListResult dataListResult = new DataListResult();
        dataListResult.setPage(pageUtil.getPage());
        dataListResult.setPageSize(pageUtil.getPageSize());
        long sum = getStoreSumByProjectId(projectId);
        dataListResult.setSum(sum);
        if (sum == 0) {
            dataListResult.setList(new ArrayList());
        } else {
            dataListResult.setList(sysStoreMapper.getListByProjectId(projectId, pageUtil));
        }
        return new ResultData(dataListResult);
    }

    @Override
    public ResultData getStore(Long id) {
        SysStore sysStore = sysStoreMapper.selectByPrimaryKey(id);
        if (sysStore == null || sysStore.getDeleteFlag()) throw new BusinessException(DATA_NOT_EXIST, "该商户信息不存在");
        return new ResultData(sysStore);
    }


    @Override
    public ResultData createStore(SysStore store) {
        sysStoreMapper.insertSelective(store);
        return new ResultData(new CreateResult(store.getId()));
    }

    @Override
    public ResultData updateStore(SysStore store) {

        return new ResultData(new UpdateResult(sysStoreMapper.updateByPrimaryKeySelective(store)));
    }

    /**
     * @return 返回所有未删除的store数目
     */
    private Long getStoreSumByProjectId(long projectId) {
        SysStoreExample example = new SysStoreExample();
        SysStoreExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteFlagEqualTo(false);
        criteria.andProjectIdEqualTo(projectId);
        return sysStoreMapper.countByExample(example);
    }

}
