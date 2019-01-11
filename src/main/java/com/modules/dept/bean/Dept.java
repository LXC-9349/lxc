package com.modules.dept.bean;

import java.util.List;

public class Dept implements  java.io.Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6956914620111957755L;
	private String deptId;
	private String deptName;
	private String remake;
	private String deptParentId;
	private Integer isUse;
	private List<Dept> son;
	
	
	
	public List<Dept> getSon() {
		return son;
	}
	public void setSon(List<Dept> son) {
		this.son = son;
	}
	public Dept(String deptId) {
		this.deptId = deptId;
	}
	public Dept() {
	}
	public Dept(String deptId, String deptName) {
		this.deptId=deptId;
		this.deptName=deptName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getRemake() {
		return remake;
	}
	public void setRemake(String remake) {
		this.remake = remake;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptParentId() {
		return deptParentId;
	}
	public void setDeptParentId(String deptParentId) {
		this.deptParentId = deptParentId;
	}
	
	
}
