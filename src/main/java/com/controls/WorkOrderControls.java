package com.controls;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.commons.utils.DateUtils;
import com.commons.utils.PageMode;
import com.commons.utils.RequestObjectUtil;
import com.interceptor.BaseCurrentWorkerAware;
import com.modules.mail.service.EmailRecordService;
import com.modules.sms.service.SmsRecordService;
import com.modules.worker.bean.Worker;
import com.modules.worker.service.WorkerService;
import com.modules.workorder.bean.WorkOrder;
import com.modules.workorder.bean.WorkOrderCJ;
import com.modules.workorder.bean.WorkOrderDHGZ;
import com.modules.workorder.bean.WorkOrderIT;
import com.modules.workorder.bean.WorkOrderYJ;
import com.modules.workorder.bean.WorkOrderZJ;
import com.modules.workorder.pojo.WorkOrderPojo;
import com.modules.workorder.service.WorkOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "工单")
@RestController
@RequestMapping("/api/workorder")
public class WorkOrderControls extends BaseCurrentWorkerAware {
	private static final Logger log = LoggerFactory.getLogger(WorkOrderControls.class);

	@Autowired
	private WorkOrderService workOrderService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private SmsRecordService smsRecordService;
	@Autowired
	private EmailRecordService emailRecordService;
	
	
	@ApiOperation(value = "工单列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "显示条数", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "no", value = "工单编号", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "createA", value = "工单开始时间", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "createB", value = "工单结束时间", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "工单状态", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "businesstype", value = "业务类型 字典", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "工单类型 字典", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "memberId", value = "客户id", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "memberName", value = "客户名称", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "客户手机号", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "undone", value = "未完成的工单,1", dataType = "Integer", paramType = "query")
	})
	@GetMapping("search")
	public ApiResult search(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			WorkOrderPojo workOrderPojo = (WorkOrderPojo) RequestObjectUtil.mapToBean(request, WorkOrderPojo.class);// 获取条件参数
			PageMode pageMode = (PageMode) RequestObjectUtil.mapToBean(request, PageMode.class);// 获取分页参数
			if (workOrderPojo == null) {
				workOrderPojo = new WorkOrderPojo();
			}
			if (pageMode == null) {
				pageMode = new PageMode();
			}
			workOrderService.search(workOrderPojo, apiResult, pageMode,getCurrentWorker());
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("工单列表", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation("工单未完成数量统计")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type", value = "工单类型 字典", dataType = "Integer", paramType = "query") })
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@GetMapping(value = "undone_count")
	public ApiResult undone_count(@RequestParam(required = false) Integer type) {
		ApiResult apiResult = new ApiResult();
		try {
			/*if (type == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}*/
			Map<String, Object> map = new HashMap<>();
			map.put("result", workOrderService.undone(type));
			apiResult.setData(map);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("工单未完成数量统计", e);
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
		}
		return apiResult;
	}
	
	@ApiOperation("工单详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "工单Id", required = true, dataType = "Integer", paramType = "query") })
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
			apiResult.setData(workOrderService.searchInfo(id));
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("工单详情", e);
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
		}
		return apiResult;
	}

	@ApiOperation("选择派发人员")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "2IT3线务4机务", required = true, dataType = "Integer", paramType = "query") })
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@GetMapping(value = "xz_worker")
	public ApiResult xz_worker(@RequestParam(required = false) Integer type) {
		ApiResult apiResult = new ApiResult();
		try {
			if (type == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			Map<String,Object> map=new HashMap<>();
			map.put("roleId",type);
			apiResult.setData(workerService.selWorker(map));
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("选择派发人员", e);
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
		}
		return apiResult;
	}
	
	@ApiOperation("新增工单_装机")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "insert_zj")
	public ApiResult insert_zj(
			@ModelAttribute(value = "WorkOrderZJ") @ApiParam(value = "Created WorkOrderZJ object", required = true) WorkOrderZJ workOrderZJ) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrderZJ == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			WorkOrder order = new WorkOrder();
			BeanUtils.copyProperties(workOrderZJ, order);
			if(StringUtils.isBlank(order.getNo()))
			order.setNo("ZJ" + DateUtils.getDate("yyyyMMddHHmmssS"));
			order.setUpdateTime(new Date());
			order.setWorkerId(getCurrentWorker().getWorkerId());
			if("1".equals(order.getStatus())){//状态为等待机务
				order.setJw(order.getAcceptWorker());
			}else if("2".equals(order.getStatus())){//状态为等待线务
				order.setXw(order.getAcceptWorker());
			}
			if (workOrderService.insertOrUpdate(order)) {
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("新增失败");
			}
		} catch (Exception e) {
			log.error("新增工单_装机", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation("新增工单_移机")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "insert_yj")
	public ApiResult insert_yj(
			@ModelAttribute(value = "WorkOrderYJ") @ApiParam(value = "Created WorkOrderYJ object", required = true) WorkOrderYJ workOrderYJ) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrderYJ == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			WorkOrder order = new WorkOrder();
			BeanUtils.copyProperties(workOrderYJ, order);
			if(StringUtils.isBlank(order.getNo()))
			order.setNo("YJ" + DateUtils.getDate("yyyyMMddHHmmssS"));
			order.setUpdateTime(new Date());
			order.setWorkerId(getCurrentWorker().getWorkerId());
			if (workOrderService.insertOrUpdate(order)) {
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("新增失败");
			}
		} catch (Exception e) {
			log.error("新增工单_移机", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation("新增工单_电话故障")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "insert_dhgz")
	public ApiResult insert_dhgz(
			@ModelAttribute(value = "WorkOrderDHGZ") @ApiParam(value = "Created WorkOrderDHGZ object", required = true) WorkOrderDHGZ workOrderDHGZ) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrderDHGZ == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			WorkOrder order = new WorkOrder();
			BeanUtils.copyProperties(workOrderDHGZ, order);
			if(StringUtils.isBlank(order.getNo()))
			order.setNo("DHGZ" + DateUtils.getDate("yyyyMMddHHmmssS"));
			order.setUpdateTime(new Date());
			order.setWorkerId(getCurrentWorker().getWorkerId());
			if (workOrderService.insertOrUpdate(order)) {
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("新增失败");
			}
		} catch (Exception e) {
			log.error("新增工单_电话故障", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation("新增工单_撤机")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "insert_cj")
	public ApiResult insert_cj(
			@ModelAttribute(value = "WorkOrderCJ") @ApiParam(value = "Created WorkOrderCJ object", required = true) WorkOrderCJ workOrderCJ) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrderCJ == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			WorkOrder order = new WorkOrder();
			BeanUtils.copyProperties(workOrderCJ, order);
			if(StringUtils.isBlank(order.getNo()))
			order.setNo("CJ" + DateUtils.getDate("yyyyMMddHHmmssS"));
			order.setUpdateTime(new Date());
			order.setWorkerId(getCurrentWorker().getWorkerId());
			if (workOrderService.insertOrUpdate(order)) {
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("新增失败");
			}
		} catch (Exception e) {
			log.error("新增工单_撤机", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation("新增工单_it")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "insert_it")
	public ApiResult insert_it(
			@ModelAttribute(value = "WorkOrderIT") @ApiParam(value = "Created WorkOrderIT object", required = true) WorkOrderIT workOrderIT) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrderIT == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			WorkOrder order = new WorkOrder();
			BeanUtils.copyProperties(workOrderIT, order);
			if(StringUtils.isBlank(order.getNo()))
			order.setNo("IT" + DateUtils.getDate("yyyyMMddHHmmssS"));
			order.setUpdateTime(new Date());
			order.setWorkerId(getCurrentWorker().getWorkerId());
			if (workOrderService.insertOrUpdate(order)) {
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("新增失败");
			}
		} catch (Exception e) {
			log.error("新增工单_it", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation("新增工单")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "insert")
	public ApiResult insert(
			@ModelAttribute(value = "WorkOrder") @ApiParam(value = "Created WorkOrder object", required = true) WorkOrder workOrder) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrder == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			WorkOrder order = workOrder;
			if(StringUtils.isBlank(order.getNo()))
			order.setNo("GD" + DateUtils.getDate("yyyyMMddHHmmssS"));
			order.setUpdateTime(new Date());
			order.setWorkerId(getCurrentWorker().getWorkerId());
			order.setDispatchName(getCurrentWorker().getWorkerId());
			if("1".equals(order.getStatus())){//状态为等待机务
				order.setJw(order.getAcceptWorker());
			}else if("2".equals(order.getStatus())){//状态为等待线务
				order.setXw(order.getAcceptWorker());
			}
			if (workOrderService.insertOrUpdate(order)) {
				log.warn("新增工单："+order.toString());
				apiResult.setData(order);
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("新增失败");
			}
		} catch (Exception e) {
			log.error("新增工单", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation("编辑工单")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "isaccpet", value = "1派发", required = false, dataType = "Integer", paramType = "query") })
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "update")
	public ApiResult update(Integer isaccpet,
			@ModelAttribute(value = "WorkOrder") @ApiParam(value = "Created WorkOrder object", required = true) WorkOrder workOrder) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrder == null || workOrder.getId() == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			Map<String,Object> orderold = workOrderService.searchInfo(workOrder.getId());
			String status=(String) orderold.get("status");
			if("5".equals(status)||"废弃".equals(status)){
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("工单已废弃无法修改");
				return apiResult;
			}
			String acceptWorker=(String) orderold.get("acceptWorker");
			String workerId=(String) orderold.get("workerId");
			if(!getCurrentWorker().getWorkerId().equals(acceptWorker)
					&&!getCurrentWorker().getWorkerId().equals(workerId)
					&&!workerService.isManager(getCurrentWorker().getWorkerId())){
				apiResult.setStatus(ResponseStatus.auth_failed.getStatusCode());
				apiResult.setDesc("没有修改权限");
				return apiResult;
			}
			workOrder.setDispatchTime(DateUtils.getDateTime());
			if (workOrderService.insertOrUpdate(workOrder)) {
				//判断是否发送通知
				if(isaccpet!=null){
					List<Worker> ml = workerService.searchInfoWorkerIds(workOrder.getAcceptWorker());
					try {
						smsRecordService.sendToWorker("短信通知工单派发", ml);
						emailRecordService.mimeMailToWorker(null, null, ml,"AccpetEmail",workOrder.getId());
					} catch (Exception e) {
						log.error(workOrder.getWorkerId()+"派发发送短信和邮件 ",e);
					}
				}
				log.warn("修改前工单："+orderold.toString());
				log.warn(getCurrentWorker().getWorkerId()+"修改后工单："+workOrder.toString());
				apiResult.setData(workOrder);
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("编辑失败");
			}
		} catch (Exception e) {
			log.error("编辑工单", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation("编辑工单_装机")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "update_zj")
	public ApiResult update_zj(
			@ModelAttribute(value = "WorkOrderZJ") @ApiParam(value = "Created WorkOrderZJ object", required = true) WorkOrderZJ workOrderZJ) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrderZJ == null || workOrderZJ.getId() == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			Map<String,Object> orderold = workOrderService.searchInfo(workOrderZJ.getId());
			String status=(String) orderold.get("status");
			if("5".equals(status)||"废弃".equals(status)){
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("工单已废弃无法修改");
				return apiResult;
			}
			String acceptWorker=(String) orderold.get("acceptWorker");
			String workerId=(String) orderold.get("workerId");
			if(!getCurrentWorker().getWorkerId().equals(acceptWorker)
					&&!getCurrentWorker().getWorkerId().equals(workerId)
					&&!workerService.isManager(getCurrentWorker().getWorkerId())){
				apiResult.setStatus(ResponseStatus.auth_failed.getStatusCode());
				apiResult.setDesc("没有修改的权限");
				return apiResult;
			}
			WorkOrder order = new WorkOrder();
			BeanUtils.copyProperties(workOrderZJ, order);
			order.setWorkerId(getCurrentWorker().getWorkerId());
			if (workOrderService.insertOrUpdate(order)) {
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("编辑失败");
			}
		} catch (Exception e) {
			log.error("编辑工单_装机", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation("编辑工单_移机")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "update_yj")
	public ApiResult update_yj(
			@ModelAttribute(value = "WorkOrderYJ") @ApiParam(value = "Created WorkOrderYJ object", required = true) WorkOrderYJ workOrderYJ) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrderYJ == null || workOrderYJ.getId() == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			Map<String,Object> orderold = workOrderService.searchInfo(workOrderYJ.getId());
			String status=(String) orderold.get("status");
			if("5".equals(status)||"废弃".equals(status)){
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("工单已废弃无法修改");
				return apiResult;
			}
			String acceptWorker=(String) orderold.get("acceptWorker");
			String workerId=(String) orderold.get("workerId");
			if(!getCurrentWorker().getWorkerId().equals(acceptWorker)
					&&!getCurrentWorker().getWorkerId().equals(workerId)
					&&!workerService.isManager(getCurrentWorker().getWorkerId())){
				apiResult.setStatus(ResponseStatus.auth_failed.getStatusCode());
				apiResult.setDesc("没有修改的权限");
				return apiResult;
			}
			WorkOrder order = new WorkOrder();
			BeanUtils.copyProperties(workOrderYJ, order);
			order.setWorkerId(getCurrentWorker().getWorkerId());
			if (workOrderService.insertOrUpdate(order)) {
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("编辑失败");
			}
		} catch (Exception e) {
			log.error("编辑工单_移机", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation("编辑工单_电话故障")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "update_dhgz")
	public ApiResult update_dhgz(
			@ModelAttribute(value = "WorkOrderDHGZ") @ApiParam(value = "Created WorkOrderDHGZ object", required = true) WorkOrderDHGZ workOrderDHGZ) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrderDHGZ == null || workOrderDHGZ.getId() == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			Map<String,Object> orderold = workOrderService.searchInfo(workOrderDHGZ.getId());
			String status=(String) orderold.get("status");
			if("5".equals(status)||"废弃".equals(status)){
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("工单已废弃无法修改");
				return apiResult;
			}
			String acceptWorker=(String) orderold.get("acceptWorker");
			String workerId=(String) orderold.get("workerId");
			if(!getCurrentWorker().getWorkerId().equals(acceptWorker)
					&&!getCurrentWorker().getWorkerId().equals(workerId)
					&&!workerService.isManager(getCurrentWorker().getWorkerId())){
				apiResult.setStatus(ResponseStatus.auth_failed.getStatusCode());
				apiResult.setDesc("没有修改的权限");
				return apiResult;
			}
			WorkOrder order = new WorkOrder();
			BeanUtils.copyProperties(workOrderDHGZ, order);
			order.setWorkerId(getCurrentWorker().getWorkerId());
			if (workOrderService.insertOrUpdate(order)) {
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("编辑失败");
			}
		} catch (Exception e) {
			log.error("编辑工单_电话故障", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation("编辑工单_撤机")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "update_cj")
	public ApiResult update_cj(
			@ModelAttribute(value = "WorkOrderCJ") @ApiParam(value = "Created WorkOrderCJ object", required = true) WorkOrderCJ workOrderCJ) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrderCJ == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			Map<String,Object> orderold = workOrderService.searchInfo(workOrderCJ.getId());
			String status=(String) orderold.get("status");
			if("5".equals(status)||"废弃".equals(status)){
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("工单已废弃无法修改");
				return apiResult;
			}
			String acceptWorker=(String) orderold.get("acceptWorker");
			String workerId=(String) orderold.get("workerId");
			if(!getCurrentWorker().getWorkerId().equals(acceptWorker)
					&&!getCurrentWorker().getWorkerId().equals(workerId)
					&&!workerService.isManager(getCurrentWorker().getWorkerId())){
				apiResult.setStatus(ResponseStatus.auth_failed.getStatusCode());
				apiResult.setDesc("没有修改的权限");
				return apiResult;
			}
			WorkOrder order = new WorkOrder();
			BeanUtils.copyProperties(workOrderCJ, order);
			order.setWorkerId(getCurrentWorker().getWorkerId());
			if (workOrderService.insertOrUpdate(order)) {
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("编辑失败");
			}
		} catch (Exception e) {
			log.error("编辑工单_撤机", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation("编辑工单_it")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "update_it")
	public ApiResult update_it(
			@ModelAttribute(value = "WorkOrderIT") @ApiParam(value = "Created WorkOrderIT object", required = true) WorkOrderIT workOrderIT) {
		ApiResult apiResult = new ApiResult();
		try {
			if (workOrderIT == null || workOrderIT.getId() == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			Map<String,Object> orderold = workOrderService.searchInfo(workOrderIT.getId());
			String status=(String) orderold.get("status");
			if("5".equals(status)||"废弃".equals(status)){
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("工单已废弃无法修改");
				return apiResult;
			}
			String acceptWorker=(String) orderold.get("acceptWorker");
			String workerId=(String) orderold.get("workerId");
			if(!getCurrentWorker().getWorkerId().equals(acceptWorker)
					&&!getCurrentWorker().getWorkerId().equals(workerId)
					&&!workerService.isManager(getCurrentWorker().getWorkerId())){
				apiResult.setStatus(ResponseStatus.auth_failed.getStatusCode());
				apiResult.setDesc("没有修改的权限");
				return apiResult;
			}
			WorkOrder order = new WorkOrder();
			BeanUtils.copyProperties(workOrderIT, order);
			order.setWorkerId(getCurrentWorker().getWorkerId());
			if (workOrderService.insertOrUpdate(order)) {
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("编辑失败");
			}
		} catch (Exception e) {
			log.error("编辑工单_it", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

}
