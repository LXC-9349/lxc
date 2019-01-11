package com.modules.workorder.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author DoubleLi
 * @time 2018年11月22日
 * 
 */

import com.commons.apiresult.ApiResult;
import com.commons.utils.BeanUtils;
import com.commons.utils.DateUtils;
import com.commons.utils.JdbcUtils;
import com.commons.utils.PageMode;
import com.commons.utils.PojoUtils;
import com.modules.base.dao.BaseMapper;
import com.modules.member.service.MemberBaseInfoService;
import com.modules.sysdict.bean.SysDict;
import com.modules.sysdict.service.SysDictService;
import com.modules.worker.bean.Worker;
import com.modules.worker.service.WorkerService;
import com.modules.workorder.bean.WorkOrder;
import com.modules.workorder.bean.WorkOrderPush;
import com.modules.workorder.pojo.WorkOrderPojo;

@Service
public class WorkOrderService {
	@Autowired
	private BaseMapper baseMapper;

	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private MemberBaseInfoService memberBaseInfoService;
	/**
	 * 新增或修改工单
	 * 
	 * @param workorder
	 * @return
	 * @time 2018年11月22日
	 * @author DoubleLi
	 */
	public Boolean insertOrUpdate(WorkOrder workorder) {
		if (workorder == null)
			return false;
		if (workorder.getId() == null) {// 新增
			//final String insertSql = PojoUtils.getInsertSQL(WorkOrder.class.getSimpleName(), dataMap, "id");
			if(workorder.getCreateTime()==null)
				workorder.setCreateTime(new Date());
			return baseMapper.insertWorkOrder(workorder) > 0;
		} else {// 更新
			Map<String, String> dataMap = PojoUtils.comparePojo(null, workorder, "serialVersionUID,id");
			final String updateSql = PojoUtils.getUpdateSQL(WorkOrder.class.getSimpleName(), dataMap, "id",
					workorder.getId());
			return baseMapper.update(updateSql) > 0;
		}
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @time 2018年11月22日
	 * @author DoubleLi
	 */
	public Boolean delete(Integer id) {
		return JdbcUtils.update("update WorkOrder set isUse=?", baseMapper, 0) > 0;
	}

	/**
	 * 工单搜索
	 * 
	 * @param workOrder
	 * @param apiResult
	 * @param pageMode
	 * @throws Exception
	 * @time 2018年11月22日
	 * @author DoubleLi
	 * @param deptId 
	 */
	public void search(WorkOrderPojo workOrderPojo, ApiResult apiResult, PageMode pageMode, Worker worker) throws Exception {
		if (workOrderPojo.getStatus() != null) {
			pageMode.setSqlWhere("(wo.status = '" + workOrderPojo.getStatus() +"' or wo.status = '"+workOrderPojo.getStatusName()+"')");
		}else{
			if(workOrderPojo.getUndone()!=null&&workOrderPojo.getUndone().intValue()==1)
				pageMode.setSqlWhere("wo.status in (1,2,28,85,3)");
			else
			pageMode.setSqlWhere("wo.status <> 5");
		}
		// 时间
		if (StringUtils.isNoneBlank(workOrderPojo.getCreateA())) {
			pageMode.setSqlWhere("wo.updateTime >='" + workOrderPojo.getCreateA() + "' ");
		}
		// 时间
		if (StringUtils.isNoneBlank(workOrderPojo.getCreateB())) {
			pageMode.setSqlWhere("wo.updateTime <='" + workOrderPojo.getCreateB() + "' + interval 1 day");
		}
		// 业务类型
		if (workOrderPojo.getBusinesstype() != null) {
			pageMode.setSqlWhere("wo.dbusinesstype =" + workOrderPojo.getBusinesstype());
		}
		//工单类型
		if (workOrderPojo.getType() != null) {
			pageMode.setSqlWhere("wo.type =" + workOrderPojo.getType());
		}
		//客户id
		if (workOrderPojo.getMemberId()!=null) {
			pageMode.setSqlWhere("wo.memberId =" + workOrderPojo.getMemberId());
		}
		//工单编号
		if (workOrderPojo.getNo() != null) {
			pageMode.setSqlWhere("wo.no ='" + workOrderPojo.getNo()+"'");
		}
		//用户名称
		if (workOrderPojo.getMemberName() != null) {
			pageMode.setSqlWhere("(m.memberName like '%" + workOrderPojo.getMemberName()
			+ "%' or  GET_FIRST_PINYIN_CHAR(m.memberName) ='" + workOrderPojo.getMemberName().toUpperCase()
			+ "' or upper(m.field21) like '%" + workOrderPojo.getMemberName().toUpperCase()+ "%')");
		}
		//电话号码
		if (workOrderPojo.getPhone() != null) {
			pageMode.setSqlWhere("(m.mobile like '%" + workOrderPojo.getPhone()+"%' or m.field20 like '%" + workOrderPojo.getPhone()+"%')");
		}
		/* where end */
		String innersql="";
		if(!worker.getWorkerId().equals("8888")){
			innersql=" inner join Worker w on (w.workerId=wo.dispatchName or w.workerId=wo.acceptWorker)";
			pageMode.setSqlWhere("w.deptId='"+worker.getDeptId()+"'");
		}
		pageMode.setSqlFrom("select  wo.* from WorkOrder wo"+innersql +" inner join MemberBaseInfo m on m.memberId=wo.memberId");
		pageMode.setGroupBy(" group by wo.no");
		pageMode.setOrderBy("order by wo.updateTime desc");
		Long total = baseMapper.selectLong(pageMode.getSearchCountSql());// 查询总条数
		pageMode.setTotal(total);
		List<Map<String, Object>> data = baseMapper.select(pageMode.getMysqlLimit());
		if (data != null && data.size() > 0) {
			//查找员工表
			List<Worker> wl=workerService.selWorker(new HashMap<>());
			// 搜索字典 工单类型 业务类型 用户部门 话机类型 电话权限 电话业务 工单状态 设备型号 故障初步定位 所属类别
			List<SysDict> sysdict = sysDictService.searchSysDict(
					"'wotype','wobusiness','wodept','wohj','wophone','wophonebussiness','wostatus','womodel','wofault','woclass','woitbus','woitstatus'");
			if (sysdict != null && sysdict.size() > 0) {
				ExecutorService executor = Executors.newCachedThreadPool();
				for (Map<String, Object> map : data) {
					Runnable r = new Runnable() {
						@Override
						public void run() {
							Integer type = (Integer) map.get("type");// 类型
							String status = (String) map.get("status");// 状态
							Integer dbusinesstype = (Integer) map.get("dbusinesstype");// 业务类型
//							Integer ddept = (Integer) map.get("ddept");// 部门
							String phonePermission = (String) map.get("phonePermission");// 电话权限
							String phoneBusiness = (String) map.get("phoneBusiness");// 电话业务
							Integer itModel = (Integer) map.get("itModel");// 设备型号
							Integer itFaulePositioning = (Integer) map.get("itFaulePositioning");// 故障初步定位
							Integer itClass = (Integer) map.get("itClass");// 所属类别
							String acceptWorker= (String) map.get("acceptWorker");// 受理人员
							String dispatchName= (String) map.get("dispatchName");// 派单人员
							String jw= (String) map.get("jw");// 机务
							String xw= (String) map.get("xw");// 线务
							Integer memberid= (Integer) map.get("memberId");// 客户
							if (memberid != null) {
								Map<String, Object> member = memberBaseInfoService.searchInfo(memberid,1);
								map.put("phone", member.get("phone"));
								map.put("office", member.get("field20"));
								map.put("ddept", member.get("mDeptName"));
								map.put("name", member.get("memberName"));
								map.put("member", member);
							}
							if(StringUtils.isNotBlank(dispatchName)){
								for (Worker w : wl) {
									if(w.getWorkerId().equals(dispatchName)){
										map.put("dispatchName", w.getWorkerName());
									}
								}
							}
							if(StringUtils.isNotBlank(acceptWorker)){
								for (Worker w : wl) {
									if(w.getWorkerId().equals(acceptWorker)){
										map.put("acceptWorker", w.getWorkerName());
									}
								}
							}
							if(StringUtils.isNotBlank(jw)){
								for (Worker w : wl) {
									if(w.getWorkerId().equals(jw)){
										map.put("jw", w.getWorkerName());
									}
								}
							}
							if(StringUtils.isNotBlank(xw)){
								for (Worker w : wl) {
									if(w.getWorkerId().equals(xw)){
										map.put("xw", w.getWorkerName());
									}
								}
							}
							for (SysDict sysDict2 : sysdict) {
								if (type != null&& "wotype".equals(sysDict2.getType())
										&& type.toString().equals(sysDict2.getValue())) {
									map.put("type", sysDict2.getLabel());
								}else if (StringUtils.isNotBlank(status)&&type.intValue()==37 && "woitstatus".equals(sysDict2.getType())
										&& status.toString().equals(sysDict2.getValue())) {
									map.put("status", sysDict2.getLabel());
								} else if (StringUtils.isNotBlank(status)&&type.intValue()==65 && "wostatus".equals(sysDict2.getType())
										&& status.equals(sysDict2.getValue())) {
									map.put("status", sysDict2.getLabel());
								} else if (dbusinesstype != null&&type.intValue()==65 && "wobusiness".equals(sysDict2.getType())
										&& dbusinesstype.toString().equals(sysDict2.getValue())) {
									map.put("dbusinesstype", sysDict2.getLabel());
								} else if (dbusinesstype != null&&type.intValue()==37 && "woitbus".equals(sysDict2.getType())
										&& dbusinesstype.toString().equals(sysDict2.getValue())) {
									map.put("dbusinesstype", sysDict2.getLabel());
								}/*else if (ddept != null && "wodept".equals(sysDict2.getType())
										&& ddept.toString().equals(sysDict2.getValue())) {
									map.put("ddept", sysDict2.getLabel());
								} */else if (phonePermission != null && "wophone".equals(sysDict2.getType())
										&& ArrayUtils.contains(phonePermission.split(","), sysDict2.getValue())) {
									map.put("phonepermissionShow",
											(map.get("phonepermissionShow")!=null?map.get("phonepermissionShow")+ ",":"") + sysDict2.getLabel());
								} else if (phoneBusiness != null && "wophonebussiness".equals(sysDict2.getType())
										&& ArrayUtils.contains(phoneBusiness.split(","), sysDict2.getValue())) {
									map.put("phonebusinessShow",
											(map.get("phonebusinessShow")!=null?map.get("phonebusinessShow")+ ",":"")  + sysDict2.getLabel());
								} else if (itModel != null && "womodel".equals(sysDict2.getType())
										&& itModel.toString().equals(sysDict2.getValue())) {
									map.put("itModel", sysDict2.getLabel());
								} else if (itFaulePositioning != null && "wofault".equals(sysDict2.getType())
										&& itFaulePositioning.toString().equals(sysDict2.getValue())) {
									map.put("itFaulePositioning", sysDict2.getLabel());
								} else if (itClass != null && "woclass".equals(sysDict2.getType())
										&& itClass.toString().equals(sysDict2.getValue())) {
									map.put("itClass", sysDict2.getLabel());
								}
							}
						}
					};
					executor.submit(r);
				}
				executor.shutdown();
				executor.awaitTermination(1, TimeUnit.HOURS);
			}
		}
		pageMode.setApiResult(apiResult, data);
	}

	public Map<String, Object> searchInfo(Integer id) {
		Map<String, Object> map = baseMapper.selectMap("select * from WorkOrder where id =" + id);
//		List<SysDict> sysdict = sysDictService.searchSysDict(
//				"'wotype','wobusiness','wodept','wohj','wophone','wophonebussiness','wostatus','womodel','wofault','woclass'");
		Integer type = (Integer) map.get("type");// 类型
//		String status = (String) map.get("status");// 状态
		Integer dbusinesstype = (Integer) map.get("dbusinesstype");// 业务类型
//		Integer ddept = (Integer) map.get("ddept");// 部门
//		String phonePermission = (String) map.get("phonePermission");// 电话权限
//		String phoneBusiness = (String) map.get("phoneBusiness");// 电话业务
		Integer itModel = (Integer) map.get("itModel");// 设备型号
		Integer itFaulePositioning = (Integer) map.get("itFaulePositioning");// 故障初步定位
		Integer itClass = (Integer) map.get("itClass");// 所属类别
		String acceptWorker= (String) map.get("acceptWorker");// 受理人员
		String dispatchName= (String) map.get("dispatchName");// 派单人员
		String jw= (String) map.get("jw");// 机务
		String xw= (String) map.get("xw");// 线务
		String dispatchTime=(String)map.get("dispatchTime");
		if(StringUtils.isNotBlank(dispatchTime)){
			map.put("dispatchTime",DateUtils.parseDate(dispatchTime).getTime());
			map.put("dispatchTimeShow",dispatchTime);
		}
		Integer dhjtype=(Integer) map.get("dhjtype");// 话机类型
		Integer memberid= (Integer) map.get("memberId");// 客户
		if (memberid != null) {
			Map<String, Object> member = memberBaseInfoService.searchInfo(memberid,1);
			map.put("member", member);
		}
		map.put("type", type!=null?type.toString():"");
		map.put("dbusinesstype", dbusinesstype!=null?dbusinesstype.toString():"");
//		map.put("ddept", ddept!=null?ddept.toString():"");
		map.put("itModel", itModel!=null?itModel.toString():"");
		map.put("itFaulePositioning", itFaulePositioning!=null?itFaulePositioning.toString():"");
		map.put("itClass", itClass!=null?itClass.toString():"");
		map.put("dhjtype", dhjtype!=null?dhjtype.toString():"");
		//查找员工表
		List<Worker> wl=workerService.selWorker(new HashMap<>());
		if(StringUtils.isNotBlank(dispatchName)){
			for (Worker w : wl) {
				if(w.getWorkerId().equals(dispatchName)){
					map.put("dispatchNameShow", w.getWorkerName());
					map.put("dispatchName", dispatchName);
					break;
				}
			}
		}
		if(StringUtils.isNotBlank(acceptWorker)){
			for (Worker w : wl) {
				if(w.getWorkerId().equals(acceptWorker)){
					map.put("acceptWorkerShow", w.getWorkerName());
					map.put("acceptWorker", acceptWorker);
					break;
				}
			}
		}
		if(StringUtils.isNotBlank(jw)){
			for (Worker w : wl) {
				if(w.getWorkerId().equals(jw)){
					map.put("jwShow", w.getWorkerName());
					map.put("jw", jw);
					break;
				}
			}
		}
		if(StringUtils.isNotBlank(xw)){
			for (Worker w : wl) {
				if(w.getWorkerId().equals(xw)){
					map.put("xwShow", w.getWorkerName());
					map.put("xw", xw);
					break;
				}
			}
		}
		/*map.put("phonePermission",null);
		map.put("phoneBusiness",null);
		for (SysDict sysDict2 : sysdict) {
			if (type != null && "wotype".equals(sysDict2.getType()) && type.toString().equals(sysDict2.getValue())) {
				map.put("type", sysDict2.getLabel());
			} else if (StringUtils.isNoneBlank(status)  && "wostatus".equals(sysDict2.getType())
					&& status.equals(sysDict2.getValue())) {
				map.put("status", sysDict2.getLabel());
			} else if (dbusinesstype != null && "wobusiness".equals(sysDict2.getType())
					&& dbusinesstype.toString().equals(sysDict2.getValue())) {
				map.put("dbusinesstype", sysDict2.getLabel());
			} else if (ddept != null && "wodept".equals(sysDict2.getType())
					&& ddept.toString().equals(sysDict2.getValue())) {
				map.put("ddept", sysDict2.getLabel());
			} else if (phonePermission != null && "wophone".equals(sysDict2.getType())
					&& ArrayUtils.contains(phonePermission.split(","), sysDict2.getValue())) {
				map.put("phonePermission", (map.get("phonePermission")!=null?(map.get("phonePermission")+ ","):"") + sysDict2.getLabel());
			} else if (phoneBusiness != null && "wophonebussiness".equals(sysDict2.getType())
					&& ArrayUtils.contains(phoneBusiness.split(","), sysDict2.getValue())) {
				map.put("phoneBusiness", (map.get("phoneBusiness")!=null?map.get("phoneBusiness")+ ",":"") + sysDict2.getLabel());
			} else if (itModel != null && "womodel".equals(sysDict2.getType())
					&& itModel.toString().equals(sysDict2.getValue())) {
				map.put("itModel", sysDict2.getLabel());
			} else if (itFaulePositioning != null && "wofault".equals(sysDict2.getType())
					&& itFaulePositioning.toString().equals(sysDict2.getValue())) {
				map.put("itFaulePositioning", sysDict2.getLabel());
			} else if (itClass != null && "woclass".equals(sysDict2.getType())
					&& itClass.toString().equals(sysDict2.getValue())) {
				map.put("itClass", sysDict2.getLabel());
			}else if (dhjtype != null && "wohj".equals(sysDict2.getType())
					&& dhjtype.toString().equals(sysDict2.getValue())) {
				map.put("dhjtype", sysDict2.getLabel());
			}
		}*/
		if(map.get("phonePermission")!=null){
			map.put("phonePermission",map.get("phonePermission").toString().split(","));
		}
		if(map.get("phoneBusiness")!=null){
			map.put("phoneBusiness",map.get("phoneBusiness").toString().split(","));
		}
		return map;
	}

	/**
	 * 根据编号查询工单
	 * @param id
	 * @return
	 * @time 2018年11月27日
	 * @author DoubleLi
	 */
	public WorkOrder searchWorkOrder(String id) {
		try {
			return BeanUtils.transMapToBean(WorkOrder.class, baseMapper.selectMap("select * from WorkOrder w where id='"+id+"' or no='"+id+"' "));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 添加日志
	 * @param string
	 * @time 2018年11月27日
	 * @author DoubleLi
	 */
	public void saveLog(String logtext) {
		JdbcUtils.insert("insert PushLog(type,logtext,createTime) values(?,?,?)", baseMapper, "接收",logtext,DateUtils.getDateTime());
	}

	/**
	 * 接收推送数据更新状态
	 * @param workOrderPush
	 * @time 2018年11月27日
	 * @author DoubleLi
	 */
	public void updateStatus(WorkOrderPush workOrderPush) {
		JdbcUtils.update("update WorkOrder set updateTime=?,status=?  where no=?", baseMapper, DateUtils.getDateTime(),workOrderPush.getStatus(),workOrderPush.getId());
	}

	/**
	 * 工单未完成数量
	 * @param type
	 * @return
	 * @time 2018年12月26日
	 * @author DoubleLi
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> undone(Integer type) {
		StringBuilder sqlBuilder = new StringBuilder("select type,count(1) count from WorkOrder where status in (1,2,28,85,3)");
		if(type != null && type > 0){
			sqlBuilder.append(" and type=").append(type);
		}
		sqlBuilder.append(" group by type");
		return JdbcUtils.selectListByT(sqlBuilder.toString(), baseMapper, Map.class);
	}
}