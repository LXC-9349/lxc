package com.modules.mail.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.commons.apiresult.ApiResult;
import com.commons.utils.JdbcUtils;
import com.commons.utils.PageMode;
import com.commons.utils.PojoUtils;
import com.modules.base.dao.BaseMapper;
import com.modules.mail.bean.EmailRecord;
import com.modules.mail.util.MailUtils;
import com.modules.member.bean.MemberBaseInfo;
import com.modules.worker.bean.Worker;

/**
 * 邮件服务
 * @author DoubleLi
 * @time 2019年1月11日
 * 
 */
@Service
public class EmailRecordService {

	@Autowired
	private MailUtils mailUtils;
	@Autowired
	private BaseMapper baseMapper;
	@Value("${fromMail.address}")
	private String from;

	public Boolean insertOrUpdate(EmailRecord emailRecord) {
		if (emailRecord == null)
			return false;
		Map<String, String> dataMap = PojoUtils.comparePojo(null, emailRecord, "serialVersionUID,id,name");
		if (emailRecord.getId() == null) {// 新增
			final String insertSql = PojoUtils.getInsertSQL(EmailRecord.class.getSimpleName(), dataMap, "id");
			return baseMapper.insert(insertSql) > 0;
		} else {// 更新
			final String updateSql = PojoUtils.getUpdateSQL(EmailRecord.class.getSimpleName(), dataMap, "id",
					emailRecord.getId());
			return baseMapper.update(updateSql) > 0;
		}
	}

	/**
	 * 发送邮件给客户
	 * 
	 * @param tolist
	 * @param sub
	 * @param content
	 * @param ml
	 * @time 2019年1月2日
	 * @author DoubleLi
	 */
	public void mimeMailToMember(String sub, String content, List<MemberBaseInfo> ml) throws Exception {
		if (ml.size() < 1)
			return;
		Map<String, Object> params = new HashMap<>();
		params.put("content", content);
		ExecutorService executor = Executors.newCachedThreadPool();
		for (MemberBaseInfo to : ml) {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					params.put("name", to.getMemberName());
					EmailRecord e = new EmailRecord();
					String html = mailUtils.render("EmailMember", params);
					try {
						if (StringUtils.isNotBlank(to.getField5())) {
							mailUtils.mimeMail(to.getField5(), sub, html, params);
							e.setStatus(1);
						} else {
							e.setStatus(2);
						}
					} catch (Exception e1) {
						e.setStatus(2);
					}
					e.setContent(html);
					e.setFrom(from);
					e.setSub(sub);
					e.setTo(to.getField5());
					e.setCreateTime(new Date());
					e.setType(1);
					e.setMemberId(to.getMemberId());
					insertOrUpdate(e);
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
	public void mimeMailToWorker(String sub, String content, List<Worker> ml,String temp,Object... parm) throws Exception {
		if (ml.size() < 1)
			return;
		Map<String, Object> params = new HashMap<>();
		if(StringUtils.isNotBlank(content))
		params.put("content", content);
		ExecutorService executor = Executors.newCachedThreadPool();
		for (Worker to : ml) {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					params.put("name", to.getWorkerName());
					String sub_u=null;
					if("AccpetEmail".equals(temp)){
						
					}
					EmailRecord e = new EmailRecord();
					String html = mailUtils.render(temp, params);
					try {
						if (StringUtils.isNotBlank(to.getEmail())) {
							mailUtils.mimeMail(to.getEmail(), StringUtils.isNotBlank(sub_u)?sub_u:sub, html, params);
							e.setStatus(1);
						} else {
							e.setStatus(2);
						}
					} catch (Exception e1) {
						e.setStatus(2);
					}
					e.setContent(html);
					e.setFrom(from);
					e.setSub(sub);
					e.setTo(to.getEmail());
					e.setCreateTime(new Date());
					e.setType(2);
					e.setWorkerId(to.getWorkerId());
					insertOrUpdate(e);
				}
			};
			executor.submit(r);
		}
		executor.shutdown();
	}

	public void search(EmailRecord emailRecord, ApiResult apiResult, PageMode pageMode) throws Exception {
		pageMode.setSqlWhere("e.isUse <> 0");
		if (emailRecord.getId() != null)
			pageMode.setSqlWhere("e.id =" + emailRecord.getId());
		if (StringUtils.isNotBlank(emailRecord.getFrom()))
			pageMode.setSqlWhere("e.from like '%" + emailRecord.getFrom() + "%'");
		if (StringUtils.isNotBlank(emailRecord.getTo()))
			pageMode.setSqlWhere("e.to like '%" + emailRecord.getTo() + "%'");
		if (emailRecord.getType() != null)
			pageMode.setSqlWhere("e.type =" + emailRecord.getType());
		if (emailRecord.getMemberId() != null) 
			pageMode.setSqlWhere("e.memberId =" + emailRecord.getMemberId());
		if (StringUtils.isNotBlank(emailRecord.getWorkerId())) 
			pageMode.setSqlWhere("e.workerId ='" + emailRecord.getWorkerId() + "'");
		if(emailRecord.getStatus()!=null)
			pageMode.setSqlWhere("e.status =" + emailRecord.getStatus());
		if (StringUtils.isNotBlank(emailRecord.getName())) {
			pageMode.setSqlWhere("(e.memberId in( select memberId from MemberBaseInfo where memberName like '%"
					+ emailRecord.getName() + "%') or e.workerId in(select workerId from workerName like '%"
					+ emailRecord.getName() + "%'))");
		}
		/* where end */
		pageMode.setSqlFrom(
				"select e.*,w.workerName,m.memberName from EmailRecord e left join Worker w on w.workerId = e.workerId left join MemberBaseInfo m on m.memberId=e.memberId");
		Long total = baseMapper.selectLong(pageMode.getSearchCountSql());// 查询总条数
		pageMode.setTotal(total);
		pageMode.setOrderBy(" order by e.createTime desc");
		List<Map<String, Object>> data = baseMapper.select(pageMode.getMysqlLimit());
		pageMode.setApiResult(apiResult, data);
	}

	public Boolean delete(EmailRecord emailRecord) {
		if (emailRecord == null)
			return false;
		String sql = "update EmailRecord set isUse=0 where id=?";
		return JdbcUtils.update(sql, baseMapper, emailRecord.getId()) > 0;
	}
}
