package com.modules.sms.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * 短信工具类
 * 
 * @author DoubleLi
 * @time 2019年1月3日
 * 
 */
@Component
public class SmsUtils {

	@Value("${sms.address}")
	private String smsAddress;
	@Value("${sms.username}")
	private String username;
	@Value("${sms.password}")
	private String password;

	private static String[] ERRCODE = { "E01：用户名或密码错误。         ", "E02：此用户不为接口用户。       ", "E04：接口访问类型出错。         ",
			"E05：接口IP不符合要求。 ", "E06：被关闭的用户。", "E98：xml解析出错。 ", "E99：系统错误，请联系管理员处理。" };
	private static String[] SUCCCODE = { "00：发送成功。", "01：smsID不正确（长度为36位以内且由数字、字母组成）。", "02：发送号码不正确（长度为N位以内，可为空）",
			"03：手机号码不正确（长度大于10位且小于14位）。 ", "04：短信内容不正确（不能为空且长度不超过1500字符）。" };

	public Map<String,Object> sendSms(String smsId, String phone, String Msg) throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(smsAddress + "/services/SMS?wsdl");
		Object[] objects = client.invoke("mtSmsList", username, password, getSendXml(smsId, phone, Msg));
		return ex(JSON.toJSONString(objects[0]));
	}

	/**
	 * xml参数填值
	 * @param smsId
	 * @param phone
	 * @param Msg
	 * @return
	 * @time 2019年1月3日
	 * @author DoubleLi
	 */
	private String getSendXml(String smsId, String phone, String Msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<SMS type=\"sendSms\">");
		sb.append("<Message SmsID=\"" + smsId + "\" SendNum=\"100\" RecvNum=\"" + phone + "\" Content=\"" + Msg
				+ "\" Status=\"1\"/>");
		sb.append("</SMS>");
		return sb.toString();
	}

	/**
	 * 结果处理
	 * @param xml
	 * @return
	 * @time 2019年1月3日
	 * @author DoubleLi
	 */
	public Map<String,Object> ex(String xml) {
		Map<String,Object> resmap=new HashMap<>();
		JSONObject j = XML.toJSONObject(xml);
		System.out.println(j.toString());
		JSONObject  mj=j.getJSONObject("SMS").getJSONObject("Message");
		String succcode=null;
		String errcode=null;
		try {
			succcode = (String) mj.get("Code");
			errcode = (String) mj.get("code");
		} catch (JSONException e) {
		}
		if(StringUtils.isNotBlank(succcode)){//正常访问
			for (String s : SUCCCODE) {
				if(s.indexOf(succcode)!=-1){
					resmap.put("msg", s);
					break;
				}
			}
			resmap.put("code", succcode);
		}else if(StringUtils.isNotBlank(errcode)){//错误访问
			for (String s : ERRCODE) {
				if(s.indexOf(succcode)!=-1){
					resmap.put("msg", s);
					break;
				}
			}
			resmap.put("code", errcode);
		}else{//异常
			resmap.put("code", "-1");
			resmap.put("msg", "未知异常");
		}
		return resmap;
	}

	/*public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<SMS type=\"sendSms\">");
		sb.append("<Message SmsID=\"" + 1 + "\" SendNum=\"100\" RecvNum=\"" + 2 + "\" Content=\"" + 3
				+ "\" Status=\"1\"/>");
		sb.append("</SMS>");
		System.out.println(ex(sb.toString()));
	}*/
}
