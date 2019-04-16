package com.controls;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commons.Constants;
import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.commons.utils.BeanUtils;
import com.commons.utils.Md5;
import com.commons.utils.PageMode;
import com.commons.utils.RequestObjectUtil;
import com.interceptor.BaseCurrentWorkerAware;
import com.modules.role.bean.Role;
import com.modules.role.service.RoleService;
import com.modules.worker.bean.Worker;
import com.modules.worker.service.WorkerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "人员管理")
@RestController
@RequestMapping("api/workers")
public class WorkerController extends BaseCurrentWorkerAware {

	private static final Logger log = LoggerFactory.getLogger(WorkerController.class);

	@Autowired
	private WorkerService workerService;

	@Autowired
	private RoleService roleService;

	@ApiOperation("权限查询")
	@GetMapping(value = "search_role")
	public ApiResult searchRole(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		List<Role> roleList = roleService.getRoleAll();
		if (roleList.size() > 2)
			roleList.remove(roleList.size() - 1);
		apiResult.setData(roleList);
		apiResult.setStatus(ResponseStatus.success.getStatusCode());
		apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		return apiResult;
	}
	
	@ApiOperation("删除")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "人员Id", required = true, dataType = "Integer", paramType = "query") })
	@DeleteMapping(value = "delete")
	public ApiResult delete(@RequestParam(required = false) Integer id) {
		ApiResult apiResult = new ApiResult();
		if (id == null) {
			apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
			apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
			return apiResult;
		}
		if(!workerService.isManager(getCurrentWorker().getWorkerId())){
			apiResult.setStatus(ResponseStatus.auth_failed.getStatusCode());
			apiResult.setDesc("没有删除权限");
			return apiResult;
		}
		log.warn("删除员工："+id);
		workerService.delete(id);
		apiResult.setStatus(ResponseStatus.success.getStatusCode());
		apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		return apiResult;
	}
	
	@ApiOperation("人员查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "显示条数", dataType = "Integer", paramType = "query"),
			/*
			 * @ApiImplicitParam(name = "workerName", value = "人员姓名", dataType =
			 * "String", paramType = "query")
			 */
	})
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@GetMapping(value = "search")
	public ApiResult search(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			Map<String, Object> paramMap = RequestObjectUtil.getParameterMap(request); // 获取条件参数
			if (paramMap == null) {
				paramMap = new HashMap<String, Object>();
			}
			PageMode pageMode = (PageMode) RequestObjectUtil.mapToBean(request, PageMode.class);// 获取分页参数
			if (pageMode == null) {
				pageMode = new PageMode();
			}
			workerService.searchWorkerList(paramMap, apiResult, pageMode);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("人员管理-人员查询", e);
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
		}
		return apiResult;
	}

	@ApiOperation("人员详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "人员Id", required = true, dataType = "Integer", paramType = "query") })
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@GetMapping(value = "info")
	public ApiResult getWorker(@RequestParam(required = false) Integer id) {
		ApiResult apiResult = new ApiResult();
		try {
			if (id == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			Worker w = workerService.searchInfo(id);
			Map<String, Object> ww = BeanUtils.transBeanToMap(w);
			ww.put("deptId", w.getDeptId().toString());
			List<Map<String, Object>> workerRole = roleService.getRoleIdByWorkerId(w.getWorkerId());
			List<Role> roleList = roleService.getRoleAll();
			Map<String, Object> resMap = new HashMap<>();
			resMap.put("worker", ww);
			if (workerRole.size() > 2)
				workerRole.remove(workerRole.size() - 1);
			resMap.put("workerRole", workerRole);
			resMap.put("roleList", roleList);
			apiResult.setData(resMap);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("人员管理-人员详情", e);
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
		}
		return apiResult;
	}

	@ApiOperation(value = "修改密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "oldPassword", value = "原始密码", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String", paramType = "query") })
	@PostMapping(value = "update_password")
	public ApiResult updatePassword(@RequestParam(required = false) String oldPassword,
			@RequestParam(required = false) String newPassword) {
		ApiResult apiResult = new ApiResult();
		if (StringUtils.isAnyBlank(oldPassword, newPassword)) {
			apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
			apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
			return apiResult;
		}
		String psw = Md5.encrpyt(oldPassword, Constants.PASSWORD_TOKEN);
		Worker worker = workerService.login(getCurrentWorker().getWorkerId(), psw);
		if (worker == null) {
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc("原始密码不正确");
			return apiResult;
		}
		String pswMd5 = Md5.encrpyt(newPassword, Constants.PASSWORD_TOKEN); // 加密
		boolean b = workerService.updatePsw(pswMd5, getCurrentWorker().getId());
		if (!b) {
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc(ResponseStatus.failed.getStatusDesc());
			return apiResult;
		}
		log.warn(worker.getId()+"_修改前密码_"+oldPassword);
		log.warn(worker.getId()+"_修改后密码_"+newPassword);
		apiResult.setStatus(ResponseStatus.success.getStatusCode());
		apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		return apiResult;
	}

	@ApiOperation(value = "系统管理人员添加")
	@RequestMapping(value = "insert", method = { RequestMethod.POST })
	public ApiResult insert(@RequestParam(required = false) String workerName,
			@RequestParam(required = false) String psw,
			@RequestParam(required = false) Integer lineNum, @RequestParam(required = false) String roleId,
			@RequestParam(required = false) Integer deptId, @RequestParam(required = false) String no, @RequestParam(required = false) String email) {
		ApiResult apiResult = new ApiResult();
		if (StringUtils.isAnyBlank(workerName,psw) || deptId == null) {
			apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
			apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
			return apiResult;
		}
		if(!workerService.isManager(getCurrentWorker().getWorkerId())){
			apiResult.setStatus(ResponseStatus.auth_failed.getStatusCode());
			apiResult.setDesc("没有添加权限");
			return apiResult;
		}
		String workerId = workerService.getMaxWorkId();
		if (lineNum != null) {
			Worker ww = workerService.searchInfoLineNum(lineNum);// 分机号
			if (ww != null) {
				apiResult.setStatus(ResponseStatus.extension_repeat.getStatusCode());
				apiResult.setDesc(ResponseStatus.extension_repeat.getStatusDesc());
				return apiResult;
			}
		}
		if (StringUtils.isNoneBlank(no)) {
			if(no.length()<6){
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("账号不能小于6位");
				return apiResult;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("no", no);
			List<Worker> wl = workerService.selWorker(map);// 账号
			if (wl == null || wl.size() > 0) {
				apiResult.setStatus(ResponseStatus.illegal_parameter.getStatusCode());
				apiResult.setDesc("账号已存在");
				return apiResult;
			}
		}
		Worker worker = new Worker();
		if(StringUtils.isBlank(psw)){
			worker.setPsw(Md5.encrpyt(Constants.LOGIN_PSW, Constants.PASSWORD_TOKEN));
		}else{
			worker.setPsw(Md5.encrpyt(psw, Constants.PASSWORD_TOKEN));
		}
		worker.setNo(no);
		worker.setWorkerName(workerName);
		worker.setWorkerId(workerId);
		worker.setLineNum(lineNum);
		worker.setDeptId(deptId);
		worker.setPsw(Md5.encrpyt(Constants.LOGIN_PSW, Constants.PASSWORD_TOKEN));
		worker.setEmail(email);
		if (StringUtils.isBlank(roleId)) {
			roleId = "5";
		} else {
			roleId += ",5";
		}
		if (workerService.addwoker(worker) && roleService.addRoleByWorker(workerId, roleId)) {
			log.warn("添加员工_"+worker.toString());
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} else {
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation(value = "修改人员信息 ")
	@io.swagger.annotations.ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Id", required = true, dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "no", value = "账号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "psw", value = "密码", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "workerName", value = "人员姓名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "lineNum", value = "分机号", required = false, dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "roleId", value = "权限", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "deptId", value = "部门", required = true, dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "email", value = "邮箱", required = false, dataType = "String", paramType = "query")
	})
	@RequestMapping(value = "update", method = { RequestMethod.POST })
	public ApiResult update(Integer id, String workerName, Integer lineNum, String roleId, String no,String psw,Integer deptId,String email) {
		ApiResult apiResult = new ApiResult();

		if (id == null ||deptId==null || StringUtils.isAnyBlank(workerName)) {
			apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
			apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
			return apiResult;
		}
		if(!workerService.isManager(getCurrentWorker().getWorkerId())){
			apiResult.setStatus(ResponseStatus.auth_failed.getStatusCode());
			apiResult.setDesc("没有修改权限");
			return apiResult;
		}
		Worker w = workerService.searchInfo(id);
		if (w == null) {
			apiResult.setStatus(ResponseStatus.illegal_parameter.getStatusCode());
			apiResult.setDesc("人员不存在");
			return apiResult;
		}
		if (lineNum != null) {
			Worker ww = workerService.searchInfoLineNum(lineNum);// 分机号
			if (ww != null && !ww.getWorkerId().equals(w.getWorkerId())) {
				apiResult.setStatus(ResponseStatus.extension_repeat.getStatusCode());
				apiResult.setDesc(ResponseStatus.extension_repeat.getStatusDesc());
				return apiResult;
			}
		}
		if (StringUtils.isNotBlank(no)&&!no.equals(w.getNo())) {
			if(no.length()<6){
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("账号不能小于6位");
				return apiResult;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("no", no);
			List<Worker> wl = workerService.selWorker(map);
			if (wl == null || wl.size() > 0) {
				apiResult.setStatus(ResponseStatus.illegal_parameter.getStatusCode());
				apiResult.setDesc("账号已存在");
				return apiResult;
			}
		}
		Worker worker = new Worker();
		if(StringUtils.isNotBlank(psw))
		worker.setPsw(Md5.encrpyt(psw, Constants.PASSWORD_TOKEN));
		worker.setId(id);
		worker.setNo(no);
		worker.setLineNum(lineNum);
		worker.setWorkerName(workerName);
		worker.setLineNum(lineNum);
		worker.setUpdateTime(new Date());
		worker.setDeptId(deptId);
		worker.setEmail(email);
		if (StringUtils.isBlank(roleId)) {
			roleId = "5";
		} else {
			roleId += ",5";
		}
		if (workerService.updateWorker(worker) && roleService.updateWorkerRole(w.getWorkerId(), roleId)) {
			log.warn("修改前员工_"+w.toString());
			log.warn("修改后员工_"+worker.toString());
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} else {
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

}
