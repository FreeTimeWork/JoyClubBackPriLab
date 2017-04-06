package com.joycity.joyclub.commons.utils;


public class PageUtil {
    /**
     * 当前页
     * 1代表第一页，前端传过来的page值也是实际值，而不是减一的索引值
     */
    private int page = 1;
    private int pageSize = 10;

    /**
     * no set
     */
    private int offset = 0;

    public PageUtil() {
    }

    public PageUtil(int page, int pageSize) {
        this();
        this.page = page;
        this.pageSize = pageSize;
        setOffset();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        setOffset();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        setOffset();
    }

    private void setOffset() {
        this.offset = (page - 1) * pageSize;
    }

    public int getOffset() {
        return offset;
    }
}
