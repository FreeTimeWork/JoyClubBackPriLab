package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.mapper.manual.store.StoreFrontMapper;
import com.joycity.joyclub.apifront.mapper.manual.designer.DesignerFrontMapper;
import com.joycity.joyclub.apifront.modal.base.IdNamePortrait;
import com.joycity.joyclub.apifront.modal.designer.DesignerInfoPage;
import com.joycity.joyclub.apifront.modal.designer.SimpleDesigner;
import com.joycity.joyclub.apifront.service.DesignerFrontService;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil.checkNull;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
@Service
public class DesignerFrontServiceImpl implements DesignerFrontService {
    @Autowired
    DesignerFrontMapper designerMapper;
    @Autowired
    StoreFrontMapper storeMapper;

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
    public ResultData getList(Long projectId,Long storeId) {
        List<SimpleDesigner> list;
        if(projectId!=null) {
            list = designerMapper.getSimpleListByProject(projectId);
        }
        else if(storeId!=null) {
            list = designerMapper.getSimpleListByStore(storeId);
        }
        else {
            list = designerMapper.getSimpleList();
        }
        return new ResultData(new ListResult(list));
    }
}
