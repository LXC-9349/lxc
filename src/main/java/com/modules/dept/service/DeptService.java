package com.modules.dept.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.utils.JdbcUtils;
import com.commons.utils.PojoUtils;
import com.commons.utils.Tools;
import com.modules.base.dao.BaseMapper;
import com.modules.dept.bean.Dept;
import com.modules.worker.bean.Worker;
import com.modules.worker.service.WorkerService;

@Service
public class DeptService {

	@Autowired
	private BaseMapper baseMapper;
	
	@Autowired
	private WorkerService workerService;
	
	/**
	 * 组织架构添加修改
	 * @param dept
	 * @return
	 * @time 2018年11月21日
	 * @author DoubleLi
	 */
	public boolean insertOrUpdate(Dept dept){
		if(dept==null)
			return false;
		String d=dept.getDeptId();
		if(StringUtils.isBlank(d))
		dept.setDeptId(Tools.generateUuid());
		Map<String, String> dataMap = PojoUtils.comparePojo(null, dept, "serialVersionUID,deptId,son");
		if(StringUtils.isBlank(d)){//新增
			final String insertSql = PojoUtils.getInsertSQL(Dept.class.getSimpleName(), dataMap, "deptId");
			return baseMapper.insert(insertSql)>0;
		}else{//更新
			final String updateSql = PojoUtils.getUpdateSQL(Dept.class.getSimpleName(), dataMap, "deptId",
					dept.getDeptId());
			return baseMapper.update(updateSql)>0;
		}
	}
	
	/**
	 * 删除组织架构
	 * @param dept
	 * @return
	 *  -1错误 -2节点还有人,人数  0 错误 1成功
	 * @time 2018年11月21日
	 * @author DoubleLi
	 */
	public String delete(Dept dept){
		if(dept==null)
			return "-1";
		Map<String,Object> map=new HashMap<>();
		map.put("deptId", dept.getDeptId());
		List<Worker> wlist= workerService.selWorker(map);
		if(wlist.size()>0)
			return "-2,"+wlist.size();
		String sql ="update Dept set isUse=0 where deptId=?";
		return  String.valueOf(JdbcUtils.update(sql, baseMapper, dept.getDeptId()));
	}
	
	/**
	 * 搜索所有组织架构
	 * @return
	 * @time 2018年11月21日
	 * @author DoubleLi
	 */
	public List<Dept> searchDeptList(String deptId){
		List<Dept> dl=JdbcUtils.selectListByT("select * from Dept where isUse=1 and deptParentId=?", baseMapper, Dept.class,deptId);
		for (Dept dept : dl) {
			dept.setSon(searchDeptList(dept.getDeptId()));
		}
		return dl;
	}
}
