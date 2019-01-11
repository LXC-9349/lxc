package com.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



public class HttpUtils {

	private static final Log logger = LogFactory.getLog(HttpUtils.class);
	private SSLClient client = null;
	private static final int SOCKET_TIMEOUT = 3000;
	private static final int CONNECT_TIMEOUT = 3000;
	
	private HttpUtils() {
		try {
			client = new SSLClient();
		} catch (Exception e) {
			logger.error("init SSLClient failed", e);
		}
	}
	
	public static HttpUtils getInstance(){
		return new HttpUtils();
	}
	
	public String doPost(String url, String params) {
		try {
			URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---------------------------------" + url);
		//url = url.replaceAll("*&*", "\\&");
		System.out.println("====================================="+url);
		return doPost(url, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
	}
	
	public String doPost(String url, Map<String, String> params){
		return doPost(url, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
	}
	
	public String doPost(String url, String param, int connectTimeOut,
			int socketTimeOut) {

		CloseableHttpResponse response = null;
		String responseContent = null;
		try {
			HttpPost httppost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(connectTimeOut)
					.setSocketTimeout(socketTimeOut).build();// 设置请求和传输超时时间
			httppost.setConfig(requestConfig);

			StringEntity myEntity = new StringEntity(param, "UTF-8");
			StringBuffer out = new StringBuffer();
			InputStream is = myEntity.getContent();
			byte[] b = new byte[4096];
			for (int n; (n = is.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			System.out.println(out.toString());
			httppost.setEntity(myEntity);

			// 执行请求
			response = client.execute(httppost);
			// 返回数据
			HttpEntity entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			logger.warn("HttpUtil.doPost[url:" + url + ",params:" + param
					+ "] exception. reason:" + e.getMessage());
			return null;
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
				// ignore
			}
		}
		return responseContent;
	}
	
	public String doPost(String url, Map<String, String> params, int connectTimeOut, int socketTimeOut) {
		// 参数校验
		if(url == null || "".equals(url)) {
			throw new IllegalArgumentException("The url can't be empty!");
		}
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		// 组织请求参数
		try {
			logger.warn("httpclient do post. url="+url+", params="+params.toString());
			httpPost = new HttpPost(url);
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			if(params != null && params.size() > 0) {
				Set<Entry<String, String>> sets = params.entrySet();
				Iterator<Entry<String, String>> it = sets.iterator();
				while(it.hasNext()) {
					Entry<String, String> entry = it.next();
					NameValuePair nameValue = new BasicNameValuePair(entry.getKey(), entry.getValue());
					paramsList.add(nameValue);
				}
			}
			UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(paramsList, Consts.UTF_8);
			httpPost.setEntity(urlEntity);
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(connectTimeOut)
					.setSocketTimeout(socketTimeOut).build();//设置请求和传输超时时间
			httpPost.setConfig(requestConfig);
			
			// 执行请求
			response = client.execute(httpPost); 
			// 返回数据
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, "UTF-8");
			}
		}catch(Exception e){
			logger.info("HttpUtil.doPost[url:"+url+",params:"+params.toString()+"] exception. reason:"+ e.getMessage());
			return null;
		}finally {
			try {
				if(response != null) 
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String doGet(String url){
		HttpGet httpGet= null;
		CloseableHttpResponse response = null;
		try {
			httpGet = new HttpGet(url);
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(CONNECT_TIMEOUT)
					.setSocketTimeout(SOCKET_TIMEOUT).build();
			httpGet.setConfig(requestConfig);
			//执行请求
			response = client.execute(httpGet);
			// 返回数据
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			logger.info("HttpUtil.doGet[url:"+url+"] exception. reason:"+ e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(response != null) 
					response.close();
			} catch (IOException e) {
			}
		}
		return null;
	}
	
}
