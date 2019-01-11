package com.modules.member.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 跟进记录表
 * @author DZH
 *
 */
public class MemberFollowRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4357652403226864822L;
	private Integer autoId;//自增ID
	private String recordId;//记录ID
	private Integer memberId;//客户ID
	private String workerId;//员工ID
	private Date recordTime;//处理时间
	private Date nextContactTime;//下次联系时间/下次跟进时间
	private String recordIp;//处理Ip
	private short isValidContact;//有效联系(0否，1是)
	private String recordContent;//备注
	private String intentionality;//意向度
	private Integer recordType;//记录类型1.普通、2.电话、3.短信、4.系统
	private String workerName;//员工名
	private String workerLine;//员工组织架构
	private Integer deptId;//员工部门ID
	private String companyId;//员工公司ID
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
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
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public Date getNextContactTime() {
		return nextContactTime;
	}
	public void setNextContactTime(Date nextContactTime) {
		this.nextContactTime = nextContactTime;
	}
	public String getRecordIp() {
		return recordIp;
	}
	public void setRecordIp(String recordIp) {
		this.recordIp = recordIp;
	}
	public short getIsValidContact() {
		return isValidContact;
	}
	public void setIsValidContact(short isValidContact) {
		this.isValidContact = isValidContact;
	}
	public String getRecordContent() {
		return recordContent;
	}
	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
	}
	public String getIntentionality() {
		return intentionality;
	}
	public void setIntentionality(String intentionality) {
		this.intentionality = intentionality;
	}
	public Integer getRecordType() {
		return recordType;
	}
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}
	public Integer getAutoId() {
		return autoId;
	}
	public void setAutoId(Integer autoId) {
		this.autoId = autoId;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getWorkerLine() {
		return workerLine;
	}
	public void setWorkerLine(String workerLine) {
		this.workerLine = workerLine;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
