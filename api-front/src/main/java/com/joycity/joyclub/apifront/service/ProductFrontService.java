package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public interface ProductFrontService {
    ResultData getInfo(Long id);

    ResultData getList(Long storeId, Long designerId, PageUtil pageUtil);
}
