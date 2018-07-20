package com.jszx.spider.platform.tool;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 日期工具类:提供日志获取，格式化，比较等功能
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年9月8日 上午11:50:26
 *
 */
public class DateTool {

	public final static String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

	public final static String FORMAT_DATE = "yyyy-MM-dd";

	/**
	 * 
	 * [方法描述]
	 * 
	 * @param c
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年9月8日 上午11:54:45
	 */
	public static int getYear(Calendar c) {
		if (c != null) {
			return c.get(Calendar.YEAR);
		} else {
			return Calendar.getInstance().get(Calendar.YEAR);
		}
	}

	/**
	 * 得到月份 注意，这里的月份依然是从0开始的
	 * 
	 * @param c
	 * @return
	 */

	/**
	 * 
	 * 方法描述:
	 * 
	 * @author lv_juntao@uisftech.com
	 * @param c
	 * @return 描述******
	 * @return int
	 */

	public static int getMonth(Calendar c) {
		if (c != null) {
			return c.get(Calendar.MONTH);
		} else {
			return Calendar.getInstance().get(Calendar.MONTH);
		}
	}

	/**
	 * 得到日期
	 * 
	 * @param c
	 * @return
	 */
	public static int getDate(Calendar c) {
		if (c != null) {
			return c.get(Calendar.DATE);
		} else {
			return Calendar.getInstance().get(Calendar.DATE);
		}
	}

	/**
	 * 得到星期
	 * 
	 * @param c
	 * @return
	 */
	public static int getDay(Calendar c) {
		if (c != null) {
			return c.get(Calendar.DAY_OF_WEEK);
		} else {
			return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		}
	}

	/**
	 * 得到小时
	 * 
	 * @param c
	 * @return
	 */
	public static int getHour(Calendar c) {
		if (c != null) {
			return c.get(Calendar.HOUR);
		} else {
			return Calendar.getInstance().get(Calendar.HOUR);
		}
	}

	/**
	 * 得到分钟
	 * 
	 * @param c
	 * @return
	 */
	public static int getMinute(Calendar c) {
		if (c != null) {
			return c.get(Calendar.MINUTE);
		} else {
			return Calendar.getInstance().get(Calendar.MINUTE);
		}
	}

	/**
	 * 得到秒
	 * 
	 * @param c
	 * @return
	 */
	public static int getSecond(Calendar c) {
		if (c != null) {
			return c.get(Calendar.SECOND);
		} else {
			return Calendar.getInstance().get(Calendar.SECOND);
		}
	}

	/**
	 * 
	 * 得到指定或者当前时间前n天的Calendar
	 * 
	 * @param c
	 * @param n
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年9月8日 上午11:55:04
	 */
	public static Calendar beforeNDays(Calendar c, int n) {
		// 偏移量，给定n天的毫秒数
		long offset = n * 24 * 60 * 60 * 1000L;
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
		return calendar;
	}

	/**
	 * 得到指定或者当前时间后n天的Calendar
	 * 
	 * @param c
	 * @param n
	 * @return
	 */
	public static Calendar afterNDays(Calendar c, int n) {
		// 偏移量，给定n天的毫秒数
		long offset = n * 24 * 60 * 60 * 1000L;
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() + offset);
		return calendar;
	}

	/**
	 * 昨天
	 * 
	 * @param c
	 * @return
	 */
	public static Calendar yesterday(Calendar c) {
		long offset = 1 * 24 * 60 * 60 * 1000L;
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
		return calendar;
	}

	/**
	 * 明天
	 * 
	 * @param c
	 * @return
	 */
	public static Calendar tomorrow(Calendar c) {
		long offset = 1 * 24 * 60 * 60 * 1000L;
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() + offset);
		return calendar;
	}

	/**
	 * 得到指定或者当前时间前offset毫秒的Calendar
	 * 
	 * @param c
	 * @param offset
	 * @return
	 */
	public static Calendar before(Calendar c, long offset) {
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
		return calendar;
	}

	/**
	 * 得到指定或者当前时间前offset毫秒的Calendar
	 * 
	 * @param c
	 * @param offset
	 * @return
	 */
	public static Calendar after(Calendar c, long offset) {
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}

		calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
		return calendar;
	}

	/**
	 * Date类型转换到Calendar类型
	 * 
	 * @param d
	 * @return
	 */
	public static Calendar Date2Calendar(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c;
	}

	/**
	 * Calendar类型转换到Date类型
	 * 
	 * @param c
	 * @return
	 */
	public static Date Calendar2Date(Calendar c) {
		return c.getTime();
	}

	/**
	 * Date类型转换到Timestamp类型
	 * 
	 * @param d
	 * @return
	 */
	public static Timestamp Date2Timestamp(Date d) {
		return new Timestamp(d.getTime());
	}

	/**
	 * Calendar类型转换到Timestamp类型
	 * 
	 * @param c
	 * @return
	 */
	public static Timestamp Calendar2Timestamp(Calendar c) {
		return new Timestamp(c.getTimeInMillis());
	}

	/**
	 * Timestamp类型转换到Calendar类型
	 * 
	 * @param ts
	 * @return
	 */
	public static Calendar Timestamp2Calendar(Timestamp ts) {
		Calendar c = Calendar.getInstance();
		c.setTime(ts);
		return c;
	}

	/**
	 * 
	 * 获取当前日期
	 * 
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月12日 下午3:59:26
	 */
	public static Date getDate() {
		try {
			return Calendar.getInstance().getTime();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * 返回string类型时间
	 * 
	 * @param format
	 * @return
	 * @author zheng_song@uisftech.com
	 * @date 2016年10月10日 上午9:52:52
	 */
	public static String getCurrentDate(String format) {
		if (format.isEmpty()) {
			format = FORMAT_TIME;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * 
	 * 返回string类型时间
	 * 
	 * @param format
	 * @return
	 * @author zheng_song@uisftech.com
	 * @date 2016年10月10日 上午9:52:52
	 */
	public static String getCurrentDate(String format, Date date) {
		if (format.isEmpty()) {
			format = FORMAT_TIME;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 
	 * 返回string类型时间
	 * 
	 * @param format
	 * @return
	 * @author zheng_song@uisftech.com
	 * @date 2016年10月10日 上午9:52:52
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 标准日期格式字符串解析成Calendar对象
	 * 
	 * @param s
	 * @return
	 */
	public static Calendar parse2Calender(String s) {
		Timestamp ts = Timestamp.valueOf(s);
		return Timestamp2Calendar(ts);
	}

	/**
	 * 标准日期格式字符串解析成Calendar对象
	 * 
	 * @param s
	 * @return
	 */
	public static String Timestamp2String(Timestamp ts, String format) {
		DateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(ts);
	}

	/**
	 * 标准日期格式字符串解析成Calendar对象
	 * 
	 * @param s
	 * @return
	 */
	public static String Timestamp2String(Timestamp ts) {
		DateFormat sdf = new SimpleDateFormat(DateTool.FORMAT_TIME);
		return sdf.format(ts);
	}

	public static Timestamp String2Timestamp(String date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		if (date.indexOf("/") > -1) {
			date = date.replaceAll("/", "-");
		}
		if (date.length() == 10) {
			date += " 00:00:00";
		}
		return Timestamp.valueOf(date);// 转换时间字符串为Timestamp
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	public static String TimsToTimesDes1(Date dateFrom, Date dateTo) {
		long l = dateTo.getTime() - dateFrom.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = l / (60 * 60 * 1000);
		long min = l / (60 * 1000);
		long s = l / 1000;
		if (day >= 1)
			return day + "天前";
		else if (hour >= 1)
			return hour + "小时前";
		else if (min >= 1)
			return min + "分钟前";
		else
			return s + "秒钟前";
	}

	public static String TimsToTimesDes2(Date dateFrom) {
		Integer cdate = Integer.parseInt(getCurrentDate("yyyyMMdd"));
		Integer pdate = Integer.parseInt(getCurrentDate("yyyyMMdd", dateFrom));
		Integer day = cdate - pdate;
		if (day >= 7)
			return format(dateFrom, "MM-dd");
		else if (day > 2)
			return getWeekOfDate(dateFrom);
		else if (day == 2)
			return "前天";
		else if (day == 1)
			return "昨天";
		else
			return format(dateFrom, "HH:mm");
	}

	/**
	 * 按分鐘加日期
	 * 
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param formatString
	 * @return
	 */
	public static String format(Date date, String formatString) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(formatString);
		return df.format(date);
	}

	/**
	 * 参数日期距离当前日期天数
	 * 
	 * @param bdate
	 *            参数日期
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date bdate, String formatString) {
		try {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(formatString);
			today = sdf.parse(sdf.format(today));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 转换成日期
	 * 
	 * @param dateString
	 * @param formatString
	 * @return
	 */
	public static Date parse(String dateString, String formatString) {
		SimpleDateFormat df = new SimpleDateFormat(formatString);
		try {
			return df.parse(dateString);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 日期转换数值类型
	 * 
	 * @param str
	 * @return 数值型日期
	 */

	public static Long DateToNumeric(String str) {
		Long result = 0l;
		try {
			str = str.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
			result = Long.parseLong(str);
		} catch (Exception e) {
			System.out.println("格式：" + str);
		}
		return result;
	}

	/**
	 * 两个时间段是否有重叠
	 * 
	 * @param date1Begin
	 * @param date1End
	 * @param date2Begin
	 * @param date2End
	 * @return
	 */
	public static boolean isOverlap(Date rangBegin, Date rangEnd, Date dateBegin, Date dateEnd) {
		if ((rangEnd.compareTo(dateBegin) < 0 && rangEnd.compareTo(dateEnd) < 0) || (rangBegin.compareTo(dateBegin) > 0
					&& rangBegin.compareTo(dateEnd) > 0)) {
			return false;
		}
		return true;
	}

	/**
	 * 按日加天数
	 * 
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return calendar.getTime();
	}

	/**
	 * 日期之间的毫秒数
	 * 
	 * @param dateFrom
	 *            起始日期 dateTo 截止日期
	 * @return Long 毫秒数
	 */
	public static Long minusDate(Date dateFrom, Date dateTo) {
		Long minus = dateTo.getTime() - dateFrom.getTime();
		return minus;
	}

}
