/**
 * 
 * @author DoubleLi
 * @time 2019年1月2日
 * 
 */
package com.modules.mail.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库存储记录信息
 * @author DoubleLi
 * @time 2019年1月11日
 * 
 */
public class EmailRecord implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2685450654643528426L;
	
	private Integer id;
	private String from;
	private String to;
	private String sub;
	private String content;
	private String chaosong;
	private String fujianPath;
	private String fujianName;
	private Date createTime;
	private Integer type;
	private Integer isUse;
	private Integer memberId;
	private String workerId;
	private Integer status;
	
	private String name;
	
	
	public EmailRecord(Integer id) {
		this.id=id;
	}
	
	public EmailRecord() {
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getChaosong() {
		return chaosong;
	}
	public void setChaosong(String chaosong) {
		this.chaosong = chaosong;
	}
	public String getFujianPath() {
		return fujianPath;
	}
	public void setFujianPath(String fujianPath) {
		this.fujianPath = fujianPath;
	}
	public String getFujianName() {
		return fujianName;
	}
	public void setFujianName(String fujianName) {
		this.fujianName = fujianName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}