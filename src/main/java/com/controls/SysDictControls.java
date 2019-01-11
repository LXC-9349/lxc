package com.controls;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.commons.utils.PageMode;
import com.commons.utils.RequestObjectUtil;
import com.interceptor.BaseCurrentWorkerAware;
import com.modules.role.service.RoleService;
import com.modules.sysdict.bean.SysDict;
import com.modules.sysdict.service.SysDictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "字典管理")
@RestController
@RequestMapping("/api/sys_dict")
public class SysDictControls extends BaseCurrentWorkerAware {
	private static final Logger log = LoggerFactory.getLogger(SysDictControls.class);
	
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private RoleService roleService;
	
	@ApiOperation(value = "字典列表")
	@GetMapping("search")
	public ApiResult search(@RequestParam(required=false)String type) {
		ApiResult apiResult = new ApiResult();
		try {
			List<SysDict> dictList=null;
			if (StringUtils.isBlank(type)) {
				dictList= sysDictService.search();
			} else {
				dictList=sysDictService.searchSysDict("'"+type+"'");
				if("wostatus".equals(type)){//工单状态权限控制
					List<Map<String,Object>> workerRole=roleService.getRoleIdByWorkerId(getCurrentWorker().getWorkerId());
					List<Integer> roleIds=new ArrayList<>();
					for (Map<String, Object> map : workerRole) {
						roleIds.add((Integer)map.get("roleId"));
					}
					if(!roleIds.contains(3)){//线务
						SysDict rd=null;
						for (SysDict d : dictList) {
							if(d.getValue().equals("3"))
								rd=d;
						}
						dictList.remove(rd);
					}
					if(!roleIds.contains(4)){//机务
						SysDict rd=null;
						for (SysDict d : dictList) {
							if(d.getValue().equals("4"))
								rd=d;
						}
						dictList.remove(rd);
					}
				}
			}
			apiResult.setData(dictList);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("字典列表", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation(value = "字典子项分页搜索")
	@ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "显示条数", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "字典类型", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "label", value = "显示值", dataType = "String", paramType = "query")
	})
	@GetMapping("search_page")
	public ApiResult search_page(HttpServletRequest request,@RequestParam(required=false)String type,@RequestParam(required=false)String label) {
		ApiResult apiResult = new ApiResult();
		try {
			if(StringUtils.isBlank(type)){
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			PageMode pageMode = (PageMode) RequestObjectUtil.mapToBean(request, PageMode.class);// 获取分页参数
			if (pageMode == null) {
				pageMode = new PageMode();
			}
			sysDictService.searchPage(label,type, apiResult, pageMode,getCurrentWorker());
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("字典子项分页搜索", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation("新增字典 子项不能超过9999")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
		@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
		@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "insert")
	public ApiResult insert(@ModelAttribute(value="SysDict") @ApiParam(value = "Created SysDict object", required = true) SysDict sysDict) {
		ApiResult apiResult = new ApiResult();
		try {
			if(sysDictService.insertOrUpdateSysDict(sysDict)){
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			}else{
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc("实际值不能重复");
			}
		} catch (Exception e) {
			log.error("新增字典", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation("编辑字典")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
		@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
		@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "update")
	public ApiResult update(@RequestParam(required=false)Integer id,@RequestParam(required=false)String label) {
		ApiResult apiResult = new ApiResult();
		try {
			if(StringUtils.isBlank(label)||id==null){
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			SysDict sysDict=new SysDict(id,label);
			if(sysDictService.insertOrUpdateSysDict(sysDict)){
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			}else{
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			}
		} catch (Exception e) {
			log.error("编辑字典", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@ApiOperation("删除字典")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
		@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
		@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@DeleteMapping(value = "delete")
	public ApiResult delete(@RequestParam(required=false)Integer id) {
		ApiResult apiResult = new ApiResult();
		try {
			if(id==null){
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			SysDict sysDict=new SysDict(id);
			sysDict.setIsUse(0);
			if(sysDictService.insertOrUpdateSysDict(sysDict)){
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			}else{
				apiResult.setStatus(ResponseStatus.error.getStatusCode());
				apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			}
		} catch (Exception e) {
			log.error("删除字典", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
}
