package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.mapper.manual.cart.PostAddressMapper;
import com.joycity.joyclub.apifront.modal.cart.ClientPostAddress;
import com.joycity.joyclub.apifront.service.PostAddressService;
import com.joycity.joyclub.commons.modal.base.CreateResult;
import com.joycity.joyclub.commons.modal.base.ListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.base.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/4/19.
 */
@Service
public class PostAddressServiceImpl implements PostAddressService {
    @Autowired
    PostAddressMapper postAddressMapper;

    @Override
    public ResultData getList(Long clientId) {
        return new ResultData(new ListResult(postAddressMapper.getList(clientId)));
    }

    @Override
    public ResultData add(ClientPostAddress postAddress) {
        postAddress.setId(null);
        postAddressMapper.insertSelective(postAddress);
        return new ResultData(new CreateResult(postAddress.getId()));
    }

    @Override
    public ResultData remove(Long id) {
        return  new ResultData(new UpdateResult(postAddressMapper.removeById(id)));
    }

    @Override
    public Integer setLastUseTimeNow(Long id) {
        return postAddressMapper.setLastUseTimeNow(id);
    }
}
