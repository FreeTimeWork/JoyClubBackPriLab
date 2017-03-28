package com.joycity.joyclub.apiback.modal.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/28.
 */
public class DataListResult {
    private List list;
    private Long sum;
    private Integer page;

    public DataListResult(List list) {
        this.list = list;
    }

    public DataListResult(List list, Long sum, Integer page) {
        this.list = list;
        this.sum = sum;
        this.page = page;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
