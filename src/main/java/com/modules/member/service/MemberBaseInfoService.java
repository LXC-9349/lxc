package com.modules.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.apiresult.ApiResult;
import com.commons.utils.JdbcUtils;
import com.commons.utils.PageMode;
import com.commons.utils.PojoUtils;
import com.modules.base.dao.BaseMapper;
import com.modules.callarea.service.CallAreaService;
import com.modules.member.bean.MemberBaseInfo;
import com.modules.member.pojo.MemberBaseInfoPojo;
import com.modules.memberdept.bean.MemberDept;
import com.modules.memberdept.service.MemberDeptService;

/**
 * 客户service
 * 
 * @author DoubleLi
 * @time 2018年12月17日
 * 
 */
@Service
public class MemberBaseInfoService {
	@Autowired
	private BaseMapper baseMapper;
	@Autowired
	private CallAreaService callAreaService;
	@Autowired
	private MemberDeptService memberDeptService;

	private String[] memberSort = { "m.field21", "m.mDeptName", "m.field20", "m.mobile", "m.field5", "m.zongdanliang",
			"m.fuwuzhong" };

	/**
	 * 新增修改
	 * 
	 * @param memberBaseInfo
	 * @return
	 * @time 2018年11月23日
	 * @author DoubleLi
	 */
	public Boolean insertOrUpdate(MemberBaseInfo memberBaseInfo) {
		if (memberBaseInfo == null)
			return false;
		Map<String, String> dataMap = PojoUtils.comparePojo(null, memberBaseInfo, "serialVersionUID,memberId");
		if (memberBaseInfo.getMemberId() == null) {// 新增
			if (memberBaseInfo.getField20() != null && memberBaseInfo.getMobile() != null
					&& (baseMapper.selectLong("select ifnull((select count(1) from MemberBaseInfo where mobile='"
							+ memberBaseInfo.getMobile() + "' and field20='" + memberBaseInfo.getField20()
							+ "'),0)") > 0)) {// 修改
				final String updateSql = PojoUtils.getUpdateSQL(MemberBaseInfo.class.getSimpleName(), dataMap,
						"mobile,field20", memberBaseInfo.getMobile(), memberBaseInfo.getField20());
				return baseMapper.update(updateSql) > 0;
			}
			Boolean flag = baseMapper.insert(PojoUtils.getInsertSQL(MemberBaseInfo.class.getSimpleName(), dataMap, "memberId")) > 0;
			return flag;
		} else {// 更新
			final String updateSql = PojoUtils.getUpdateSQL(MemberBaseInfo.class.getSimpleName(), dataMap, "memberId",
					memberBaseInfo.getMemberId());
			return baseMapper.update(updateSql) > 0;
		}
	}

	/**
	 * 导入客户新增修改
	 * 
	 * @param memberBaseInfo
	 * @return
	 * @time 2018年11月23日
	 * @author DoubleLi
	 */
	public Boolean insertOrUpdateExcel(MemberBaseInfo memberBaseInfo) {
		if (memberBaseInfo == null)
			return false;
		Map<String, String> dataMap = PojoUtils.comparePojo(null, memberBaseInfo, "serialVersionUID,memberId");
		if (memberBaseInfo.getField20() != null && memberBaseInfo.getMobile() != null
				&& (baseMapper.selectLong(
						"select ifnull((select count(1) from MemberBaseInfo where mobile='" + memberBaseInfo.getMobile()
								+ "' and field20='" + memberBaseInfo.getField20() + "'),0)") > 0)) {// 修改
			final String updateSql = PojoUtils.getUpdateSQL(MemberBaseInfo.class.getSimpleName(), dataMap,
					"mobile,field20", memberBaseInfo.getMobile(), memberBaseInfo.getField20());
			return baseMapper.update(updateSql) > 0;
		} else {// 新增
			final String insertSql = PojoUtils.getInsertSQL(MemberBaseInfo.class.getSimpleName(), dataMap, "memberId");
			return baseMapper.insert(insertSql) > 0;
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
		return JdbcUtils.update("update MemberBaseInfo set isUse=? where memberId=?", baseMapper, 0, id) > 0;
	}

	/**
	 * 搜索
	 * 
	 * @param memberBaseInfoPojo
	 * @param apiResult
	 * @param pageMode
	 * @throws Exception
	 * @time 2018年12月29日
	 * @author DoubleLi
	 */
	public void search(MemberBaseInfoPojo memberBaseInfoPojo, ApiResult apiResult, PageMode pageMode) throws Exception {
		pageMode.setSqlWhere("m.isUse <> 0");
		// 名称
		if (StringUtils.isNotBlank(memberBaseInfoPojo.getName())) {
			pageMode.setSqlWhere("(m.memberName like '%" + pageMode.noSqlInjection(memberBaseInfoPojo.getName())
					+ "%' or  GET_FIRST_PINYIN_CHAR(m.memberName) ='" + pageMode.noSqlInjection(memberBaseInfoPojo.getName().toUpperCase())
					+ "' or upper(m.field21) like '%" + pageMode.noSqlInjection(memberBaseInfoPojo.getName().toUpperCase()) + "%')");
		}
		// 工单时间
		if (StringUtils.isNoneBlank(memberBaseInfoPojo.getWorkOrderTimeA())) {
			pageMode.setSqlWhere("wo.dispatchTime >='" + memberBaseInfoPojo.getWorkOrderTimeA() + "'");
		}
		// 工单时间
		if (StringUtils.isNoneBlank((memberBaseInfoPojo.getWorkOrderTimeB()))) {
			pageMode.setSqlWhere("wo.dispatchTime <='" + memberBaseInfoPojo.getWorkOrderTimeB() + "'");
		}
		// 手机号码
		if (StringUtils.isNoneBlank(memberBaseInfoPojo.getPhone())) {
			pageMode.setSqlWhere("(m.mobile like '%" + pageMode.noSqlInjection(memberBaseInfoPojo.getPhone()) + "%' or m.field20 like '%"
					+ memberBaseInfoPojo.getPhone() + "%')");
		}
		// 用户部门
		if (StringUtils.isNoneBlank(memberBaseInfoPojo.getDept())) {
			List<MemberDept> dlist = memberDeptService.searchDeptList(memberBaseInfoPojo.getDept());
			List<MemberDept> addList = new ArrayList<>();
			if (dlist.size() > 0)
				for (MemberDept m : dlist) {
					List<MemberDept> son = m.getSon();
					if (son != null && son.size() > 0) {
						addList.addAll(son);
						for (MemberDept memberDept : son) {
							if (memberDept.getSon() != null && memberDept.getSon().size() > 0) {
								addList.addAll(memberDept.getSon());
							}
						}
					}
				}
			if(addList.size()>0)
				dlist.addAll(addList);
			StringBuffer d = new StringBuffer("'" + memberBaseInfoPojo.getDept() + "',");
			for (MemberDept memberDept : dlist) {
				d.append("'").append(memberDept.getDeptId()).append("',");
			}
			pageMode.setSqlWhere("m.mDeptName in(" + d.substring(0, d.length() - 1) + ")");
		}
		// 用户类型
		if (StringUtils.isNoneBlank(memberBaseInfoPojo.getType())) {
			pageMode.setSqlWhere("m.memberType='" + memberBaseInfoPojo.getType() + "'");
		}
		// 座机
		if (StringUtils.isNoneBlank(memberBaseInfoPojo.getField20())) {
			pageMode.setSqlWhere("m.field20  like '%" + pageMode.noSqlInjection(memberBaseInfoPojo.getField20()) + "%'");
		}
		/* where end */
		pageMode.setSqlFrom(
				"select  m.*,count(if((wo.status in (1,2,28,85,3)),1,NUll)) fuwuzhong,count(if((wo.status in (1,2,28,85,3,4,8)),1,NUll)) zongdanliang,m.field21 pinyin,m.field20 office,m.field27 fax,m.field5 mail,m.field24 fangjian,m.field37 beizhu,m.housePhone number,m.field23 home from MemberBaseInfo m left join WorkOrder wo on m.memberId=wo.memberId and wo.status in(1,2,28,85,3,4,8)");
		pageMode.setGroupBy("group by m.memberId");
		Long total = baseMapper.selectLong(pageMode.getSearchCountSql());// 查询总条数
		pageMode.setTotal(total);
		StringBuffer orderby = new StringBuffer();
		if (StringUtils.isNotBlank(memberBaseInfoPojo.getSort())) {// 排序不为空
			String[] sort = memberBaseInfoPojo.getSort().split(",");
			String[] sortType = memberBaseInfoPojo.getSortType().split(",");
			orderby.append("order by ");
			for (int i = 0; i < sort.length; i++) {
				Integer s = Integer.valueOf(sort[i]);
				String t = "";
				try {
					if (sortType.length != sort.length)
						t = "2";
					else
						t = sortType[i];
				} catch (Exception e) {
				}
				t = t.equals("1") ? "asc" : "desc";
				orderby.append("if(").append(memberSort[s]).append(" is null or ").append(memberSort[s])
						.append("='',0,1) desc,").append(memberSort[s]).append(" ").append(t).append(",");
			}
		}
		if (orderby.length() == 0)
			orderby.append("order by if(m.field20 is null or m.field20 ='',0,1) desc,m.field20,m.mobile ");
		pageMode.setOrderBy(" ) m " + orderby.substring(0, orderby.length() - 1));
		List<Map<String, Object>> data = baseMapper.select("select * from (" + pageMode.getMysqlLimit());
		if (data != null && data.size() > 0) {
			List<MemberDept> alldept = memberDeptService.searchList();
			ExecutorService executor = Executors.newCachedThreadPool();
			for (Map<String, Object> map : data) {
				String mDeptName = (String) map.get("mdeptName");// 类型
				map.put("ddept", mDeptName);
				Runnable r = new Runnable() {
					@Override
					public void run() {
						// 归属地
						String mobile = (String) map.get("mobile");// 类型
						if (StringUtils.isNotBlank(mobile) && mobile.length() > 7) {
							List<Map<String, Object>> cl = callAreaService.searchArea(mobile);
							if (cl != null)
								for (Map<String, Object> mm : cl) {
									map.put("province", mm.get("province"));
									map.put("city", mm.get("city"));
									break;
								}
						}
						if (StringUtils.isNotBlank(mDeptName)) {
							MemberDept sd = memberDeptService.getDept(alldept, mDeptName);
							Map<String, Object> dept = new HashMap<>();
							if (sd != null) {
								MemberDept sd1 = memberDeptService.getDept(alldept, sd.getDeptParentId());
								dept.put("chu", sd1 != null ? sd1.getDeptName() : "");
								if (sd1 != null) {
									MemberDept sd2 = memberDeptService.getDept(alldept, sd1.getDeptParentId());
									dept.put("danwei", sd2.getDeptName());
								} else {
									dept.put("danwei", "");
								}
							}
							dept.put("zhiwu", sd.getDeptName());
							map.put("dept", dept);
						}
					}
				};
				executor.submit(r);
			}
			executor.shutdown();
			executor.awaitTermination(1, TimeUnit.HOURS);
		}
		pageMode.setApiResult(apiResult, data);
	}

	public Map<String, Object> searchInfo(Integer id, Integer... type) {
		if (id == null)
			return null;
		Map<String, Object> map = baseMapper.selectMap(
				"select *,field21 pinyin,field20 office,field27 fax,field5 mail,field24 fangjian,field37 beizhu,housePhone number,field23 home from MemberBaseInfo where memberId ="
						+ id);
		map.put("name", map.get("memberName"));
		map.put("phone", map.get("mobile"));
		String mDeptName = (String) map.get("mdeptName");// 类型
		List<MemberDept> alldept = memberDeptService.searchList();
		int t = 0;
		if (type.length != 0)
			t = type[0];
		if (StringUtils.isNotBlank(mDeptName)) {
			MemberDept sd = memberDeptService.getDept(alldept, mDeptName);
			Map<String, Object> dept = new HashMap<>();
			if (sd != null) {
				MemberDept sd1 = memberDeptService.getDept(alldept, sd.getDeptParentId());
				if (t == 1)
					dept.put("chu", sd1 != null ? sd1.getDeptName() : "");
				else
					dept.put("chu", sd1 != null ? sd1.getDeptId() : "");
				if (sd1 != null) {
					MemberDept sd2 = memberDeptService.getDept(alldept, sd1.getDeptParentId());
					if (t == 1)
						dept.put("danwei", sd2.getDeptName());
					else
						dept.put("danwei", sd2.getDeptId());
				} else {
					dept.put("danwei", "");
				}
			}
			if (t == 1)
				dept.put("zhiwu", sd.getDeptName());
			else
				dept.put("zhiwu", sd.getDeptId());
			map.put("dept", dept);
			if (t == 1)
				map.put("danweiShow", dept.get("danwei")+"/"+dept.get("chu")+"/"+dept.get("zhiwu"));
		}
		return map;
	}

	public List<MemberBaseInfo> searchInfoByPhone(String phone) {
		try {
			return JdbcUtils.selectListByT("select * from MemberBaseInfo where mobile=?", baseMapper,
					MemberBaseInfo.class, phone);
		} catch (Exception e) {
			return null;
		}
	}

	public List<MemberBaseInfo> searchInfoByIds(String ids) {
		try {
			return JdbcUtils.selectListByT("select * from MemberBaseInfo where memberId in(" + ids + ") ", baseMapper,
					MemberBaseInfo.class, "");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据参数搜索
	 * 
	 * @param m
	 * @return
	 * @time 2018年12月29日
	 * @author DoubleLi
	 */
	public List<MemberBaseInfo> searchByParm(MemberBaseInfo m) {
		StringBuffer wheresb = new StringBuffer();
		if (StringUtils.isNotBlank(m.getMdeptName())) {
			List<MemberDept> dlist = memberDeptService.searchDeptList(m.getMdeptName());
			StringBuffer d = new StringBuffer("'" + m.getMdeptName() + "',");
			for (MemberDept memberDept : dlist) {
				d.append("'").append(memberDept.getDeptId()).append("',");
			}
			wheresb.append(" and mdeptName in(").append(d.substring(0, d.length() - 1)).append(")");
		}
		return JdbcUtils.selectListByT("select * from MemberBaseInfo where 1=1" + wheresb, baseMapper,
				MemberBaseInfo.class);
	}
}
