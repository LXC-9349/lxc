package com.modules.sms.models;

import java.io.Serializable;
import java.util.Date;

public class SmsRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1745383355258527280L;

	private Integer id;
	private String smsId;
	private String phone;
	private String content;
	private Integer status;
	private Integer type;
	private Date createTime;
	private Integer isUse;
	private Integer memberId;
	private String workerId;
	private String backmsg;
	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SmsRecord(Integer id) {
		this.id = id;
	}

	

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public SmsRecord() {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsUse() {
		return isUse;
	}

	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public String getBackmsg() {
		return backmsg;
	}

	public void setBackmsg(String backmsg) {
		this.backmsg = backmsg;
	}

}
