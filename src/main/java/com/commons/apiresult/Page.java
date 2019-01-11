package com.commons.apiresult;

public class Page {
	private Long total;// 总条数
	private Integer pageSize;// 总页数
	private Integer page;// 当前页

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
