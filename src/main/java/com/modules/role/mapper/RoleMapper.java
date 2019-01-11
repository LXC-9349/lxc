package com.modules.role.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.modules.role.bean.Role;


@Mapper
public interface RoleMapper {
	
	/**
	 * 获取得所有角色
	 * @param companyId
	 * @return
	 */
	List<Role> getRoleAll();
	 
	/**
	 * 根据员工Id获取该员工所拥有的角色
	 * @param workerId
	 * @param companyId
	 * @return
	 */
	List<Map<String,Object>> getRoleIdByWorkerId(@Param("workerId")String workerId);

}