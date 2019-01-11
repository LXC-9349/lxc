package com.controls.push;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commons.annontations.NoLogin;
import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.commons.utils.Md5;
import com.interceptor.BaseCurrentWorkerAware;
import com.modules.worker.bean.Worker;
import com.modules.worker.service.WorkerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "接收第三方数据推送")
@RestController
@RequestMapping("/api/tp")
public class PushApi extends BaseCurrentWorkerAware {
	private static final Logger log = LoggerFactory.getLogger(PushApi.class);

	@Value("${push_token}")
	private String push_token;

	@Value("${socket_token}")
	private String socket_token;
	@Value("${thrid.ip}")
	private String thridIp;

	@Autowired
	private WorkerService workerService;

	@NoLogin
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ts", value = "时间戳", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名", dataType = "String", paramType = "query") })
	@ApiOperation(value = "socket中间件参数信息")
	@GetMapping("socket_info")
	public ApiResult socket_info(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			Long nowts =new Date().getTime();
			String ts =request.getParameter("ts");
			String sign = request.getParameter("sign");
			if (StringUtils.isAnyBlank(sign,ts)) {
				apiResult.setStatus(ResponseStatus.failed.getStatusCode());
				apiResult.setDesc("参数不能为空");
				return apiResult;
			}
			String s=null;
			try {
				s = Md5.getMD5ofStr(ts+socket_token);
			} catch (Exception e) {
				s=null;
			}
			if (s!=null&&s.equals(sign)&&(nowts-Long.valueOf(ts)<60*1000)) {//超过60秒签名无效
				Worker w = workerService.searchInfoWorkerId("8000");
				apiResult.getResultMap().put("workerId", w.getWorkerId());
				apiResult.getResultMap().put("lineNum", w.getLineNum());
				apiResult.getResultMap().put("proxyIp", thridIp);
				apiResult.getResultMap().put("proxyPort", "3456");
				apiResult.setData(apiResult.getResultMap());
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.failed.getStatusCode());
				apiResult.setDesc("签名校验失败");
			}
		} catch (Exception e) {
			log.error("socket中间件参数信息", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@NoLogin
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ts", value = "时间戳", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "linenum", value = "分机号", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "sign", value = "签名", dataType = "String", paramType = "query") })
	@ApiOperation(value = "通过分机号获取socket中间件参数信息")
	@GetMapping("socket_info_linenum")
	public ApiResult socket_info_linenum(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			Long nowts =new Date().getTime();
			String ts =request.getParameter("ts");
			String sign = request.getParameter("sign");
			String linenum = request.getParameter("linenum");
			if (StringUtils.isAnyBlank(linenum,sign,ts)) {
				apiResult.setStatus(ResponseStatus.failed.getStatusCode());
				apiResult.setDesc("参数不能为空");
				return apiResult;
			}
			String s=null;
			try {
				s = Md5.getMD5ofStr(linenum+ts+socket_token);
			} catch (Exception e) {
				s=null;
			}
			if (s!=null&&s.equals(sign)&&(nowts-Long.valueOf(ts)<60*1000)) {//超过60秒签名无效
				Worker w = workerService.searchInfoLineNum(Integer.valueOf(linenum));
				if(w==null){
					apiResult.setStatus(ResponseStatus.failed.getStatusCode());
					apiResult.setDesc("呼叫中心不存在分机："+linenum);
					return apiResult;
				}
				apiResult.getResultMap().put("workerId", w.getWorkerId());
				apiResult.getResultMap().put("lineNum", w.getLineNum());
				apiResult.getResultMap().put("proxyIp", thridIp);
				apiResult.getResultMap().put("proxyPort", "3456");
				apiResult.setData(apiResult.getResultMap());
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.failed.getStatusCode());
				apiResult.setDesc("签名校验失败");
			}
		} catch (Exception e) {
			log.error("通过分机号获取socket中间件参数信息", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	public static void main(String[] args) {
		String t=String.valueOf(new Date().getTime());
		System.out.println(t);
		System.out.println(Md5.getMD5ofStr("8571"+t+"d0f7391f6e9a496b92f10d41a33b599a"));
	}
}
