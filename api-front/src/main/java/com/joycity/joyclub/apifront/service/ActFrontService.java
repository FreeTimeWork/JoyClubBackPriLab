package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface ActFrontService {
    ResultData getInfo(Long id);
    ResultData getList(Long storeId);
}
