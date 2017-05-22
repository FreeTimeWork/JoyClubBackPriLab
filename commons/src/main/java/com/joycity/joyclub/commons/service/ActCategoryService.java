package com.joycity.joyclub.commons.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.modal.generated.SysActCategory;


/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ActCategoryService {
    /**
     * @return data为按创建时间倒序的所有项目列表
     */
    ResultData getList();

    /**
     * 返回某个项目的具体信息
     * @param id
     * @return
     */
    ResultData getActCategory(Long id);
    ResultData updateActCategory(Long id, SysActCategory actCategory);
    ResultData createActCategory(SysActCategory actCategory);
}
