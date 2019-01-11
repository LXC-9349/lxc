package com.modules.member.bean;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "MemberPhone", description = "客户手机号码映射表")
public class MemberPhone implements Serializable {
	private static final long serialVersionUID = 6253356546504719582L;
	@ApiModelProperty(value = "自增长ID", hidden = true)
	private Integer id;
	@ApiModelProperty(value = "客户ID", required = true)
	private Long memberId;
	@ApiModelProperty(value = "联系人")
	private String linkman;
	@ApiModelProperty(value = "手机号")
	private String phone;
	@ApiModelProperty(value = "所属客户")
	private String companyId;
	@ApiModelProperty(value = "职位")
	private String place;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "标记")
	private Integer done;
	@ApiModelProperty(value = "标记时间")
	private Date doneTime;
	@ApiModelProperty(value = "微信号")
	private String wechatId;
	@ApiModelProperty(value = "1有效 0无效")
	private Integer valid;

	public MemberPhone() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDone() {
		return done;
	}

	public void setDone(Integer done) {
		this.done = done;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "MemberPhone [id=" + id + ", memberId=" + memberId + ", linkman=" + linkman + ", phone=" + phone
				+ ", companyId=" + companyId + ", place=" + place + ", createTime=" + createTime + ", done=" + done
				+ ", doneTime=" + doneTime + ", wechatId=" + wechatId + ", valid=" + valid + "]";
	}

}
