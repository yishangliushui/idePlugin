package org.yishang.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


/**
 * Description:日期时间操作的工具类
 *
 * @author sunxing
 */
public class DateUtil {
	/**
	 * 日期格式(yyyy-MM-dd)
	 */
	public static final String YYYY_MM_DD_EN = "yyyy-MM-dd";
	/**
	 * 日期格式(yyyyMMdd)
	 */
	public static final String YYYYMMDD_EN = "yyyyMMdd";
	/**
	 * 日期格式(yyyy-MM)
	 */
	public static final String YYYY_MM_EN = "yyyy-MM";
	/**
	 * 日期格式(yyyyMM)
	 */
	public static final String YYYYMM_EN = "yyyyMM";
	/**
	 * 日期格式(yyyy-MM-dd HH:mm:ss)
	 */
	public static final String YYYY_MM_DD_HH_MM_SS_EN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式(yyyy-MM-dd HH:mm:ss)
	 */
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式(yyyyMMddHHmmss)
	 */
	public static final String YYYYMMDDHHMMSS_EN = "yyyyMMddHHmmss";
	/**
	 * 日期格式(MMddHHmmss)
	 */
	public static final String MMDDHHMMSS_EN = "MMddHHmmss";
	/**
	 * 日期格式(yyyy年MM月dd日)
	 */
	public static final String YYYY_MM_DD_CN = "yyyy年MM月dd日";
	/**
	 * 日期格式(yyyy年MM月dd日HH时mm分ss秒)
	 */
	public static final String YYYY_MM_DD_HH_MM_SS_CN = "yyyy年MM月dd日HH时mm分ss秒";
	/**
	 * 日期格式(yyyy年MM月dd日HH时mm分)
	 */
	public static final String YYYY_MM_DD_HH_MM_CN = "yyyy年MM月dd日HH时mm分";
	/**
	 * 一天开始时间
	 */
	public static final String TIME_START_STR = " 00:00:00";
	/**
	 * 一天结束时间
	 */
	public static final String TIME_END_STR = " 23:59:59";
	/**
	 * 日期格式(HH:mm:ss)
	 */
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String HHMMSS = "HHmmss";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DateUtil.class);
	/**
	 * DateFormat缓存
	 */
	private static Map<String, DateFormat> dateFormatMap = new HashMap<String, DateFormat>();

	private DateUtil() {

	}

	/**
	 * 获取DateFormat
	 *
	 * @param dateTimeStr
	 * @param formatStr
	 * @return
	 */
	public static DateFormat getDateFormat(String formatStr) {
		DateFormat df = dateFormatMap.get(formatStr);
		if (df == null) {
			df = new SimpleDateFormat(formatStr);
			dateFormatMap.put(formatStr, df);
		}
		return df;
	}

	/**
	 * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
	 *
	 * @param dateTimeStr
	 * @param formatStr
	 * @return
	 */
	public static Date getDate(String dateTimeStr, String formatStr) {
		try {
			if (dateTimeStr == null || "".equals(dateTimeStr)) {
				return null;
			}
			DateFormat sdf = DateUtil.getDateFormat(formatStr);
			return sdf.parse(dateTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转化dateTimeStr为Date类型
	 *
	 * @param dateTimeStr
	 * @param formatStr
	 * @return
	 */
	public static Date convertDate(String dateTimeStr) {
		try {
			if (dateTimeStr == null || "".equals(dateTimeStr)) {
				return null;
			}
			DateFormat sdf = DateUtil.getDateFormat(YYYY_MM_DD_EN);
			return sdf.parse(dateTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 按照默认显示日期时间的格式"yyyy-MM-dd"，转化dateTimeStr为Date类型
	 * dateTimeStr必须是"yyyy-MM-dd"的形式
	 *
	 * @param dateTimeStr
	 * @return
	 */
	public static Date getDate(String dateTimeStr) {
		return getDate(dateTimeStr, YYYY_MM_DD_EN);
	}

	/**
	 * 将Timestamp时间转为指定格式的字符串
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getTimestampStr(Timestamp timestamp, String pattern) {
		if (null == timestamp) {
			return "";
		}

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timestamp.getTime());
		return new SimpleDateFormat(pattern).format(c.getTime());
	}

	/**
	 * 将YYYYMMDD转换成Date日期
	 *
	 * @param date
	 * @return
	 * @throws BusinessException
	 */
	public static Date transferDate(String date) {
		if (StringUtils.isEmpty(date) || date.length() != 8) {
			throw new RuntimeException("日期格式错误");
		}
		String con = "-";
		String yyyy = date.substring(0, 4);
		String mm = date.substring(4, 6);
		String dd = date.substring(6, 8);
		int month = Integer.parseInt(mm);
		int day = Integer.parseInt(dd);
		if (month < 1 || month > 12 || day < 1 || day > 31) {
			throw new RuntimeException("日期格式错误");
		}
		String str = yyyy + con + mm + con + dd;
		return DateUtil.getDate(str, DateUtil.YYYY_MM_DD_EN);
	}

	/**
	 * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String dateToDateString(Date date) {
		return dateToDateString(date, YYYY_MM_DD_HH_MM_SS_EN);
	}

	/**
	 * 将Date转换成formatStr格式的字符串
	 *
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateToDateString(Date date, String formatStr) {
		DateFormat df = getDateFormat(formatStr);
		return df.format(date);
	}

	/**
	 * 将String转换成formatStr格式的字符串
	 *
	 * @param dateTime
	 * @param formatStr1
	 * @param formatStr2
	 * @return
	 */
	public static String stringToDateString(String date, String formatStr1,
	                                        String formatStr2) {
		Date d = getDate(date, formatStr1);
		DateFormat df = getDateFormat(formatStr2);

		return df.format(d);
	}

	/**
	 * 获取当前日期yyyy-MM-dd的形式
	 *
	 * @return
	 */
	public static String getCurDate() {
		return dateToDateString(new Date(), YYYY_MM_DD_EN);
	}

	/**
	 * 获取当前日期yyyy-MM-dd的形式
	 *
	 * @return
	 */
	public static String getCurDateByPattern(String pattern) {
		return dateToDateString(new Date(), pattern);
	}

	/***
	 * 获取昨天的日期
	 *
	 * @return yyyyMMdd格式
	 */
	public static String getYestdayDate() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DATE, -1);
		return dateToDateString(currentDate.getTime(), DateUtil.YYYYMMDD_EN);
	}


	/**
	 * 获取指定日期的前一天日期
	 *
	 * @param date 指定日期  Date类型
	 * @return 前一天日期 Date类型
	 */
	public static Date getYestdayDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}


	/**
	 * 获取昨天日期时间yyyy-MM-dd HH:mm:ss的形式
	 *
	 * @return
	 */
	public static String getYestDayDateTime() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DATE, -1);
		return dateToDateString(currentDate.getTime(), YYYY_MM_DD_HH_MM_SS_EN);
	}

	/***
	 * 获取当天的开始时间
	 *
	 * @return
	 */
	public static String getCurBeginDate() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return dateToDateString((Date) currentDate.getTime().clone());
	}

	/***
	 * 获取当天的结束时间
	 *
	 * @return
	 */
	public static String getCurEndDate() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		return dateToDateString((Date) currentDate.getTime().clone());
	}

	/***
	 * 获取昨天的开始时间
	 *
	 * @return
	 */
	public static String getYestDayBeginDate() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DATE, -1);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return dateToDateString((Date) currentDate.getTime().clone());
	}

	/***
	 * 获取昨天的结束时间
	 *
	 * @return
	 */
	public static String getYestDayEndDate() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DATE, -1);
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		return dateToDateString((Date) currentDate.getTime().clone());
	}

	/***
	 * 获取当前月的第一天
	 *
	 * @return
	 */
	public static String getCurMonthFirstDay() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.DAY_OF_MONTH, 1);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return dateToDateString((Date) currentDate.getTime().clone());
	}

	// 获取7天前日期
	public static String getWeekBeforeDay() {
		DateFormat df = new SimpleDateFormat("yyyy-M-d");
		String time = df.format(new Date().getTime() - 7 * 24 * 60 * 60 * 1000);
		return time;
	}

	/***
	 * 获取上一个月的第一天
	 *
	 * @return
	 */
	public static String getLastMonthFirstDay() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MONTH, -1);
		currentDate.set(Calendar.DAY_OF_MONTH, 1);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return dateToDateString((Date) currentDate.getTime().clone());
	}

	/**
	 * 获取上个月的最后一天
	 *
	 * @return YYYY-MM-DD HH:mm:ss格式
	 */
	public static String getLastMonthLastDay() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.DATE, 0);
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		return dateToDateString((Date) currentDate.getTime().clone());
	}

	/**
	 * 获取当前月的最后一天
	 *
	 * @return
	 */
	public static String getCurMonthLastDay() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.DATE, 1);
		currentDate.roll(Calendar.DATE, -1);
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		return dateToDateString((Date) currentDate.getTime().clone());
	}

	/**
	 * 获取当前日期yyyy年MM月dd日的形式
	 *
	 * @return
	 */
	public static String getCurCNDate() {
		return dateToDateString(new Date(), YYYY_MM_DD_CN);
	}

	/**
	 * 获取当前日期时间yyyy-MM-dd HH:mm:ss的形式
	 *
	 * @return
	 */
	public static String getCurDateTime() {
		return dateToDateString(new Date(), YYYY_MM_DD_HH_MM_SS_EN);
	}

	/**
	 * 获取当前日期时间yyyyMMddHHmmss的形式
	 *
	 * @return
	 */
	public static String getCurDateTimes() {
		return dateToDateString(new Date(), YYYYMMDDHHMMSS_EN);
	}

	/**
	 * 获取当前日期时间MMddHHmmss的形式
	 *
	 * @return
	 */
	public static String getCurDateMmTimes() {
		return dateToDateString(new Date(), MMDDHHMMSS_EN);
	}

	/**
	 * 获取当前日期时间yyyyMMdd的形式
	 *
	 * @return
	 */
	public static String getCurDates() {
		return dateToDateString(new Date(), YYYYMMDD_EN);
	}

	/**
	 * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
	 *
	 * @return
	 */
	public static String getCurZhCNDateTime() {
		return dateToDateString(new Date(), YYYY_MM_DD_HH_MM_SS_CN);
	}

	/**
	 * 比较两个"yyyy-MM-dd HH:mm:ss"格式的日期，之间相差多少毫秒,time2-time1
	 *
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long compareDateStr(String time1, String time2) {
		Date d1 = getDate(time1);
		Date d2 = getDate(time2);
		return d2.getTime() - d1.getTime();
	}

	/**
	 * 将小时数换算成返回以毫秒为单位的时间
	 *
	 * @param hours
	 * @return
	 */
	public static long getMicroSec(BigDecimal hours) {
		BigDecimal bd;
		bd = hours.multiply(new BigDecimal(3600 * 1000));
		return bd.longValue();
	}

	/**
	 * 将String(yyyy-MM-dd HH:mm:ss)转换成timestamp
	 *
	 * @param date
	 * @return
	 */
	public static Timestamp stringToTimestamp(String formatStr) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		SimpleDateFormat df1 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_EN);
		try {
			Date date = df1.parse(formatStr);
			String time = df1.format(date);
			ts = Timestamp.valueOf(time);

		} catch (ParseException e) {
			LOGGER.error("DateUtil.stringToTimestamp异常", e);
		}
		return ts;
	}

	/**
	 * 获取当前日期years年后的一个(formatStr)的字符串
	 *
	 * @param months
	 * @param formatStr
	 * @return
	 */
	public static String getDateStringOfYear(int years, String formatStr) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(new Date());
		now.add(Calendar.YEAR, years);
		return dateToDateString(now.getTime(), formatStr);
	}

	/**
	 * 获取当前日期mon月后的一个(formatStr)的字符串
	 *
	 * @param months
	 * @param formatStr
	 * @return
	 */
	public static String getDateStringOfMon(int months, String formatStr) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(new Date());
		now.add(Calendar.MONTH, months);
		return dateToDateString(now.getTime(), formatStr);
	}

	/**
	 * 获取当前日期days天后的一个(formatStr)的字符串
	 *
	 * @param days
	 * @param formatStr
	 * @return
	 */
	public static String getDateStringOfDay(int days, String formatStr) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(new Date());
		now.add(Calendar.DATE, days);
		return dateToDateString(now.getTime(), formatStr);
	}

	/**
	 * 获取当前日期hours小时后的一个(formatStr)的字符串
	 *
	 * @param hours
	 * @param formatStr
	 * @return
	 */
	public static String getDateStringOfHour(int hours, String formatStr) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(new Date());
		now.add(Calendar.HOUR_OF_DAY, hours);
		return dateToDateString(now.getTime(), formatStr);
	}

	/**
	 * 获取指定日期mon月后的一个(formatStr)的字符串
	 *
	 * @param date
	 * @param mins
	 * @param formatStr
	 * @return
	 */
	public static String getDateOfMon(String date, int mon, String formatStr) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(DateUtil.getDate(date, formatStr));
		now.add(Calendar.MONTH, mon);
		return dateToDateString(now.getTime(), formatStr);
	}

	/**
	 * 获取指定日期day天后的一个(formatStr)的字符串
	 *
	 * @param date
	 * @param mins
	 * @param formatStr
	 * @return
	 */
	public static String getDateOfDay(String date, int day, String formatStr) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(DateUtil.getDate(date, formatStr));
		now.add(Calendar.DATE, day);
		return dateToDateString(now.getTime(), formatStr);
	}

	/**
	 * 获取指定日期mins分钟后的一个(formatStr)的字符串
	 *
	 * @param date
	 * @param mins
	 * @param formatStr
	 * @return
	 */
	public static String getDateOfMin(String date, int mins, String formatStr) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(DateUtil.getDate(date, formatStr));
		now.add(Calendar.SECOND, mins * 60);
		return dateToDateString(now.getTime(), formatStr);
	}

	/**
	 * 获取指定日期mins分钟后的一个日期
	 *
	 * @param date
	 * @param mins
	 * @param formatStr
	 * @return
	 */
	public static Date getDateOfMin(Date date, int mins) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(date);
		now.add(Calendar.SECOND, mins * 60);
		return now.getTime();
	}

	/**
	 * 获取当前日期mins分钟后的一个(formatStr)的字符串
	 *
	 * @param mins
	 * @param formatStr
	 * @return
	 */
	public static String getDateStringOfMin(int mins, String formatStr) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(new Date());
		now.add(Calendar.MINUTE, mins);
		return dateToDateString(now.getTime(), formatStr);
	}

	/**
	 * 获取当前日期mins分钟后的一个日期
	 *
	 * @param mins
	 * @return
	 */
	public static Date getDateOfMin(int mins) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(new Date());
		now.add(Calendar.MINUTE, mins);
		return now.getTime();
	}

	/**
	 * 获取当前日期sec秒后的一个(formatStr)的字符串
	 *
	 * @param sec
	 * @param formatStr
	 * @return
	 */
	public static String getDateStringOfSec(int sec, String formatStr) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(new Date());
		now.add(Calendar.SECOND, sec);
		return dateToDateString(now.getTime(), formatStr);
	}

	/**
	 * 获得指定日期月份的天数
	 *
	 * @return
	 */
	public static int getMonthDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 获得系统当前月份的天数
	 *
	 * @return
	 */
	public static int getCurentMonthDay() {
		Date date = Calendar.getInstance().getTime();
		return getMonthDay(date);
	}

	/**
	 * 获得指定日期月份的天数 yyyy-mm-dd
	 *
	 * @return
	 */
	public static int getMonthDay(String date) {
		Date strDate = getDate(date, YYYY_MM_DD_EN);
		return getMonthDay(strDate);
	}

	/**
	 * 获取19xx,20xx形式的年
	 *
	 * @param d
	 * @return
	 */
	public static int getYear(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.YEAR);
	}

	/**
	 * 获取月份，1-12月
	 *
	 * @param d
	 * @return
	 */
	public static int getMonth(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.MONTH) + 1;
	}

	public static String getMonth(int m) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.add(Calendar.MONTH, m);
		return dateToDateString(now.getTime(), YYYY_MM_DD_EN);
	}

	/**
	 * 获取xxxx-xx-xx的日
	 *
	 * @param d
	 * @return
	 */
	public static int getDay(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取Date中的小时(24小时)
	 *
	 * @param d
	 * @return
	 */
	public static int getHour(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取Date中的分钟
	 *
	 * @param d
	 * @return
	 */
	public static int getMin(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.MINUTE);
	}

	/**
	 * 获取Date中的秒
	 *
	 * @param d
	 * @return
	 */
	public static int getSecond(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.SECOND);
	}

	/**
	 * 得到本周周一
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		c.add(Calendar.DATE, -dayOfWeek + 1);
		return dateToDateString(c.getTime(), YYYY_MM_DD_EN);
	}

	/**
	 * 得到上周周一
	 *
	 * @return yyyy-MM-dd
	 */
	public static String geLastWeekMonday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(convertDate(getMondayOfThisWeek()));
		cal.add(Calendar.DATE, -7);
		return dateToDateString(cal.getTime(), YYYY_MM_DD_EN);
	}

	/**
	 * 得到本周周日
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		c.add(Calendar.DATE, -dayOfWeek + 7);
		return dateToDateString(c.getTime());
	}

	/**
	 * 得到本周周(*)
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getDayOfThisWeek(int num) {
		Calendar c = Calendar.getInstance();
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		c.add(Calendar.DATE, -dayOfWeek + num);
		return dateToDateString(c.getTime(), YYYY_MM_DD_EN);
	}

	/**
	 * 得到本月指定天
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getDayOfThisMoon(String num) {
		String date = dateToDateString(new Date(), YYYY_MM_EN);
		return date + "-" + num;
	}

	/***
	 * 得到本年本月
	 *
	 * @param num
	 * @return
	 */
	public static String getCurYearMonth() {
		String date = dateToDateString(new Date(), YYYY_MM_EN);
		return date;
	}

	/***
	 * 得到本年本月
	 *
	 * @param num
	 * @return
	 */
	public static String getCurYearMonthByYYYYMM() {
		String date = dateToDateString(new Date(), YYYYMM_EN);
		return date;
	}

	/***
	 * 得到上年本月
	 *
	 * @param num
	 * @return
	 */
	public static String getCurYearLastMonth() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.YEAR, -1);
		return dateToDateString(currentDate.getTime(), YYYY_MM_EN);
	}

	/**
	 * 获取两个日期相差的天数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getQuotByDays(String beginDate, String endDate) {
		long quot = 0;
		DateFormat df = getDateFormat(YYYY_MM_DD_EN);
		try {
			Date d1 = df.parse(beginDate);
			Date d2 = df.parse(endDate);
			quot = d2.getTime() - d1.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	/**
	 * 根据日期追加的天数，得到一个新日期
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static String getDateAddDay(String date, int days) {
		DateFormat df = getDateFormat(YYYY_MM_DD_EN);
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(date));
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + days);

			return df.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDateAddDay(String date, int days, String pattern) {
		DateFormat df = getDateFormat(pattern);
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(date));
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + days);

			return df.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据日期获取前几天，得到一个新日期
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static String getDateBeforeDay(String date, int days) {
		DateFormat df = getDateFormat(YYYY_MM_DD_EN);
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(date));
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - days);

			return df.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDateBeforeDay(String date, int days, String pattern) {
		DateFormat df = getDateFormat(pattern);
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(date));
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - days);

			return df.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 时间长度描述
	 *
	 * @param secondDistance 　时间差（秒）
	 * @return
	 */
	public static String getTimeLengthName(long secondDistance) {
		String str = "";
		long a = 60L * 60 * 24 * 30;
		long monCount = (long) Math.floor(secondDistance / a);
		long b = a / 31;
		long c = secondDistance % a;
		long dayCount = b > 0 ? (long) Math.floor(c / b) : 0;
		long e = b / 24;
		long f = c % b;
		long hourCount = e > 0 ? (long) Math.floor(f / e) : 0;
		long g = e / 60;
		long h = f % e;
		long minCount = g > 0 ? (long) Math.floor(h / g) : 0;
		long i = g / 60;
		long j = h % g;
		long secCount = i > 0 ? (long) Math.floor(j / i) : 0;
		if (monCount > 0) {
			str += monCount + "个月";
		}
		if (dayCount > 0) {
			str += dayCount + "天";
		}
		if (hourCount > 0) {
			str += hourCount + "小时";
		}
		if (minCount > 0) {
			str += minCount + "分钟";
		}
		if (secCount > 0) {
			str += secCount + "秒";
		}
		return str;
	}

	/**
	 * 获取自然月的最大天数
	 *
	 * @param year  年
	 * @param month 月
	 * @return
	 */
	public static int getMaxDay(String year, String month) {
		// 计算某一月份的最大天数

		Calendar time = Calendar.getInstance();
		// 若不clear，很多信息会继承自系统当前时间
		time.clear();
		time.set(Calendar.YEAR, Integer.valueOf(year));
		time.set(Calendar.MONTH, Integer.valueOf(month) - 1);
		int day = time.getActualMaximum(Calendar.DAY_OF_MONTH); // 本月总天数
		return day;
	}

	/**
	 * @param time        时间戳
	 * @param datePattern 转换时间格式
	 * @return
	 */
	public static String getDateStr(long time, String datePattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
		Date temp = new Date(time);
		return formatter.format(temp);
	}


	/**
	 * 按指定的格式格式化日期字符串
	 *
	 * @param strDate
	 * @param formatStr
	 * @return
	 */
	public static String formatStrDate(String strDate, String formatStr) {
		DateFormat df = getDateFormat(formatStr);
		Date date = getDate(strDate, formatStr);
		return df.format(date);
	}

	public static String timeStamp2String(Timestamp ts) {

		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 方法一
			tsStr = sdf.format(ts);
			// 方法二
		} catch (Exception e) {
			LOGGER.error("DateUtil.timeStamp2String异常", e);
		}

		return tsStr;
	}

	/**
	 * @param args
	 * @throws ParseException
	 *             public static void main(String[] args) throws ParseException
	 *             { SimpleDateFormat sdf = new
	 *             SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date d1 =
	 *             sdf.parse("2012-09-19 10:10:10"); Date d2 =
	 *             sdf.parse("2012-09-18 10:10:00");
	 *             System.out.println(daysBetween(d1, d2));
	 *             System.out.println(daysBetween("2012-09-08 10:10:10",
	 *             "2012-09-15 00:00:00")); }
	 */

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数 正数为天数，0为不相差，负数为开始时间大于结束时间
	 * @throws ParseException
	 */
	public static int daysBetween(Date sdate, Date edate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date smdate = sdf.parse(sdf.format(sdate));
		Date bdate = sdf.parse(sdf.format(edate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(betweenDays));
	}

	/**
	 * 字符串的日期格式的计算
	 *
	 * @return 正数为天数，0为不相差，负数为开始时间大于结束时间
	 */
	public static int daysBetween(String smdate, String bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(betweenDays));
	}

	public static String getDaysInfo(Date startDate, Date endDate) {

		// milliseconds
		long different = endDate.getTime() - startDate.getTime();

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		return elapsedDays + "天" + elapsedHours + "小时" + elapsedMinutes + "分";

	}

	/***
	 * 获取上一个月的第一天
	 * YYYYMMDD
	 * @return
	 */
	public static String getLastMonthFirstDayByYYYYMMDD() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MONTH, -1);
		currentDate.set(Calendar.DAY_OF_MONTH, 1);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return dateToDateStrings((Date) currentDate.getTime().clone());
	}

	/**
	 * 获取上个月的最后一天
	 * YYYYMMDD
	 *
	 * @return
	 */
	public static String getLastMonthLastDayByYYYYMMDD() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.DATE, 0);
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		return dateToDateStrings((Date) currentDate.getTime().clone());
	}

	public static String dateToDateStrings(Date date) {
		return dateToDateString(date, YYYYMMDD_EN);
	}

	/**
	 * 把yyyymmdd转成yyyy-MM-dd格式
	 *
	 * @param str
	 * @return
	 */
	public static String formatDate(String str) {
		SimpleDateFormat sf1 = new SimpleDateFormat(YYYYMMDD_EN);
		SimpleDateFormat sf2 = new SimpleDateFormat(YYYY_MM_DD_EN);
		String sfstr = "";
		try {
			sfstr = sf2.format(sf1.parse(str));
		} catch (ParseException e) {
			LOGGER.error("日期转换出错：" + e.getMessage());
			e.printStackTrace();
		}
		return sfstr;
	}

	/**
	 * 取得指定年月的第一天（需要注意的是：月份是从0开始的，比如说如果输入5的话，实际上显示的是4月份的第一天，千万不要搞错了哦）
	 *
	 * @param year  指定年
	 * @param month 指定月
	 * @return 指定年月的第一天
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return dateToDateString((Date) cal.getTime().clone(), YYYY_MM_DD_EN);
	}

	/**
	 * 取得指定年月的最后一天（需要注意的是：月份是从0开始的，比如说如果输入5的话，实际上显示的是4月份的最后一天，千万不要搞错了哦）
	 *
	 * @param year  指定年
	 * @param month 指定月
	 * @return 指定年月的最后一天
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, cal.getMinimum(Calendar.DATE));
		cal.roll(Calendar.DATE, -1);
		return dateToDateString((Date) cal.getTime(), YYYY_MM_DD_EN);
	}

	/**
	 * 取得指定年后的日期
	 *
	 * @param year 指定年
	 * @return 指定年后的日期
	 */
	public static Date getDateAfterYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int currenYear = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, currenYear + year);
		return new Date(calendar.getTimeInMillis());
	}

	/**
	 * 回去今天对应的星期
	 *
	 * @return
	 */
	public static int getWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 将时间转换为指定格式的时间字符串
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToStr(Date date, String pattern) {
		if (date == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}


	/**
	 * 获取两个时间之间的月份
	 */
	public static List<String> getBetweenMonth(Date startDate, Date endDate) {
		ArrayList<String> list = new ArrayList<String>();
		;
		//定义接收日历
		Calendar startDateCal = Calendar.getInstance();
		Calendar endDateCal = Calendar.getInstance();
		//设置日历时间
		startDateCal.setTime(startDate);
		endDateCal.setTime(endDate);
		while (startDateCal.before(endDateCal)) { //判断开始时间日历是否在结束时间日历前
			startDateCal.add(Calendar.MONTH, 1);//开始时间加一
			if (startDateCal.compareTo(endDateCal) != 0) { //避免将最后一月的日期加入到结果中
				LOGGER.info("中间月份" + dateToStr(startDateCal.getTime(), "yyyyMM"));
				list.add(dateToStr(startDateCal.getTime(), "yyyyMM"));
			}
		}
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}


	/**
	 * 将日期转换为字符串
	 *
	 * @param date   DATE日期
	 * @param format 转换格式
	 * @return 字符串日期
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}


	/**
	 * 将yyyyMMdd格式的字符串转换为yyyy-MM-dd格式的字符串
	 *
	 * @param dateStr yyyyMMdd格式
	 * @return yyyy-MM-dd格式
	 */
	public static String formatDateStr(String dateStr) throws Exception {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = simpleDateFormat.parse(dateStr);

		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat2.format(date);
	}


	/**
	 * 将给定的日期字符串转换为自定义格式的日期字符串
	 *
	 * @param dateStr   日期字符串
	 * @param oldFormat 旧格式
	 * @param newFormat 新格式
	 * @return
	 * @throws Exception
	 */
	public static String formateDateStrStyle(String dateStr, String oldFormat, String newFormat) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldFormat);
		Date date = simpleDateFormat.parse(dateStr);

		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(newFormat);
		return simpleDateFormat2.format(date);
	}


	/**
	 * 将yyyy-MM-dd格式的字符串转换为Date类型
	 *
	 * @param dateStrWithLine yyyyMMdd格式
	 * @return Date类型
	 * @throws Exception
	 */
	public static Date formatDateStrWithLine2Date(String dateStrWithLine) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.parse(dateStrWithLine);
	}


	/**
	 * 将yyyyMMdd格式的字符串转换为Date类型
	 *
	 * @param dateStr yyyyMMdd格式
	 * @return Date类型
	 * @throws Exception
	 */
	public static Date formatDateStr2Date(String dateStr) throws Exception {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		return simpleDateFormat.parse(dateStr);
	}


	/**
	 * 将yyyyMM格式的字符串转换为yyyy-MM格式的字符串
	 *
	 * @param dateStr yyyyMM格式
	 * @return yyyy-MM格式
	 */
	public static String formatMonthDateStr(String dateStr) throws Exception {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		Date date = simpleDateFormat.parse(dateStr);

		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM");
		return simpleDateFormat2.format(date);
	}


	/**
	 * 获取上个月的日期
	 *
	 * @return 上个月日期  yyyyMM格式
	 */
	public static String getLastMonthDate() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.YYYYMM_EN);
		return simpleDateFormat.format((Date) calendar.getTime().clone());
	}


	/**
	 * 判断日期是否是当月第一天
	 *
	 * @param date Date类型
	 * @return 是否是当月第一天
	 */
	public static Boolean checkIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH) == 1;
	}


	/**
	 * 获取指定日期所在月份的第一天
	 *
	 * @param date 指定日期
	 * @return Date 当月第一天日期
	 */
	public static Date getFirstDayOfCurrentMonth(Date date) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		String firstDayStr = simpleDateFormat.format(date) + "-01";

		SimpleDateFormat newSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return newSimpleDateFormat.parse(firstDayStr);
	}


	/**
	 * 返回指定月份的日期集合
	 *
	 * @param date yyyyMM格式的日期串
	 * @return 指定月份的日期集合, 如：[20180201,20180202]
	 */
	public static List<String> dateListOfMonth(String date) {
		List<String> dayList = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if ("".equals(date)) {
			calendar.add(Calendar.MONTH, -1);
		} else {
			calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
			calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(4)) - 1);
		}
		for (int i = 1; i <= calendar.getActualMaximum(Calendar.DATE); i++) {
			calendar.set(Calendar.DATE, i);
			dayList.add(sdf.format(calendar.getTime()));
		}
		return dayList;
	}

	/**
	 * 返回指定月份的日期集合
	 *
	 * @param date yyyyMM格式的日期串
	 * @return 指定月份的日期集合, 如：[2018-02-01,2018-02-02]
	 */
	public static List<String> dateListOfMonthToFormat(String date) {
		List<String> dayList = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if ("".equals(date)) {
			calendar.add(Calendar.MONTH, -1);
		} else {
			calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
			calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(4)) - 1);
		}
		for (int i = 1; i <= calendar.getActualMaximum(Calendar.DATE); i++) {
			calendar.set(Calendar.DATE, i);
			dayList.add(sdf.format(calendar.getTime()));
		}
		return dayList;
	}


	/**
	 * 获取年月
	 *
	 * @return
	 */
	public static String[] getYearMonth(String date) {
		String month = "";
		String year = "";
		if ("".equals(date)) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
			year = String.valueOf(calendar.get(Calendar.YEAR));
		} else {
			year = date.substring(0, 4);
			month = date.substring(4);
		}
		String[] arr = {year, month};
		return arr;
	}

	/**
	 * 获得指定日期的前一天
	 *
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat(DateUtil.YYYY_MM_DD_EN).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat(DateUtil.YYYY_MM_DD_EN).format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 *
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat(DateUtil.YYYY_MM_DD_EN).parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat(DateUtil.YYYY_MM_DD_EN).format(c.getTime());
		return dayAfter;
	}


	/**
	 * 获取指定上个月
	 *
	 * @param month
	 * @return
	 */
	public static String getPrevMonthDate(String month) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM").parse(month);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		date = calendar.getTime();
		String accDate = format.format(date);
		return accDate;
	}

	/**
	 * 时间格式转换
	 *
	 * @param endTime yyyy-MM-ddTHH:mm:ss.SSS+SSSS
	 * @return
	 */
	public static String auxGetCommnetTime(String endTime) {
		//先替换"T"，再替换"+0000"
		endTime = endTime.replaceAll("T", " ").replaceAll("\\+0000", "");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date parse = null;
		try {
			parse = simpleDateFormat.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//需要转化成的时间格式,时间格式自定义
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(parse);
	}


	public static void main(String[] args) throws Exception {
//    	System.out.println(DateUtil.getLastDayOfMonth(2016, 8));

//		System.out.println(formatDateStr("20181001"));

//		System.out.println("2018-10-01".replaceAll("-", ""));

//        System.out.println(getLastMonthDate());
//        System.out.println(formatMonthDateStr(getLastMonthDate()));

//        System.out.println(dateListOfMonth("201802"));

//        System.out.println(System.currentTimeMillis()); 1546936005203
//        System.out.println(DateUtil.getDateStr(Long.parseLong(1541433365 + ""), DateUtil.YYYY_MM_DD_HH_MM_SS));
//        System.out.println(DateUtil.getDateStr(Long.parseLong("1546936005203"), DateUtil.YYYY_MM_DD_HH_MM_SS));
		//System.out.println(getLastMonthLastDay());
		// System.out.println(DateUtil.formateDateStrStyle(getLastMonthLastDay(), DateUtil.YYYY_MM_DD_HH_MM_SS, DateUtil.YYYY_MM_DD_EN));

//        System.out.println(getMonthDay("2020-05-01"));
//        System.out.println(getMonthDay("2020-04-30"));
//        System.out.println("d======"+daysBetween("2020-05-01","2020-05-01"));
//        System.out.println(getPrevMonthDate("2020-05"));
//        System.out.println(getQuotByDays("2021-07-01","2021-07-27"));
//        System.out.println(formateDateStrStyle(getCurMonthFirstDay(), DateUtil.YYYY_MM_DD_HH_MM_SS, DateUtil.YYYY_MM_DD_EN));
//        System.out.println(getCurDate());
//
//
//        System.out.println(dateListOfMonthToFormat("202107"));
//
//        System.out.println("----"+formateDateStrStyle("2012-12-21 23:22:45", DateUtil.YYYY_MM_DD_HH_MM_SS, DateUtil.YYYYMMDD_EN));
//
//        System.out.println(getDateBeforeDay(getYestdayDate(),7,DateUtil.YYYYMMDD_EN));
//        System.out.println(getYestdayDate());
//
//        System.out.println("=="+stringToDateString("2012-12-21 23:22:45",DateUtil.YYYY_MM_DD_HH_MM_SS,DateUtil.YYYYMMDD_EN));
//
//        System.out.println("d======"+daysBetween("2021-12-05 00:00:00","2021-12-07 00:00:00"));
//
//        System.out.println("getDateOfMin=="+dateToDateString(getDateOfMin(3)));
//        System.out.println("d======"+daysBetween("2021-12-05 00:00:00",stringToDateString("20210506",DateUtil.YYYYMMDD_EN,DateUtil.YYYY_MM_DD_EN)));
//
//        System.out.println("dateToDateStrings==="+dateToDateStrings(new Date()));
//        System.out.println("getCurBeginDate="+getCurBeginDate());
//        System.out.println("getCurEndDate="+getCurEndDate());
//
//        System.out.println(auxGetCommnetTime("2022-04-20T17:58:41.000+0800"));
//
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:sssZ");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sssZ");
//
//        System.out.println( "date==>" + sdf1.format(new Date()));
//        System.out.println( "date==>" + sdf2.format(new Date()));

		System.out.println(DateUtil.parseTimeToMillis("00:01:10", DateUtil.HH_MM_SS));

	}

	public static Integer parseTimeToMillis(String date) {
		Integer toMillis = DateUtil.parseTimeToMillis(date, DateUtil.HHMMSS);
		if (toMillis == null) {
			toMillis = DateUtil.parseTimeToMillis(date, DateUtil.HH_MM_SS);
		}
		if (toMillis != null) {
			return toMillis;
		}
		return null;
	}

	public static Integer parseTimeToMillis(String time, String format) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			LocalTime localTime = LocalTime.parse(time, formatter);
			return localTime.toSecondOfDay(); // Convert to milliseconds
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
