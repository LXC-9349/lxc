package com.modules.role.service;

import java.util.List;
import java.util.Map;

import com.modules.role.bean.Role;

/**
 * 角色名管理
 * @author DZH
 *
 */
public interface RoleService {

	/**
	 * 根据角色ID获取角色
	 * @param groupId
	 * @return
	 */
	Role getRoleById(Integer roleId);

	
	/**
	 * 以下方法源自原项目。。。
	 * @param roleId
	 * @return
	 */
	List<String> getWorkersbyRoleId(Integer roleId);
	

	
	/**
	 * 根据workerId删除员工所拥有的所有角色
	 * @param workerId
	 * @param companyId
	 */
	Boolean deleteWorkerOfRoleByWorkerId(String workerId);

	/**
	 * 根据员工编号获取该员工所有的角色ID
	 * @param workerId
	 * @param companyId
	 * @return
	 */
	List<Map<String,Object>> getRoleIdByWorkerId(String workerId);

	List<Role> getRoleAll();


	/**
	 * 添加用户角色
	 * @param workerId
	 * @param roleId
	 * @return
	 * @time 2018年11月16日
	 * @author DoubleLi
	 */
	Boolean addRoleByWorker(String workerId, String roleId);


	/**
	 * 更新角色
	 * @param workerId
	 * @param roleId
	 * @return
	 * @time 2018年11月16日
	 * @author DoubleLi
	 */
	boolean updateWorkerRole(String workerId, String roleId);
}
