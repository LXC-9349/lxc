package com.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类
 * @author DoubleLi
 * @time 2018年12月10日
 * 
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
		"yyyyMMdd","HHmmss","HH:mm:ss", "yyyy年MM月","yyyyMMdd HHmmss","yyyy年MM月dd日"};

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat orderSdf = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat timeSDF = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat timeSdf = new SimpleDateFormat("HHmmss");

	/**
	 * 返回一个date
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date paseerDateformat(String dateStr,String format) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d=null;
		try {
			d=sdf.parse(dateStr);
		} catch (Exception e) {
			throw e;
		}
		return d;
	}

	/**
	 * 由出生日期获得年龄
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
    public static  int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }
	/**
	 *
	 * 比较两个时间大小
	 * d1小于d2 为true
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean dateContrast(Date d1, Date d2) {
		if (d1.before(d2)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 
	 * @param DATE1
	 * @param DATE2
	 * @return DATE1<DATE2 :true
	 */
	public static boolean compare_date(String DATE1, String DATE2) {
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() >= dt2.getTime()) {
	                System.out.println("dt1 在dt2前");
	                return true;
	            } else if (dt1.getTime() < dt2.getTime()) {
	                System.out.println("dt1在dt2后");
	                return false;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return false;
	}
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 *
	 * @param date
	 * @param index 日期显示格式的下标 "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM","yyyyMMdd","HHmmss","HH:mm:ss"
	 * @return
	 */
	public static String formatDate(Date date,int index) {
		return DateFormatUtils.format(date, parsePatterns[index]);
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 * * @param index 日期显示格式的下标 "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM","yyyyMMdd","HHmmss","HH:mm:ss"
	 * @return
	 */
	public static String formatDate2String(Date date,int index) {
		SimpleDateFormat dateSdf = new SimpleDateFormat(parsePatterns[index]);
		return dateSdf.format(date);
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String timeStamp2Date(String seconds, String format) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		if (format == null || format.isEmpty()) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds + "000")));
	}
	public static void main(String[] args) {
		System.out.println(timeStamp2Date("",null));
	}
	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}

	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

	/**
	 * 获取两个日期之间的天数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
 
	/**
	 * 格式化日期
	 *
	 * @param date
	 * @return
	 */
	public static String formatDate2String(Date date) {
		return dateSdf.format(date);
	}

	public static String formatDate2String2(Date date) {
		return sdf.format(date);
	}

	public static String formatDate2String3(Date date) {
		return orderSdf.format(date);
	}

	public static String formatDate2String4(Date date) {
		return shortSdf.format(date);
	}

	public static boolean judgePass24Hour(Date startTime, Date endTime) {
		long differ = endTime.getTime() - startTime.getTime();
		double result = differ * 1.0 / (1000 * 60 * 60);
		if (result <= 24) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断时间间隔是否超过六个月
	 *
	 * @param startTime
	 * @return
	 */
	public static boolean isExpire(Date startTime) {
		if (startTime == null) {
			return true;
		}

		long len = System.currentTimeMillis() - startTime.getTime();
		// long limitTime = 6*30*24*60*60*1000;
		long limitTime = 1627869184;// 六个月的毫秒数
		// return len - 1000 > 0 ? true : false;
		return (len - limitTime) > 0 ? true : false;
	}

	/**
	 * 判断时间间隔是否超过一年
	 *
	 * @param startTime
	 * @return
	 */
	public static boolean isExpire2(Date startTime){
		if(startTime == null){
			return true;
		}
		long len = System.currentTimeMillis() - startTime.getTime();
		long limitTime = 12L*30L*24L*60L*60L*1000L;
//		return len - 1000 > 0 ? true : false;
		return (len - limitTime) > 0 ? true : false;
	}

/*	*//**
	 * @param args
	 * @throws ParseException
	 *//*
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
	//	System.out.println(parseDate("2010/3/6",parsePatterns));
		System.out.println(getNextDay("2015-12-15",-3));
		System.out.println(formatDate2String(new Date(),12));
	}
	*/
	public static String formatDate3(Date date) {
		return DateFormatUtils.format(date, parsePatterns[5]);
	}
	/**
	 * 获取时间的多少天之前或之后
	 * @param date
	 * @param day
	 * @return
	 */
	public static String getNextDay(String date,int day) {
		if(date==null||"".equals(date)){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parseDate(date));
		calendar.add(Calendar.DAY_OF_MONTH, day);
		Date time = calendar.getTime();
		return formatDate(time,0);
	}
	/**
	 * 间隔年
	 * @param date
	 * @param day
	 * @return
	 * @time 2018年12月25日
	 * @author DoubleLi
	 */
	public static String getNextYear(String date,int year) {
		if(date==null||"".equals(date)){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parseDate(date));
		calendar.add(Calendar.YEAR, year);
		Date time = calendar.getTime();
		return formatDate(time,0);
	}
	/**
	 * 获取季度
	 * @param date
	 * @return
	 */
	public static String getQuarter(Date date) {
		Integer season = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
		case Calendar.JANUARY:
		case Calendar.FEBRUARY:
		case Calendar.MARCH:
			season = 1;
			break;
		case Calendar.APRIL:
		case Calendar.MAY:
		case Calendar.JUNE:
			season = 2;
			break;
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.SEPTEMBER:
			season = 3;
			break;
		case Calendar.OCTOBER:
		case Calendar.NOVEMBER:
		case Calendar.DECEMBER:
			season = 4;
			break;
		default:
			break;
		}
		return season.toString();
	}

	/**
	* 获取某年某月的最后一天
	* @param year 年
	* @param month 月
	* @return 最后一天
	*/
	public static String getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return "31";
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return "30";
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return "29";
			} else {
				return "28";
			}
		}
		return "0";
	}

	/**
	 * 是否闰年
	 *
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	/**
	 * 获取前一天
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 设置给出的Date的时间部分为：00:00:00.000
	 * @param date
	 * @return
	 */
	public static Date setTimeZero(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 每次加18个月
	 * @return
	 */
	public static String yearLaterDate(){
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
	    calendar.add(Calendar.MONTH, 18);
	    date = calendar.getTime();
	    return DateUtils.formatDate(date, 12);
	}

	/**
	 * @param month
	 * @return
	 * @time 2018年10月26日
	 * @author DoubleLi
	 */
	public static String DateMonth(int month){
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
	    calendar.add(Calendar.MONTH, month);
	    date = calendar.getTime();
	    return DateUtils.formatDate(date, 2);
	}
	/**
	 * 年
	 * @return
	 */
	public static Date closeYearDate(Date date,int number){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
	    calendar.add(Calendar.YEAR, number);
	    date = calendar.getTime();
	    return date;
	}

	/**
	 * 月
	 * @return
	 */
	public static Date closeMonthDate(Date date,int number){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
	    calendar.add(Calendar.MONTH, number);
	    date = calendar.getTime();
	    return date;
	}

	/**
	 * 日
	 * @return
	 */
	public static Date closeDayDate(Date date,int number){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
	    calendar.add(Calendar.DAY_OF_MONTH, number);
	    date = calendar.getTime();
	    return date;
	}

	public static Date setTimeMax(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		calendar.set(Calendar.MILLISECOND, 999);
		date = calendar.getTime();
		return date;
	}

	public static boolean isSameDate(Date date1, Date date2) {
		try {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);

			boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
			boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);//年-月
			//boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);//年-月-日

			return isSameMonth;
		} catch (Exception e) {
			return false;
		}
    }

	public static String secondToTime(long a) throws ParseException {
		long hour = a / 3600; // 小时
		long minute = a % 3600 / 60; // 分钟
		long second = a % 60; // 秒
		String t=hour + ":" + minute + ":" + second;
		return timeSDF.format(timeSDF.parse(t));
	}
	/*public static void main(String[] args) {
		try {
			System.out.println(secondToTime(180));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}*/


	/**
	 * 根据时间获取上一个月最后一天
	 * @param month
	 * @time 2018年9月25日
	 * @author DoubleLi
	 * @return 
	 */
	public static String getbeforeMonthday(String... month) throws Exception {
		Date time=new Date();
		if(month!=null&&month.length>0){
			time=paseerDateformat(month[0],"yyyy-MM");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateSdf.format(calendar.getTime());
 
	}

	/**
	 * 当月最后一天
	 * @param month
	 * @return
	 * @throws Exception
	 * @time 2018年12月25日
	 * @author DoubleLi
	 */
	public static String getMonthday(String... month) throws Exception {
		Date time=new Date();
		if(month!=null&&month.length>0){
			time=paseerDateformat(month[0],"yyyy-MM");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateSdf.format(calendar.getTime());
	}
	
	/**
	 * 根据时间获取下一个月第一天
	 * @param month
	 * @time 2018年9月25日
	 * @author DoubleLi
	 * @return 
	 */
	public static String getlastMonthday(String... month) throws Exception  {
		Date time=new Date();
		if(month!=null&&month.length>0){
			time=paseerDateformat(month[0],"yyyy-MM");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateSdf.format(calendar.getTime());
	}
	
	/**
	 * 周一
	 * @param date
	 * @return
	 * @time 2018年10月29日
	 * @author DoubleLi
	 */
	public static Date getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}
	
	/**
	 * 当月天数
	 * @param date
	 * @return
	 * @time 2018年12月24日
	 * @author DoubleLi
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}
