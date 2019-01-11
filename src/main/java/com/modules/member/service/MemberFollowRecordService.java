package com.modules.member.service;

import com.modules.member.bean.MemberFollowRecord;

/**
 * 跟进记录Service
 * @author DZH
 *
 */
public interface MemberFollowRecordService {
	
	/**
	 * 通用新增跟进记录
	 * @param record
	 * @return
	 */
	boolean insertFollowRecord(MemberFollowRecord record);

}
