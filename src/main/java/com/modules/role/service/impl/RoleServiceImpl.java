package com.modules.role.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modules.base.dao.BaseMapper;
import com.modules.role.bean.Role;
import com.modules.role.mapper.RoleMapper;
import com.modules.role.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private BaseMapper baseMapper;
	
	
	@Override
	public Role getRoleById(Integer roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWorkersbyRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteWorkerOfRoleByWorkerId(String workerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map<String,Object>> getRoleIdByWorkerId(String workerId) {
		return roleMapper.getRoleIdByWorkerId(workerId);
	}

	@Override
	public List<Role> getRoleAll() {
		return roleMapper.getRoleAll();
	}

	@Override
	public Boolean addRoleByWorker(String workerId, String roleId) {
		if(StringUtils.isBlank(roleId))
			return false;
		StringBuffer sbsql=new StringBuffer("insert ignore into WorkerOfRole (workerId,roleId) values");
		for (String r : roleId.split(",")) {
			sbsql.append("('");
			sbsql.append(workerId);
			sbsql.append("','").append(r);
			sbsql.append("'),");
		}
		return baseMapper.insert(sbsql.substring(0,sbsql.length()-1))>0;
	}

	@Override
	public boolean updateWorkerRole(String workerId, String roleId) {
		if(StringUtils.isBlank(roleId))
			return false;
		baseMapper.del("delete from WorkerOfRole where workerId='"+workerId+"'");
		return addRoleByWorker(workerId,roleId);
	}
 
}
