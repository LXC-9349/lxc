package com.commons.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author LiuJiaJun
 */
public class PojoUtils {

	/**
	 * 通过response传送数据到客户端
	 * 
	 * @param response
	 * @param content
	 * @throws IOException
	 * @author LiuJiaJun
	 */
	public static void sendClient(HttpServletResponse response, Object content) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(content);
		out.flush();
		out.close();
	}

	public static Map<String, String> comparePojo(Object src, Object dest) {
		return comparePojo(src, dest, "serialVersionUID");
	}

	public static Map<String, Object> comparePojo(Object src, Object dest, boolean allowNull) {
		return comparePojo(src, dest, allowNull, "serialVersionUID");
	}

	public static Map<String, String> comparePojo(Object src, Object dest, String excludeFields) {
		if (dest == null) {
			return null;
		}
		if (src != null && !dest.getClass().equals(src.getClass())) {
			return null;
		}
		Field[] fs = dest.getClass().getDeclaredFields();
		Class<?> superClass = dest.getClass().getSuperclass();
		while (superClass != null && superClass != Object.class) {
			fs = ArrayUtils.addAll(fs, superClass.getDeclaredFields());
			superClass = superClass.getSuperclass();
		}
		String[] excludes = excludeFields == null ? new String[] {} : excludeFields.split(",");
		List<String> excludeFieldList = Arrays.asList(excludes);
		Map<String, String> valuesMap = new HashMap<String, String>();
		for (int i = 0; i < fs.length; i++) {
			String name = fs[i].getName();
			Object value = null;
			if (excludeFieldList.contains(name))
				continue;
			Object v1 = null;
			Object v2 = null;
			try {
				Method m = dest.getClass().getMethod("get" + StringUtils.capitalize(name));
				v1 = m.invoke(dest);
				if (src != null) {
					v2 = m.invoke(src);
				}
			} catch (Exception e) {
				System.err.println(e + " : name=" + name + ",value=" + value);
			}
			if (v1 == null && v2 != null) {
				continue;
			}
			if (v1 != null && !v1.equals(v2)) {
				if (v1 instanceof Date) {
					valuesMap.put(name, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(v1));
				} else {
					valuesMap.put(name, v1.toString());
				}
			}
		}
		return valuesMap;
	}

	public static Map<String, Object> comparePojo(Object src, Object dest, boolean allowNull, String excludeFields) {
		if (dest == null) {
			return null;
		}
		if (src != null && !dest.getClass().equals(src.getClass())) {
			return null;
		}
		Field[] fs = dest.getClass().getDeclaredFields();
		Class<?> superClass = dest.getClass().getSuperclass();
		while (superClass != null && superClass != Object.class) {
			fs = ArrayUtils.addAll(fs, superClass.getDeclaredFields());
			superClass = superClass.getSuperclass();
		}
		String[] excludes = excludeFields == null ? new String[] {} : excludeFields.split(",");
		List<String> excludeFieldList = Arrays.asList(excludes);
		Map<String, Object> valuesMap = new HashMap<String, Object>();
		for (int i = 0; i < fs.length; i++) {
			String name = fs[i].getName();
			Object value = null;
			if (excludeFieldList.contains(name))
				continue;
			Object v1 = null;
			Object v2 = null;
			try {
				Method m = dest.getClass().getMethod("get" + StringUtils.capitalize(name));
				v1 = m.invoke(dest);
				if (src != null) {
					v2 = m.invoke(src);
				}
			} catch (Exception e) {
				System.err.println(e + " : name=" + name + ",value=" + value);
			}
			if (allowNull || (v1 != null && ObjectUtils.notEqual(v1, v2))) {
				if (v1 instanceof Date) {
					valuesMap.put(name, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(v1));
				} else {
					valuesMap.put(name, v1);
				}
			}
		}
		return valuesMap;
	}

	public static String getInsertSQL(String table, Map<String, String> dataMap, String primaryKey) {
		if (dataMap == null || dataMap.isEmpty())
			return null;
		StringBuilder sql1 = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		sql1.append("insert ignore into ").append(table).append(" (");
		sql2.append(" ) values (");
		Set<String> keySet = dataMap.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			Object value = dataMap.get(key);
			if (primaryKey != null && key.equalsIgnoreCase(primaryKey)) {
				continue;
			}
			if (value == null) {
				continue;
			} else {
				sql1.append("`").append(key).append("`").append(", ");
				sql2.append("'").append(encodeSQL(value.toString())).append("', ");
			}
		}
		sql1 = new StringBuilder(sql1.subSequence(0, sql1.lastIndexOf(",")));
		sql2 = new StringBuilder(sql2.subSequence(0, sql2.lastIndexOf(",")));
		sql1.append(sql2).append(")");
		return sql1.toString();
	}
	public static String encodeSQL(String sql) {
		if (sql == null) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < sql.length(); ++i) {
				char c = sql.charAt(i);
				switch (c) {
				case '\b':
					sb.append("\\b");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\"':
					sb.append("\\\"");
					break;
				case '\'':
					sb.append("\'\'");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				default:
					sb.append(c);
				}
			}

			return sb.toString();
		}
	}
	public static String getReplaceSQL(String table, Map<String, String> dataMap, String primaryKey) {
		StringBuilder sql1 = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		sql1.append("replace into ").append(table).append(" (");
		sql2.append(" ) values (");
		Set<String> keySet = dataMap.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			Object value = dataMap.get(key);
			if (primaryKey != null && key.equalsIgnoreCase(primaryKey)) {
				continue;
			}
			if (value == null) {
				continue;
			} else {
				sql1.append(key).append(", ");
				sql2.append("'").append(encodeSQL(value.toString())).append("', ");
			}
		}
		sql1 = new StringBuilder(sql1.subSequence(0, sql1.lastIndexOf(",")));
		sql2 = new StringBuilder(sql2.subSequence(0, sql2.lastIndexOf(",")));
		sql1.append(sql2).append(")");
		return sql1.toString();
	}

	/**
	 * 
	 * @param table
	 * @param dataMap
	 * @param primaryKey
	 *            复合主键用,隔开
	 * @param primaryValue
	 * @return
	 * @author LiuJiaJun
	 */
	public static String getUpdateSQL(String table, Map<String, String> dataMap, String primaryKey,
			Object... primaryValue) {
		if (dataMap == null || dataMap.size() == 0) {
			return null;
		}
		StringBuilder sql1 = new StringBuilder();
		sql1.append("update ").append(table).append(" set ");
		Set<String> keySet = dataMap.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			Object value = dataMap.get(key);
			if (value == null) {
				continue;
			} else {
				sql1.append("`").append(key).append("`").append("=");
				sql1.append("'").append(encodeSQL(value.toString())).append("', ");
			}
		}
		sql1 = new StringBuilder(sql1.subSequence(0, sql1.lastIndexOf(",")));
		sql1.append(" where 1=1");
		String[] keys = primaryKey.split(",");
		for (int i = 0; i < keys.length; i++) {
			if (primaryValue[i].getClass() == Integer.class) {
				if (NumberUtils.isDigits(CommonUtils.evalString(primaryValue[i]))) {
					sql1.append(" and ").append(keys[i]).append("=").append(primaryValue[i]);
				}
			} else if (primaryValue[i].getClass() == Long.class) {
				if (NumberUtils.isDigits(CommonUtils.evalString(primaryValue[i]))) {
					sql1.append(" and ").append(keys[i]).append("=").append(primaryValue[i]);
				}
			} else if (primaryValue[i].getClass() == String.class) {
				String pv = CommonUtils.evalString(primaryValue[i]);
				pv = encodeSQL(pv);
				sql1.append(" and ").append(keys[i]).append("='").append(pv).append("'");
			} else if (primaryValue[i].getClass() == Date.class) {
				if (CommonUtils.evalDate(primaryValue[i]) != null) {
					sql1.append(" and ").append(keys[i]).append("='").append(primaryValue[i]).append("'");
				}
			}
		}
		return sql1.toString();
	}

	public static String getUpdateSQL(String table, Map<String, Object> dataMap, boolean allowNull, String primaryKey,
			Object... primaryValue) {
		if (dataMap == null || dataMap.size() == 0) {
			return null;
		}
		StringBuilder sql1 = new StringBuilder();
		sql1.append("update ").append(table).append(" set ");
		Set<String> keySet = dataMap.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			Object value = dataMap.get(key);
			if (value == null) {
				if (allowNull) {
					sql1.append(key).append("=").append(value).append(",");
				} else {
					continue;
				}
			} else {
				sql1.append(key).append("=");
				sql1.append("'").append(encodeSQL(value.toString())).append("', ");
			}
		}
		sql1 = new StringBuilder(sql1.subSequence(0, sql1.lastIndexOf(",")));
		sql1.append(" where 1=1");
		String[] keys = primaryKey.split(",");
		for (int i = 0; i < keys.length; i++) {
			if (primaryValue[i].getClass() == Integer.class) {
				if (NumberUtils.isDigits(CommonUtils.evalString(primaryValue[i]))) {
					sql1.append(" and ").append(keys[i]).append("=").append(primaryValue[i]);
				}
			} else if (primaryValue[i].getClass() == Long.class) {
				if (NumberUtils.isDigits(CommonUtils.evalString(primaryValue[i]))) {
					sql1.append(" and ").append(keys[i]).append("=").append(primaryValue[i]);
				}
			} else if (primaryValue[i].getClass() == String.class) {
				String pv = CommonUtils.evalString(primaryValue[i]);
				pv = encodeSQL(pv);
				sql1.append(" and ").append(keys[i]).append("='").append(pv).append("'");
			} else if (primaryValue[i].getClass() == Date.class) {
				if (CommonUtils.evalDate(primaryValue[i]) != null) {
					sql1.append(" and ").append(keys[i]).append("='").append(primaryValue[i]).append("'");
				}
			}
		}
		return sql1.toString();
	}

	/**
	 * 通过JavaBean反射方式得到select查询语句
	 * 
	 * @param <T>
	 * @param table
	 *            表名
	 * @param clazz
	 *            JavaBean类的字节码
	 * @param excludeFields
	 *            排除不需要查询的字段
	 * @param tableFields
	 *            需要加的条件字段
	 * @param values
	 *            对应值
	 * @return
	 * @author LiuJiaJun
	 */
	public static <T> String getSelectSql(String table, Class<T> clazz, String excludeFields, String tableFields,
			Object... values) {
		String[] excludes = excludeFields == null ? new String[] {} : excludeFields.split(",");
		List<String> excludeFieldList = Arrays.asList(excludes);
		Field[] fields = clazz.getDeclaredFields();
		Class<?> superClass = clazz.getSuperclass();
		while (superClass != null && superClass != Object.class) {
			fields = ArrayUtils.addAll(fields, superClass.getDeclaredFields());
			superClass = superClass.getSuperclass();
		}
		StringBuilder sb = new StringBuilder(100);
		sb.append("select ");
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			if (!excludeFieldList.contains(fieldName)) {
				sb.append(fieldName).append(",");
			}
		}
		sb = new StringBuilder(sb.subSequence(0, sb.lastIndexOf(",")));
		if (table == null)
			table = clazz.getSimpleName();
		sb.append(" from ").append(table);
		sb.append(" where 1=1");
		if (tableFields != null && values[0] != null) {
			String[] keys = tableFields.split(",");
			for (int i = 0; i < keys.length; i++) {
				Object value = values[i];
				if (value != null) {
					sb.append(" and ").append(keys[i]);
					if (value.getClass() == Integer.class) {
						if (NumberUtils.isDigits(CommonUtils.evalString(value))) {
							sb.append("=").append(value);
						}
					} else if (value.getClass() == Long.class) {
						if (NumberUtils.isDigits(CommonUtils.evalString(value))) {
							sb.append("=").append(value);
						}
					} else if (value.getClass() == String.class) {
						String pv = CommonUtils.evalString(value);
						pv = encodeSQL(pv);
						sb.append("='").append(value).append("'");
					} else if (value.getClass() == Date.class) {
						if (CommonUtils.evalDate(value) != null) {
							sb.append("='").append(value).append("'");
						}
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 生成查询语句
	 * 
	 * @param clazz
	 * @param excludeFields
	 * @param all
	 * @param orderInfo
	 * @param tableFields
	 * @param values
	 * @return
	 */
	public static <T> String getSelectSql(Class<T> clazz, String excludeFields, boolean all, String[] orderInfo,
			String tableFields, Object... values) {
		String[] excludes = excludeFields == null ? new String[] {} : excludeFields.split(",");
		List<String> excludeFieldList = Arrays.asList(excludes);
		Field[] fields = clazz.getDeclaredFields();
		Class<?> superClass = clazz.getSuperclass();
		while (superClass != null && superClass != Object.class) {
			fields = ArrayUtils.addAll(fields, superClass.getDeclaredFields());
			superClass = superClass.getSuperclass();
		}
		StringBuilder sb = new StringBuilder(500);
		sb.append("select ");
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			if (!excludeFieldList.contains(fieldName)) {
				sb.append(fieldName).append(",");
			}
		}
		sb = new StringBuilder(sb.subSequence(0, sb.lastIndexOf(",")));
		sb.append(" from ").append(clazz.getSimpleName());
		sb.append(" where 1=1");
		if (tableFields != null && values[0] != null) {
			String[] keys = tableFields.split(",");
			for (int i = 0; i < keys.length; i++) {
				Object value = values[i];
				if (value != null) {
					if (value.getClass() == Integer.class) {
						if (NumberUtils.isDigits(CommonUtils.evalString(value))) {
							sb.append(" and ").append(keys[i]);
							sb.append("=").append(value);
						}
					} else if (value.getClass() == Long.class) {
						if (NumberUtils.isDigits(CommonUtils.evalString(value))) {
							sb.append(" and ").append(keys[i]);
							sb.append("=").append(value);
						}
					} else if (value.getClass() == String.class) {
						String pv = CommonUtils.evalString(value);
						pv = encodeSQL(pv);
						if(StringUtils.isNotBlank(pv)){
							sb.append(" and ").append(keys[i]);
							sb.append("='").append(value).append("'");
						}
					} else if (value.getClass() == Date.class) {
						if (CommonUtils.evalDate(value) != null) {
							sb.append(" and ").append(keys[i]);
							sb.append("='").append(value).append("'");
						}
					}
				}
			}
		}
		if (orderInfo != null && orderInfo.length > 0) {
			String[] orderBys = orderInfo[0] == null ? null : orderInfo[0].split(",");
			if (orderBys != null && orderBys.length > 0) {
				String sortType = StringUtils.EMPTY;
				if (orderInfo.length > 1 && orderInfo[1] != null)
					sortType = orderInfo[1];
				sb.append(" order by ");
				for (int i = 0; i < orderBys.length; i++) {
					if (i > 0)
						sb.append(",");
					sb.append(orderBys[i] + " " + sortType);
				}
			}
		}
		if (!all)
			sb.append(" limit 1");
		return sb.toString();
	}

	/**
	 * 生成查询语句（取一条数据的SQL）
	 * 
	 * @param clazz
	 * @param excludeFields
	 * @param orderInfo
	 * @param tableFields
	 * @param values
	 * @return
	 */
	public static <T> String getSelectSql(Class<T> clazz, String excludeFields, String[] orderInfo, String tableFields,
			Object... values) {
		return getSelectSql(clazz, excludeFields, false, orderInfo, tableFields, values);
	}

	/**
	 * 得到select字段语句
	 * 
	 * @param clazz
	 * @param aliasName
	 * @param excludeFields
	 * @return
	 */
	public static <T> String getSelectSqlOfFields(Class<T> clazz, String aliasName, String excludeFields) {
		String[] excludes = excludeFields == null ? new String[] {} : excludeFields.split(",");
		List<String> excludeFieldList = Arrays.asList(excludes);
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder sb = new StringBuilder(500);
		sb.append("select ");
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			if (!excludeFieldList.contains(fieldName)) {
				sb.append(aliasName).append(".").append(fieldName).append(",");
			}
		}
		sb = new StringBuilder(sb.subSequence(0, sb.lastIndexOf(",")));
		return sb.toString();
	}

	public static String[] getArrayPropertis(Object data, String propertyName, String filterName, Object filterValue,
			boolean uniq) {
		return getArrayPropertis(data, propertyName, filterName, filterValue, Sign.eq, uniq);
	}

	public static String[] getArrayPropertis(Object data, String propertyName, String filterName, Object filterValue) {
		return getArrayPropertis(data, propertyName, filterName, filterValue, Sign.eq, false);
	}

	public static <T> Object getProperties(T obj, String propertyName) {
		if (obj == null)
			return null;
		if (obj instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) obj;
			return map.get(propertyName);
		} else {
			Class<?> clazz = obj.getClass();
			try {
				Method method = clazz.getMethod("get" + StringUtils.capitalize(propertyName));
				return method.invoke(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> void setProperties(T obj, String propertyName, Object propertyValue) {
		if (obj == null)
			return;
		if (obj instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) obj;
			map.put(propertyName, propertyValue);
		} else {
			Class<?> clazz = obj.getClass();
			try {
				Method method = clazz.getMethod("set" + StringUtils.capitalize(propertyName), propertyValue.getClass());
				method.invoke(obj, propertyValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 取集合列表指定属性的值，返回一个数组
	 * 
	 * @param data
	 * @param propertyName
	 * @param filterName
	 *            过滤属性
	 * @param filterValue
	 *            对应过滤属性值(filterName这个属性的值与这个值相等)
	 * @param sign
	 *            {@link Sign}
	 * @return
	 * @author LiuJiaJun
	 * @date 2013-5-22上午09:41:25
	 */
	public static String[] getArrayPropertis(Object data, String propertyName, String filterName, Object filterValue,
			Sign sign, boolean uniq) {
		if (data == null)
			return null;
		if (data instanceof List) {
			List<?> src = (List<?>) data;
			if (src.size() == 0)
				return null;
			Collection<String> result = uniq ? new HashSet<>() : new ArrayList<String>();
			Object value = null;
			for (int i = 0; i < src.size(); i++) {
				Object obj = src.get(i);
				value = getProperties(obj, propertyName);
				String strVal = CommonUtils.evalString(value);
				if (StringUtils.isNotBlank(strVal)) {
					if (filterName != null && filterValue != null) {
						Object fv = getProperties(obj, filterName);
						if (compare(fv, filterValue, sign)) {
							result.add(strVal);
						}
					} else {
						result.add(strVal);
					}
				}
			}
			return CommonUtils.evalStrToArray(result);
		}
		return null;
	}

	private static boolean compare(Object original, Object target, Sign sign) {
		if (original == null || target == null)
			return false;
		if (sign == Sign.eq) {
			return original.equals(target);
		} else {
			boolean isNumber = NumberUtils.isDigits(original.toString()) && NumberUtils.isDigits(target.toString());
			if (isNumber) {
				Number o = NumberUtils.createNumber(original.toString());
				Number t = NumberUtils.createNumber(target.toString());
				if (sign == Sign.gt) {
					return o.doubleValue() > t.doubleValue();
				} else if (sign == Sign.ge) {
					return o.doubleValue() >= t.doubleValue();
				} else if (sign == Sign.lt) {
					return o.doubleValue() < t.doubleValue();
				} else if (sign == Sign.le) {
					return o.doubleValue() <= t.doubleValue();
				} else if (sign == Sign.ne) {
					return o.doubleValue() != t.doubleValue();
				}
			}
		}
		return false;
	}

	/**
	 * 比较符号
	 * 
	 * @author Administrator
	 *
	 */
	public enum Sign {
		eq, gt, lt, ge, le, ne
	}


}
