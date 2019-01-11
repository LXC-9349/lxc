package com.modules.member.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

 
/**
 * 客户导入信息
 * @author DoubleLi
 * @time 2018年11月5日
 * 
 */
public class MemberExcelImport {
	@Excel(name = "xuhao", orderNum = "0")
	private String xuhao;
	@Excel(name = "danwei", orderNum = "1")
	private String danwei;
	@Excel(name = "chu", orderNum = "2")
	private String chu;
	@Excel(name = "zhiwu", orderNum = "3")
	private String zhiwu;
	@Excel(name = "xingming", orderNum = "4")
	private String xingming;
	@Excel(name = "bangongdidian", orderNum = "5")
	private String bangongdidian;
	@Excel(name = "bangongdianhua", orderNum = "6")
	private String bangongdianhua;
	@Excel(name = "shouji", orderNum = "7")
	private String shouji;
	@Excel(name = "youxiang", orderNum = "8")
	private String youxiang;
	@Excel(name = "pinyin", orderNum = "9")
	private String pinyin;
	
	
	
	public String getBangongdidian() {
		return bangongdidian;
	}
	public void setBangongdidian(String bangongdidian) {
		this.bangongdidian = bangongdidian;
	}
	public String getXuhao() {
		return xuhao;
	}
	public void setXuhao(String xuhao) {
		this.xuhao = xuhao;
	}
	public String getDanwei() {
		return danwei;
	}
	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}
	public String getChu() {
		return chu;
	}
	public void setChu(String chu) {
		this.chu = chu;
	}
	public String getZhiwu() {
		return zhiwu;
	}
	public void setZhiwu(String zhiwu) {
		this.zhiwu = zhiwu;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	 
	public String getBangongdianhua() {
		return bangongdianhua;
	}
	public void setBangongdianhua(String bangongdianhua) {
		this.bangongdianhua = bangongdianhua;
	}
	public String getShouji() {
		return shouji;
	}
	public void setShouji(String shouji) {
		this.shouji = shouji;
	}
	public String getYouxiang() {
		return youxiang;
	}
	public void setYouxiang(String youxiang) {
		this.youxiang = youxiang;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	
	
 
}
