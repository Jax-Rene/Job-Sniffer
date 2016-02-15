/*
 * @(#)CommonUtils.java Jun 30, 2009
 * 
 * Copyright 2008 by ChinanetCenter Corporation.
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ChinanetCenter Corporation ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with ChinanetCenter.
 * 
 */
package com.zhuangjy.framework.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LW
 * @date Jun 30, 2009
 * @version 1.0
 */
public class CommonUtils {

	// 基本指标
	public static final String BASE_All = "All";
	public static final String BASE_CPU = "Cpu";
	public static final String BASE_MEMORY = "Memory";
	public static final String BASE_LOAD = "Load";
	public static final String BASE_ETH = "Eth";
	public static final String BASE_DISK = "Disk";
	// HTTP 服务指标
	public static final String HTTP_All = "All";
	public static final String HTTP_CURRUSER = "CurrUser";
	public static final String HTTP_CACHERATE = "CacheRate";
	public static final String HTTP_INOUTBANDWIDTH = "InOutBandwidth";
	public static final String HTTP_INOUTTRAFFIC = "InOutTraffic";

	public static final long ONE_DAY_MILLI_SECONDS = 86400000l;
	public static final String DAY_FORMAT = "yyyy-MM-dd";
	public static final long ONE_HOUR_MILLI_SECONDS = 3600000l;
	public static final String HOUR_FORMAT = "yyyy-MM-dd HH";

	/**
	 * 取得当天的指定日期格�??
	 * 
	 * @param partten
	 * @return
	 */
	public static String getNow(String partten) {
		return Tools.formatDate(new Date(), partten);
	}

	/**
	 * 取得某天的指定日期格�??
	 * 
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		return Tools.formatDate(date, Tools.YYYYMMDD);
	}

	public static String getDate(Date date, String partten) {
		return Tools.formatDate(date, partten);
	}

	/**
	 * 获得时间段中的日期列�??
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static List<String> getDateArea(String fromDate, String toDate) {
		if (null == fromDate || fromDate.equals("")) {
			fromDate = getNow(Tools.YYYYMMDD);
			toDate = getNow(Tools.YYYYMMDD);
		}
		List<String> list = new ArrayList<String>();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date start = format.parse(fromDate);
			Date end = format.parse(toDate);
			long lstart = start.getTime();
			long lend = end.getTime();
			for (; (lend - lstart) >= 0;) {
				Date sdate = new Date(lstart);
				list.add(getDate(sdate));
				lstart = lstart + 24 * 60 * 60 * 1000;
			}

		} catch (ParseException e) {

		}
		return list;
	}

	/**
	 * 获得FromDate �?? toDate之间相距多少小时
	 * 
	 * @param fromDate
	 *            必须为yyyy-MM-dd HH:mm:ss格式
	 * @param toDate
	 *            必须为yyyy-MM-dd HH:mm:ss格式
	 * @return
	 */
	public static long getHowHours(String fromDate, String toDate) {
		if (null == fromDate || fromDate.equals("")) {
			fromDate = getNow(Tools.YYYYMMDD);
			toDate = getNow(Tools.YYYYMMDD);
		}
		Date start = null, end = null;
		try {
			start = Tools.parseDate(fromDate, Tools.YYYYMMDDHHMMSS);
			end = Tools.parseDate(toDate, Tools.YYYYMMDDHHMMSS);
		} catch (Exception e) {
			return 0;
		}
		long lstart = start.getTime();
		long lend = end.getTime();
		return (lend - lstart) / (60 * 60 * 1000);

	}

	public static long getHowHours(Date fromDate, Date toDate) {
		long lstart = fromDate.getTime();
		long lend = toDate.getTime();
		return (lend - lstart) / (60 * 60 * 1000);
	}

	/**
	 * 时间修复，如fromDate:2009-06-29 toDate:2009-06-29 用户数据库查询时 应该修复�??
	 * fromDate:2009-06-29 00�??00�??00 toDate:2009-06-29 23�??59�??59
	 * 另外如果时间为NULL 或�?? “�?? 那么我们认为它是默认当天
	 * 
	 * @param date
	 * @param isFromDate
	 * @return
	 */
	public static String repairTime(String date, boolean isFromDate) {
		if (null == date || date.equals(""))
			date = getNow(Tools.YYYYMMDD);
		if (isFromDate) {
			return date + " 00:00:00";
		} else {
			return date + " 23:59:59";
		}
	}

	/**
	 * 自定义起始时间
	 * 
	 * @param mdh
	 *            ：month , day ,hour
	 * @param period =
	 *            0 当前小时，月，天 �periodperiodperiod period>0 即为�zzui最近多少小时，天，�月
	 * @return
	 */
	public static String getFromDate(String mdh, int period) {
		if (mdh.equalsIgnoreCase("day")) {
			if (period == 0)
				return Tools.formatDate(new Date(new Date().getTime()),
						Tools.YYYYMMDD)
						+ " 00:00:00";
			else
				return Tools.formatDate(new Date(new Date().getTime() - 24 * 60
						* 60 * 1000l), Tools.YYYYMMDDHHMMSS);
		} else if (mdh.equalsIgnoreCase("month")) {
			if (period == 0)
				return Tools.formatDate(new Date(), Tools.YYYYMMDD).substring(
						0, 7)
						+ "-01 00:00:00";
			return Tools.formatDate(new Date(new Date().getTime() - period * 30
					* 24 * 60 * 60 * 1000l), Tools.YYYYMMDDHHMMSS);
		} else if (mdh.equalsIgnoreCase("hour")) {
			if (period == 0)
				return Tools.formatDate(new Date(), Tools.YYYYMMDDHHMMSS)
						.substring(0, 13)
						+ ":00:00";
			else
				return Tools.formatDate(new Date(new Date().getTime() - period
						* 60 * 60 * 1000l), Tools.YYYYMMDDHHMMSS);
		}
		return repairTime(null, true);
	}

	/**
	 * 自定义截止时间
	 * 
	 * @param mdh
	 *            ：month , day ,hour
	 * @param period =
	 *            0 当前小时，月，天 �periodperiodperiod period>0 即为�zzui最近多少小时，天，�月
	 * @return
	 */
	public static String getToDate(String mdh, int period) {
		return Tools.formatDate(new Date(), Tools.YYYYMMDDHHMMSS);
	}

	// object to double
	public static Double objTodouble(Object obj) {
		return Double.valueOf(obj.toString()).doubleValue();
	}

	private static float hourTimes = 60 * 60 * 1000.0f;

	// 获得起始时间和截止时间相距多少小时
	public static float getHours(String fromDate, String toDate) {
		Date fd = Tools.parseDate(fromDate, Tools.YYYYMMDDHHMMSS);
		Date td = Tools.parseDate(toDate, Tools.YYYYMMDDHHMMSS);
		float length = (td.getTime() - fd.getTime()) / hourTimes;
		return length;
	}

	/**
	 * 根据定义的格式，返回距离当前时间间隔的日期
	 * 
	 * @param type
	 * @param count
	 * @return
	 */
	public static String getDateTimeByPeriod(String type, int count) {
		if (type.equals("hour")) {
			return plusOrMinusHours(new Date(), count);
		} else if (type.equals("day")) {
			return plusOrMinusDays(new Date(), count);
		} else if (type.equals("month")) {
			return plusOrMinusDays(new Date(), count * 30);
		}
		return null;
	}

	/**
	 * 根据时间，返回往前或者往后的间隔时间
	 * 
	 * @param dateTime
	 * @param count
	 * @return
	 */
	public static String plusOrMinusDays(Date dateTime, long count) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateTime.setTime(dateTime.getTime() + count * 86400000);
			return sdf.format(dateTime);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String plusOrMinusDays(String dateTime, long count) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(dateTime);
			date.setTime(date.getTime() + count * 86400000);
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String plusOrMinusHours(Date dateTime, long count) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateTime.setTime(dateTime.getTime() + count * 3600000);
			return sdf.format(dateTime);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String plusOrMinusHours(String dateTime, long count) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(dateTime);
			date.setTime(date.getTime() + count * 3600000);
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static boolean isLinux() {
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1) {
			return true;
		}
		return false;
	}

	public static String getInetAddress() {

		try {
			InetAddress inet = InetAddress.getLocalHost();
			return inet.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) {

		System.out.println(CommonUtils.getFromDate("day", 7));
		System.out.println(CommonUtils.getToDate("day", 7));

	}

}
