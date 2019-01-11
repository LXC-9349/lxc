package com.modules.memberdept.bean;

import java.util.List;

public class MemberDept implements  java.io.Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6956914620111957755L;
	private String deptId;
	private String deptName;
	private String remake;
	private String deptParentId;
	private Integer isUse;
	private List<MemberDept> son;
	
	
	
	public List<MemberDept> getSon() {
		return son;
	}
	public void setSon(List<MemberDept> son) {
		this.son = son;
	}
	public MemberDept(String deptId) {
		this.deptId = deptId;
	}
	public MemberDept() {
	}
	public MemberDept(String deptId, String deptName) {
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
