package com.zhuangjy.framework.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Tools {
	
	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String HHMMSS = "HH:mm:ss";
	public static final String HHMM = "HH:mm";
	
	public static final String HH_MM_ = "HH时mm分";
	public static final String DD_HH_MM = "dd日HH时mm分";
	public static final String MM_DD_HH = "MM月dd日HH时";
	
	public static SimpleDateFormat dateFormatyMd = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateFormatyMdHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateFormatHms = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat  dateFormatHm = new SimpleDateFormat("HH:mm");
	

	public static String formatDecimal(double d) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d);
	}
	
	public static String getPattenXLabel(Date dateTime,String xLabelShowType){
		if(xLabelShowType.equals("hour")){
			SimpleDateFormat sdf = new SimpleDateFormat(Tools.DD_HH_MM);
			return sdf.format(dateTime);
		}else if(xLabelShowType.equals("day")){
			SimpleDateFormat sdf = new SimpleDateFormat(Tools.MM_DD_HH);
			return sdf.format(dateTime);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(Tools.HH_MM_);
			return sdf.format(dateTime);
		}
	}

	public static String formatDate(Date date, String patten) {		
		if(patten.equals(YYYYMMDD)) return dateFormatyMd.format(date);
		if(patten.equals(YYYYMMDDHHMMSS)) return dateFormatyMdHms.format(date);
		if(patten.equals(HHMMSS)) return dateFormatHms.format(date);
		if(patten.equals(HHMM)) return dateFormatHm.format(date);	
		return dateFormatyMd.format(date);
	}

	public static Date parseDate(String date, String patten) {
		try{
			if(patten.equals(YYYYMMDD)) return dateFormatyMd.parse(date);
			if(patten.equals(YYYYMMDDHHMMSS)) return dateFormatyMdHms.parse(date);
			if(patten.equals(HHMMSS)) return dateFormatHms.parse(date);
			if(patten.equals(HHMM)) return dateFormatHm.parse(date);
			return dateFormatyMd.parse(date);
		}catch(Exception ex){
			return null;
		}
	}	
	
	public static String getXLabelShowType(String fromDate, String toDate) {
		List<String> dateAreaList = CommonUtils.getDateArea(fromDate, toDate);
		int days = dateAreaList.size();
		if (days > 1 && days <= 31) {
			return "hour";		
		}else if(days > 31){		
			return "day";
		}else return "default";
	}
	
	public static String getTimeShowType(String fromDate, String toDate) {
		List<String> dateAreaList = CommonUtils.getDateArea(fromDate, toDate);
		int days = dateAreaList.size();
		if (days > 3 && days <= 31) {
			return "hour";		
		}else if(days > 31){		
			return "day";
		}else return "default";
	}

	public static String checkStrNull(String str) {
		if (str == null) return "";
		return str;
	}
	
	public static boolean isNull(String str) {
		if (str == null || str.equals("")) return true;
		else return false;
	}
	
	
	
	
}
