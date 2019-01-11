package com.commons;

import org.springframework.core.env.Environment;

import com.commons.utils.SpringUtil;

public class Constants {

	private static Environment env=SpringUtil.getBean(Environment.class);
	
	/** 登录 */
	public static final String LOGIN_PSW = env.getProperty("login_psw"); // 添加系统管理人员----默认密码
	public static final String SESSION_INVALID_TIME = env.getProperty("session_invalid_time"); // 默认登录过期时间

	public static final String PASSWORD_TOKEN = env.getProperty("password_token"); // 系统加密、解密TOKEN

	/**
	 * 手机号码验证<br>
	 * true表示非手机号不显示<br>
	 * false表示非手机号也显示，如果座机号<br>
	 * 该属性作用于号码显示、号码输入验证
	 */
	public static boolean valid_phone = false;

	// data数据
	/**
	 * 返回的数据
	 */
	public static final String DATA_LIST = "list";

	/**
	 * 返回结果
	 */
	public static final String DATA_RESULT = "result";

	/**
	 * 分页参数
	 */
	public static final String DATA_PAGE = "page";

	/**
	 * 权限数据
	 */
	public static final String DATA_AUTH = "permission";
}
