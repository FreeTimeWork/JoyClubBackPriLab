package com.joycity.joyclub.api.service;

import com.joycity.joyclub.api.constant.ResultCode;
import com.joycity.joyclub.api.entity.base.ResultData;
import com.joycity.joyclub.api.entity.project.BaseProject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
public interface ProjectService {
    /**
     * 返回所有的项目的简单信息
     * @return
     */
   ResultData getList();
}
