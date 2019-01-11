package com.modules.member.service.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.utils.PojoUtils;
import com.modules.base.dao.BaseMapper;
import com.modules.member.bean.MemberFollowRecord;
import com.modules.member.service.MemberFollowRecordService;

@Service
public class MemberFollowRecordServiceImpl implements MemberFollowRecordService {

	private static final Log log = LogFactory.getLog("exception");
	
	@Autowired
	private BaseMapper baseMapper;
	
	public boolean insertFollowRecord(MemberFollowRecord record) {
		
		record.setRecordId(String.valueOf(System.currentTimeMillis()));
		Map<String, String> dataMap = PojoUtils.comparePojo(null, record, "serialVersionUID,deptId,workerName");
		final String insertSql = PojoUtils.getInsertSQL(MemberFollowRecord.class.getSimpleName(), dataMap, "autoId");
		try {
			baseMapper.insert(insertSql);
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			return false;
		}
		
		return true;
	}
}
