package com.modules.sysdict.service;

import java.util.List;

import com.commons.apiresult.ApiResult;
import com.commons.utils.PageMode;
import com.modules.sysdict.bean.SysDict;
import com.modules.worker.bean.Worker;

public interface SysDictService {
	 /**
	  * 根据类型查找字典 type可以是多个类型
	 * @param type
	 * @return
	 * @time 2018年11月16日
	 * @author DoubleLi
	 */
	List<SysDict> searchSysDict(String type);

	/**
	 * 父级字典列表
	 * @return
	 * @time 2018年11月16日
	 * @author DoubleLi
	 */
	List<SysDict> search();

	/**
	 * 新增或修改
	 * @param sysDict
	 * @return
	 * @time 2018年11月16日
	 * @author DoubleLi
	 */
	boolean insertOrUpdateSysDict(SysDict sysDict);

	/**
	 * 新增
	 * @param d
	 * @time 2018年12月18日
	 * @author DoubleLi
	 */
	void insert(SysDict d);

	/**
	 * 分页搜索
	 * @param type
	 * @param apiResult
	 * @param pageMode
	 * @param currentWorker
	 * @time 2018年12月18日
	 * @author DoubleLi
	 * @param type 
	 */
	void searchPage(String label, String type, ApiResult apiResult, PageMode pageMode, Worker currentWorker);

	/**
	 * 获取字典
	 * @param type
	 * @param value
	 * @return
	 * @time 2019年1月4日
	 * @author DoubleLi
	 */
	String getLabel(String type, String value);
	
}
