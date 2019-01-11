package com.modules.role.bean;

import java.io.Serializable;

/**
 * 角色表
 * 
 */
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1932850442913914970L;

	private Integer roleId;

    private String roleName;

    private Integer orderNo;

    /**
     * 角色说明
     */
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}


}