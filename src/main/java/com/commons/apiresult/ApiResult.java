package com.commons.apiresult;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * api接口返回的数据格式
 * 
 * @author Administrator
 *
 */
public class ApiResult {

	/**
	 * 状态码，参见{@link ResponseStatus}
	 */
	private Integer status;
	/**
	 * 状态描述
	 */
	private String desc;
	/**
	 * 返回的数据
	 */
	private Object data;
	
	private Map<String,Object> resultMap=new HashMap<>();
	
	@JsonIgnore
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	/**
	 * 自定义表格时，返回的列
	 */
	// private Object title;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getData() {
		if (this.resultMap.size() != 0) {
			data = resultMap;
		}
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	/*
	 * public Object getTitle() { return title; } public void setTitle(Object
	 * title) { this.title = title; }
	 */
}
