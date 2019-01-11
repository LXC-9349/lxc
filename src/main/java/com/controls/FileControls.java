package com.controls;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.commons.annontations.NoLogin;
import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.commons.utils.FileUtils;
import com.interceptor.BaseCurrentWorkerAware;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "媒体类型处理bean")
@RestController
@RequestMapping("/api/file")
public class FileControls extends BaseCurrentWorkerAware {
	private static final Logger log = LoggerFactory.getLogger(FileControls.class);
	
	@ApiOperation(value = "文件上传")
	@PostMapping("file_upload")
	public ApiResult file_upload(MultipartFile file) {
		ApiResult apiResult = new ApiResult();
		try {
			String path=FileUtils.upload(file);
			apiResult.setData(path);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("文件上传", e);
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
		}
		return apiResult;
	}
	
	@NoLogin
	@ApiOperation(value = "文件下载")
	@ApiImplicitParams({ @ApiImplicitParam(name = "filePath", value = "文件路径", dataType = "String", paramType = "query")
	})
	@GetMapping("file_download")
	public void file_download(@RequestParam(required = false) String filePath,HttpServletResponse response,HttpServletRequest request) {
		ApiResult apiResult = new ApiResult();
		try {
			String desc=FileUtils.downFile(filePath, request, response, "");
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(desc);
		} catch (Exception e) {
			log.error("文件下载", e);
		}
	}
	
}
