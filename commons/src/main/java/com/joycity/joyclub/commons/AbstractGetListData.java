package com.joycity.joyclub.commons;

import com.joycity.joyclub.commons.modal.base.ListResult;
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
        ListResult listResult = new ListResult();
        listResult.setByPageUtil(pageUtil);

        long sum = countByFilter();
        listResult.setSum(sum);
        if (sum == 0) {
            listResult.setList(new ArrayList());
        } else {

            listResult.setList(selectByFilter());
        }
        return new ResultData(listResult);
    }
}