package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.sun.corba.se.spi.orbutil.fsm.Guard;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
public interface ProjectVipCardRangeService {
    ResultData getRanges(long projectId);

    /**
     * 给某个项目添加一个会员卡号段
     *
     * @param projectId
     * @param type
     * @param min
     * @param max
     * @return
     */
    ResultData addRange(long projectId, String type, long min, long max);

    /**
     * 修改某个会员卡号段
     *
     * @param id
     * @param min
     * @param max
     * @return
     */
    ResultData updateRange(long id, long min, long max);
}
