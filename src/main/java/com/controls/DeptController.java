package com.controls;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.interceptor.BaseCurrentWorkerAware;
import com.modules.dept.bean.Dept;
import com.modules.dept.service.DeptService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "组织架构")
@RestController
@RequestMapping("/api/dept")
public class DeptController extends BaseCurrentWorkerAware {
	private static final Logger log = LoggerFactory.getLogger(DeptController.class);
	
	@Autowired
	private DeptService deptService;
	
	@ApiOperation(value = "查询")
	@GetMapping("search")
	public ApiResult search() {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setData(deptService.searchDeptList("0"));
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("组织架构查询", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation(value = "新增")
	@ApiImplicitParams({ @ApiImplicitParam(name = "deptName", value = "名称", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "deptName", value = "上级id没有默认0可为空", dataType = "int", paramType = "query")
	})
	@PostMapping("insert")
	public ApiResult insert(@RequestParam(required = false) String deptName,@RequestParam(required = false) String deptParentId) {
		ApiResult apiResult = new ApiResult();
		try {
			if(StringUtils.isBlank(deptName)){
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
			}
			if(deptParentId==null){
				deptParentId="0";
			}
			Dept d=new Dept();
			d.setDeptName(deptName);
			d.setDeptParentId(deptParentId);
			deptService.insertOrUpdate(d);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("组织架构新增", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation(value = "修改")
	@ApiImplicitParams({ @ApiImplicitParam(name = "deptName", value = "名称", dataType = "String", paramType = "query")
	, @ApiImplicitParam(name = "deptId", value = "名称", dataType = "int", paramType = "query")
	})
	@PostMapping("update")
	public ApiResult update(@RequestParam(required = false) String deptName,@RequestParam(required = false) String deptId) {
		ApiResult apiResult = new ApiResult();
		try {
			if(StringUtils.isAnyBlank(deptName,deptId)){
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			deptService.insertOrUpdate(new Dept(deptId,deptName));
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("组织架构修改", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation(value = "删除")
	@ApiImplicitParams({ @ApiImplicitParam(name = "deptId", value = "名称", dataType = "int", paramType = "query")
	})
	@DeleteMapping("delete")
	public ApiResult delete(@RequestParam(required = false) String deptId) {
		ApiResult apiResult = new ApiResult();
		try {
			if(deptId==null){
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			String res=deptService.delete(new Dept(deptId));
			if("1".equals(res)){
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			}else if(res.startsWith("-2")){
				apiResult.setStatus(ResponseStatus.data_referenced.getStatusCode());
				apiResult.setDesc("该节点下拥有"+res.split(",")[1]+"名工作人员.请先前往[人员管理]中将这些员工移动至其他小组再进行此操作.");
			}else{
				apiResult.setStatus(ResponseStatus.failed.getStatusCode());
				apiResult.setDesc(ResponseStatus.failed.getStatusDesc());
			}
		} catch (Exception e) {
			log.error("组织架构删除", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
}
