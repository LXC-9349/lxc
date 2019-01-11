package com.modules.graph.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.apiresult.ApiResult;
import com.commons.utils.DateUtils;
import com.modules.base.dao.BaseMapper;
import com.modules.graph.pojo.GraphParmPojo;

/**
 * 图表service
 * 数据已删除 sql可参考
 * @author DoubleLi
 * @time 2018年12月24日
 * 
 */
@Service
public class GraphService {
	@Autowired
	private BaseMapper baseMapper;

	/**
	 * 呼叫量
	 * 
	 * @param type
	 *            1日2月3年
	 * @param graphParmPojo
	 * @param apiResult
	 * @throws Exception
	 * @time 2018年12月24日
	 * @author DoubleLi
	 */
	public void callAmount(GraphParmPojo graphParmPojo, ApiResult apiResult) throws Exception {
		StringBuffer beginTime = new StringBuffer();
		StringBuffer endTime = new StringBuffer();
		StringBuffer weidu = new StringBuffer();
		StringBuffer groupby = new StringBuffer();
		List<Map<String, Object>> diffres = new ArrayList<>();
		// 按维度计算日期范围
		typeAnalysis(graphParmPojo, beginTime, endTime, weidu, groupby, diffres, 0);
		StringBuffer sql = new StringBuffer("select ");
		sql.append(weidu);
		sql.append("count(IF(callType=1,1, NULL)) callin,count(IF(callType=0,1, NULL)) callout from CallRecord");
		sql.append(" where startTime >").append("'").append(beginTime).append("' - interval 1 day");
		sql.append(" and startTime <").append("'").append(endTime).append("' + interval 1 day");
		sql.append(" group by ").append(groupby);
		List<Map<String, Object>> res = baseMapper.select(sql.toString());
		diff: for (Map<String, Object> map : diffres) {
			if (res != null)
				for (Map<String, Object> m : res) {
					String t = m.get("t").toString();
					if (map.get("t").toString().equals(t)) {
						map.put("callin", m.get("callin"));
						map.put("callout", m.get("callout"));
						continue diff;
					}
				}
			map.put("callin", 0);
			map.put("callout", 0);
		}
		Map<String,Object> retmap=new HashMap<>();
		subTmap(diffres,graphParmPojo);
		retmap.put("list", diffres);
		apiResult.setData(retmap);
	}

	/**
	 * 呼叫总量环比
	 * 
	 * @param graphParmPojo
	 * @param apiResult
	 * @time 2018年12月24日
	 * @author DoubleLi
	 */
	public void callAmountRingRatio(GraphParmPojo graphParmPojo, ApiResult apiResult) throws Exception {
		StringBuffer beginTime = new StringBuffer();
		StringBuffer endTime = new StringBuffer();
		StringBuffer weidu = new StringBuffer();
		StringBuffer groupby = new StringBuffer();
		List<Map<String, Object>> diffres = new ArrayList<>();
		// 按维度计算日期范围
		typeAnalysis(graphParmPojo, beginTime, endTime, weidu, groupby, diffres, 1);// 跨时间
		StringBuffer sql = new StringBuffer("select ");
		sql.append(weidu);
		sql.append("count(1) sumcall from CallRecord");
		sql.append(" where startTime >").append("'").append(beginTime).append("' - interval 1 day");
		sql.append(" and startTime <").append("'").append(endTime).append("' + interval 1 day");
		sql.append(" group by ").append(groupby);
		List<Map<String, Object>> res = baseMapper.select(sql.toString());
		// 环比计算 环比=（B-A）/A*100% 以月度统计为列：4月环比中 B表示4月总量，A表示3月总量；
		DecimalFormat df = new DecimalFormat("0.00");
		double lastcount = 0.0;// A
		diff: for (Map<String, Object> map : diffres) {
			String tt = map.get("t").toString();
			if (res != null)
				for (Map<String, Object> m : res) {
					String t = m.get("t").toString();
					if (tt.equals(t)) {
						int sumcall = Integer.valueOf(m.get("sumcall").toString());// B
						map.put("sumcall", sumcall);
						if (lastcount != 0.0)
							map.put("ringratio", df.format((sumcall - lastcount) / lastcount * 100));
						else
							map.put("ringratio", 0);
						lastcount = sumcall;
						continue diff;
					}
				}
			lastcount = 0.0;
			map.put("sumcall", 0);
			map.put("ringratio", 0);
		}
		diffres.remove(0);
		Map<String,Object> retmap=new HashMap<>();
		subTmap(diffres,graphParmPojo);
		retmap.put("list", diffres);
		apiResult.setData(retmap);
	}

	/**
	 * 工单总量
	 * 
	 * @param graphParmPojo
	 * @param apiResult
	 * @time 2018年12月24日
	 * @author DoubleLi
	 */
	public void workorderAmount(GraphParmPojo graphParmPojo, ApiResult apiResult) throws Exception {
		StringBuffer beginTime = new StringBuffer();
		StringBuffer endTime = new StringBuffer();
		StringBuffer weidu = new StringBuffer();
		StringBuffer groupby = new StringBuffer();
		List<Map<String, Object>> diffres = new ArrayList<>();
		// 按维度计算日期范围
		typeAnalysis(graphParmPojo, beginTime, endTime, weidu, groupby, diffres, 0);
		StringBuffer sql = new StringBuffer("select ");
		sql.append(weidu);
		sql.append(
				"count(if((wo.status in (1,2,28,85,3)),1,NUll)) `processcount`,count(if((wo.status in (4,8)),1,NUll)) `complete` from WorkOrder wo");
		sql.append(" where createTime >").append("'").append(beginTime).append("' - interval 1 day");
		sql.append(" and createTime <").append("'").append(endTime).append("' + interval 1 day");
		if(graphParmPojo.getWotype()!=null){
			sql.append(" and wo.type=").append(graphParmPojo.getWotype());
		}
		// sql.append(" group by ").append(groupby);//这里为饼图不需要分组
		List<Map<String, Object>> res = baseMapper.select(sql.toString().replaceAll("startTime", "createTime"));
		// 去掉分组处理
		/*
		 * diff: for (Map<String, Object> map : diffres) { if (res != null) for
		 * (Map<String, Object> m : res) { String t = m.get("t").toString(); if
		 * (map.get("t").toString().equals(t)) { map.put("processcount",
		 * m.get("processcount")); map.put("complete", m.get("complete"));
		 * continue diff; } } map.put("processcount", 0); map.put("complete",
		 * 0); }
		 */
		Map<String,Object> retmap=new HashMap<>();
		retmap.put("list", res);
		apiResult.setData(retmap);
	}

	/**
	 * 工单处理时效
	 * 
	 * @param graphParmPojo
	 * @param apiResult
	 * @time 2018年12月25日
	 * @author DoubleLi
	 */
	public void workorderAging(GraphParmPojo graphParmPojo, ApiResult apiResult) throws Exception {
		StringBuffer beginTime = new StringBuffer();
		StringBuffer endTime = new StringBuffer();
		StringBuffer weidu = new StringBuffer();
		StringBuffer groupby = new StringBuffer();
		List<Map<String, Object>> diffres = new ArrayList<>();
		// 按维度计算日期范围
		typeAnalysis(graphParmPojo, beginTime, endTime, weidu, groupby, new ArrayList<>(), 0);
		StringBuffer sql = new StringBuffer("select ");
		// sql.append(weidu);//去掉维度
		sql.append(
				"(case when (wo.status in (1,2,28,85,3)) then timestampdiff(hour,wo.createTime,CURDATE()) else timestampdiff(hour,wo.createTime,wo.updateTime) end) `counthour` from WorkOrder wo where wo.status in (1,2,28,85,3,4,8)");
		sql.append(" and createTime >").append("'").append(beginTime).append("' - interval 1 day");
		sql.append(" and createTime <").append("'").append(endTime).append("' + interval 1 day");
		if(graphParmPojo.getWotype()!=null){
			sql.append(" and wo.type=").append(graphParmPojo.getWotype());
		}
		// sql.append(" group by ").append(groupby);//去掉分组
		List<Map<String, Object>> res = baseMapper.select(sql.toString().replaceAll("startTime", "createTime"));
		Map<String, Object> m1 = new HashMap<>();
		m1.put("interval", 0);
		m1.put("desc", "1天内");
		m1.put("count", 0);
		Map<String, Object> m2 = new HashMap<>();
		m2.put("interval", 1);
		m2.put("desc", "1-2天");
		m2.put("count", 0);
		Map<String, Object> m3 = new HashMap<>();
		m3.put("interval", 2);
		m3.put("desc", "2-3天");
		m3.put("count", 0);
		Map<String, Object> m4 = new HashMap<>();
		m4.put("interval", 3);
		m4.put("desc", "3-5天");
		m4.put("count", 0);
		Map<String, Object> m5 = new HashMap<>();
		m5.put("interval", 4);
		m5.put("desc", "5天以上");
		m5.put("count", 0);
		if (res != null)
			for (Map<String, Object> m : res) {
				int counthour = Integer.valueOf(m.get("counthour").toString());
				Map<String, Object> diffm = null;
				if (counthour <= 24) {
					diffm = m1;
				} else if (counthour > 24 && counthour <= 24 * 2) {
					diffm = m2;
				} else if (counthour > 24 * 2 && counthour <= 24 * 3) {
					diffm = m3;
				} else if (counthour > 24 * 3 && counthour <= 24 * 5) {
					diffm = m4;
				} else if (counthour > 24 * 5) {
					diffm = m5;
				}
				int count = (int) diffm.get("count");
				count = count + 1;
				diffm.put("count", count);
			}
		diffres.add(m1);
		diffres.add(m2);
		diffres.add(m3);
		diffres.add(m4);
		diffres.add(m5);
		Map<String,Object> retmap=new HashMap<>();
		retmap.put("list", diffres);
		apiResult.setData(retmap);
	}

	/**
	 * 工单量同比
	 * 
	 * @param graphParmPojo
	 * @param apiResult
	 * @time 2018年12月25日
	 * @author DoubleLi
	 */
	public void workorderYearOnYear(GraphParmPojo graphParmPojo, ApiResult apiResult) throws Exception {
		StringBuffer beginTime = new StringBuffer();
		StringBuffer endTime = new StringBuffer();
		StringBuffer weidu = new StringBuffer();
		StringBuffer groupby = new StringBuffer();
		List<Map<String, Object>> diffres = new ArrayList<>();
		// 按维度计算日期范围
		typeAnalysis(graphParmPojo, beginTime, endTime, weidu, groupby, diffres, 2);
		StringBuffer sql = new StringBuffer("select ");
		sql.append(weidu);
		sql.append(" count(1) `count` from WorkOrder wo where wo.status in (1,2,28,85,3,4,8)");
		if (graphParmPojo.getType().intValue() == 1) {// 为日统计前一年的一个月就行
			sql.append(" and ((createTime >").append("'").append(beginTime).append("' - interval 1 day");
			sql.append(" and createTime <").append("'").append(DateUtils.getMonthday(beginTime.toString()))
					.append("' + interval 1 day)");
			sql.append(" or (createTime >").append("'").append(endTime).append("' - interval 1 day");
			sql.append(" and createTime <").append("'").append(DateUtils.getMonthday(endTime.toString()))
					.append("' + interval 1 day))");
		} else {
			sql.append(" and createTime >").append("'").append(beginTime).append("' - interval 1 day");
			sql.append(" and createTime <").append("'").append(endTime).append("' + interval 1 day");
		}
		if(graphParmPojo.getWotype()!=null){
			sql.append(" and wo.type=").append(graphParmPojo.getWotype());
		}
		sql.append(" group by ").append(groupby);
		List<Map<String, Object>> res = baseMapper.select(sql.toString().replaceAll("startTime", "createTime"));
		// 数据库填值
		diff: for (Map<String, Object> map : diffres) {// 赋值
			String tt=map.get("t").toString();
			if (res != null)
				for (Map<String, Object> m : res) {
					String t = m.get("t").toString();
					if (tt.equals(t)) {
						map.put("count", m.get("count"));
						continue diff;
					}
				}
			map.put("count", 0);
		}
		// 同比赋值
		List<Map<String, Object>> result = new ArrayList<>();
		for (Map<String, Object> map : res) {
			String t = map.get("t").toString();// 日期
			Double c_a = Double.valueOf(map.get("count").toString());// 工单量
			String before = (Integer.valueOf(t.split("-")[0]) - 1) + "";// 计算前一年的对应时间
			if (graphParmPojo.getType().intValue() == 1) {// 日
															// --为了防止2016-02-29取不到2015-02-29
				before = before + "-" + t.split("-")[1] + "-" + t.split("-")[2];
			} else if (graphParmPojo.getType().intValue() == 2) {// 月
				before = before + "-" + t.split("-")[1];
			} else if (graphParmPojo.getType().intValue() == 3) {// 年
			}
			for (Map<String, Object> map1 : res) {// 查询符合的数据
				String tt = map1.get("t").toString();// 日期
				if (tt.equals(before)) {// 能取到同比
					Double c_b = Double.valueOf(map1.get("count").toString());// 工单量
					Map<String, Object> rm = new HashMap<>();
					rm.put("a", c_a);
					rm.put("b", c_b);
					// 同比=上一年同期工单量-本年同期工单量/上一年同期工单量*100%
					if (c_b != null && c_b.intValue() != 0)
						rm.put("tb", (c_b - c_a) / c_b * 100);
					else
						rm.put("tb", 0.00);
					result.add(rm);
				}
			}
		}
		// 最后处理结果 支取当前选定年份的数据
		List<Map<String, Object>> lastResult = new ArrayList<>();
		last: for (Map<String, Object> map : diffres) {// 只取选定的年的数据
			String t = map.get("t").toString();
			if (StringUtils.startsWith(t, graphParmPojo.getYear()) || (graphParmPojo.getType().intValue() == 3)) {// 支取当前选定年份的数据
				for (Map<String, Object> map2 : result) {
					String tt = (String) map2.get("t");
					if (tt.equals(t)) {
						map.putAll(map2);
						lastResult.add(map);
						continue last;
					}
				}
				map.put("a", 0);
				map.put("b", 0);
				map.put("tb", 0.00);
				lastResult.add(map);
			}
		}
		if (graphParmPojo.getType().intValue() == 3)
			lastResult.remove(0);
		Map<String,Object> retmap=new HashMap<>();
		subTmap(lastResult,graphParmPojo);
		retmap.put("list", lastResult);
		apiResult.setData(retmap);
	}

	/**
	 * 工单量环比
	 * 
	 * @param graphParmPojo
	 * @param apiResult
	 * @time 2018年12月25日
	 * @author DoubleLi
	 */
	public void workorderRingRatio(GraphParmPojo graphParmPojo, ApiResult apiResult) throws Exception {
		StringBuffer beginTime = new StringBuffer();
		StringBuffer endTime = new StringBuffer();
		StringBuffer weidu = new StringBuffer();
		StringBuffer groupby = new StringBuffer();
		List<Map<String, Object>> diffres = new ArrayList<>();
		// 按维度计算日期范围
		typeAnalysis(graphParmPojo, beginTime, endTime, weidu, groupby, diffres, 1);// 跨时间
		StringBuffer sql = new StringBuffer("select ");
		sql.append(weidu);
		sql.append("  count(1) `count` from WorkOrder wo where wo.status in (1,2,28,85,3,4,8)");
		sql.append(" and createTime >").append("'").append(beginTime).append("' - interval 1 day");
		sql.append(" and createTime <").append("'").append(endTime).append("' + interval 1 day");
		if(graphParmPojo.getWotype()!=null){
			sql.append(" and wo.type=").append(graphParmPojo.getWotype());
		}
		sql.append(" group by ").append(groupby);
		List<Map<String, Object>> res = baseMapper.select(sql.toString().replaceAll("startTime", "createTime"));
		// 环比=（B-A）/A*100% 以月度统计为列：4月环比中 B表示4月总量，A表示3月总量；
		DecimalFormat df = new DecimalFormat("0.00");
		double lastcount = 0.0;// A
		diff: for (Map<String, Object> map : diffres) {
			String tt=map.get("t").toString();
			if (res != null)
				for (Map<String, Object> m : res) {
					String t = m.get("t").toString();
					if (tt.equals(t)) {
						int count = Integer.valueOf(m.get("count").toString());// B
						map.put("count", count);
						if (lastcount != 0.0)
							map.put("ringratio", df.format((count - lastcount) / lastcount * 100));
						else
							map.put("ringratio", 0);
						lastcount = count;
						continue diff;
					}
				}
			lastcount = 0.0;
			map.put("count", 0);
			map.put("ringratio", 0);
		}
		diffres.remove(0);
		Map<String,Object> retmap=new HashMap<>();
		subTmap(diffres,graphParmPojo);
		retmap.put("list", diffres);
		apiResult.setData(retmap);
	}

	/**
	 * 按维度计算日期范围
	 * 
	 * @param graphParmPojo
	 * @param beginTime
	 * @param endTime
	 * @param weidu
	 * @param groupby
	 * @param diffres
	 * @param type
	 *            对应相应规则 1涉及到跨时间 2跨一年
	 * @time 2018年12月24日
	 * @author DoubleLi
	 */
	private void typeAnalysis(GraphParmPojo graphParmPojo, StringBuffer beginTime, StringBuffer endTime,
			StringBuffer weidu, StringBuffer groupby, List<Map<String, Object>> diffres, int type) throws Exception {
		switch (graphParmPojo.getType().intValue()) {
		case 1:
			weidu.append(
					" CONCAT(YEAR(startTime),'-',date_format(startTime,'%m'),'-',date_format(startTime,'%d')) `t`,");
			groupby.append("date_format(startTime, '%Y%M%D')");
			beginTime.append(graphParmPojo.getYear()).append("-").append(graphParmPojo.getMonth()).append("-")
					.append("01");
			if (type == 1) {
				beginTime = new StringBuffer(DateUtils.getNextDay(beginTime.toString(), -1));
				Map<String, Object> rm = new HashMap<>();
				rm.put("t", beginTime.toString());
				diffres.add(rm);
			} else if (type == 2) {
				beginTime = new StringBuffer(DateUtils.getNextYear(beginTime.toString(), -1));
				for (int i = 0; i < DateUtils.getDaysOfMonth(DateUtils.parseDate(beginTime)); i++) {
					Map<String, Object> rm = new HashMap<>();
					int j = i + 1;
					rm.put("t", (Integer.valueOf(graphParmPojo.getYear()) - 1) + "-" + graphParmPojo.getMonth() + "-"
							+ (j < 10 ? "0" + j : j));
					diffres.add(rm);
				}
			}
			endTime.append(graphParmPojo.getYear()).append("-").append(graphParmPojo.getMonth()).append("-")
					.append(DateUtils.getDaysOfMonth(
							DateUtils.parseDate(graphParmPojo.getYear() + "-" + graphParmPojo.getMonth())));
			for (int i = 0; i < DateUtils.getDaysOfMonth(DateUtils.parseDate(endTime)); i++) {
				Map<String, Object> rm = new HashMap<>();
				int j = i + 1;
				rm.put("t", graphParmPojo.getYear() + "-" + graphParmPojo.getMonth() + "-" + (j < 10 ? "0" + j : j));
				diffres.add(rm);
			}
			break;
		case 2:
			weidu.append("CONCAT(YEAR(startTime),'-',date_format(startTime,'%m')) `t`,");
			groupby.append("date_format(startTime, '%Y%M')");
			if (type == 1) {
				beginTime.append(Integer.valueOf(graphParmPojo.getYear()) - 1).append("-").append("12").append("-")
						.append("01");
				Map<String, Object> rm = new HashMap<>();
				rm.put("t", (Integer.valueOf(graphParmPojo.getYear()) - 1) + "-" + "12");
				diffres.add(rm);
			} else if (type == 2) {
				beginTime.append(Integer.valueOf(graphParmPojo.getYear()) - 1).append("-")
						.append(graphParmPojo.getMonth()).append("-").append("01");
				for (int i = 0; i < 12; i++) {
					Map<String, Object> rm = new HashMap<>();
					int j = i + 1;
					rm.put("t", (Integer.valueOf(graphParmPojo.getYear()) - 1) + "-" + (j < 10 ? "0" + j : j));
					diffres.add(rm);
				}
			} else {
				beginTime.append(graphParmPojo.getYear()).append("-").append("01").append("-").append("01");
			}
			endTime.append(graphParmPojo.getYear()).append("-").append(graphParmPojo.getMonth()).append("-")
					.append(graphParmPojo.getDay());
			for (int i = 0; i < 12; i++) {
				Map<String, Object> rm = new HashMap<>();
				int j = i + 1;
				rm.put("t", graphParmPojo.getYear() + "-" + (j < 10 ? "0" + j : j));
				diffres.add(rm);
			}
			break;
		case 3:
			weidu.append("YEAR(startTime) `t`,");
			groupby.append("date_format(startTime, '%Y')");
			if (type == 1 || type == 2) {
				beginTime.append(Integer.valueOf(graphParmPojo.getYear()) - 6).append("-").append("01").append("-")
						.append("01");
				Map<String, Object> rm = new HashMap<>();
				rm.put("t", Integer.valueOf(graphParmPojo.getYear()) - 5);
				diffres.add(rm);
			}else{
				beginTime.append(Integer.valueOf(graphParmPojo.getYear()) - 5).append("-").append("01").append("-")
				.append("01");
			}
			for (int i = 4; i >= 0; i--) {
				Map<String, Object> rm = new HashMap<>();
				int j = Integer.valueOf(graphParmPojo.getYear()) - i;
				rm.put("t", j);
				diffres.add(rm);
			}
			endTime.append(graphParmPojo.getYear()).append("-").append(graphParmPojo.getMonth()).append("-")
					.append(graphParmPojo.getDay());
			break;
		default:
			break;
		}
	}
	
	/**
	 * 结果截取
	 * @param diffres
	 * @param graphParmPojo
	 * @time 2018年12月26日
	 * @author DoubleLi
	 */
	private void subTmap(List<Map<String, Object>> diffres, GraphParmPojo graphParmPojo) {
		 switch (graphParmPojo.getType().intValue()) {
		case 1://日
			for (Map<String, Object> map : diffres) {
				String t=map.get("t").toString();
				map.put("t", t.split("-")[2]);
			}
			break;
		case 2://月
			for (Map<String, Object> map : diffres) {
				String t=map.get("t").toString();
				map.put("t", t.split("-")[1]);
			}
			break;
		default:
			break;
		}
	}
}