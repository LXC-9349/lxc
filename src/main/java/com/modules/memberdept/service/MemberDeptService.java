package com.modules.memberdept.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.utils.BeanUtils;
import com.commons.utils.JdbcUtils;
import com.commons.utils.PojoUtils;
import com.commons.utils.Tools;
import com.modules.base.dao.BaseMapper;
import com.modules.member.bean.MemberBaseInfo;
import com.modules.member.service.MemberBaseInfoService;
import com.modules.memberdept.bean.MemberDept;

@Service
public class MemberDeptService {

	@Autowired
	private BaseMapper baseMapper;

	@Autowired
	private MemberBaseInfoService memberBaseInfoService;

	/**
	 * 客户组织架构添加修改
	 * 
	 * @param memberDept
	 * @return
	 * @time 2018年11月21日
	 * @author DoubleLi
	 */
	public String insertOrUpdate(MemberDept memberDept) {
		if (memberDept == null)
			return null;
		String d = memberDept.getDeptId();
		if (StringUtils.isBlank(memberDept.getDeptId()))
			memberDept.setDeptId(Tools.generateUuid());
		Map<String, String> dataMap = PojoUtils.comparePojo(null, memberDept, "serialVersionUID,son");
		if (StringUtils.isBlank(d)) {// 新增
			final String insertSql = PojoUtils.getInsertSQL(MemberDept.class.getSimpleName(), dataMap, null);
			return baseMapper.insert(insertSql) > 0 ? memberDept.getDeptId() : null;
		} else {// 更新
			final String updateSql = PojoUtils.getUpdateSQL(MemberDept.class.getSimpleName(), dataMap, "deptId",
					memberDept.getDeptId());
			return baseMapper.update(updateSql) > 0 ? memberDept.getDeptId() : null;
		}
	}

	/**
	 * 删除部门
	 * 
	 * @param dept
	 * @return -1错误 -2节点还有人,人数 0 错误 1成功
	 * @time 2018年11月21日
	 * @author DoubleLi
	 */
	public String delete(MemberDept memberDept) {
		if (memberDept == null)
			return "-1";
		if (baseMapper.selectLong("select ifnull((select count(1) from MemberDept where isUse=1 and deptParentId='"
				+ memberDept.getDeptId() + "'),0)").intValue() != 0) {
			return "-3";
		}
		MemberBaseInfo m = new MemberBaseInfo();
		m.setMdeptName(memberDept.getDeptId());
		List<MemberBaseInfo> wlist = memberBaseInfoService.searchByParm(m);
		if (wlist.size() > 0)
			return "-2," + wlist.size();
		String sql = "update MemberDept set isUse=0 where deptId=?";
		return String.valueOf(JdbcUtils.update(sql, baseMapper, memberDept.getDeptId()));
	}

	/**
	 * 搜索所有客户组织架构--分级
	 * 
	 * @return
	 * @time 2018年11月21日
	 * @author DoubleLi
	 */
	public List<MemberDept> searchDeptList(String deptId) {
		List<MemberDept> dl = JdbcUtils.selectListByT("select * from MemberDept where isUse=1 and deptParentId=?",
				baseMapper, MemberDept.class, deptId);
		for (MemberDept dept : dl) {
			dept.setSon(searchDeptList(dept.getDeptId()));
		}
		return dl;
	}
	
	/**
	 * 搜索所有客户组织架构不分级
	 * @param deptId
	 * @return
	 * @time 2018年12月29日
	 * @author DoubleLi
	 */
	public List<MemberDept> searchList() {
		List<MemberDept> dl = JdbcUtils.selectListByT("select * from MemberDept where isUse=1",baseMapper, MemberDept.class, "");
		return dl;
	}
	/**
	 * 搜索所有客户组织架构
	 * 
	 * @return
	 * @time 2018年11月21日
	 * @author DoubleLi
	 */
	public MemberDept searchInfo(String deptId) {
		try {
			MemberDept md=BeanUtils.transMapToBean(MemberDept.class, JdbcUtils.select("select * from MemberDept where isUse=1 and deptId=?", baseMapper, deptId));
			return md;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 获取父级是是否为顶级
	 * 
	 * @param deptParentId
	 * @return
	 * @time 2018年12月29日
	 * @author DoubleLi
	 */
	public Boolean parentIsFrist(String deptParentId) {
		try {
			Map<String, Object> m = JdbcUtils.select("select deptParentId from MemberDept where deptId=?", baseMapper,
					deptParentId);
			if ("0".equals(m.get("deptParentId").toString()))
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 获取部门
	 * @param list
	 * @param deptId
	 * @return
	 * @time 2018年12月29日
	 * @author DoubleLi
	 */
	public MemberDept getDept(List<MemberDept> list,String deptId){
		for (MemberDept memberDept : list) {
			if(memberDept.getDeptId().equals(deptId))
				return memberDept;
		}
		return null;
	}

	/**
	 * 二级处
	 * @param bumenId
	 * @return
	 * @time 2018年12月29日
	 * @author DoubleLi
	 */
	public List<MemberDept> searchChu(List<MemberDept>  d,String bumenId) {
		if(d==null)
			return null;
		for (MemberDept memberDept : d) {
			if(memberDept.getDeptId().equals(bumenId)){//单位一样
				 return memberDept.getSon();
			}
		}
		return null;
	}

	/**
	 * 三级职位
	 * @param ld2
	 * @param chuId
	 * @return
	 * @time 2018年12月29日
	 * @author DoubleLi
	 */
	public List<MemberDept> searchZhiwei(List<MemberDept> ld2, String chuId) {
		if(ld2==null)
			return null;
		for (MemberDept memberDept : ld2) {
			if(memberDept.getDeptId().equals(chuId))
				return memberDept.getSon();
		}
		return null;
	}

}
