package com.controls;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commons.Constants;
import com.commons.annontations.NoLogin;
import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.commons.utils.BeanUtils;
import com.commons.utils.CookieUtils;
import com.commons.utils.Md5;
import com.modules.role.service.RoleService;
import com.modules.worker.bean.Worker;
import com.modules.worker.service.WorkerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "登录相关")
@RestController
@RequestMapping("/api/user/*")
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@SuppressWarnings("rawtypes")
	@Autowired
	public RedisTemplate redisTemplate;

	@Autowired
	private WorkerService workerService;
	@Autowired
	private RoleService roleService;

	@SuppressWarnings("unchecked")
	@NoLogin
	@ApiOperation(value = "人员登陆")
	@PostMapping(value = "login")
	public ApiResult login(@RequestParam String username, @RequestParam String password, HttpServletRequest request,
			HttpServletResponse response) {
		ApiResult apiResult = new ApiResult();

		if (StringUtils.isAnyBlank(username, password)) {
			apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
			apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
			return apiResult;
		}
		try {
			String psw = Md5.encrpyt(password, Constants.PASSWORD_TOKEN);
			Worker worker = workerService.login(username, psw);
			if (worker == null) {
				apiResult.setStatus(ResponseStatus.failed.getStatusCode());
				apiResult.setDesc("用户号或者密码不正确");
				return apiResult;
			}
			String token = UUID.randomUUID().toString();
			redisTemplate.opsForValue().set(token, worker.getId(), Integer.valueOf(Constants.SESSION_INVALID_TIME),
					TimeUnit.MINUTES);
			CookieUtils.addCookie(response, "token", token, 60 * 60 * 24 * 7);// 一周过期
			// 更新登录时间
			workerService.updateWorkerOnlineTime(worker.getWorkerId());
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("token", token);
			apiResult.setData(resultMap);
			log.warn("人员登录:"+worker.getWorkerId());
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("登陆", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@NoLogin
	@ApiOperation(value = "获取人员信息")
	@RequestMapping(value = "info", method = { RequestMethod.GET })
	public ApiResult info(HttpServletRequest request, HttpServletResponse response) {
		ApiResult apiResult = new ApiResult();
		try {
			String token = request.getHeader("token");
			token = StringUtils.isBlank(token) ? CookieUtils.getCookie(request, "token") : token;
			if (token == null || token.equals("")) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			Integer w_id = (Integer) redisTemplate.opsForValue().get(token);
			if (w_id == null) {
				apiResult.setStatus(ResponseStatus.no_login.getStatusCode());
				apiResult.setDesc(ResponseStatus.no_login.getStatusDesc());
				return apiResult;
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Worker w = workerService.searchInfo(w_id);
			Map<String, Object> ww = BeanUtils.transBeanToMap(w);
			ww.put("deptId", w.getDeptId().toString());
			resultMap.put("worker", ww);
			resultMap.put("roles", roleService.getRoleIdByWorkerId(w.getWorkerId()));
			apiResult.setData(resultMap);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("获取人员信息", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "登出接口", httpMethod = "POST")
	@RequestMapping(value = "logout", method = { RequestMethod.POST })
	public ApiResult logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApiResult result = new ApiResult();
		String token = request.getHeader("token");
		token = StringUtils.isBlank(token) ? CookieUtils.getCookie(request, "token") : token;
		if (StringUtils.isBlank(token)) {
			result.setStatus(ResponseStatus.null_param.getStatusCode());
			result.setDesc(ResponseStatus.null_param.getStatusDesc());
			return result;
		}
		Integer w_id = (Integer) redisTemplate.opsForValue().get(token);
		if (w_id == null) {
			result.setStatus(ResponseStatus.failed.getStatusCode());
			result.setDesc("未登录");
			return result;
		}
		try {
			boolean b = redisTemplate.delete(token);
			if (!b) {
				result.setStatus(ResponseStatus.failed.getStatusCode());
				result.setDesc(ResponseStatus.failed.getStatusDesc());
				return result;
			}
			CookieUtils.removeCookie(response, "token");
			result.setStatus(ResponseStatus.success.getStatusCode());
			result.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("注销", e);
			result.setStatus(ResponseStatus.error.getStatusCode());
			result.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return result;
	}
}
