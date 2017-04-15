package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.mapper.manual.act.ActFrontMapper;
import com.joycity.joyclub.apifront.mapper.manual.act.ActPriceFrontMapper;
import com.joycity.joyclub.apifront.mapper.manual.store.StoreFrontMapper;
import com.joycity.joyclub.apifront.modal.act.ActInfoPage;
import com.joycity.joyclub.apifront.modal.base.IdNamePortrait;
import com.joycity.joyclub.apifront.service.ActFrontService;
import com.joycity.joyclub.commons.modal.base.DataListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joycity.joyclub.apifront.util.ThrowBusinessExceptionUtil.checkNull;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
@Service
public class ActFrontServiceImpl implements ActFrontService {
    @Autowired
    ActFrontMapper actMapper;
    @Autowired
    StoreFrontMapper storeMapper;
    @Autowired
    ActPriceFrontMapper actPriceMapper;

    @Override
    public ResultData getInfo(Long id) {
        ActInfoPage info = actMapper.getInfo(id);

        checkNull(info, "活动不存在");
        Integer price = actPriceMapper.getNowPrice(id);
        checkNull(price, "活动未上架或者已下架");
        info.setPrice(price);
        IdNamePortrait store = storeMapper.getSimpleInfo(info.getStoreId());
        checkNull(store, "商户不存在");
        info.setStore(store);
        return new ResultData(info);
    }

    // TODO: 2017/4/13 待完善
    @Override
    public ResultData getList(Long storeId) {
        return new ResultData(new DataListResult(actMapper.selectByStore(storeId)));
    }
}
