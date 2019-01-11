package com.modules.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;

import com.modules.member.bean.MemberBaseInfo;
import com.modules.workorder.bean.WorkOrder;

/**
 * 全局增删改查
 * @author DoubleLi
 * @time 2019年1月11日
 * 
 */
@Mapper
@Scope("prototype")
public interface BaseMapper{
	Integer insert(@Param("sql")String sql);
	Integer update(@Param("sql")String sql);
	Integer del(@Param("sql")String sql);
	
	Map<String,Object> selectMap(@Param("sql")String sql);
	/**
	 * 总条数
	 * @param searchCountSql
	 * @return
	 * @time 2018年8月10日
	 * @author DoubleLi
	 */
	Long selectLong(@Param("searchCountSql")String searchCountSql);

	/**
	 * sql数据
	 * @param mysqlLimit
	 * @return
	 * @time 2018年8月10日
	 * @author DoubleLi
	 */
	List<Map<String, Object>> select(@Param("sql")String sql);
	
	/**
	 * 返回新增id
	 * @param sql
	 * @param baseBean
	 * @return
	 * @time 2018年11月26日
	 * @author DoubleLi
	 */
	Integer insertId(@Param("memberBaseInfo")MemberBaseInfo memberBaseInfo);
	/**
	 * 新增工单
	 * @param insertSql
	 * @return
	 * @time 2018年12月6日
	 * @author DoubleLi
	 */
	Integer insertWorkOrder(@Param("workOrder")WorkOrder workOrder);
}