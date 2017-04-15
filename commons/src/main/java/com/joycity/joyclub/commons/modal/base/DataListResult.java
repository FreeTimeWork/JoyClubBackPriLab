package com.joycity.joyclub.commons.modal.base;

import com.joycity.joyclub.commons.utils.PageUtil;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/3/28.
 */
public class DataListResult {
    private List list;
    private Long sum;
    private Integer page;
    private Integer pageSize;

    public DataListResult() {
    }

    public DataListResult(List list) {
        this();
        this.list = list;
    }

    public DataListResult(List list, Long sum, Integer page, Integer pageSize) {
        this.list = list;
        this.sum = sum;
        this.page = page;
        this.pageSize = pageSize;
    }

    public DataListResult(List list, Long sum, Integer page) {
        this.list = list;
        this.sum = sum;
        this.page = page;
    }
    public void setByPageUtil(PageUtil pageUtil) {
        setPage(pageUtil.getPage());
        setPageSize(pageUtil.getPageSize());
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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
