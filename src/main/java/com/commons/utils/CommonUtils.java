package com.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.alibaba.fastjson.JSONObject;

public class CommonUtils {

	/** 格式化字符串 */
	public static String replaceStr(String target) {
		String result = target.trim().replace("，", ",").replace("、", ",").replace("，，", ",").replace("\r\n", ",")
				.replace("\n", ",").replace("\r", ",").replace(",,", ",");
		return result;
	}

	/**
	 * 
	 * @return Matcher
	 */
	public static String serialObject(Object obj) {
		if (obj == null)
			return "";
		if (obj instanceof Date) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(obj);
		} else {
			return obj.toString();
		}
	}

	/**
	 * 
	 * 方法说明：把表一的数据合并到表二的数
	 * 
	 * @param list1
	 * @param list2
	 * @param groupFields
	 * @param copyFields
	 * @return
	 */
	public static void mergeAlgorithm(List<Map<String, Object>> list1, List<Map<String, Object>> list2,
			String[] groupFields, String[] copyFields) {
		if (list1.isEmpty() && list2.isEmpty()) {
			return;
		}
		if (list1.isEmpty()) {
			return;
		}
		if (list2.isEmpty()) {
			for (int i = 0; i < list1.size(); i++) {
				list2.add(list1.get(i));
			}
			return;
		}

		Map<String, Map<String, Object>> groupMap = new HashMap<String, Map<String, Object>>();
		for (int i = 0; i < list2.size(); i++) {
			Map<String, Object> item = list2.get(i);
			String groupKey = "";
			for (int j = 0; j < groupFields.length; j++) {
				String gv = CommonUtils.serialObject(item.get(groupFields[j]));
				groupKey += "-" + gv + "-";
			}
			groupMap.put(groupKey, item);
		}

		for (int i = 0; i < list1.size(); i++) {
			Map<String, Object> item = list1.get(i);
			String groupKey = "";
			for (int j = 0; j < groupFields.length; j++) {
				String gv = CommonUtils.serialObject(item.get(groupFields[j]));
				groupKey += "-" + gv + "-";
			}
			Map<String, Object> isExistItem = groupMap.get(groupKey);
			if (isExistItem != null) {
				for (int j = 0; j < copyFields.length; j++) {
					String innerKey = copyFields[j];
					isExistItem.put(innerKey, item.get(innerKey));
				}
			} else {
				list2.add(item);
			}
		}
		return;
	}

	/**
	 * 
	 * 方法说明：统一向list增加某些值
	 * 
	 * @param list
	 */
	public static void initField(List<Map<String, Object>> list, String[] key, Object[] value, String[] refInnerValue) {
		if (list == null || list.isEmpty()) {
			return;
		}
		for (Map<String, Object> item : list) {
			for (int i = 0; i < key.length; i++) {
				if (item.get(key[i]) == null) {
					item.put(key[i], value[i]);
				}
			}
			int size = refInnerValue != null ? refInnerValue.length : 0;
			for (int i = 0; refInnerValue != null && i < size;) {
				if (i + 1 < size) {
					item.put(refInnerValue[i + 1], item.get(refInnerValue[i]));
				}
				i += 2;
			}
		}
	}

	/**
	 * 
	 * 方法说明：求出给出日期的日部分
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getBaseDate(Date date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(df.format(date));
	}

	/**
	 * @param date
	 * @return
	 */
	public static String formateDateToStr(Date date) {
		if (date == null)
			return null;
		// 一种格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		return df.format(date);
	}

	/**
	 * @param date
	 * @return
	 */
	public static String formateDateToStr2(Date date) {
		if (date == null)
			return null;
		// 一种格式
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
		return df.format(date);
	}

	/**
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String formateDateToStr(Date date, String formatStr) {
		if (date == null)
			return null;
		// 一种格式
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		return df.format(date);
	}

	/**
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateFormStr(String source) throws ParseException {
		if (source == null)
			return null;
		// 一种格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		return df.parse(source);
	}
	
	/**
	 * 字符串输出指定格式
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static String strParseDateFormStr(String source,String formatStr) throws ParseException {
		if (source == null)
			return null;
		// 先输出日期格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.ENGLISH);
		// 再转成字符串
		SimpleDateFormat df2 = new SimpleDateFormat(formatStr, Locale.ENGLISH);
		Date date = df.parse(source);
		return df2.format(date);
	}
	
	/**
	 * @param source
	 * @param formatStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateFormStr(String source, String... formatStr) {
		if (source == null)
			return null;
		for (String string : formatStr) {
			// 一种格式
			SimpleDateFormat df = new SimpleDateFormat(string);
			try {
				return df.parse(source);
			} catch (ParseException e) {
			}
		}
		return null;
	}

	/**
	 * 将一个时间字符串转化为时间的下一天并返回字符串
	 * 
	 * @param date时间字符串
	 * @param pattern
	 *            时间格式
	 * @return 传入 2011-11-02 返回2011-11-03
	 */
	public static String strToNextDateStr(String date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(df.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.DATE, 1);
		return df.format(cal.getTime());
	}

	/**
	 * 将一个时间字符串转化为时间的前一天并返回字符串
	 * 
	 * @param date时间字符串
	 * @param pattern
	 *            时间格式
	 * @return 传入 2011-11-02 返回2011-11-01
	 */
	public static String strToPrevDateStr(String date, String pattern) {
		return strToPrevDateStr(date, pattern, -1);
	}

	/**
	 * 将一个时间字符串转化为时间的前/后day天并返回字符串
	 * 
	 * @param date时间字符串
	 * @param pattern
	 *            时间格式
	 * @param day
	 *            多少天
	 * @return
	 */
	public static String strToPrevDateStr(String date, String pattern, int day) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(df.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.DATE, day);
		return df.format(cal.getTime());
	}

	public static String yesterdayToStr() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		return formateDateToStr(c.getTime());
	}

	/**
	 * @return
	 */
	public static String todayToStr() {
		return formateDateToStr(new Date());
	}

	public static String todayToStr(String pattern) {
		return formateDateToStr(new Date(), pattern);
	}

	/**
	 * @return
	 */
	public static String tomorrowToStr() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		return formateDateToStr(c.getTime());
	}

	public static Date tomorrow() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	public static String tomorrowToStr(String pattern) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		return formateDateToStr(c.getTime(), pattern);
	}

	/**
	 * @return
	 */
	public static String oneWeekaBeforeToStr() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		return formateDateToStr(c.getTime());
	}

	/**
	 * 这一天 星期几 1=星期天 2=星期１
	 */
	public static int getDayOfWeek(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK);

	}

	/**
	 * 一周里面第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date firstDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/**
	 * 一周里面最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date lastDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return cal.getTime();
	}

	/**
	 * @return
	 */
	public static String oneMonthBeforeToStr() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -30);
		return formateDateToStr(c.getTime());
	}

	/**
	 * @param day
	 * @return
	 */
	public static String anyDayBeforeToStr(int day) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 0 - Math.abs(day));
		return formateDateToStr(c.getTime());
	}

	public static Date add(Date date, int field, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(field, amount);
		return c.getTime();
	}

	public static String add(Date date, int field, int amount, String format) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(field, amount);
		return formateDateToStr(c.getTime(), format);
	}

	public static String addMonth(Date date, int amount, String format) {
		Date tmpDate = add(date, Calendar.MONTH, amount);
		return formateDateToStr(tmpDate, format);
	}

	public static int convertUnit(String unit) {
		int result = -1;
		if (StringUtils.equalsIgnoreCase("day", unit)) {
			result = Calendar.DAY_OF_MONTH;
		} else if (StringUtils.equalsIgnoreCase("hour", unit)) {
			result = Calendar.HOUR_OF_DAY;
		} else if (StringUtils.equalsIgnoreCase("minute", unit)) {
			result = Calendar.MINUTE;
		} else if (StringUtils.equalsIgnoreCase("second", unit)) {
			result = Calendar.SECOND;
		} else if (StringUtils.equalsIgnoreCase("week", unit)) {
			result = Calendar.WEEK_OF_MONTH;
		}
		return result;
	}

	/**
	 * @return
	 */
	public static String firstDayOfMonthToStr(Date date) {
		if (date == null)
			date = new Date();
		return formateDateToStr(date).replaceFirst("\\d{2}$", "01");
	}

	/**
	 * 获取某月第一天
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String firstDayOfMonthToStr(Date date, String formatStr) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		// 获取某月最小天数
		int firstDay = cale.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cale.set(Calendar.DAY_OF_MONTH, firstDay);
		return formateDateToStr(clearHMS(cale.getTime()), formatStr);
	}

	/**
	 * 获取某月最后一天
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String lastDayOfMonthToStr(Date date, String formatStr) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// 获取某月最大天数
		int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		c.set(Calendar.DAY_OF_MONTH, lastDay);
		return formateDateToStr(fullHMS(c.getTime()), formatStr);
	}

	/**
	 * @param date
	 * @return
	 */
	public static Date firstDayOfMonth(Date date) {
		if (date == null)
			date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 获取某个月的第一天，如2010-10-01
	 * 
	 * @param num
	 *            0表示当月、-1表示上月、1表示下月
	 * @return
	 */
	public static String firstDayOfAnyMonth(int num) {
		Date date = add(new Date(), Calendar.MONTH, num);
		return firstDayOfMonthToStr(date);
	}

	/**
	 * 获取某个月的最后一天，如2010-10-31
	 * 
	 * @param num
	 *            0表示当月、-1表示上月、1表示下月
	 * @return
	 */
	public static String lastDayOfAnyMonth(int num) {
		Date date = add(new Date(), Calendar.MONTH, num);
		return lastDayOfMonthToStr(date);
	}

	/**
	 * @return
	 */
	public static String lastDayOfMonthToStr(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		int dm = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DATE, -dm);
		return formateDateToStr(c.getTime());
	}

	public static Date lastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		int dm = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DATE, -dm);
		return c.getTime();

	}

	/**
	 * @param Date
	 * @return
	 */
	public static String firstDayOfNextMonthToStr(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		int dm = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DATE, 1 - dm);
		return formateDateToStr(c.getTime());
	}

	/**
	 * @param date
	 * @return
	 */
	public static int getMonthDays(Date date) {
		if (null == date)
			date = new Date();
		return toCalendar(date).getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @return
	 */
	public static String chineseMonthYear() {
		return formateDateToStr(new Date()).replaceFirst("\\d{2}$", "").replaceFirst("-", "年").replaceFirst("-", "月");
	}

	/**
	 * @return
	 */
	public static String chineseMonthYearToStr(String my) {
		return my.replaceAll("年|月", "-") + "01";
	}

	/**
	 * @param my
	 * @return
	 * @throws ParseException
	 */
	public static Date chineseMonthYearToDate(String my) throws ParseException {
		return parseDateFormStr(my.replaceAll("年|月", "-") + "01");
	}

	/**
	 * 把age=29变成1984-*-*（*是明天的日期）
	 * 
	 * @param age
	 * @return
	 */
	public static String getAccurateBirthdayByAge(Integer age) {
		Calendar c = Calendar.getInstance();
		age = 0 - Math.abs(age);
		c.add(Calendar.YEAR, age);
		return strToNextDateStr(formateDateToStr(c.getTime()), "yyyy-MM-dd");
	}

	/**
	 * @param age
	 * @return
	 * @throws ParseException
	 */
	public static String getBirthdayByAge(Integer age) throws ParseException {
		Calendar c = Calendar.getInstance();
		age = 0 - Math.abs(age);
		c.add(Calendar.YEAR, age);
		return formateDateToStr(c.getTime()).replaceFirst("(\\d{4})-\\d{2}-\\d{2}", "$1-01-01");
	}

	/**
	 * @param begin
	 * @return
	 */
	public static Integer getDifferentDays(Date begin) {
		Date now;
		try {
			now = parseDateFormStr(formateDateToStr(new Date()));
			begin = parseDateFormStr(formateDateToStr(begin));
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
		long nms = now.getTime();
		long bms = begin.getTime();
		return (int) ((nms - bms) / (24 * 60 * 60 * 1000));
	}

	public static Integer getDifferentDays(Date begin, Date end) {
		try {
			end = parseDateFormStr(formateDateToStr(end));
			begin = parseDateFormStr(formateDateToStr(begin));
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
		long ems = end.getTime();
		long bms = begin.getTime();
		return (int) ((ems - bms) / (24 * 60 * 60 * 1000));
	}

	/**
	 * @param obj
	 * @return string
	 */
	public static String evalString(Object obj) {
		if (obj == null)
			return "";
		return obj.toString();
	}

	public static String evalToNull(Object obj) {
		if (obj == null || "null".equalsIgnoreCase(obj.toString()))
			return null;
		return StringUtils.trimToNull(obj.toString());
	}

	/**
	 * @param obj
	 * @return string
	 */
	public static int evalInt(Object obj) {
		if (obj == null)
			return -1000;
		return NumberUtils.toInt(obj.toString(), -1000);
	}

	public static Integer evalInteger(Object obj) {
		String str = obj == null ? null : obj.toString();
		if (StringUtils.isBlank(str))
			return null;
		return NumberUtils.toInt(obj.toString(), -1000);
	}

	public static int evalInt(Object obj, int defautValue) {
		if (obj == null)
			return defautValue;
		return NumberUtils.toInt(obj.toString(), defautValue);
	}

	/**
	 * @param obj
	 * @return
	 */
	public static long evalLong(Object obj) {
		if (obj == null)
			return -1000;
		return NumberUtils.toLong(obj.toString(), -1000);
	}

	/**
	 * @param obj
	 * @return
	 */
	public static long evalLong(Object obj, long defaultValue) {
		if (obj == null)
			return defaultValue;
		return NumberUtils.toLong(obj.toString(), defaultValue);
	}

	/**
	 * @param obj
	 * @return
	 */
	public static boolean evalBoolean(Object obj) {
		if (obj == null)
			return false;
		return (Boolean) obj;
	}

	/**
	 * @param obj
	 * @return
	 */
	public static Number evalNumber(Object obj) {
		if (obj == null)
			return null;
		try {
			return (Number) obj;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param obj
	 * @return
	 */
	public static Date evalDate(Object obj) {
		if (obj == null)
			return null;
		try {
			return (Date) obj;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param obj
	 * @return
	 */
	public static Date evalStrDate(Object obj) {
		if (obj == null)
			return null;
		try {
			return parseDateFormStr(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param obj
	 * @return
	 */
	public static Date evalStrDate(String obj, Date defaultDate) {
		if (StringUtils.isBlank(obj))
			return defaultDate;
		try {
			return parseDateFormStr(obj);
		} catch (Exception e) {
			return defaultDate;
		}
	}

	public static Date evalStrDate(Object obj, String formatStr) {
		if (obj == null)
			return null;
		try {
			return parseDateFormStr(obj.toString(), formatStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 方法说明：返回字符串数组.
	 * 
	 * @param str
	 * @return
	 */
	public static String[] evalStrToArray(Collection<String> list) {
		String[] a = null;
		if (null != list && !list.isEmpty()) {
			a = new String[0];
			for (String s : list) {
				if (null == s) {
					continue;
				}
				a = ArrayUtils.add(a, s);
			}
		}
		return a;
	}

	public static String[] evalStrToArray(Object[] object) {
		String[] a = null;
		if (null != object && object.length > 0) {
			a = new String[0];
			for (Object obj : object) {
				if (null == obj) {
					continue;
				}
				a = ArrayUtils.add(a, obj.toString());
			}
		}
		return a;
	}

	/**
	 * @param str
	 * @return
	 */
	public static List<Long> evalStrToLongArray(String str) {
		String[] a = null;
		if (str != null) {
			a = str.split(",");
		}
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; a != null && i < a.length; i++) {
			long l = evalLong(StringUtils.trim(a[i]));
			if (l != -1000) {
				list.add(l);
			}
		}
		return list;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static List<Integer> evalStrToIntegerArray(String str) {
		String[] a = null;
		if (str != null) {
			a = str.split(",");
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; a != null && i < a.length; i++) {
			int l = evalInt(StringUtils.trim(a[i]));
			if (l != -1000) {
				list.add(l);
			}
		}
		return list;
	}

	/**
	 * @return Matcher
	 */
	public static Matcher getMatcher(String regular, String input) {
		Pattern p = Pattern.compile(regular, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(input);
		return m;
	}

	/**
	 * @return Matcher
	 */
	public static boolean isMatched(String regular, String input) {
		Pattern p = Pattern.compile(regular, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(input);
		return m.find();
	}

	/**
	 * @throws Exception
	 */
	public static void throwAnException(String condition, String sql) throws Exception {
		throw new RuntimeException("非法查询条件[" + condition + "]:" + sql);
	}

	/**
	 * @param list
	 * @param symbol
	 * @param quote
	 * @return string
	 */
	public static String listJoin(List<? extends Object> list, String symbol, Boolean quote) {
		quote = quote == null ? false : quote;
		String st = quote ? "'" : "";
		String str = "";
		if (list == null || list.isEmpty())
			return "";
		int i = 0;
		for (; i < list.size() - 1; i++) {
			str += st + list.get(i).toString() + st + symbol;
		}
		str += st + list.get(i).toString() + st;
		return str;
	}

	/**
	 * @param str
	 * @return InputStream
	 * @throws UnsupportedEncodingException
	 */
	public static InputStream toInputStream(String str) {
		String temp = str == null ? "" : str;
		try {
			return new ByteArrayInputStream(temp.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static void sortAlgorithm(List<Map<String, Object>> list, String sortField) {
		sortAlgorithms(list, sortField, 2);
	}

	public static void sortAlgorithms(List<Map<String, Object>> list, String sortField, int precision) {
		if (list == null || list.isEmpty())
			return;
		if (precision < 0) {
			return;
		}
		Map<String, Object> first = list.get(0);
		Number pren = CommonUtils.evalNumber(first.get(sortField));
		if (pren == null)
			pren = 0;
		int hRand = 1;
		first.put("hRand", hRand);
		for (int i = 1; i < list.size(); i++) {
			Map<String, Object> item = list.get(i);
			Number n = CommonUtils.evalNumber(item.get(sortField));
			if (n == null) {
				item.put(sortField, 0);
				n = 0;
			}
			BigDecimal a = new BigDecimal(n.doubleValue());
			a = a.setScale(precision, BigDecimal.ROUND_HALF_UP);
			BigDecimal b = new BigDecimal(pren.doubleValue());
			b = b.setScale(precision, BigDecimal.ROUND_HALF_UP);
			double s = Math.abs(a.subtract(b).doubleValue());
			if (s >= 1 / Math.pow(10, precision)) {
				hRand = i + 1;
			}
			item.put("hRand", hRand);
			pren = n;
		}
	}

	/**
	 * @param str
	 * @return
	 */
	public static void replaceList(List<Map<String, Object>> list, String[] keys) {
		if (list == null || list.isEmpty())
			return;
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> item = list.get(i);
			for (String key : keys) {
				String value = CommonUtils.evalString(item.get(key));
				if (value != null && value.length() > 0) {
					item.put(key, value.replaceFirst("^[0-9\\.\\-\\+\\s\\:]+", ""));
				}
			}
		}
	}

	/**
	 * 得到间隔工作日后的时间
	 * 
	 * @param startDate
	 * @param days
	 * @return
	 */
	public static Date getWorkDays(Date startDate, int days) {
		Date endDate = startDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int count = 0;
		while (count < days) {
			cal.add(Calendar.DATE, 1);
			int day = cal.get(Calendar.DAY_OF_WEEK);
			if (day < 7 && day > 1) {
				count++;
			}
			endDate = cal.getTime();
		}
		return endDate;
	}

	public static <T> long[] parseLongArr(List<T> list) {
		if (list == null)
			return null;
		long[] arr = new long[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Long(list.get(i).toString());
		}
		return arr;
	}

	/**
	 * single是否包含于array
	 * 
	 * @param single
	 * @param ability
	 * @return
	 */
	public static boolean contains(int single, int[] array) {
		for (int element : array) {
			if (single == element) {
				return true;
			}
		}
		return false;
	}

	/**
	 * rangeValue是否包含containValue
	 * 
	 * @param rangeValue
	 * @param containValue
	 * @return
	 */
	public static boolean containsRange(String rangeValue, String regex, String containValue) {
		if (StringUtils.isNotBlank(rangeValue)) {
			String[] range = rangeValue.split(regex);
			for (String v : range) {
				if (StringUtils.isNotBlank(v)) {
					if (v.equals(containValue)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static <T> T ifThenElse(boolean bool, T a, T b) {
		return bool ? a : b;
	}

	/**
	 * 数组遍历
	 * 
	 * @param obj
	 * @param split
	 *            分割符
	 * @param emptyReplace
	 *            为空时内容为空时替换成什么
	 * @return
	 */
	public static String arrayToString(Object[] obj, String split, String emptyReplace) {
		if (ArrayUtils.isEmpty(obj))
			return "";
		if (!StringUtils.isNotBlank(split))
			split = ",";
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < obj.length; i++) {
			str.append(StringUtils.isBlank(obj[i].toString()) ? emptyReplace : obj[i].toString());
			if (i != obj.length - 1)
				str.append(split);
		}
		return str.toString();
	}

	public static String arrayToString(int[] obj, String split) {
		if (ArrayUtils.isEmpty(obj))
			return "";
		if (!StringUtils.isNotBlank(split))
			split = ",";
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < obj.length; i++) {
			str.append(obj[i]);
			if (i != obj.length - 1)
				str.append(split);
		}
		return str.toString();
	}

	public static double evalDouble(Object obj) {
		if (obj == null)
			return 0d;
		else if (StringUtils.isNotBlank(obj.toString()))
			return Double.parseDouble(obj.toString().trim());
		return 0d;
	}

	public static double evalDouble(Object obj, double defaultValue) {
		if (obj == null)
			return defaultValue;
		else if (StringUtils.isNotBlank(obj.toString()))
			return Double.parseDouble(obj.toString().trim());
		return 0d;
	}

	public static double evalFloat(Object obj) {
		if (obj == null)
			return 0f;
		else if (StringUtils.isNotBlank(obj.toString()))
			return Float.parseFloat(obj.toString().trim());
		return 0f;
	}

	public static float evalFloat(Object obj, float defaultValue) {
		if (obj == null)
			return defaultValue;
		else if (StringUtils.isNotBlank(obj.toString()))
			return Float.parseFloat(obj.toString().trim());
		return 0f;
	}

	/**
	 * 集合是否为空
	 */
	public static <T> boolean isEmpty(Collection<T> c) {
		if (c != null && c.size() > 0) {
			boolean flag = true;
			for (T t : c) {
				if (t != null) {
					flag = false;
				}
			}
			return flag;
		}
		return true;
	}

	public static <T> boolean isNotEmpty(Collection<T> c) {
		return !isEmpty(c);
	}

	public static <T> boolean isNotBlank(Collection<T> c) {
		if (c == null || c.size() == 0)
			return false;
		return true;
	}

	public static int[] evalIntArray(String[] str) {
		if (str == null || str.length == 0) {
			return null;
		}
		int[] arrs = new int[str.length];
		for (int i = 0; i < str.length; i++) {
			arrs[i] = CommonUtils.evalInt(str[i].trim(), 0);
		}
		return arrs;
	}

	public static int[] evalIntArray(Object[] str) {
		if (str == null || str.length == 0) {
			return null;
		}
		int[] arrs = new int[0];
		for (int i = 0; i < str.length; i++) {
			if (str[i] == null)
				continue;
			Integer v = CommonUtils.evalInteger(str[i]);
			if (v != null) {
				arrs = ArrayUtils.add(arrs, v);
			}
		}
		return arrs;
	}

	/**
	 * 整形转换为整形数组 e.g. 23转换为{2,3}
	 * 
	 * @param num
	 * @return
	 */
	public static int[] toIntArray(int num) {
		int i = Math.abs(num);
		int len = String.valueOf(i).length();
		int[] result = new int[len];
		int j = 0;
		while (true) {
			int temp = i % 10;
			result[j] = temp;
			i = i / 10;
			if (i == 0)
				break;
			++j;
		}
		return result;
	}

	public static Integer[] evalIntegerArray(int[] intArr) {
		if (intArr == null || intArr.length == 0) {
			return null;
		}
		Integer[] arrs = new Integer[intArr.length];
		for (int i = 0; i < intArr.length; i++) {
			arrs[i] = intArr[i];
		}
		return arrs;
	}

	public static Integer[] evalIntegerArray(String[] str) {
		if (str == null || str.length == 0) {
			return null;
		}
		Integer[] arrs = new Integer[str.length];
		for (int i = 0; i < str.length; i++) {
			if (str[i] == null)
				continue;
			arrs[i] = Integer.parseInt(str[i].trim());
		}
		return arrs;
	}

	public static Integer[] evalIntegerArray(Object[] str) {
		if (str == null || str.length == 0) {
			return null;
		}
		Integer[] arrs = new Integer[str.length];
		for (int i = 0; i < str.length; i++) {
			if (str[i] == null)
				continue;
			arrs[i] = Integer.parseInt(str[i].toString());
		}
		return arrs;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] removeEmpty(T[] t) {
		if (t == null || t.length == 0)
			return t;
		List<T> list = new ArrayList<T>();
		for (T a : t) {
			if (a != null) {
				list.add(a);
			}
		}
		Object[] result = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = (T) list.get(i);
		}
		return (T[]) result;
	}

	public static Long[] evalLongArray(String[] str) {
		Long[] arrs = new Long[str.length];
		for (int i = 0; i < str.length; i++) {
			arrs[i] = Long.parseLong(str[i].trim());
		}
		return arrs;
	}

	public static Date getCalendarField(Date paymentDate, String unit, Integer amount) {
		int field = Calendar.DAY_OF_MONTH;// 默认为day
		if ("year".equalsIgnoreCase(unit)) {
			field = Calendar.YEAR;
		} else if ("month".equalsIgnoreCase(unit)) {
			field = Calendar.MONDAY;
		} else if ("week".equalsIgnoreCase(unit)) {
			amount = amount * 7;
		} else if ("hour".equalsIgnoreCase(unit)) {
			field = Calendar.HOUR_OF_DAY;
		}
		return CommonUtils.add(paymentDate, field, amount);
	}

	public static Integer zeroToNull(Integer num) {
		return num != null && num != 0 ? num : null;
	}

	public static float getRounding(float f) {
		DecimalFormat df = new DecimalFormat("#.00");
		return NumberUtils.toFloat(df.format(f));

	}

	/** 去除小数点 */
	public static String formatFloat(Object value) {
		if (value == null)
			return null;
		DecimalFormat df = new DecimalFormat("0");
		df.setDecimalSeparatorAlwaysShown(false);
		return df.format(value);
	}

	/**
	 * 获取客户端IP(穿透CDN与代理)
	 * 
	 * @param request
	 * @return
	 */
	public static String getRealRemoteIp(HttpServletRequest request) {
		if (request == null)
			return null;
		String ip = request.getHeader("Cdn-Src-Ip");
		request.setAttribute("Cdn_Src_Ip", ip == null ? "null" : ip);
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
			request.setAttribute("x_forwarded_for", ip == null ? "null" : ip);
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			request.setAttribute("RemoteAddr", ip == null ? "null" : ip);
		}
		return ip;
	}

	public static boolean isIe(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		boolean isIe = false;
		// ie11 以上的版本去掉了MSIE，增加了特有的“rv:”
		if (header != null && (header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1)) {
			isIe = true;
		}
		return isIe;
	}

	/**
	 * 是否支持websocket
	 * 
	 * @param request
	 * @return
	 */
	public static boolean supportWebSocket(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		boolean supportWebSocket = true;
		if (header != null && header.indexOf("MSIE 6.0") > -1) {
			supportWebSocket = false;
		} else if (header != null && header.indexOf("MSIE 7.0") > -1) {
			supportWebSocket = false;
		} else if (header != null && header.indexOf("MSIE 8.0") > -1) {
			supportWebSocket = false;
		} else if (header != null && header.indexOf("MSIE 9.0") > -1) {
			supportWebSocket = false;
		}
		return supportWebSocket;
	}

	public static Calendar toCalendar(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c;
	}

	public static int getYear(Date date) {
		return toCalendar(date).get(Calendar.YEAR);
	}

	public static int getYear(Calendar c) {
		return c.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		return toCalendar(date).get(Calendar.MONTH);
	}

	public static int getMonth(Calendar c) {
		return c.get(Calendar.MONTH);
	}

	public static int getDay(Date date) {
		return toCalendar(date).get(Calendar.DAY_OF_MONTH);
	}

	public static int getDay(Calendar c) {
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getToday() {
		return toCalendar(new Date()).get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date d) {
		return toCalendar(d).get(Calendar.HOUR_OF_DAY);
	}

	public static int getHour(Calendar c) {
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Calendar c) {
		return c.get(Calendar.MINUTE);
	}

	public static String replace(String src, String regex, String... replacement) {
		if (src == null)
			return null;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(src);
		int gc = 0;
		while (matcher.find()) {
			if (replacement[gc] != null) {
				src = src.replace(matcher.group(), replacement[gc]);
			}
			++gc;
		}
		return src;
	}

	public static String colourReplace(String src, String regex, String replacement) {
		if (src == null || regex == null)
			return src;
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(src);
		return matcher.replaceAll(replacement);
		/*
		 * boolean find = matcher.find(); while(find){ String delayReplace =
		 * matcher.group(); src = src.replace(delayReplace, String.format(replacement,
		 * delayReplace)); find = matcher.find(); } return src;
		 */
	}

	/** 根据正则表达式取第几个匹配值 顺序从1开始 可以更层次匹配数据 */
	public static String getMatchValue(String src, String regex, int sequence, String[] subRegex, int[] subSeq) {
		String result = null;
		if (src == null)
			return result;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(src);
		int gc = 1;
		while (matcher.find()) {
			if (gc == sequence) {
				result = matcher.group();
				if (subRegex != null && subRegex.length > 0) {
					for (int i = 0; i < subRegex.length; i++) {
						result = getMatchValue(result, subRegex[i], subSeq[i], null, null);
					}
				}
				break;
			}
			++gc;
		}
		return result;
	}

	/**
	 * 将某个整数的第几位设置成0或者1
	 * 
	 * @param targetNum
	 *            要更改的整数
	 * @param position
	 *            整数的第几位
	 * @param resultNum
	 *            置0或者置1
	 * @return
	 */
	public static int changeBinaryVal(int targetNum, int position, int resultNum) {
		if (position <= 0 || resultNum < 0) {
			return -1;
		}
		Double en = Math.pow(2, position - 1);
		int n = en.intValue();
		int result = 0;
		if (resultNum == 0) {
			result = targetNum & ~n;
		} else if (resultNum == 1) {
			result = targetNum|n;
		}
		return result;
	}

	/**
	 * 判断是否购具有某种服务，对应处理userInfo的health字段
	 * 
	 * @param targetNum
	 *            要判断的整数
	 * @param position
	 *            整数的第几位
	 * @return
	 */
	public static boolean isBuyMail(int targetNum, int position) {
		if (position <= 0) {
			return false;
		}
		Double en = Math.pow(2, position - 1);
		int value = en.intValue();
		return (targetNum & value) == value;
	}

	/** 是否能访问指定URL */
	public static boolean access(String urlStr) {
		boolean result = false;
		if (StringUtils.isBlank(urlStr))
			return result;
		if (!StringUtils.startsWith(urlStr.toLowerCase(), "http://")) {
			urlStr = "http://" + urlStr;
		}
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			int reponseCode = connection.getResponseCode();
			if (reponseCode == HttpURLConnection.HTTP_OK || reponseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
				result = true;
			}
		} catch (Exception e) {
			return false;
		}
		return result;
	}

	public static int calcDays(Date d1, Date d2) {
		if (d1 == null || d2 == null)
			return 0;
		return (int) ((d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 将数组objects组成以分隔符separator 相隔的字符串
	 * 
	 * @param objects
	 * @param separator
	 * @return
	 */
	public static String join(Object[] objects, String separator) {
		if (objects == null || objects.length < 1)
			return null;
		if (separator == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objects.length; i++) {
			sb.append(objects[i]);
			if (i < (objects.length - 1)) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * 获取当前日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getCurDate(Date date) throws ParseException {
		return parseDateFormStr(formateDateToStr(date));
	}

	public static String getTvPhone(String phone) {
		if (StringUtils.isBlank(phone))
			return phone;
		char[] arr = phone.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if (i >= 3) {
				arr[i] = String.valueOf((int) (Math.random() * 10)).charAt(0);
			}
		}
		phone = String.valueOf(arr);
		return phone;
	}

	/** 获取指定月份总共有多少周 */
	public static int getWeeksOfMonth(Date date) {
		int result = 0;
		Calendar tmp = Calendar.getInstance();
		tmp.setTime(date);
		int curYear = tmp.get(Calendar.YEAR);
		int curMonth = tmp.get(Calendar.MONTH) + 1;
		Calendar c = Calendar.getInstance();
		for (int i = 1; i <= 6; i++) {// 一个月最多5个星期
			c.setTime(date);
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			c.set(Calendar.WEEK_OF_MONTH, i);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			if (year == curYear && month == curMonth) {
				++result;
			}
		}
		return result;
	}

	public static boolean isMonday(Date date) {
		Calendar c = Calendar.getInstance();
		String dateStr = CommonUtils.formateDateToStr(date);
		Date tmpDate = CommonUtils.parseDateFormStr(dateStr, "yyyy-MM-dd");
		long curDate = tmpDate.getTime();
		for (int i = 1; i <= 6; i++) {
			c.setTime(tmpDate);
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			c.set(Calendar.WEEK_OF_MONTH, i);
			if (c.getTime().getTime() == curDate) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据指定日期返回日期在哪个月的第几周<br/>
	 * 两个key，分别为month和week
	 * 
	 * @param date
	 * @return
	 */
	public static Map<String, Object> getWeekInfo(Date date) {
		if (date == null)
			return null;
		int week = 0;
		Calendar tmp = Calendar.getInstance();
		tmp.setTime(date);
		int curYear = tmp.get(Calendar.YEAR);
		int curMonth = tmp.get(Calendar.MONTH) + 1;
		Calendar c = Calendar.getInstance();
		String dateStr = CommonUtils.formateDateToStr(date);
		long curDate = CommonUtils.parseDateFormStr(dateStr, "yyyy-MM-dd").getTime();
		long firstWeekDate1 = 0;
		long firstWeekDate2 = 0;
		for (int i = 1; i <= 6; i++) {// 一个月最多5个星期
			c.setTimeInMillis(curDate);
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.set(Calendar.WEEK_OF_MONTH, i);
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			if (year == curYear && month == curMonth) {
				++week;
				long begin = c.getTime().getTime();
				long end = CommonUtils.add(c.getTime(), Calendar.DATE, 6).getTime();
				if (curDate >= begin && curDate <= end) {
					break;
				}
			}
			if (i == 1) {
				firstWeekDate1 = c.getTime().getTime();
				firstWeekDate2 = CommonUtils.add(c.getTime(), Calendar.DATE, 6).getTime();
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("month", CommonUtils.firstDayOfMonthToStr(date));
		result.put("week", week);
		int weeks = CommonUtils.getWeeksOfMonth(date);
		if (curDate >= firstWeekDate1 && curDate <= firstWeekDate2 && weeks == week) {
			Date month = DateUtils.addMonths(date, -1);
			int weeksTmp = CommonUtils.getWeeksOfMonth(month);
			result.put("month", CommonUtils.firstDayOfMonthToStr(month));
			result.put("week", weeksTmp);
		}
		return result;
	}

	/**
	 * 清除时分秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date clearHMS(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 把时分秒跳到23点59分59秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date fullHMS(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static boolean isNum(String string) {
		if (StringUtils.isBlank(string)) {
			return false;
		}
		try {
			Integer.valueOf(string);// 把字符串强制转换为数字
			return true;// 如果是数字，返回True
		} catch (Exception e) {
			return false;// 如果抛出异常，返回False
		}
	}

	/**
	 * 获取当前时间的星期一
	 * 
	 * @param time
	 * @return
	 */
	public static String getWeekByDate(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		String imptimeBegin = sdf.format(cal.getTime());
		System.out.println("所在周星期一的日期：" + imptimeBegin);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String firstday;
		// 获取前月的第一天
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		System.out.println(firstday);
		return imptimeBegin;
	}

	/**
	 * 获取某月所有日期(1号,2号,3号...)
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static List<String> getMonthStr(Date date) {
		int monthDays = getMonthDays(date);
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= monthDays; i++) {
			list.add(String.valueOf(i) + "号");
		}
		return list;
	}

	/**
	 * 获取某月所有日期(1号,2号,3号...)
	 * 
	 * @param source
	 *            日期字符串
	 * @param formatStr
	 *            格式化字符串,例如 'yyyy-MM-dd'
	 * @return
	 */
	public static List<String> getMonthStr(String source, String formatStr) {
		Date date = new Date();
		if (StringUtils.isNotBlank(source))
			date = CommonUtils.parseDateFormStr(source, formatStr);
		return getMonthStr(date);
	}

	/**
	 * 获取当前的月份的1号
	 * 
	 * @return
	 */
	public static String getMonthByDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 获取当前月的第一天
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		String firstday = format.format(cale.getTime());
		System.out.println(firstday);
		return firstday;
	}

	/**
	 * 获取当前月份的最后一天
	 * 
	 * @return
	 */
	public static String getMonthLastByDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		return last;
	}

	/**
	 * 获取某月第一天或最后一天
	 * 
	 * @param time
	 *            时间,格式为:yyyy-MM
	 * @param type
	 *            类型,firstDay某月第一天,lastDay某月最后一天
	 * @return
	 */
	public static String getDayType(String time, String type) {
		if (StringUtils.isBlank(type))
			return null;
		Date date = new Date();
		if (StringUtils.isNotBlank(time))
			date = CommonUtils.parseDateFormStr(time, "yyyy-MM");
		if (null == date)
			return null;
		if ("firstDay".equals(type))
			return CommonUtils.firstDayOfMonthToStr(date, "yyyy-MM-dd HH:mm:ss");
		else if ("lastDay".equals(type))
			return CommonUtils.lastDayOfMonthToStr(date, "yyyy-MM-dd HH:mm:ss");
		else
			return null;
	}

	/**
	 * 将给定字符串str左填充"0",直到str的位数等于len
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String leftPaddingZero(String str, int len) {
		// 判断str字符串是否为空或者null
		if (str != null && !"".equals(str)) {
			if (str.length() < len) {// 字符串长度小于指定长度，需要左填充
				// 1.使用字符串的格式化，先左填充空格
				String format = "%" + len + "s";
				String tempResult = String.format(format, str);

				// 2.使用String的replace函数将空格转换为指定字符即可
				String finalResult = tempResult.replace(" ", "0");

				return finalResult;
			} else {
				return str;
			}
		} else {
			return "左填充的字符串不能为空！";
		}
	}

	/**
	 * 从字符中提取指定位置的数字
	 * 
	 * @param str
	 * @param len
	 * @param start
	 * @return
	 */
	public static Integer getValueFromStr(String str, int len, int start) {
		if (null != str && str.length() >= start + len) {
			String valStr = str.substring(start, start + len);
			return Integer.parseInt(valStr);
		} else {
			return null;
		}
	}


	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static JSONObject transToLowerObject(JSONObject jsonObject) {
		if (jsonObject == null)
			return null;
		JSONObject result = new JSONObject();
		Set<Entry<String, Object>> set = jsonObject.entrySet();
		for (Iterator<Entry<String, Object>> it = set.iterator(); it.hasNext();) {
			Entry<String, Object> entry = it.next();
			result.put(StringUtils.lowerCase(entry.getKey()), entry.getValue());
		}
		return result;
	}

	/*
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/**
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisor
	 *            除数
	 * @param scale
	 *            小数点后几位(含小数点)
	 * @return
	 */
	public static double div(double dividend, double divisor, int scale) {
		BigDecimal b1 = new BigDecimal(dividend);
		BigDecimal b2 = new BigDecimal(divisor);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String getWeekToStr(Calendar cal) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		return weekDays[getWeekToInt(cal)];
	}

	public static int getWeekToInt(Calendar cal) {
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return w < 0 ? 0 : w;
	}

	public static String getWeekToStr(Date date) {
		return getWeekToStr(toCalendar(date));
	}

	public static int getWeekToInt(Date date) {
		return getWeekToInt(toCalendar(date));
	}

	public static int getDaysByYearMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.DATE, -1);
		return c.get(Calendar.DATE);
	}

	public static List<Date> dayReport(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int dayNumOfMonth = getDaysByYearMonth(year, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		List<Date> result = new ArrayList<Date>();
		for (int i = 0; i < dayNumOfMonth; i++, cal.add(Calendar.DATE, 1)) {
			result.add(cal.getTime());
		}
		return result;
	}

	public static List<Date> dayReport(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		return dayReport(c.getTime());
	}

	public static boolean compareToDate(Date source) {
		return new Date().before(source);
	}

	public static Integer getDayDiff(String minuend, String subtrahend) {
		long between_days = 0L;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(subtrahend));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(minuend));
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);
		} catch (Exception e) {
			return null;
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static Integer getDayDiff(String subtrahend) {
		return getDayDiff(todayToStr("yyyy-MM-dd"), subtrahend);
	}
	public static Integer getDayDiff(Date subtrahend) {
		return getDayDiff(formateDateToStr(subtrahend, "yyyy-MM-dd"));
	}

	public static void main(String[] args) throws Exception {
		// System.out.println(parseDateFormStr("2018-05-21 17:27:57.919811", "yyyy-MM-dd
		// HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss"));
		System.out.println(getDayDiff("2018-07-16"));
		
	}
}