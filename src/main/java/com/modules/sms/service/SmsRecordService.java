package com.modules.sms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.apiresult.ApiResult;
import com.commons.utils.DateUtils;
import com.commons.utils.JdbcUtils;
import com.commons.utils.PageMode;
import com.commons.utils.PojoUtils;
import com.commons.utils.Tools;
import com.modules.base.dao.BaseMapper;
import com.modules.member.bean.MemberBaseInfo;
import com.modules.sms.models.SmsRecord;
import com.modules.sms.util.SmsUtils;
import com.modules.worker.bean.Worker;

@Service
public class SmsRecordService {

	@Autowired
	private SmsUtils smsUtils;
	@Autowired
	private BaseMapper baseMapper;

	public Boolean insertOrUpdate(SmsRecord smsRecord) {
		if (smsRecord == null)
			return false;
		Map<String, String> dataMap = PojoUtils.comparePojo(null, smsRecord, "serialVersionUID,id,name");
		if (smsRecord.getId() == null) {// 新增
			final String insertSql = PojoUtils.getInsertSQL(SmsRecord.class.getSimpleName(), dataMap, "id");
			return baseMapper.insert(insertSql) > 0;
		} else {// 更新
			final String updateSql = PojoUtils.getUpdateSQL(SmsRecord.class.getSimpleName(), dataMap, "id",
					smsRecord.getId());
			return baseMapper.update(updateSql) > 0;
		}
	}

	/**
	 * 发送短息给客户
	 * 
	 * @param tolist
	 * @param sub
	 * @param content
	 * @param ml
	 * @time 2019年1月2日
	 * @author DoubleLi
	 */
	public void sendToMember(String content, List<MemberBaseInfo> ml) throws Exception {
		if (ml.size() < 1)
			return;
		ExecutorService executor = Executors.newCachedThreadPool();
		for (MemberBaseInfo to : ml) {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					SmsRecord s = new SmsRecord();
					String smsId = DateUtils.getDate("yyyyMMddHHmmssS") + Tools.getRandomNumForApplogin();
					s.setSmsId(smsId);
					try {
						if (StringUtils.isNotBlank(to.getMobile())) {
							Map<String, Object> msg = smsUtils.sendSms(smsId, to.getMobile(), content);
							String code = msg.get("code").toString();
							s.setBackmsg(msg.get("msg").toString());
							if (code.equals("00"))
								s.setStatus(1);
							else
								s.setStatus(2);
						} else {
							s.setStatus(2);
							s.setBackmsg("号码为空");
						}
					} catch (Exception e1) {
						s.setStatus(2);
						s.setBackmsg("未知异常");
					}
					s.setContent(content);
					s.setPhone(to.getMobile());
					s.setCreateTime(new Date());
					s.setType(1);
					s.setMemberId(to.getMemberId());
					insertOrUpdate(s);
				}
			};
			executor.submit(r);
		}
		executor.shutdown();
	}

	/**
	 * 给员工发送邮件
	 * 
	 * @param tolist
	 * @param sub
	 * @param content
	 * @param ml
	 * @time 2019年1月2日
	 * @author DoubleLi
	 */
	public void sendToWorker(String content, List<Worker> ml) throws Exception {
		if (ml.size() < 1)
			return;
		ExecutorService executor = Executors.newCachedThreadPool();
		for (Worker to : ml) {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					SmsRecord s = new SmsRecord();
					String smsId = DateUtils.getDate("yyyyMMddHHmmssS") + Tools.getRandomNumForApplogin();
					s.setSmsId(smsId);
					try {
						if (StringUtils.isNotBlank(to.getPhone())) {
							Map<String, Object> msg = smsUtils.sendSms(smsId, to.getPhone(), content);
							String code = msg.get("code").toString();
							s.setBackmsg(msg.get("msg").toString());
							if (code.equals("00"))
								s.setStatus(1);
							else
								s.setStatus(2);
						} else {
							s.setStatus(2);
							s.setBackmsg("号码为空");
						}
					} catch (Exception e1) {
						s.setStatus(2);
						s.setBackmsg("未知异常");
					}
					s.setContent(content);
					s.setPhone(to.getPhone());
					s.setCreateTime(new Date());
					s.setType(2);
					s.setWorkerId(to.getWorkerId());
					insertOrUpdate(s);
				}
			};
			executor.submit(r);
		}
		executor.shutdown();
	}

	public void search(SmsRecord smsRecord, ApiResult apiResult, PageMode pageMode) throws Exception {
		pageMode.setSqlWhere("e.isUse <> 0");
		if (smsRecord.getId() != null)
			pageMode.setSqlWhere("e.id =" + smsRecord.getId());
		if (StringUtils.isNotBlank(smsRecord.getPhone()))
			pageMode.setSqlWhere("e.phone like '%" + smsRecord.getPhone() + "%'");
		if (StringUtils.isNotBlank(smsRecord.getContent()))
			pageMode.setSqlWhere("e.content like '%" + smsRecord.getContent() + "%'");
		if (smsRecord.getType() != null)
			pageMode.setSqlWhere("e.type =" + smsRecord.getType());
		if (smsRecord.getMemberId() != null) 
			pageMode.setSqlWhere("e.memberId =" + smsRecord.getMemberId());
		if (StringUtils.isNotBlank(smsRecord.getWorkerId())) 
			pageMode.setSqlWhere("e.workerId ='" + smsRecord.getWorkerId() + "'");
		if (StringUtils.isNotBlank(smsRecord.getName())) {
			pageMode.setSqlWhere("(e.memberId in( select memberId from MemberBaseInfo where memberName like '%"
					+ smsRecord.getName() + "%') or e.workerId in(select workerId from workerName like '%"
					+ smsRecord.getName() + "%'))");
		}
		/* where end */
		pageMode.setSqlFrom(
				"select e.*,w.workerName,m.memberName from SmsRecord e left join Worker w on w.workerId = e.workerId left join MemberBaseInfo m on m.memberId=e.memberId");
		Long total = baseMapper.selectLong(pageMode.getSearchCountSql());// 查询总条数
		pageMode.setTotal(total);
		pageMode.setOrderBy(" order by e.createTime desc");
		List<Map<String, Object>> data = baseMapper.select(pageMode.getMysqlLimit());
		pageMode.setApiResult(apiResult, data);
	}

	public Boolean delete(SmsRecord smsRecord) {
		if (smsRecord == null)
			return false;
		String sql = "update SmsRecord set isUse=0 where id=?";
		return JdbcUtils.update(sql, baseMapper, smsRecord.getId()) > 0;
	}
}
