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
import com.modules.memberdept.bean.MemberDept;
import com.modules.memberdept.service.MemberDeptService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "客户部门管理")
@RestController
@RequestMapping("/api/memberdept")
public class MemberDeptController extends BaseCurrentWorkerAware {
	private static final Logger log = LoggerFactory.getLogger(MemberDeptController.class);
	
	@Autowired
	private MemberDeptService memberDeptService;
	
	@ApiOperation(value = "查询")
	@GetMapping("search")
	public ApiResult search() {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.getResultMap().put("memberDept",memberDeptService.searchDeptList("0"));
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("客户组织架构查询", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation(value = "新增")
	@ApiImplicitParams({ @ApiImplicitParam(name = "deptName", value = "名称", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "deptParentId", value = "上级id没有默认0可为空", dataType = "int", paramType = "query")
	})
	@PostMapping("insert")
	public ApiResult insert(@RequestParam(required = false) String deptName,@RequestParam(required = false) String deptParentId) {
		ApiResult apiResult = new ApiResult();
		try {
			if(StringUtils.isBlank(deptName)){
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			if(StringUtils.isBlank(deptParentId)){
				deptParentId="0";
			}else{
				if(memberDeptService.searchInfo(deptParentId)==null){
					apiResult.setStatus(ResponseStatus.illegal_parameter.getStatusCode());
					apiResult.setDesc("上级不存在");
					return apiResult;
				}
			}
			MemberDept d=new MemberDept();
			d.setDeptName(deptName);
			d.setDeptParentId(deptParentId);
			memberDeptService.insertOrUpdate(d);
			if(!deptParentId.equals("0")){//添加的不为为第一级
				if(memberDeptService.parentIsFrist(deptParentId)){//当前添加的为第二级
					//添加默认部门
					MemberDept defaultd=new MemberDept();
					defaultd.setDeptName("--");
					defaultd.setDeptParentId(d.getDeptId());
					memberDeptService.insertOrUpdate(defaultd);
				}
			}
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("客户组织架构新增", e);
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
			}
			memberDeptService.insertOrUpdate(new MemberDept(deptId,deptName));
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
			}
			String res=memberDeptService.delete(new MemberDept(deptId));
			if("1".equals(res)){
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			}else if(res.startsWith("-2")){
				apiResult.setStatus(ResponseStatus.data_referenced.getStatusCode());
				apiResult.setDesc("该节点下拥有"+res.split(",")[1]+"名客户.请先前往[客户管理]中将这些客户移动至其他部门再进行此操作.");
			}else if(res.startsWith("-3")){
				apiResult.setStatus(ResponseStatus.data_referenced.getStatusCode());
				apiResult.setDesc("该节点下拥有子节点请将子节点移除再进行此操作.");
			}else{
				apiResult.setStatus(ResponseStatus.failed.getStatusCode());
				apiResult.setDesc(ResponseStatus.failed.getStatusDesc());
			}
		} catch (Exception e) {
			log.error("客户组织架构删除", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
}
