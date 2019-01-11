package com.modules.member.bean;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "MemberBaseInfo", description = "客户表")
public class MemberBaseInfo implements Serializable {
	private static final long serialVersionUID = -6919962782511957780L;
	@ApiModelProperty(value = "客户ID")
	private Integer memberId;
	@ApiModelProperty(value = "客户名称")
	private String memberName;
	@ApiModelProperty(value = "手机号")
	private String mobile;
	@ApiModelProperty(value = "证件号")
	private String cardNo;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "创建人")
	private String workerId;
	@ApiModelProperty(value = "所属部门")
	private String mdeptName;
	@ApiModelProperty(value = "客户类型 系统客户 自定义客户")
	private String memberType;
	@ApiModelProperty(value = "地区")
	private String address;
	@ApiModelProperty(value = "渠道来源")
	private String source;
	@ApiModelProperty(value = "推广点")
	private String promotionSite;
	@ApiModelProperty(value = "微信号")
	private String wechatId;
	@ApiModelProperty(value = "客户编号")
	private String field4;
	@ApiModelProperty(value = "邮箱")
	private String field5;
	@ApiModelProperty(value = "客户类型 0:机构 1:个人")
	private String field6;
	@ApiModelProperty(value = "绑卡状态 C:未绑卡 N:绑卡")
	private String field7;
	@ApiModelProperty(value = "手机验证状态 C:未验证 N:已验证")
	private String field8;
	@ApiModelProperty(value = "交易密码")
	private String field9;
	@ApiModelProperty(value = "用户注册时间")
	private String field10;
	@ApiModelProperty(value = "累计错误次数")
	private String field11;
	@ApiModelProperty(value = "当天错误次数")
	private String field12;
	@ApiModelProperty(value = "修改日期")
	private String field13;
	@ApiModelProperty(value = "最后一次错误输入交易密码时间")
	private String field14;
	@ApiModelProperty(value = "投资者类型 0-普通投资者 1-专业投资者")
	private String field15;
	@ApiModelProperty(value = "登录时间")
	private String field16;
	@ApiModelProperty(value = "登录次数")
	private String field17;
	@ApiModelProperty(value = "性别")
	private String field18;
	@ApiModelProperty(value = "年龄")
	private String field19;
	@ApiModelProperty(value = "居民类型")
	private String field20;
	@ApiModelProperty(value = "姓(英文/拼音)")
	private String field21;
	@ApiModelProperty(value = "名(英文/拼音)")
	private String field22;
	@ApiModelProperty(value = "住址(英文/拼音)")
	private String field23;
	@ApiModelProperty(value = "出生地")
	private String field24;
	@ApiModelProperty(value = "出生地(英文/拼音)")
	private String field25;
	@ApiModelProperty(value = "纳税人居民国")
	private String field26;
	@ApiModelProperty(value = "纳税人识别号")
	private String field27;
	@ApiModelProperty(value = "客户分类 A1 A2 B1 B2")
	private String field28;
	@ApiModelProperty(value = "是否留存 0否 1是")
	private String field29;
	@ApiModelProperty(value = "QQ")
	private String field30;
	@ApiModelProperty(value = "用户状态 1已注册 2已绑卡 3已评测 4已交易")
	private String field31;
	@ApiModelProperty(value = "持仓状态 1空仓 2首投 3复投")
	private String field32;
	@ApiModelProperty(value = "画像总结")
	private String field33;
	private String field34;
	private String field35;
	private String field36;
	@ApiModelProperty(value = "批次编号")
	private String field37;
	@ApiModelProperty(value = "备案人")
	private String field38;
	@ApiModelProperty(value = "备案信息")
	private String field39;
	private String field40;
	private String field41;
	private String nickName;//昵称
	private String housePhone;//座机
	private String companyName;//公司
	private Integer industry;//行业
	private String position;//职位
	private String detailedAddress;//地区
	private String webSite;//网址
	private Integer isUse;//
	public MemberBaseInfo() {
		super();
	}

	public MemberBaseInfo(String memberName) {
		this.memberName=memberName;
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	 

	public String getMdeptName() {
		return mdeptName;
	}

	public void setMdeptName(String mdeptName) {
		this.mdeptName = mdeptName;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPromotionSite() {
		return promotionSite;
	}

	public void setPromotionSite(String promotionSite) {
		this.promotionSite = promotionSite;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getField4() {
		return field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public String getField5() {
		return field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

	public String getField6() {
		return field6;
	}

	public void setField6(String field6) {
		this.field6 = field6;
	}

	public String getField7() {
		return field7;
	}

	public void setField7(String field7) {
		this.field7 = field7;
	}

	public String getField8() {
		return field8;
	}

	public void setField8(String field8) {
		this.field8 = field8;
	}

	public String getField9() {
		return field9;
	}

	public void setField9(String field9) {
		this.field9 = field9;
	}

	public String getField10() {
		return field10;
	}

	public void setField10(String field10) {
		this.field10 = field10;
	}

	public String getField11() {
		return field11;
	}

	public void setField11(String field11) {
		this.field11 = field11;
	}

	public String getField12() {
		return field12;
	}

	public void setField12(String field12) {
		this.field12 = field12;
	}

	public String getField13() {
		return field13;
	}

	public void setField13(String field13) {
		this.field13 = field13;
	}

	public String getField14() {
		return field14;
	}

	public void setField14(String field14) {
		this.field14 = field14;
	}

	public String getField15() {
		return field15;
	}

	public void setField15(String field15) {
		this.field15 = field15;
	}

	public String getField16() {
		return field16;
	}

	public void setField16(String field16) {
		this.field16 = field16;
	}

	public String getField17() {
		return field17;
	}

	public void setField17(String field17) {
		this.field17 = field17;
	}

	public String getField18() {
		return field18;
	}

	public void setField18(String field18) {
		this.field18 = field18;
	}

	public String getField19() {
		return field19;
	}

	public void setField19(String field19) {
		this.field19 = field19;
	}

	public String getField20() {
		return field20;
	}

	public void setField20(String field20) {
		this.field20 = field20;
	}

	public String getField21() {
		return field21;
	}

	public void setField21(String field21) {
		this.field21 = field21;
	}

	public String getField22() {
		return field22;
	}

	public void setField22(String field22) {
		this.field22 = field22;
	}

	public String getField23() {
		return field23;
	}

	public void setField23(String field23) {
		this.field23 = field23;
	}

	public String getField24() {
		return field24;
	}

	public void setField24(String field24) {
		this.field24 = field24;
	}

	public String getField25() {
		return field25;
	}

	public void setField25(String field25) {
		this.field25 = field25;
	}

	public String getField26() {
		return field26;
	}

	public void setField26(String field26) {
		this.field26 = field26;
	}

	public String getField27() {
		return field27;
	}

	public void setField27(String field27) {
		this.field27 = field27;
	}

	public String getField28() {
		return field28;
	}

	public void setField28(String field28) {
		this.field28 = field28;
	}

	public String getField29() {
		return field29;
	}

	public void setField29(String field29) {
		this.field29 = field29;
	}

	public String getField30() {
		return field30;
	}

	public void setField30(String field30) {
		this.field30 = field30;
	}

	public String getField31() {
		return field31;
	}

	public void setField31(String field31) {
		this.field31 = field31;
	}

	public String getField32() {
		return field32;
	}

	public void setField32(String field32) {
		this.field32 = field32;
	}

	public String getField33() {
		return field33;
	}

	public void setField33(String field33) {
		this.field33 = field33;
	}

	public String getField34() {
		return field34;
	}

	public void setField34(String field34) {
		this.field34 = field34;
	}

	public String getField35() {
		return field35;
	}

	public void setField35(String field35) {
		this.field35 = field35;
	}

	public String getField36() {
		return field36;
	}

	public void setField36(String field36) {
		this.field36 = field36;
	}

	public String getField37() {
		return field37;
	}

	public void setField37(String field37) {
		this.field37 = field37;
	}

	public String getField38() {
		return field38;
	}

	public void setField38(String field38) {
		this.field38 = field38;
	}

	public String getField39() {
		return field39;
	}

	public void setField39(String field39) {
		this.field39 = field39;
	}

	public String getField40() {
		return field40;
	}

	public void setField40(String field40) {
		this.field40 = field40;
	}

	public String getField41() {
		return field41;
	}

	public void setField41(String field41) {
		this.field41 = field41;
	}

	@Override
	public String toString() {
		return "MemberBaseInfo [memberId=" + memberId + ", memberName=" + memberName + ", mobile=" + mobile
				+ ", cardNo=" + cardNo + ", createTime=" + createTime + ", workerId=" + workerId + ", companyId="
				+ mdeptName + ", memberType=" + memberType + ", address=" + address + ", source=" + source
				+ ", promotionSite=" + promotionSite + ", wechatId=" + wechatId + ", field4=" + field4 + ", field5="
				+ field5 + ", field6=" + field6 + ", field7=" + field7 + ", field8=" + field8 + ", field9=" + field9
				+ ", field10=" + field10 + ", field11=" + field11 + ", field12=" + field12 + ", field13=" + field13
				+ ", field14=" + field14 + ", field15=" + field15 + ", field16=" + field16 + ", field17=" + field17
				+ ", field18=" + field18 + ", field19=" + field19 + ", field20=" + field20 + ", field21=" + field21
				+ ", field22=" + field22 + ", field23=" + field23 + ", field24=" + field24 + ", field25=" + field25
				+ ", field26=" + field26 + ", field27=" + field27 + ", field28=" + field28 + ", field29=" + field29
				+ ", field30=" + field30 + ", field31=" + field31 + ", field32=" + field32 + ", field33=" + field33
				+ ", field34=" + field34 + ", field35=" + field35 + ", field36=" + field36 + ", field37=" + field37
				+ ", field38=" + field38 + ", field39=" + field39 + ", field40=" + field40 + ", field41=" + field41+"]";
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHousePhone() {
		return housePhone;
	}

	public void setHousePhone(String housePhone) {
		this.housePhone = housePhone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getIndustry() {
		return industry;
	}

	public void setIndustry(Integer industry) {
		this.industry = industry;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

}
