package com.modules.worker.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.commons.apiresult.ApiResult;
import com.commons.utils.JdbcUtils;
import com.commons.utils.PageMode;
import com.commons.utils.PojoUtils;
import com.modules.base.dao.BaseMapper;
import com.modules.role.service.RoleService;
import com.modules.worker.bean.Worker;
import com.modules.worker.dao.WorkerMapper;

@Service
public class WorkerService {

	@Autowired
	private WorkerMapper workerMapper;
	@Autowired
	private BaseMapper baseMapper;
	@Autowired
	private RoleService roleService;

	public Worker login(String username, String password) {
		return workerMapper.login(username, password);
	}

	public List<Worker> selWorker(Map<String, Object> map) {
		try {
			return workerMapper.selWorker(map);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean addwoker(Worker worker) {
		return workerMapper.addwoker(worker) > 0 ? true : false;
	}

	public boolean updateWorker(Worker worker) {
		Map<String, String> dataMap = PojoUtils.comparePojo(null, worker, "serialVersionUID,id");
		final String updateSql = PojoUtils.getUpdateSQL(Worker.class.getSimpleName(), dataMap, "id", worker.getId());
		return baseMapper.update(updateSql) > 0 ? true : false;
	}

	public boolean updatePsw(String password, Integer id) {
		return baseMapper.update("update Worker set psw='" + password + "' where id=" + id) > 0 ? true : false;
	}

	public void updateWorkerOnlineTime(String workerId) {
		workerMapper.updateWorkerOnlineTime(workerId);
	}

	public void searchWorkerList(Map<String, Object> map, ApiResult apiResult, PageMode pageMode) {
		/*
		 * if(map.containsKey("workerName")){ pageMode.setSqlWhere(
		 * " w.workerName like '%"+map.get("workerName")+"%'"); }
		 * if(map.containsKey("phoneNumber")){ pageMode.setSqlWhere(
		 * " w.phoneNumber like '%"+map.get("phoneNumber")+"%'"); }
		 * if(map.containsKey("id")){ pageMode.setSqlWhere(" w.id like '%"
		 * +map.get("id")+"%'"); }
		 */
		pageMode.setSqlWhere("workerStatus <>1");
		pageMode.setSqlFrom(
				"select w.*,d.deptName,GROUP_CONCAT(wor.roleName  ORDER BY wor.roleName) role  from Worker w left join (select d.deptId did,d.deptName from Dept d) d on w.deptId = d.did left join (select wor.workerId,r.roleName from Role r inner join WorkerOfRole wor on r.roleId=wor.roleId)wor on wor.workerId=w.workerId ");
		pageMode.setGroupBy("group by w.id");
		Long total = baseMapper.selectLong(pageMode.getSearchCountSql());// 查询总条数
		pageMode.setTotal(total);
		List<Map<String, Object>> resultMap = baseMapper.select(pageMode.getMysqlLimit());
		pageMode.setApiResult(apiResult, resultMap);
	}

	public Worker searchInfo(Integer id) {
		Worker w = workerMapper.getWorker(id);
		return w;
	}

	public Worker searchInfoWorkerId(String workerId) {
		Worker w = workerMapper.getWorkerWorkerId(workerId);
		return w;
	}

	public Worker searchInfoLineNum(Integer lineNum) {
		Worker w = workerMapper.getInfoLineNum(lineNum);
		return w;
	}

	public String randomKefu() {
		try {
			Map<String, Object> map = baseMapper
					.selectMap("select workerId from Worker where post in(2,20) order by rand() limit 1;");
			return map != null ? map.get("workerId").toString() : null;
		} catch (EmptyResultDataAccessException e) {
		}
		return null;
	}

	public synchronized String getMaxWorkId() {
		Long workerId = baseMapper.selectLong("select workseq from Seq  limit 1");
		if (workerId == null) {
			baseMapper.del("delete from Seq");
			baseMapper.insert("insert into Seq (workseq) values(101)");
			workerId = 101L;
		} else {
			workerId = workerId + 1;
			baseMapper.update("update Seq set workseq=" + workerId);
		}
		return workerId.toString();
	}

	public void delete(Integer id) {
		JdbcUtils.update("update Worker set workerStatus=? where id=?", baseMapper, 1, id);
	}

	public boolean isManager(String workerId) {
		if (StringUtils.isBlank(workerId))
			return false;
		// if(StringUtils.equals(workerId, "8888")) return true;
		List<Map<String, Object>> roles = roleService.getRoleIdByWorkerId(workerId);
		String[] roleIds = PojoUtils.getArrayPropertis(roles, "roleId", null, null);
		if (ArrayUtils.contains(roleIds, "1"))
			return true;
		return false;
	}

	public List<Worker> searchInfoWorkerIds(String workerIds) {
		try {
			return JdbcUtils.selectListByT("select * from Worker where workerId in(" + workerIds + ") ", baseMapper,
					Worker.class, "");
		} catch (Exception e) {
			return null;
		}
	}
}
