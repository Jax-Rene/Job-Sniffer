package com.zhuangjy.framework.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhuangjy.DateRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Author:王旗
 * Date:2015/6/8 19:19
 * Description:
 */
public class DateUtils {

    public static final String[] PATTERN = new String[]{"yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH", "yyyy-MM-dd"};

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
    
    public static Date parse(Object obj) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        if (Number.class.isAssignableFrom(obj.getClass())) {
            return new Date(Long.valueOf(obj.toString()));
        }
        String datestr = obj.toString();
        try {
            Date date = org.apache.commons.lang3.time.DateUtils.parseDate(datestr, PATTERN);
            return date;
        } catch (ParseException e) {
            logger.error("error parsing date [" + datestr + "] ", e);
            return null;
        }
    }

    public static Date dayStart(String datestr) {
        Date date = parse(datestr);
        return dayStart(date);
    }

    public static Date dayStart(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        setCalendarDate(c, 0, 0, 0, 0);
        return c.getTime();
    }

    public static Date nextDayStart(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        setCalendarDate(c, 0, 0, 0, 0);
        return plus(c.getTime(), Calendar.DAY_OF_YEAR, 1);
    }

    public static Date dayEnd(String datestr) {
        Date date = parse(datestr);
        return dayEnd(date);
    }

    public static Date dayEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        setCalendarDate(c, 23, 59, 59, 999);
        return c.getTime();
    }

    public static Date plus(Date date, int field, int plus) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, plus);
        return c.getTime();
    }
    
    private static void setCalendarDate(Calendar c, int hour, int minute, int second, int milli) {
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND,milli);
    }

    public static List<DateRange> splitDate(Date day, int interval) {
        Date start = dayStart(day);
        Date end = nextDayStart(day);
        Date tmp;
        List<DateRange> list = new ArrayList<DateRange>();
        while (start.compareTo(end) < 0) {
            tmp = plus(start, Calendar.MINUTE, interval);
            list.add(new DateRange(start, tmp));
            start = tmp;
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(splitDate(new Date(), 5), SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat));
    }
    
}
