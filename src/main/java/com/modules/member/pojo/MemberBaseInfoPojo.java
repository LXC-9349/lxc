
package com.modules.member.pojo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author DoubleLi
 * @time 2018年11月23日
 * 
 */
public class MemberBaseInfoPojo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3551817066168171832L;
	@ApiModelProperty(value="用户名称",required=false)
	private String name;//用户名称
	@ApiModelProperty(value="工单时间",required=false)
	private String workOrderTimeA;//工单时间
	@ApiModelProperty(value="工单时间",required=false)
	private String workOrderTimeB;//工单时间
	@ApiModelProperty(value="电话号码",required=false)
	private String phone;//电话号码
	@ApiModelProperty(value="用户部门",required=false)
	private String dept;//用户部门
	@ApiModelProperty(value="用户类型",required=false)
	private String type;//用户类型
	@ApiModelProperty(value="办公电话",required=false)
	private String field20;//用户类型
	@ApiModelProperty(value="排序字段0姓名1单位2办公电话3手机4电子邮箱5工单总量6服务中的工单 demo:（1,2,3）",required=false)
	private String sort;
	@ApiModelProperty(value="排序类型1升序2倒序按排序字段对应(1,1,1)",required=false)
	private String sortType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWorkOrderTimeA() {
		return workOrderTimeA;
	}
	public void setWorkOrderTimeA(String workOrderTimeA) {
		this.workOrderTimeA = workOrderTimeA;
	}
	public String getWorkOrderTimeB() {
		return workOrderTimeB;
	}
	public void setWorkOrderTimeB(String workOrderTimeB) {
		this.workOrderTimeB = workOrderTimeB;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getField20() {
		return field20;
	}
	public void setField20(String field20) {
		this.field20 = field20;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	
}