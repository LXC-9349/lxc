package com.modules.sysdict.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.modules.sysdict.bean.SysDict;


@Mapper
public interface SysDictMapper{

	/**
	 * 搜索字典
	 * @param type
	 * @return
	 * @time 2018年10月24日
	 * @author DoubleLi
	 */
	List<SysDict> searchSysDict(@Param("type") String type);
	
	/**
	 * 所有字典
	 * @return
	 * @time 2018年11月16日
	 * @author DoubleLi
	 */
	List<SysDict> searchAllParent();

	void insert(@Param("sysDict")SysDict sysDict);
	
}