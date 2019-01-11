package com.commons.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.commons.apiresult.ApiResult;
import com.commons.apiresult.Page;

/**
 * 分页辅助类
 * @author DoubleLi
 * @time 2018年12月10日
 * 
 */
public class PageMode {
	private Integer page;//页码
	private Integer pageSize;//页面显示数据
	private int max;//最后页码
	private Long total;//总条数
	private String mysqlLimit;//mysql分页条件
	private StringBuffer sqlWhere=new StringBuffer();
	private StringBuffer sqlFrom=new StringBuffer();
	private String orderBy;
	private String groupBy;
	public Integer getPage() {
		return page==null?1:page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize==null?100:pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirst() {
		if(getPage()>getMax())
			setPage(max);//超过处理
		int pageNo=(this.getPage()-1)<0?0:(this.getPage()-1);
		return this.getPageSize()*pageNo;
	}
	public int getMax() {
		if(getTotal()!=0)
		max=(int) (this.getTotal()%this.getPageSize()>0?this.getTotal()/this.getPageSize()+1:this.getTotal()/this.getPageSize());
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

 

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getMysqlLimit() {
		mysqlLimit=getSourceSql()+getGroupBy()+" "+getOrderBy()+" limit "+getFirst()+","+getPageSize();
		return mysqlLimit;
	}

	public void setMysqlLimit(String mysqlLimit) {
		this.mysqlLimit = mysqlLimit;
	}

	public String getSearchCountSql() {
		return "select count(1) from ("+getSourceSql()+getGroupBy()+") mysqlcount";
	}

	public String getSourceSql() {
		return sqlFrom.toString()+sqlWhere.toString();
	}

	public StringBuffer getSqlWhere() {
		return sqlWhere;
	}

	public void setSqlWhere(String sqlWhere) {
		if(this.sqlWhere.length()==0)
		this.sqlWhere.append(" where 1=1 ");
		this.sqlWhere.append(" and ").append(sqlWhere);
	}

	public StringBuffer getSqlFrom() {
		return sqlFrom;
	}

	public void setSqlFrom(String sqlFrom) {
		this.sqlFrom.append(sqlFrom);
	}

	public String getOrderBy() {
		return orderBy==null?"":orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy==null?"":" "+groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public void setApiResult(ApiResult apiResult, List<Map<String, Object>> resultMap) {
		Page page=new Page(getTotal(), getPageSize(), getPage()<=0?1:getPage());
		Map<String, Object> map=new HashMap<>();
		map.put("list", resultMap);
		map.put("page", page);
		apiResult.setData(map);
	}
	
	 
}
