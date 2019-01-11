package com.interceptor;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.commons.Constants;
import com.commons.annontations.NoLogin;
import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.commons.utils.CookieUtils;
import com.modules.worker.bean.Worker;
import com.modules.worker.service.WorkerService;

/**
 * 登陆用户拦截器
 * @author DoubleLi
 * @time 2019年1月11日
 * 
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
	
	@Autowired
	private WorkerService workerService;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		try {
			((HandlerMethod) arg2).getBean();
		} catch (Exception e) {
			System.err.println(e);
			return;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		Object bean;
		ApiResult apiResult = new ApiResult();
		try {
			bean = ((HandlerMethod) arg2).getBean();
		} catch (Exception e) {
			return true;
		}
	    if(arg2.getClass().isAssignableFrom(HandlerMethod.class)) {
            //获得注解对象
            NoLogin noLogin = ((HandlerMethod) arg2).getMethodAnnotation(NoLogin.class);
            if(noLogin!=null){
            	return true;
            }
		}
		String token = request.getHeader("token");
		token=StringUtils.isBlank(token)?CookieUtils.getCookie(request, "token"):token;
		Integer w_id;
		try {
			w_id = StringUtils.isNotBlank(token)?(Integer)redisTemplate.opsForValue().get(token):null;
		} catch (Exception e) {
			return false;
		} 
		if(w_id == null ){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			apiResult.setStatus(ResponseStatus.no_login.getStatusCode());
			apiResult.setDesc(ResponseStatus.no_login.getStatusDesc());
			out.print(JSON.toJSON(apiResult));
			out.flush();
			return false;
		}else{
			//此处可优化 key值剩余过期时间过长可不操作redis，减少内存消耗*****、、
			Worker worker=workerService.searchInfo(w_id);
			redisTemplate.opsForValue().set(token,w_id, Integer.valueOf(Constants.SESSION_INVALID_TIME),TimeUnit.MINUTES);
			CurrentWorkerAware aware = (CurrentWorkerAware) bean;
			aware.setCurrentWorker(worker);		
		}
		return true;
	}

}
