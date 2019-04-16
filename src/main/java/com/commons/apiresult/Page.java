package com.commons.apiresult;
/**
 *
 * 功能描述:  分页配置
 * @author: DoubleLi
 * @date: 2019/4/16 14:50
 */
public class Page {

    /**
     * 总条数
     */
    private Long total;
    /**
     * 总页数
     */
    private Integer pageSize;
    /**
     * 当前页
     */
    private Integer page;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Page(Long total, Integer pageSize, Integer page) {
        super();
        this.total = total;
        this.pageSize = pageSize;
        this.page = page;
    }

    public Page() {
        super();
    }

    @Override
    public String toString() {
        return "Page [total=" + total + ", pageSize=" + pageSize + ", page=" + page + "]";
    }

}
