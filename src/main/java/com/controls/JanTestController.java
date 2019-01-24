package com.controls;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.modules.jantest.bean.JanTest;
import com.modules.jantest.service.JanTestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.commons.utils.PageMode;
import com.commons.utils.RequestObjectUtil;
import com.interceptor.BaseCurrentWorkerAware;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Author DoubleLi
 * Date  2019-01-24
 */
@RestController
@RequestMapping(value = "/api/janTest")
public class JanTestController extends BaseCurrentWorkerAware  {

	private static final Logger log = LoggerFactory.getLogger(JanTestController.class);
	
    @Autowired
    private JanTestService janTestService;

    @ApiOperation(value = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "显示条数", dataType = "Integer", paramType = "query")
			  })
	@GetMapping("search")
	public ApiResult search(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			JanTest janTest = (JanTest) RequestObjectUtil.mapToBean(request, JanTest.class);// 获取条件参数
			PageMode pageMode = (PageMode) RequestObjectUtil.mapToBean(request, PageMode.class);// 获取分页参数
			if (janTest == null) {
				janTest = new JanTest();
			}
			if (pageMode == null) {
				pageMode = new PageMode();
			}
			janTestService.searchPage(apiResult, pageMode,janTest);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("查询", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

    @ApiOperation("详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Id", required = true, dataType = "Integer", paramType = "query") })
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@GetMapping(value = "info")
	public ApiResult info(@RequestParam(required = false) Integer id) {
		ApiResult apiResult = new ApiResult();
		try {
			if (id == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			JanTest janTest = janTestService.get(id);
			Map<String, Object> resMap = new HashMap<>();
			resMap.put("janTest", janTest);
			apiResult.setData(resMap);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("详情", e);
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
		}
		return apiResult;
	}

    @ApiOperation(value = "添加")
	@PostMapping(value = "insert")
	public ApiResult insert(@ModelAttribute(value = "JanTest") @ApiParam(value = "Created JanTest object", required = true) JanTest janTest) {
		ApiResult apiResult = new ApiResult();
		if (janTestService.insert(janTest)>0) {
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			return apiResult;
		}else{
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc(ResponseStatus.failed.getStatusDesc());
		}
		return apiResult;
	}

     @ApiOperation(value = "修改")
	@PostMapping(value = "update")
	public ApiResult update(@ModelAttribute(value = "JanTest") @ApiParam(value = "Created JanTest object", required = true) JanTest janTest) {
		ApiResult apiResult = new ApiResult();
		if (janTestService.update(janTest)>0) {
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			return apiResult;
		}else{
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc(ResponseStatus.failed.getStatusDesc());
		}
		return apiResult;
	}

   @ApiOperation("删除")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Id", required = true, dataType = "Integer", paramType = "query") })
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@DeleteMapping(value = "delete")
	public ApiResult delete(@RequestParam(required = false) Integer id) {
		ApiResult apiResult = new ApiResult();
		try {
			if (id == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			janTestService.delete(id);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
		}
		return apiResult;
	}

}
