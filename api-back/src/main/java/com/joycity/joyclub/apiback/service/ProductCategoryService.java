package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.generated.SysProductCategory;
import com.joycity.joyclub.apiback.util.PageUtil;

/**
 * Created by CallMeXYZ on 2017/3/29.
 */
public interface ProductCategoryService {
    /**
     * @return data为按创建时间倒序的所有项目列表
     */
    ResultData getList();

    /**
     * 返回某个项目的具体信息
     * @param id
     * @return
     */
    ResultData getProductCategory(Long id);
    ResultData updateProductCategory(Long id, SysProductCategory productCategory);
    ResultData createProductCategory(SysProductCategory productCategory);
}
