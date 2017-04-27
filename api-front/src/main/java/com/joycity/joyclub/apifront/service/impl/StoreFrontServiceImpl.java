package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.mapper.manual.store.StoreFrontMapper;
import com.joycity.joyclub.apifront.mapper.manual.designer.DesignerFrontMapper;
import com.joycity.joyclub.apifront.modal.designer.SimpleDesigner;
import com.joycity.joyclub.apifront.modal.store.StoreInfoPage;
import com.joycity.joyclub.apifront.service.StoreFrontService;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil.checkNull;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
@Service
public class StoreFrontServiceImpl implements StoreFrontService {
    @Autowired
    StoreFrontMapper storeFrontMapper;
    @Autowired
    DesignerFrontMapper designerMapper;


    @Override
    public ResultData getInfo(Long id) {
        StoreInfoPage info= storeFrontMapper.getInfo(id);
        checkNull(info, "商户不存在");
        List<SimpleDesigner> designers= designerMapper.getSimpleListByStore(id);
        info.setDesigners(designers);
        return new ResultData(info);
    }

    @Override
    public ResultData getList(Long projectId, PageUtil pageUtil) {
        ListResult listResult = new ListResult(storeFrontMapper.selectSimpleByFilter(projectId, pageUtil));
        listResult.setByPageUtil(pageUtil);
        return new ResultData(listResult);
    }

    // TODO: 2017/4/13 待完善
   /* @Override
    public ResultData getList(Long storeId) {
        return new ResultData(new ListResult(productMapper.selectByStore(storeId)));
    }*/
}
