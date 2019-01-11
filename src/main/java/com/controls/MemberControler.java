package com.controls;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.commons.annontations.NoLogin;
import com.commons.apiresult.ApiResult;
import com.commons.apiresult.ResponseStatus;
import com.commons.utils.ChineseInital;
import com.commons.utils.PageMode;
import com.commons.utils.RequestObjectUtil;
import com.interceptor.BaseCurrentWorkerAware;
import com.modules.member.bean.MemberBaseInfo;
import com.modules.member.excel.MemberExcelImport;
import com.modules.member.pojo.MemberBaseInfoPojo;
import com.modules.member.service.MemberBaseInfoService;
import com.modules.memberdept.bean.MemberDept;
import com.modules.memberdept.service.MemberDeptService;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "客户管理")
@RestController
@RequestMapping("api/member")
public class MemberControler extends BaseCurrentWorkerAware {

	private static final Logger log = LoggerFactory.getLogger(MemberControler.class);

	@Autowired
	private MemberBaseInfoService memberBaseInfoService;
	@Autowired
	private MemberDeptService memberDeptService;

	@ApiOperation("搜索客户")
	@ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "显示条数", dataType = "Integer", paramType = "query"),
	})
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100004, message = "未登录"),
			@ApiResponse(code = 100005, message = "未设置可显字段"), @ApiResponse(code = 100008, message = "非法请求") })
	@GetMapping(value = "search")
	public ApiResult search(HttpServletRequest request,
			@ModelAttribute(value = "MemberBaseInfoPojo") @ApiParam(value = "Created memberBaseInfoPojo object", required = true) MemberBaseInfoPojo memberBaseInfoPojo) {
		ApiResult apiResult = new ApiResult();
		try {
			PageMode pageMode = (PageMode) RequestObjectUtil.mapToBean(request, PageMode.class);// 获取分页参数
			if (pageMode == null) {
				pageMode = new PageMode();
			}
			memberBaseInfoService.search(memberBaseInfoPojo, apiResult, pageMode);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("人员管理-人员查询", e);
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
		}
		return apiResult;
	}

	@ApiOperation("客户详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "客户Id", required = true, dataType = "Integer", paramType = "query") })
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
			apiResult.setData(memberBaseInfoService.searchInfo(id));
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("客户详情", e);
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
		}
		return apiResult;
	}

	@ApiOperation("删除")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "客户Id", required = true, dataType = "Integer", paramType = "query") })
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
			memberBaseInfoService.delete(id);
			log.warn("删除客户_"+id);
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("删除客户", e);
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
		}
		return apiResult;
	}

	@ApiOperation(value = "修改客户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "客户姓名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "pinyin", value = "拼音", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "id", value = "客户Id", required = true, dataType = "Integer", paramType = "query"),
			@ApiImplicitParam(name = "deptId", value = "部门", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "电话号码", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "number", value = "number", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "office", value = "分机", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "fax", value = "fax", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "home", value = "home", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "mail", value = "邮箱", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "fangjian", value = "房间", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "beizhu", value = "备注", required = false, dataType = "String", paramType = "query") })
	@PostMapping(value = "update")
	public ApiResult update(@RequestParam(required = false) String name, @RequestParam(required = false) Integer id,
			@RequestParam(required = false) String deptId, @RequestParam(required = false) String phone,
			@RequestParam(required = false) String pinyin, @RequestParam(required = false) String number,
			@RequestParam(required = false) String office, @RequestParam(required = false) String fax,
			@RequestParam(required = false) String home, @RequestParam(required = false) String mail,
			@RequestParam(required = false) String fangjian, @RequestParam(required = false) String beizhu) {
		ApiResult apiResult = new ApiResult();
		if (StringUtils.isAnyBlank(deptId, name) || id == null) {
			apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
			apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
			return apiResult;
		}
		if (StringUtils.isBlank(phone) && StringUtils.isBlank(office)) {
			apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
			apiResult.setDesc("办公电话和手机号码不能同时为空");
			return apiResult;
		}
		if (memberDeptService.searchInfo(deptId) == null) {
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc("职位不存在");
			return apiResult;
		}
		Map<String, Object>  oldm=memberBaseInfoService.searchInfo(id);
		if (oldm == null) {
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
			apiResult.setDesc("客户不存在");
			return apiResult;
		}
		MemberBaseInfo mb = new MemberBaseInfo();
		mb.setMemberId(id);
		mb.setMobile(phone);
		mb.setMemberName(name);
		mb.setMdeptName(deptId);
		if (StringUtils.isNotBlank(pinyin)) {
			mb.setField21(pinyin);
		} else if (StringUtils.isNotBlank(name)) {
			mb.setField21(ChineseInital.getAllFirstLetter(name));// 获取首字母
		}
		mb.setHousePhone(number);
		mb.setField20(office);
		mb.setField27(fax);
		mb.setField5(mail);
		mb.setField24(fangjian);
		mb.setField37(beizhu);
		mb.setField23(home);
		mb.setCreateTime(new Date());
		if (!memberBaseInfoService.insertOrUpdate(mb)) {
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc(ResponseStatus.failed.getStatusDesc());
			return apiResult;
		}
		log.warn("修改前客户_"+oldm.toString());
		log.warn("修改后客户_"+mb.toString());
		apiResult.setStatus(ResponseStatus.success.getStatusCode());
		apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		return apiResult;
	}

	@ApiOperation(value = "客户添加")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "pinyin", value = "拼音", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "deptId", value = "部门", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "电话号码", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "number", value = "number", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "office", value = "分机", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "fax", value = "fax", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "home", value = "home", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "mail", value = "邮箱", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "fangjian", value = "房间", required = false, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "beizhu", value = "备注", required = false, dataType = "String", paramType = "query") })
	@PostMapping(value = "insert")
	public ApiResult insert(@RequestParam(required = false) String name, @RequestParam(required = false) String deptId,
			@RequestParam(required = false) String phone, @RequestParam(required = false) String pinyin,
			@RequestParam(required = false) String number, @RequestParam(required = false) String office,
			@RequestParam(required = false) String fax, @RequestParam(required = false) String home,
			@RequestParam(required = false) String mail, @RequestParam(required = false) String fangjian,
			@RequestParam(required = false) String beizhu) {
		ApiResult apiResult = new ApiResult();
		if (StringUtils.isAnyBlank(deptId, name)) {
			apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
			apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
			return apiResult;
		}
		if (StringUtils.isBlank(phone) && StringUtils.isBlank(office)) {
			apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
			apiResult.setDesc("办公电话和手机号码不能同时为空");
			return apiResult;
		}
		if (memberDeptService.searchInfo(deptId) == null) {
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc("职位不存在");
			return apiResult;
		}
		MemberBaseInfo mb = new MemberBaseInfo();
		mb.setMemberName(name);
		mb.setMobile(phone);
		mb.setMdeptName(deptId);
		mb.setCreateTime(new Date());
		mb.setWorkerId(getCurrentWorker().getWorkerId());
		mb.setMemberType("自定义客户");
		if (StringUtils.isNotBlank(pinyin)) {
			mb.setField21(pinyin);
		} else if (StringUtils.isNotBlank(name)) {
			mb.setField21(ChineseInital.getAllFirstLetter(name));// 获取首字母
		}
		mb.setHousePhone(number);
		mb.setField20(office);
		mb.setField27(fax);
		mb.setField5(mail);
		mb.setField24(fangjian);
		mb.setField37(beizhu);
		mb.setField23(home);
		if (!memberBaseInfoService.insertOrUpdate(mb)) {
			apiResult.setStatus(ResponseStatus.failed.getStatusCode());
			apiResult.setDesc(ResponseStatus.failed.getStatusDesc());
			return apiResult;
		}
		log.warn("新增客户_"+mb.toString());
		apiResult.setData(mb);
		apiResult.setStatus(ResponseStatus.success.getStatusCode());
		apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		return apiResult;
	}

	@NoLogin
	@ApiOperation("客户信息模板导出")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100005, message = "未设置可显字段"),
			@ApiResponse(code = 100008, message = "非法请求") })
	@GetMapping(value = "export_template")
	public ApiResult export_template() {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.getResultMap().put("url", "/templates/MemberTempleate.xlsx");
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
		} catch (Exception e) {
			log.error("客户信息模板导出", e);
		}
		return apiResult;
	}

	@NoLogin
	@ApiOperation("客户信息导入")
	@ApiResponses({ @ApiResponse(code = 100000, message = "请求成功"), @ApiResponse(code = 100001, message = "请求失败"),
			@ApiResponse(code = 100003, message = "参数为空"), @ApiResponse(code = 100005, message = "未设置可显字段"),
			@ApiResponse(code = 100008, message = "非法请求") })
	@PostMapping(value = "import")
	public ApiResult edit(MultipartFile file) {
		ApiResult apiResult = new ApiResult();
		try {
			if (file.isEmpty()) {
				apiResult.setStatus(ResponseStatus.null_param.getStatusCode());
				apiResult.setDesc(ResponseStatus.null_param.getStatusDesc());
				return apiResult;
			}
			ImportParams params = new ImportParams();
			params.setHeadRows(1);
			List<MemberExcelImport> list = ExcelImportUtil.importExcel(file.getInputStream(), MemberExcelImport.class,
					params);
			List<MemberBaseInfo> ml = new ArrayList<>();
			for (MemberExcelImport me : list) {
				List<MemberDept> d = memberDeptService.searchDeptList("0");// 一级部门
																			// 防止重复添加每次查询
				String bumenId = "";
				String chuId = "";
				String defaultId = "";
				String zhiweiId = "";
				boolean f1 = true;
				if (d.size() > 0)
					for (MemberDept d1 : d) {
						if (d1.getDeptName().equals(me.getDanwei())) {
							f1 = false;
							bumenId = d1.getDeptId();
							break;
						}
					}
				if (f1) {// 添加部门
					MemberDept bumen = new MemberDept();
					bumen.setDeptName(me.getDanwei());
					bumen.setDeptParentId("0");
					memberDeptService.insertOrUpdate(bumen);
					bumenId = bumen.getDeptId();
				}
				// 处,处理
				boolean f2 = true;
				List<MemberDept> ld2 = memberDeptService.searchChu(d, bumenId);// 二级处
				if (ld2 != null && ld2.size() > 0)
					for (MemberDept memberDept : ld2) {
						if (memberDept.getDeptName().equals(me.getChu())) {
							f2 = false;
							chuId = memberDept.getDeptId();
							for (MemberDept s : memberDept.getSon()) {
								if (s.getDeptName().equals("--")) {
									defaultId = s.getDeptId();
									break;
								}
							}
							break;
						}
					}
				if (f2) {// 添加处
					MemberDept chu = new MemberDept();
					chu.setDeptName(me.getChu());
					chu.setDeptParentId(bumenId);
					memberDeptService.insertOrUpdate(chu);
					chuId = chu.getDeptId();
					MemberDept defaultd = new MemberDept();
					defaultd.setDeptName("--");
					defaultd.setDeptParentId(chuId);
					memberDeptService.insertOrUpdate(defaultd);
					defaultId = defaultd.getDeptId();
				}
				// 职位处理

				List<MemberDept> ld3 = memberDeptService.searchZhiwei(ld2, chuId);// 职位
				if (StringUtils.isNotBlank(me.getZhiwu())) {
					boolean f3 = true;
					if (ld3 != null && ld3.size() > 0)
						for (MemberDept memberDept : ld3) {
							if (memberDept.getDeptName().equals(me.getZhiwu())) {
								zhiweiId = memberDept.getDeptId();
								f3 = false;
								break;
							}
						}
					if (f3) {
						MemberDept zhiwei = new MemberDept();
						zhiwei.setDeptName(me.getZhiwu());
						zhiwei.setDeptParentId(chuId);
						memberDeptService.insertOrUpdate(zhiwei);
						zhiweiId = zhiwei.getDeptId();
					}
				} else {
					zhiweiId = defaultId;
				}
				MemberBaseInfo m = new MemberBaseInfo();
				m.setMemberName(me.getXingming());
				m.setField21(me.getPinyin());
				m.setMdeptName(zhiweiId);
				m.setField20(me.getBangongdianhua());
				m.setMobile(me.getShouji());
				m.setField5(me.getYouxiang());
				m.setField24(me.getBangongdidian());
				ml.add(m);
			}
			for (MemberBaseInfo insertm : ml) {
				insertm.setCreateTime(new Date());
				insertm.setMemberType("自定义客户");
				memberBaseInfoService.insertOrUpdateExcel(insertm);
				log.warn("模板导入客户信息_"+insertm.toString());
			}
			apiResult.setStatus(ResponseStatus.success.getStatusCode());
			apiResult.setDesc(ResponseStatus.success.getStatusDesc());
		} catch (Exception e) {
			log.error("客户信息导入", e);
			apiResult.setDesc(ResponseStatus.error.getStatusDesc());
			apiResult.setStatus(ResponseStatus.error.getStatusCode());
		}
		return apiResult;
	}
}
