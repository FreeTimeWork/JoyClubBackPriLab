package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/4/14.
 */
public interface StoreFrontService {
    ResultData getInfo(Long id);

    ResultData getList(Long projectId, PageUtil pageUtil);

}
