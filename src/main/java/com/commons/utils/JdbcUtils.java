package com.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.modules.base.dao.BaseMapper;

/**
 * 对BaseMapper加入的辅助方法
 * @author DoubleLi
 * @time 2018年12月10日
 * 
 */
public class JdbcUtils {
	/**
	 * ?替换的方法
	 * @param sql
	 * @param baseMapper
	 * @param args
	 * @return
	 * @time 2018年11月19日
	 * @author DoubleLi
	 */
	public static int update(String sql, BaseMapper baseMapper, Object... args) {
		return baseMapper.update(constructSql(sql, args));
	}
	
	/**
	 * ?替换的方法
	 * @param sql
	 * @param baseMapper
	 * @param args
	 * @return
	 * @time 2018年11月19日
	 * @author DoubleLi
	 */
	public static int insert(String sql, BaseMapper baseMapper, Object... args) {
		return baseMapper.insert(constructSql(sql, args));
	}
	
	/**
	 * ?替换的方法
	 * @param sql
	 * @param baseMapper
	 * @param args
	 * @return
	 * @time 2018年11月19日
	 * @author DoubleLi
	 */
	public static Map<String, Object> select(String sql, BaseMapper baseMapper, Object... args) {
		return baseMapper.selectMap(constructSql(sql, args));
	}
	/**
	 * ?替换的方法
	 * @param sql
	 * @param baseMapper
	 * @param args
	 * @return
	 * @time 2018年11月19日
	 * @author DoubleLi
	 */
	public static Long selectLong(String sql, BaseMapper baseMapper, Object... args) {
		return baseMapper.selectLong(constructSql(sql, args));
	}
	public static String constructSql(String sql, Object[] array) {
		if(array.length==0)
			return sql;
		String key = "\\?";
		Pattern p = Pattern.compile(key);
		Matcher m = p.matcher(sql);
		StringBuffer stringBuffer = new StringBuffer();
		int i = 0;
		boolean result = m.find();
		while (result) {
			m.appendReplacement(stringBuffer, "'" + array[i].toString() + "'");
			result = m.find();
			i++;
		}
		return String.valueOf(m.appendTail(stringBuffer));
	}

	public static void main(String[] args) {
		System.out.println(constructSql("select ??? from ss", new Object[] { 1, "11", 3 }));
	}
	
	/**
	 * ?替换的方法
	 * @param sql
	 * @param baseMapper
	 * @param args
	 * @return
	 * @time 2018年11月19日
	 * @author DoubleLi
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> selectListByT(String sql, BaseMapper baseMapper, Class<T> class1, Object... args) {
		List<Map<String, Object>> list=baseMapper.select(constructSql(sql, args));
		if(class1 == Map.class) return (List<T>) list;
		List<T> result=new ArrayList<T>();
		for (Map<String, Object> map : list) {
			try {
				result.add(BeanUtils.transMapToBean(class1, map));
			} catch (Exception e) {
			}
		}
		return result;
	}
	/**
	 * ?替换的方法
	 * @param sql
	 * @param baseMapper
	 * @param args
	 * @return
	 * @time 2018年11月19日
	 * @author DoubleLi
	 */
	public static Integer delete(String sql, BaseMapper baseMapper, Object... args) {
		return baseMapper.del(constructSql(sql, args));
	}

}
