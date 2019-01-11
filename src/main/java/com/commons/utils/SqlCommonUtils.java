package com.commons.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;

/**
 * 老系统发短信用到
 * @author DoubleLi
 * @time 2018年12月19日
 * 
 */
@Deprecated
public class SqlCommonUtils {
	
	private final static String  SELECT = "select";
	private final static String  FROM = " from ";
	private final static int MAXNUM = 100000;
	
	
	/**
	 * 
	 * 取得sql相应的计算个数语句(formateSqlForCount函数别名).
	 * @param sql
	 * @return
	 */
	public static String getCountSql(String sql){
		return formateSqlForCount(sql);
	}
	
	
	/**
	 * 
	 * 方法说明：根据相应的SQL语句自动产生它的计算行数SQL语句
	 * @return
	 */
	public static String formateSqlForCount(String sql){
		return formateSqlForCount(sql, "1");
	}
	

	/**
	 * 
	 * 方法说明：根据相应的SQL语句自动产生它的计算行数SQL语句
	 * @return
	 */
	public static String formateSqlForCount2(String sql){
		return formateSqlForCount2(sql, "1");
	}
	
	public static String formateSqlForCount(String sql, String count){
		sql = sql.replaceAll("(^\\s*\\()|(\\)\\s*$)", "").replaceAll("(?i)select\\s+", "select ").replaceAll("(?i)from\\s+", "from ").replaceAll("(?i)order\\s+by", "order by").replaceAll("(?i)limit", "limit");
		int pointer = sql.indexOf(SELECT, 0);
		//简单语法分析栈
		LinkedList<String> stack  =  new LinkedList<String>();
		stack.push(SELECT);
		while(!stack.isEmpty()){
			String first = stack.getFirst();
			int p1 = sql.indexOf(SELECT, pointer + 4);
			int p2 = sql.indexOf(FROM, pointer + 4);
			p1 = (p1 == -1)? MAXNUM: p1;
			p2 = (p2 == -1)? MAXNUM: p2;
			if(p1 == MAXNUM && p2 == MAXNUM) throw new RuntimeException("SQL语句非法...");
			if(p1 <= p2){
				pointer = p1;
				if(first.equals(SELECT))stack.push(SELECT);
				else stack.pop();
			}else{
				pointer = p2;
				if(first.equals(FROM))stack.push(FROM);
				else stack.pop();
			}
		}		
		int fromIndex = pointer;
		sql = sql.substring(fromIndex);
		int orderIndex = sql.lastIndexOf("order by");
		if(orderIndex != -1 && sql.indexOf(")", orderIndex) < 0){
			sql = sql.substring(0, orderIndex);
		}
		int limitIndex = sql.lastIndexOf("limit");
		if(limitIndex != -1 && sql.indexOf(")", limitIndex) < 0){
			sql = sql.substring(0, limitIndex);
		}
		return "select count("+ count +") " + sql;
	}
	
	public static String formateSqlForCount2(String sql, String count){
		sql = sql.replaceAll("(^\\s*\\()|(\\)\\s*$)", "").replaceAll("(?i)select\\s+", "select ").replaceAll("(?i)from\\s+", "from ").replaceAll("(?i)order\\s+by", "order by").replaceAll("(?i)limit", "limit");
		int pointer = sql.indexOf(SELECT, 0);
		//简单语法分析栈
		LinkedList<String> stack  =  new LinkedList<String>();
		stack.push(SELECT);
		while(!stack.isEmpty()){
			String first = stack.getFirst();
			int p1 = sql.indexOf(SELECT, pointer + 4);
			int p2 = sql.indexOf(FROM, pointer + 4);
			p1 = (p1 == -1)? MAXNUM: p1;
			p2 = (p2 == -1)? MAXNUM: p2;
			if(p1 == MAXNUM && p2 == MAXNUM) throw new RuntimeException("SQL语句非法...");
			if(p1 <= p2){
				pointer = p1;
				if(first.equals(SELECT))stack.push(SELECT);
				else stack.pop();
			}else{
				pointer = p2;
				if(first.equals(FROM))stack.push(FROM);
				else stack.pop();
			}
		}		
		int fromIndex = pointer;
		sql = sql.substring(fromIndex);
		int orderIndex = sql.lastIndexOf("order by");
		if(orderIndex != -1 && sql.indexOf(")", orderIndex) < 0){
			sql = sql.substring(0, orderIndex);
		}
		int limitIndex = sql.lastIndexOf("limit");
		if(limitIndex != -1 && sql.indexOf(")", limitIndex) < 0){
			sql = sql.substring(0, limitIndex);
		}
		return "select count("+ count +") coo ,(SELECT h.campersState FROM `HouseCheck` h WHERE ic.`campersId`=h.`campersId` AND h.checkDate=CURDATE() " +
					" ORDER BY h.checkDate LIMIT 1)AS state " + sql;
	}
	
	/**
	 * 
	 * 方法说明：简单的SQLSERVER2000分页特殊处理程序(因为它不支持limit 与 rownum)
	 * 其它说明：最后一定要有一个order by 语句(最优的方案应该是用max 主键，但不通用，放弃)
	 * @return
	 */
	public static String simpleMsPagination(String sql, String orderField, long totalCount, long offSet, long size) {
		long end =  offSet + size;
		size = (end <= totalCount) ? size : (totalCount - offSet);
		end = (end <= totalCount) ? end : totalCount;
		sql = sql.replaceAll("(?i)order\\s+by", "order by").replaceAll("(?i)\\s+asc", " asc").replaceAll("(?i)\\s+desc", " desc");
		sql = sql.replaceFirst("top\\s+###", "top " + end);
		int desc = sql.lastIndexOf("desc");		
		boolean down = (desc != -1)? true : false;
		sql = "select * from(select top " + size + " * from( " + sql + " ) as tempA  order by tempA." + orderField + " " + (down? "asc": "desc") + ") as tempB order by tempB." + orderField + " " + (down? "desc": "asc") + "";
		return sql;
	}
	
	/**
	 * 
	 * 方法说明：函数模板转换
	 * @param sql
	 * @return string
	 * @throws ParseException 
	 */
	public static String templateFunction(String sql) throws ParseException {
		sql =  	sql
				.replaceAll("(?i)L5\\s*\\(([^\\(\\)]+)\\)", "left($1,5)")
		  		.replaceAll("(?i)L7\\s*\\(([^\\(\\)]+)\\)", "left($1,7)")
		  		.replaceAll("(?i)L10\\s*\\(([^\\(\\)]+)\\)", "left($1,10)")
		  		;
		// 直接产生加一天日期数值
		String str001 = "(?i)AD\\s*\\(([^\\(\\)]+)\\)";
		Matcher mt001 = CommonUtils.getMatcher(str001, sql);
		while (mt001.find()) {
			Date g1 = CommonUtils.parseDateFormStr(mt001.group(1).replaceAll("(^')|('$)", ""));
			Date dv = DateUtils.addDays(g1, 1);
			sql = sql.replaceFirst(str001, "'" + CommonUtils.formateDateToStr(dv) + "'");
		}
		// 直接产生年龄数值
		String str002 = "TT\\((\\d+)((-|\\+|\\*|/)(\\d+))?\\)";
		Matcher mt = CommonUtils.getMatcher(str002, sql);
		while (mt.find()) {
			Integer age = CommonUtils.evalInt(mt.group(1));
			String g3 = mt.group(3);
			String g4 = mt.group(4);
			if (g3 != null && g4 != null) {
				int gi4 = Integer.valueOf(g4);
				if ("-".equals(g3))
					age -= gi4;
				else if ("+".equals(g3))
					age += gi4;
				else if ("*".equals(g3))
					age *= gi4;
				else if ("/".equals(g3))
					age /= gi4;
			}
			String rp = "'" + CommonUtils.getBirthdayByAge(age) + "'";
			sql = sql.replaceFirst(str002, rp);
		}
		// 直接产生一个月的第一天
		String str003 = "(?i)FDM\\s*\\('(\\d{4}年\\d{2}月)'\\)";
		Matcher mt003 = CommonUtils.getMatcher(str003, sql);
		while (mt003.find()) {
			String temp003 = CommonUtils.chineseMonthYearToStr(mt003.group(1));
			sql = sql.replaceFirst(str003, "'" + temp003 + "'");
		}
		// 直接产生一个月的最后一天(实际是下个月第一天)
		String str004 = "(?i)LDM\\s*\\('(\\d{4}年\\d{2}月)'\\)";
		Matcher mt004 = CommonUtils.getMatcher(str004, sql);
		while (mt004.find()) {			
			String temp004 = CommonUtils.firstDayOfNextMonthToStr(CommonUtils.chineseMonthYearToDate(mt004.group(1)));
			sql = sql.replaceFirst(str004, "'" + temp004 + "'");
		}			
		return sql;
	}
	
	
	/**
	 * 
	 * 方法说明：删除一些特殊字符，防止sql注入delete update insert语句(只是是针对查询)
	 * 其它说明: 本来计划放到query切面的
	 * @return sql
	 */
	public static String replaceSpecialWord(String querySql){
		querySql = querySql.replaceAll(";", "\\\\;");
		//上面一个就够，下面的是再安全起见...		
		String a2 = "[^\\.~$',_a-zA-Z_0-9]";
		String a1 = "(?i)" + a2;
		querySql = querySql.replaceAll(a1 + "delete" + a2, "--");
		querySql = querySql.replaceAll(a1 + "update" + a2, "--");
		querySql = querySql.replaceAll(a1 + "insert" + a2, "--");
		querySql = querySql.replaceAll(a1 + "replace" + a2,"--");
		return querySql;
	}
	
	/**
	 * 获取子表名
	 * @param tableName
	 * @param memberId
	 * @return
	 */
	public static String getSubTableName(String tableName, long memberId) {
		long endNum = memberId % 100;

		if (endNum > 9) {
			return new StringBuilder(tableName).append("_").append(endNum)
					.toString();
		} else if (endNum < 0) {
			return new StringBuilder(tableName).toString();
		} else {
			return new StringBuilder(tableName).append("_0").append(endNum)
					.toString();
		}
	}
	
	/**
	 * 
	 * 方法说明：分1000时，取得表名使用这个东西
	 * @return
	 */
	public static String getMod1000SubTableName(String tableName, Long memberId){
		String mod = memberId % 1000 + "";
		return tableName + "_" +  StringUtils.leftPad(mod, 3, "0");
	}
	
	public static String getMod1000SubTableName(String tableName, Integer memberId){
		return getMod1000SubTableName(tableName,(long)memberId);
	}
	
}
