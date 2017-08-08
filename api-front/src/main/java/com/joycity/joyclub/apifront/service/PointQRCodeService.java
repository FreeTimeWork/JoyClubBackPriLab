package com.joycity.joyclub.apifront.service;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by Administrator on 2017/8/6.
 */
public interface PointQRCodeService {
    ResultData getQRCode(Long clientId);
    ResultData getQRCodeForMallcoo(Long projectId,String ticket,String openUserId);

}
