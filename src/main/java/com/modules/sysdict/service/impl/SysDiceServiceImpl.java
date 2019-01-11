package com.modules.sysdict.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.apiresult.ApiResult;
import com.commons.utils.PageMode;
import com.commons.utils.PojoUtils;
import com.commons.utils.Tools;
import com.modules.base.dao.BaseMapper;
import com.modules.sysdict.bean.SysDict;
import com.modules.sysdict.dao.SysDictMapper;
import com.modules.sysdict.service.SysDictService;
import com.modules.worker.bean.Worker;

@Service(value="sysDictService")
public class SysDiceServiceImpl implements SysDictService {
	@Autowired
	private SysDictMapper sysDictMapper;
	@Autowired
	private BaseMapper baseMapper;
	
	
	@Override
	public List<SysDict> searchSysDict(String type) {
		return sysDictMapper.searchSysDict(type);
	}

	@Override
	public List<SysDict> search() {
		return sysDictMapper.searchAllParent();
	}
	@Override
	public boolean insertOrUpdateSysDict(SysDict sysDict) {
		if(sysDict==null)
			return false;
		if(sysDict.getId()==null&&sysDict.getValue()!=null&&!sysDict.getValue().matches("^[0-9]*$")){//不为数字
			int i=0;
			while (true) {
				String value=Tools.getRandomNumForApplogin();
				Long t=baseMapper.selectLong("select ifnull((select count(1) from SysDict where type='"+sysDict.getType()+"' and value='"+value+"' ),0)");
				if(t.intValue()==0){
					sysDict.setValue(value);
					break;
				}
				i++;
				if(i==9999){
					return false;
				}
			}
		}
		Map<String, String> dataMap = PojoUtils.comparePojo(null, sysDict, "serialVersionUID,id");
		if(sysDict.getId()==null){//新增
			final String insertSql = PojoUtils.getInsertSQL(SysDict.class.getSimpleName(), dataMap, "id");
			return baseMapper.insert(insertSql)>0;
		}else{//更新
			final String updateSql = PojoUtils.getUpdateSQL(SysDict.class.getSimpleName(), dataMap, "id",
					sysDict.getId());
			return baseMapper.update(updateSql)>0;
		}
	}

	@Override
	public void insert(SysDict sysDict) {
		if(sysDict.getValue()!=null&&!sysDict.getValue().matches("^[0-9]*$")){//不为数字
			int i=0;
			while (true) {
				String value=Tools.getRandomNumForApplogin();
				Long t=baseMapper.selectLong("select ifnull((select count(1) from SysDict where type='"+sysDict.getType()+"' and value='"+value+"' ),0)");
				if(t.intValue()==0){
					sysDict.setValue(value);
					break;
				}
				i++;
				if(i==10000){
					return;
				}
			}
		}
		sysDictMapper.insert(sysDict);
	}

	@Override
	public void searchPage(String label,String type, ApiResult apiResult, PageMode pageMode, Worker currentWorker) {
		if(StringUtils.isNotBlank(label)){
			pageMode.setSqlWhere("label like '%"+label+"%'");
		}
		pageMode.setSqlWhere("type in ('"+type+"')");
		pageMode.setSqlWhere("isUse=1");
		pageMode.setSqlWhere("parent>0");
		pageMode.setOrderBy("order by sort asc");
		pageMode.setSqlFrom("select * from SysDict");
		Long total = baseMapper.selectLong(pageMode.getSearchCountSql());// 查询总条数
		pageMode.setTotal(total);
		List<Map<String, Object>> data = baseMapper.select(pageMode.getMysqlLimit());
		pageMode.setApiResult(apiResult, data);
	}
	
	@Override
	public String getLabel(String type,String value){
		try {
			Map<String,Object> map=baseMapper.selectMap("select label from SysDict where `type`='"+type+"' and `value`='"+value+"' and isUse=1 limit 1");
			return map!=null?map.get("label").toString():"未知";
		} catch (Exception e) {
			return "未知";
		}
	}
}
