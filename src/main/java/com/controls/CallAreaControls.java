package com.controls;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.interceptor.BaseCurrentWorkerAware;
import com.modules.callarea.service.CallAreaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "归属地查询")
@RestController
@RequestMapping("/api/callarea")
public class CallAreaControls extends BaseCurrentWorkerAware {
	private static final Logger log = LoggerFactory.getLogger(CallAreaControls.class);

	@Autowired
	private CallAreaService callAreaService;

	@ApiOperation(value = "归属地查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "mobile", value = "手机号码", dataType = "String", paramType = "query") })
	@GetMapping("search")
	public ApiResult search(@RequestParam(required = false) String mobile) {
		ApiResult apiResult = new ApiResult();
		try {
			List<Map<String, Object>> cl = callAreaService.searchArea(mobile);
			if (cl != null)
				for (Map<String, Object> mm : cl) {
					apiResult.getResultMap().put("province", mm.get("province"));
					apiResult.getResultMap().put("city", mm.get("city"));
					break;
				}
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("归属地查询", e);
		}
		return apiResult;
	}

}
