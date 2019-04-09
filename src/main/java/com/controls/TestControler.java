package com.controls;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.commons.annontations.NoLogin;
import com.commons.utils.FileUtils;
import com.interceptor.BaseCurrentWorkerAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(tags = "开发测试配置")
@Controller
public class TestControler extends BaseCurrentWorkerAware {

	@Value("${project.path}")
	private String projectPath;
	
	@GetMapping("/test_scoket")
	@NoLogin
	public String test_scoket() {
		return "main";
	}

	@GetMapping("/admin")
	@NoLogin
	public Object index(HttpServletRequest request) {
		request.setAttribute("isadmin", true);
		return "index";
	}

	@ApiOperation("开启sql打印")
	@GetMapping(value = "/log_debug", produces = "application/json;charset=utf-8")
	@NoLogin
	@ResponseBody
	public String log_debug() {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.getLogger("org.springframework.web.servlet.DispatcherServlet").setLevel(Level.DEBUG);
		loggerContext.getLogger("com.modules").setLevel(Level.DEBUG);
		return "开启成功";
	}

	@ApiOperation("关闭sql打印")
	@GetMapping(value = "/log_warn", produces = "application/json;charset=utf-8")
	@NoLogin
	@ResponseBody
	public String log_warn() {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.getLogger("org.springframework.web.servlet.DispatcherServlet").setLevel(Level.WARN);
		loggerContext.getLogger("com.modules").setLevel(Level.WARN);
		return "关闭成功";
	}

	/*@Reference
	private UserDubboService userDubboService;

	@ApiOperation("测试dubbo")
	@GetMapping(value = "/dubbo", produces = "application/json;charset=utf-8")
	@NoLogin
	@ResponseBody
	public ApiResult dubbo() {
		ApiResult apiResult=new ApiResult();
		apiResult.setData(userDubboService.select("select * from Worker"));
		return apiResult;
	}*/

	@ApiOperation("部署")
	@PostMapping(value = "/deploy", produces = "application/json;charset=utf-8")
	@NoLogin
	@ResponseBody
	public String deploy(MultipartFile[] files) {
		if (files.length > 0) {
			for (MultipartFile m : files) {
				if (m.getOriginalFilename().indexOf("src") != -1) {
					FileUtils.upload(m, projectPath);
				}
				if (m.getOriginalFilename().indexOf("pom") != -1) {
					FileUtils.upload(m, projectPath);
				}
			}
			Runnable r = new Runnable() {
				@Override
				public void run() {
					try {
						String shpath = projectPath+"/springboot-prod.sh deploy";
						Runtime.getRuntime().exec(shpath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			r.run();
		}
		return "部署成功";
	}
}
