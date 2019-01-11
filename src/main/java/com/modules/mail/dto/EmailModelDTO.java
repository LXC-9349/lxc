package com.modules.mail.dto;

import java.io.InputStream;
import java.io.Serializable;

/**
 * 邮件服务器发送信息
 * @author DoubleLi
 * @time 2019年1月11日
 * 
 */
public class EmailModelDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7802826952741996558L;

	/**
	 * 发送者
	 */
	private String from;
	
	/**
	 * 接受者
	 */
	private String[] to;
	
	/**
	 * 抄送
	 */
	private String[] cc;
	
	/**
	 * 邮件主题
	 */
	private String subject;
	
	/**
	 * 邮件内容
	 */
	private String text;
	
	/**
	 * 附件路径
	 */
	private InputStream[] is;
	/**
	 * 附件名称
	 */
	private String[] fjName;
	
	
 
	public EmailModelDTO(String from, String[] to, String[] cc, String subject, String text, InputStream[] is,
			String[] fjName) {
		super();
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.subject = subject;
		this.text = text;
		this.is = is;
		this.fjName = fjName;
	}

	 
	public String getFrom() {
		return from;
	}
 
	public void setFrom(String from) {
		this.from = from;
	}
 
	public String[] getTo() {
		return to;
	}
 
	public void setTo(String[] to) {
		this.to = to;
	}
 
	public String[] getCc() {
		return cc;
	}
 
	public void setCc(String[] cc) {
		this.cc = cc;
	}
 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
	public String getSubject() {
		return subject;
	}
 
	public void setSubject(String subject) {
		this.subject = subject;
	}
 
	public String getText() {
		return text;
	}
 
	public void setText(String text) {
		this.text = text;
	}


	public InputStream[] getIs() {
		return is;
	}


	public void setIs(InputStream[] is) {
		this.is = is;
	}


	public String[] getFjName() {
		return fjName;
	}


	public void setFjName(String[] fjName) {
		this.fjName = fjName;
	}
}
