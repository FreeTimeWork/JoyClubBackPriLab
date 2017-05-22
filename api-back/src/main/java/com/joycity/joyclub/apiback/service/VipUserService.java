package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by CallMeXYZ on 2017/4/27.
 */
public interface VipUserService {
        ResultData getList(/*Long projectId, */String cardType, Integer pointStart, Integer pointEnd, String vipNo, String cardNo, String phone, PageUtil pageUtil);
}
