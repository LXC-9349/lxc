package com.modules.sysdict.bean;

import io.swagger.annotations.ApiModelProperty;

public class SysDict implements  java.io.Serializable  {

	/**
	 * 系统字典
	 */
	private static final long serialVersionUID = -4031920375398986672L;
	@ApiModelProperty(hidden=true)
	private Integer id;
	@ApiModelProperty(value="实际值",required=true)
	private String value;
	@ApiModelProperty(value="显示值",required=true)
	private String label;
	@ApiModelProperty(value="父级type",required=true)
	private String type;
	@ApiModelProperty(value="排序",required=false)
	private String sort;
	@ApiModelProperty(hidden=true)
	private Integer isUse;
	@ApiModelProperty(value="父级id",required=true)
	private Integer parent;
	@ApiModelProperty(hidden=true)
	private Integer isedit;
	
	
	public SysDict(Integer id, String label) {
		this.id=id;
		this.label=label;
	}
	
	public SysDict() {
	}
	
	public SysDict(Integer id) {
		this.id=id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public Integer getIsedit() {
		return isedit;
	}
	public void setIsedit(Integer isedit) {
		this.isedit = isedit;
	}
	
	
}
