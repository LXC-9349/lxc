package com.commons.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class CookieUtils {

	public static final String VALUE_ENCODE = "utf-8";
	public static final String DEFAULT_PATH = "/";
	/**
	 * 默认一年
	 */
	public static final int DEFAULT_MAX_AGE = 60 * 60 * 24 * 365;

	/**
	 * 
	 * @param response
	 * @param key
	 * @param value
	 * @param domain
	 * @param path
	 * @param maxAge
	 *            an integer specifying the maximum age of the cookie in
	 *            seconds; if negative, means the cookie is not stored; if zero,
	 *            deletes the cookie
	 */
	public static final void addCookie(final HttpServletResponse response, final String key,
			final String value, final String domain, final String path, final int maxAge) {
		String encodedValue;
		try {
			encodedValue = value == null ? "" : URLEncoder.encode(value, VALUE_ENCODE);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		Cookie c = new Cookie(key, encodedValue);
		if (domain != null) {
			c.setDomain(domain);
		}
		c.setPath(path);
		c.setMaxAge(maxAge);
		response.addCookie(c);
	}
	
	public static final void addCookie(final HttpServletResponse response, final String key,
			final String value, final String domain, final String path, final int maxAge, boolean encode){
		if(encode){
			addCookie(response, key, value, domain, path, maxAge);
		}else{
			Cookie c = new Cookie(key, value);
			if (domain != null) {
				c.setDomain(domain);
			}
			c.setPath(path);
			c.setMaxAge(maxAge);
			response.addCookie(c);
		}
	}

	public static final void addCookie(final HttpServletResponse response, final String key,
			final String value, final String path, final int maxAge) {
		addCookie(response, key, value, null, path, maxAge);
	}

	public static final void addCookie(final HttpServletResponse response, final String key,
			final String value, final int maxAge) {
		addCookie(response, key, value, null, DEFAULT_PATH, maxAge);
	}

	public static final void addCookie(final HttpServletResponse response, final String key,
			final String value) {
		addCookie(response, key, value, null, DEFAULT_PATH, DEFAULT_MAX_AGE);
	}
	
	public static final void addCookie(final HttpServletResponse response, final String key,
			final String value, final boolean encode) {
		addCookie(response, key, value, null, DEFAULT_PATH, DEFAULT_MAX_AGE, encode);
	}

	public static final void removeCookie(final HttpServletResponse response, final String key,
			final String domain, final String path) {
		addCookie(response, key, "", domain, path, 0);
	}

	public static final void removeCookie(final HttpServletResponse response, final String key,
			final String path) {
		addCookie(response, key, "", null, path, 0);
	}

	public static final void removeCookie(final HttpServletResponse response, final String key) {
		addCookie(response, key, "", null, DEFAULT_PATH, 0);
	}

	public static final String getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie c : cookies) {
			if (StringUtils.equals(key, c.getName())) {
				return c.getValue();
			}
		}
		return null;
	}
}
