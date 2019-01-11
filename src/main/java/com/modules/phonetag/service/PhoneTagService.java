package com.modules.phonetag.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.apiresult.ApiResult;
import com.commons.utils.JdbcUtils;
import com.commons.utils.PageMode;
import com.commons.utils.PojoUtils;
import com.modules.base.dao.BaseMapper;
import com.modules.phonetag.bean.PhoneTag;
import com.modules.sysdict.bean.SysDict;
import com.modules.sysdict.service.SysDictService;

/**
 * 电话标签
 * 
 * @author DoubleLi
 * @time 2019年1月2日
 * 
 */
@Service
public class PhoneTagService {
	@Autowired
	private BaseMapper baseMapper;
	@Autowired
	private SysDictService sysDictService;

	public Boolean insertOrUpdate(PhoneTag phoneTag) {
		if (phoneTag == null)
			return false;
		Map<String, String> dataMap = PojoUtils.comparePojo(null, phoneTag, "serialVersionUID,id");
		if (phoneTag.getId() == null) {// 新增
			final String insertSql = PojoUtils.getInsertSQL(PhoneTag.class.getSimpleName(), dataMap, "id");
			return baseMapper.insert(insertSql) > 0;
		} else {// 更新
			final String updateSql = PojoUtils.getUpdateSQL(PhoneTag.class.getSimpleName(), dataMap, "id",
					phoneTag.getId());
			return baseMapper.update(updateSql) > 0;
		}
	}

	public Boolean delete(PhoneTag phoneTag) {
		if (phoneTag == null)
			return false;
		String sql = "delete from  PhoneTag where id=?";
		return JdbcUtils.delete(sql, baseMapper, phoneTag.getId()) > 0;
	}

	public void search(PhoneTag phoneTag, ApiResult apiResult, PageMode pageMode) throws Exception {
		pageMode.setSqlWhere("p.isUse <> 0");
		if (phoneTag.getId() != null)
			pageMode.setSqlWhere("p.id =" + phoneTag.getId());
		if (phoneTag.getTagValue() != null)
			pageMode.setSqlWhere("p.tagValue =" + phoneTag.getTagValue());
		if (StringUtils.isNotBlank(phoneTag.getPhone()))
			pageMode.setSqlWhere("p.phone like '%" + phoneTag.getPhone() + "%'");
		/* where end */
		pageMode.setSqlFrom("select * from PhoneTag p");
		/*pageMode.setGroupBy("group by p.phone");*/
		Long total = baseMapper.selectLong(pageMode.getSearchCountSql());// 查询总条数
		pageMode.setTotal(total);
		List<Map<String, Object>> data = baseMapper.select(pageMode.getMysqlLimit());
		if (data != null && data.size() > 0) {
			List<SysDict> sysdict = sysDictService.searchSysDict("'phonetag'");
			for (Map<String, Object> map : data) {
				String tagValue = map.get("tagValue").toString();
				if (StringUtils.isNotBlank(tagValue)) {
					for (SysDict sd : sysdict) {
						if (tagValue.equals(sd.getValue())) {
							map.put("tag",sd.getLabel());
							break;
						}
					}
				}
			}
		}
		pageMode.setApiResult(apiResult, data);
	}

	public Map<String, Object> searchPhone(String phone) {
		if(StringUtils.isBlank(phone))
			return null;
		Map<String, Object> map = JdbcUtils.select(
				"select phone,group_concat(tagValue) tagValue  from PhoneTag p where phone =? group by p.phone",
				baseMapper, phone);
		if (map != null) {
			List<SysDict> sysdict = sysDictService.searchSysDict("'phonetag'");
			String tagValue = (String) map.get("tagValue");
			if (StringUtils.isNotBlank(tagValue)) {
				String[] t = tagValue.split(",");
				StringBuffer v = new StringBuffer();
				for (SysDict sd : sysdict) {
					if (ArrayUtils.contains(t, sd.getValue())) {
						v.append(sd.getLabel()).append(",");
					}
				}
				if (v.length() > 0)
					map.put("tag", v.substring(0, v.length() - 1));
			}

		}
		return map;
	}
	
	public Map<String, Object> searchInfo(Integer id) {
		if(id==null)
			return null;
		Map<String, Object> map = JdbcUtils.select(
				"select phone,group_concat(tagValue) tagValue  from PhoneTag p where id =? group by p.phone",
				baseMapper, id);
		if (map != null) {
			List<SysDict> sysdict = sysDictService.searchSysDict("'phonetag'");
			String tagValue = (String) map.get("tagValue");
			if (StringUtils.isNotBlank(tagValue)) {
				String[] t = tagValue.split(",");
				StringBuffer v = new StringBuffer();
				for (SysDict sd : sysdict) {
					if (ArrayUtils.contains(t, sd.getValue())) {
						v.append(sd.getLabel()).append(",");
					}
				}
				if (v.length() > 0)
					map.put("tag", v.substring(0, v.length() - 1));
			}

		}
		return map;
	}
}
