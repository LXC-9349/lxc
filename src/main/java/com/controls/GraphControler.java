package com.controls;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.commons.utils.DateUtils;
import com.commons.utils.RequestObjectUtil;
import com.interceptor.BaseCurrentWorkerAware;
import com.modules.graph.pojo.GraphParmPojo;
import com.modules.graph.service.GraphService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 统计图表
 * 
 * @author DoubleLi
 * @time 2018年12月24日
 * 
 */
@Api(tags = "统计图表")
@RestController
@RequestMapping("/api/graph")
public class GraphControler extends BaseCurrentWorkerAware {
	private static final Logger log = LoggerFactory.getLogger(GraphControler.class);
	@Autowired
	private GraphService graphService;

	@ApiOperation(value = "呼叫量 return(t:时间 callin:呼入 callout呼出)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "统计维度（1日2月3年）默认2", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份默认当前年份,传值必须4位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "month", value = "月份默认当前月份,传值必须2位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "day", value = "默认当天,传值必须2位", dataType = "String", paramType = "query") })
	@GetMapping("/call_amount")
	public ApiResult call_amount(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			GraphParmPojo graphParmPojo = (GraphParmPojo) RequestObjectUtil.mapToBean(request, GraphParmPojo.class);// 获取条件参数
			if (graphParmPojo == null)
				graphParmPojo = new GraphParmPojo();
			if (checkParm(graphParmPojo, apiResult)) {
				graphService.callAmount(graphParmPojo, apiResult);
			}
		} catch (Exception e) {
			log.error("呼叫量", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation(value = "呼叫总量环比 return(sumcall总呼叫量 ringratio环比)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "统计维度（1日2月3年）默认2", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份默认当前年份,传值必须4位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "month", value = "月份默认当前月份,传值必须2位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "day", value = "默认当天,传值必须2位", dataType = "String", paramType = "query") })
	@GetMapping("/call_amount_ring_ratio")
	public ApiResult call_amount_ring_ratio(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			GraphParmPojo graphParmPojo = (GraphParmPojo) RequestObjectUtil.mapToBean(request, GraphParmPojo.class);// 获取条件参数
			if (graphParmPojo == null)
				graphParmPojo = new GraphParmPojo();
			if (checkParm(graphParmPojo, apiResult)) {
				graphService.callAmountRingRatio(graphParmPojo, apiResult);
			}
		} catch (Exception e) {
			log.error("呼叫总量环比", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation(value = "工单总量(complete完成processcount处理中)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "统计维度（1日2月3年）默认2", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份默认当前年份,传值必须4位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "month", value = "月份默认当前月份,传值必须2位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "day", value = "默认当天,传值必须2位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "wotype", value = "工单类型 字典", dataType = "Integer", paramType = "query")})
	@GetMapping("/workorder_amount")
	public ApiResult workorder_amount(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			GraphParmPojo graphParmPojo = (GraphParmPojo) RequestObjectUtil.mapToBean(request, GraphParmPojo.class);// 获取条件参数
			if (graphParmPojo == null)
				graphParmPojo = new GraphParmPojo();
			if (checkParm(graphParmPojo, apiResult)) {
				graphService.workorderAmount(graphParmPojo, apiResult);
			}
		} catch (Exception e) {
			log.error("工单总量", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation(value = "工单处理时效 return（interval：下标 count：数量 desc：描述）")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "统计维度（1日2月3年）默认2", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份默认当前年份,传值必须4位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "month", value = "月份默认当前月份,传值必须2位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "day", value = "默认当天,传值必须2位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "wotype", value = "工单类型 字典", dataType = "Integer", paramType = "query")})
	@GetMapping("/workorder_aging")
	public ApiResult workorder_aging(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			GraphParmPojo graphParmPojo = (GraphParmPojo) RequestObjectUtil.mapToBean(request, GraphParmPojo.class);// 获取条件参数
			if (graphParmPojo == null)
				graphParmPojo = new GraphParmPojo();
			if (checkParm(graphParmPojo, apiResult)) {
				graphService.workorderAging(graphParmPojo, apiResult);
			}
		} catch (Exception e) {
			log.error("工单处理时效", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation(value = "工单量同比 return(a：当前 b：前一时间 tb：同比) ")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "统计维度（1日2月3年）默认2", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份默认当前年份,传值必须4位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "month", value = "月份默认当前月份,传值必须2位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "day", value = "默认当天,传值必须2位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "wotype", value = "工单类型 字典", dataType = "Integer", paramType = "query")})
	@GetMapping("/workorder_year_on_year")
	public ApiResult workorder_year_on_year(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			GraphParmPojo graphParmPojo = (GraphParmPojo) RequestObjectUtil.mapToBean(request, GraphParmPojo.class);// 获取条件参数
			if (graphParmPojo == null)
				graphParmPojo = new GraphParmPojo();
			if (checkParm(graphParmPojo, apiResult)) {
				graphService.workorderYearOnYear(graphParmPojo, apiResult);
			}
		} catch (Exception e) {
			log.error("工单量同比", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation(value = "工单量环比 return(count数量 ringratio环比)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "统计维度（1日2月3年）默认2", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "year", value = "年份默认当前年份,传值必须4位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "month", value = "月份默认当前月份,传值必须2位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "day", value = "默认当天,传值必须2位", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "wotype", value = "工单类型 字典", dataType = "Integer", paramType = "query")})
	@GetMapping("/workorder_ring_ratio")
	public ApiResult workorder_ring_ratio(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			GraphParmPojo graphParmPojo = (GraphParmPojo) RequestObjectUtil.mapToBean(request, GraphParmPojo.class);// 获取条件参数
			if (graphParmPojo == null)
				graphParmPojo = new GraphParmPojo();
			if (checkParm(graphParmPojo, apiResult)) {
				graphService.workorderRingRatio(graphParmPojo, apiResult);
			}
		} catch (Exception e) {
			log.error("工单量环比", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	private Boolean checkParm(GraphParmPojo graphParmPojo, ApiResult apiResult) {
		if (graphParmPojo.getType() != null
				&& !ArrayUtils.contains(new Integer[] { 1, 2, 3 }, graphParmPojo.getType().intValue())) {
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc("统计维度");
			return false;
		} else if (graphParmPojo.getType() == null) {
			graphParmPojo.setType(2);
		}

		if (StringUtils.isNotBlank(graphParmPojo.getYear()) && graphParmPojo.getYear().length() != 4) {
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc("年份错误");
			return false;
		} else if (StringUtils.isBlank(graphParmPojo.getYear())) {
			graphParmPojo.setYear(DateUtils.getYear());
		}
		if (StringUtils.isNotBlank(graphParmPojo.getMonth()) && graphParmPojo.getMonth().length() != 2) {
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc("月份错误");
			return false;
		} else if (StringUtils.isBlank(graphParmPojo.getMonth())) {
			graphParmPojo.setMonth(DateUtils.getMonth());
		}
		if (StringUtils.isNotBlank(graphParmPojo.getDay()) && graphParmPojo.getDay().length() != 2) {
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc("天错误");
			return false;
		} else if (StringUtils.isBlank(graphParmPojo.getDay())) {
			graphParmPojo.setDay(DateUtils.getDay());
		}
		apiResult.setStatus(ResponseStatus.success.getStatusCode());
		apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		return true;
	}
}
