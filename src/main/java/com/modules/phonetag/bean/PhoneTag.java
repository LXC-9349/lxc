package com.modules.phonetag.bean;

import java.io.Serializable;

/**
 * 电话标签
 * @author DoubleLi
 * @time 2019年1月2日
 * 
 */
public class PhoneTag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1195852444624478083L;
	
	private Integer id;
	private Integer tagValue;
	private String phone;
	private String remark;
	private Integer isUse;
	public PhoneTag(Integer id, Integer tagValue, String remark) {
		this.id = id;
		this.tagValue = tagValue;
		this.remark = remark;
	}
 
	public PhoneTag() {
	}


	public PhoneTag(Integer id) {
		this.id=id;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getTagValue() {
		return tagValue;
	}
	public void setTagValue(Integer tagValue) {
		this.tagValue = tagValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	
	
}
