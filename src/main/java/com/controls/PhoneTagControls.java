package com.controls;

import javax.servlet.http.HttpServletRequest;

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
import com.commons.utils.PageMode;
import com.commons.utils.RequestObjectUtil;
import com.interceptor.BaseCurrentWorkerAware;
import com.modules.phonetag.bean.PhoneTag;
import com.modules.phonetag.service.PhoneTagService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "电话标签")
@RestController
@RequestMapping("/api/phone_tag")
public class PhoneTagControls extends BaseCurrentWorkerAware {
	private static final Logger log = LoggerFactory.getLogger(PhoneTagControls.class);

	@Autowired
	private PhoneTagService phoneTagService;

	@ApiOperation(value = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "显示条数", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "编号", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "手机号码", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "tagValue", value = "标签", dataType = "Integer", paramType = "query") })
	@GetMapping("search")
	public ApiResult search(HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			PhoneTag phoneTag = (PhoneTag) RequestObjectUtil.mapToBean(request, PhoneTag.class);// 获取条件参数
			PageMode pageMode = (PageMode) RequestObjectUtil.mapToBean(request, PageMode.class);// 获取分页参数
			if (phoneTag == null) {
				phoneTag = new PhoneTag();
			}
			if (pageMode == null) {
				pageMode = new PageMode();
			}
			phoneTagService.search(phoneTag, apiResult, pageMode);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("电话标签查询", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation(value = "手机标签查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "phone", value = "手机号码", dataType = "String", paramType = "query") })
	@GetMapping("search_phone")
	public ApiResult search_phone(String phone) {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.getResultMap().put("phonetag", phoneTagService.searchPhone(phone));
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("手机标签查询", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation(value = "新增")
	@ApiImplicitParams({ @ApiImplicitParam(name = "phone", value = "手机号码", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "tagValue", value = "标签", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "描述", dataType = "int", paramType = "query") })
	@PostMapping("insert")
	public ApiResult insert(@RequestParam(required = false) String phone,
			@RequestParam(required = false) Integer tagValue, @RequestParam(required = false) String remark) {
		ApiResult apiResult = new ApiResult();
		try {
			if (StringUtils.isBlank(phone) || tagValue == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
			}
			PhoneTag pt = new PhoneTag();
			pt.setPhone(phone);
			pt.setTagValue(tagValue);
			pt.setRemark(remark);
			phoneTagService.insertOrUpdate(pt);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("新增电话标签", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation(value = "修改")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "编号", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "tagValue", value = "标签", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "描述", dataType = "int", paramType = "query") })
	@PostMapping("update")
	public ApiResult update(@RequestParam(required = false) Integer id,
			@RequestParam(required = false) Integer tagValue, @RequestParam(required = false) String remark) {
		ApiResult apiResult = new ApiResult();
		try {
			if (id == null || tagValue == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			phoneTagService.insertOrUpdate(new PhoneTag(id, tagValue, remark));
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("修改电话标签", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}

	@ApiOperation(value = "删除")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "编号", dataType = "int", paramType = "query") })
	@DeleteMapping("delete")
	public ApiResult delete(@RequestParam(required = false) Integer id) {
		ApiResult apiResult = new ApiResult();
		try {
			if (id == null) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			if (phoneTagService.delete(new PhoneTag(id))) {
				apiResult.setStatus(ResponseStatus.success.getStatusCode());
				apiResult.setDesc(ResponseStatus.success.getStatusDesc());
			} else {
				apiResult.setStatus(ResponseStatus.failed.getStatusCode());
				apiResult.setDesc(ResponseStatus.failed.getStatusDesc());
			}
		} catch (Exception e) {
			log.error("删除电话标签", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
}
