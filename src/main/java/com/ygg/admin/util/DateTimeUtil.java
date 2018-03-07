package com.ygg.admin.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeUtil
{
    public static String WEB_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public static String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    
    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDD = "yyyyMMdd";
    
    public static DateTimeFormatter FORMAT = DateTimeFormat.forPattern(WEB_FORMAT);
    
    private static DateTimeFormatter TIMESTAMP_DATE_TIME_FORMAT = DateTimeFormat.forPattern(TIMESTAMP_FORMAT);
    
    private static SimpleDateFormat SDF = new SimpleDateFormat(WEB_FORMAT);
    
    private static SimpleDateFormat TIMESTAMP_FORMAT_SDF = new SimpleDateFormat(TIMESTAMP_FORMAT);
    
    /**
     * 以字符串(yyyy-MM-dd HH:mm:ss) 格式返回当前时间
     * 
     * @return
     */
    public static String now()
    {
        return DateTime.now().toString(WEB_FORMAT);
    }
    
    public static String now(String fmt)
    {
        return DateTime.now().toString(fmt);
    }
    
    /**
     * 将(yyyy-MM-dd HH:mm:ss) 格式 字符串 转换为 DateTime
     * 
     * @param s
     * @return
     */
    public static DateTime string2DateTime(String s)
    {
        if (s == null || "".equals(s))
        {
            return null;
        }
        else
        {
            return DateTime.parse(s, FORMAT);
        }
    }
    
    /**
     * 将fmt 格式 字符串s 转换为 DateTime
     * 
     * @param s
     * @return
     */
    public static DateTime string2DateTime(String s, String fmt)
    {
        if (s != null && !"".equals(s) && fmt != null && !"".equals(fmt))
        {
            DateTimeFormatter cuFmt = DateTimeFormat.forPattern(fmt);
            return DateTime.parse(s, cuFmt);
        }
        return null;
    }
    
    /**
     * 将(yyyy-MM-dd HH:mm:ss) 格式 字符串 转换为 Date
     * 
     * @param s
     * @return
     */
    public static Date string2Date(String s)
    {
        if (s != null && !"".equals(s))
        {
            try
            {
                return SDF.parse(s);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * 将 fmt 格式 字符串 转换为 Date
     * 
     * @param s
     * @param fmt
     * @return
     */
    public static Date string2Date(String s, String fmt)
    {
        if (s != null && !"".equals(s) && fmt != null && !"".equals(fmt))
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat(fmt);
                return sdf.parse(s);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * 将Date 转换成 "yyyy-MM-dd HH:mm:ss" 格式的字符串
     * 
     * @param date
     * @return
     */
    public static String dateToString(Date date)
    {
        return SDF.format(date);
    }
    
    /**
     * 将实际类型为java.sql.Timestamp
     * 
     * @param timestamp
     * @return
     */
    public static String timestampObjectToString(Object timestamp)
    {
        if (timestamp == null)
        {
            return "";
        }
        return ((Timestamp)timestamp).toString();
    }
    
    public static String timestampStringToWebString(String timestamp)
        throws Exception
    {
        try
        {
            if (timestamp == null || "".equals(timestamp))
            {
                return "";
            }
            Date d = TIMESTAMP_FORMAT_SDF.parse(timestamp);
            return SDF.format(d);
        }
        catch (Exception e)
        {
            return timestamp;
        }
    }
    
    public static DateTime getDateTimeByWebStringOrTimeStampString(String timeString)
    {
        if (timeString == null || "".equals(timeString) || "null".equals(timeString))
        {
            return null;
        }
        else
        {
            try
            {
                return DateTime.parse(timeString, FORMAT);
            }
            catch (Exception e)
            {
                try
                {
                    return DateTime.parse(timeString, TIMESTAMP_DATE_TIME_FORMAT);
                }
                catch (Exception e2)
                {
                }
            }
            return null;
        }
    }
    
    public static int daysBetween(Date first, Date end)
        throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        first = sdf.parse(sdf.format(first));
        end = sdf.parse(sdf.format(end));
        Calendar cal = Calendar.getInstance();
        cal.setTime(first);
        long firstMillis = cal.getTimeInMillis();
        cal.setTime(end);
        long endMillis = cal.getTimeInMillis();
        long between_days = (endMillis - firstMillis) / (1000 * 3600 * 24);
        
        return Integer.parseInt(String.valueOf(between_days));
    }
    
    public static String timestampObjectToWebString(Object timestamp)
        throws Exception
    {
        if (timestamp == null)
        {
            return "";
        }
        return timestampStringToWebString(((Timestamp)timestamp).toString());
    }
    
    public static Integer getDayHour(String date)
    {
        return Integer.valueOf(string2DateTime(date).toString("yyyyMMddHH"));
    }
    
    /**
     * 
     * @return
     */
    public static Date getLastMonday()
    {
        Calendar now = Calendar.getInstance();
        while (now.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
        {// 2代表周1
            now.add(Calendar.DAY_OF_YEAR, -1);
        }
        return now.getTime();
    }
    
    public static Date getFisrDateOfMonth()
    {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, 1);
        return now.getTime();
    }
    
    public static Date getLastDate()
    {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, -1);
        return now.getTime();
    }
    
    public static Date getLastDateFormat()
    {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            return sdf.parse(sdf.format(now.getTime()));
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    public static Date addDate(Date date, int days)
    {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        
        now.add(Calendar.DAY_OF_YEAR, days);
        return now.getTime();
    }
    
    /**
     * 周一返回2 周二返回3 周日返回1
     * 
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date)
    {
        Calendar now = Calendar.getInstance();
        now.clear();
        now.setTime(date);
        return now.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 将DATE类型装换成字符串格式
     * 
     * @param date
     * @param type 要转换成的字符串格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2String(Date date, String type)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        String time = sdf.format(date);
        return time;
    }
    
    public static void main(String[] args)
        throws Exception
    {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(7);
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-02"));
        // System.out.println(cal.getMinimalDaysInFirstWeek());
        cal.setMinimalDaysInFirstWeek(7);
        System.out.println(cal.get(Calendar.WEEK_OF_YEAR));// 从1开始
        System.out.println(cal.get(Calendar.MONTH));// 从0开始
        System.out.println(getDayOfWeek(new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01")));
        
        System.out.println(getLastDateFormat());
    }
    
    public static LocalDate trans2LocalDate(Date date)
    {
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(date.toInstant(), zone).toLocalDate();
    }
    
    public static LocalDateTime trans2LocalDateTime(Date date)
    {
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(date.toInstant(), zone);
    }
    
    public static Date trans2Date(LocalDateTime localDateTime)
    {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }
    
    public static Date trans2Date(LocalDate localDate)
    {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }
    
}
