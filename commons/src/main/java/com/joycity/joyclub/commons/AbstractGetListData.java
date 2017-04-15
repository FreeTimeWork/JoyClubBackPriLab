package com.joycity.joyclub.commons;

import com.joycity.joyclub.commons.modal.base.DataListResult;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取列表数据时的返回值
 * Created by CallMeXYZ on 2017/4/12.
 */
public abstract class AbstractGetListData<T>{
    public abstract Long countByFilter();

    public abstract List<T> selectByFilter();

    public ResultData getList(PageUtil pageUtil) {
        DataListResult dataListResult = new DataListResult();
        dataListResult.setByPageUtil(pageUtil);

        long sum = countByFilter();
        dataListResult.setSum(sum);
        if (sum == 0) {
            dataListResult.setList(new ArrayList());
        } else {

            dataListResult.setList(selectByFilter());
        }
        return new ResultData(dataListResult);
    }
}