/**
 * 
 */
package com.modules.worker.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

 
/**
 * 登陆人员实体
 * @author DoubleLi
 * @time 2018年11月16日
 * 
 */
public class Worker implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String workerId;
	private String workerName;
	private String psw;
	private Integer maxAllotTheDay;
	private Integer isAllot;
	private Integer workerStatus;
	private String workQueue;
	private Date createTime;
	private Date updateTime;
	private Date workerOnlineTime;
	private Integer lineNum;
	private Integer preset;
	private Integer signStatus;
	private String headPic;
	private Date birthday;
	private Integer sex;
	private Integer online;
	private Integer deptId;
	private String no;
	private String email;
	private String phone;
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	@JsonIgnore
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public Integer getMaxAllotTheDay() {
		return maxAllotTheDay;
	}
	public void setMaxAllotTheDay(Integer maxAllotTheDay) {
		this.maxAllotTheDay = maxAllotTheDay;
	}
	public Integer getIsAllot() {
		return isAllot;
	}
	public void setIsAllot(Integer isAllot) {
		this.isAllot = isAllot;
	}
	public Integer getWorkerStatus() {
		return workerStatus;
	}
	public void setWorkerStatus(Integer workerStatus) {
		this.workerStatus = workerStatus;
	}
	 
	public String getWorkQueue() {
		if(StringUtils.isBlank(workQueue))
			this.workQueue="test";
		return workQueue;
	}
	public void setWorkQueue(String workQueue) {
		this.workQueue = workQueue;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getWorkerOnlineTime() {
		return workerOnlineTime;
	}
	public void setWorkerOnlineTime(Date workerOnlineTime) {
		this.workerOnlineTime = workerOnlineTime;
	}
	public Integer getLineNum() {
		return lineNum;
	}
	public void setLineNum(Integer lineNum) {
		this.lineNum = lineNum;
	}
	public Integer getPreset() {
		return preset;
	}
	public void setPreset(Integer preset) {
		this.preset = preset;
	}
	public Integer getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}
	public String getHeadPic() {
		return headPic;
	}
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getOnline() {
		return online;
	}
	public void setOnline(Integer online) {
		this.online = online;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	
	
}
