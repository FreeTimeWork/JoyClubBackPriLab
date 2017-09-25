package com.joycity.joyclub.act.service;

import com.joycity.joyclub.act.modal.generated.SaleActType;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

import java.util.List;

/**
 * Created by fangchen.chai on 2017/9/14
 * 活动的大类
 */
public interface ActTypeService {

    ResultData getList(PageUtil pageUtil);

    ResultData createActType(SaleActType actType);

    ResultData deleteActType(Long id);

    List<SaleActType> getSaleActTypes();

}
