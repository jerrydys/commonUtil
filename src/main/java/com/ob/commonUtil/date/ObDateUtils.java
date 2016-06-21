package com.ob.commonUtil.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ObDateUtils {
	
	private final static String format = "yyyy-MM-dd HH:mm:ss";
	
	/**  
     * 计算两个日期之间相差的天数  
     * @param smalldate 较小的时间 
     * @param bigdate  较大的时间 
     * @return 天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smalldate,Date bigdate) throws ParseException{    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smalldate=sdf.parse(sdf.format(smalldate));  
        bigdate=sdf.parse(sdf.format(bigdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smalldate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bigdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }   
	
    /**
     * 计算两个字符串日期的间隔天数
     * @param smalldate 小的日期
     * @param bigdate  大的日期
     * @return 天数
     * @throws ParseException
     */
    public static int daysBetween(String smalldate,String bigdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smalldate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bigdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  	
	
	/**
     * 比较时间大小
     * @param DATE1
     * @param DATE2
     * @return DATE1>DATE2则返回1，反之返回-1，相等返回0
     * @throws ParseException 
     */
    public static int compareDate(String DATE1, String DATE2) throws ParseException {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dt1 = df.parse(DATE1);
        Date dt2 = df.parse(DATE2);
        if (dt1.getTime() > dt2.getTime()) {
                return 1;
        } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
        } else {
                return 0;
        }
    }
	
	/**
     * 比较时间大小
     * @param DATE1
     * @param DATE2
     * @return DATE1>DATE2则返回1，反之返回-1，相等返回0
     * @throws ParseException 
     */
    public static int compareDate(Date  DATE1, Date DATE2){
        if (DATE1.getTime() > DATE2.getTime()) {
                return 1;
        } else if (DATE1.getTime() < DATE2.getTime()) {
                return -1;
        } else {
                return 0;
        }
    }
	
	/**
     * 年份加减
     * @param originalTime
     * @param years
     * @return
     */
    public static Date addOrSubYears(Date originalTime,int years){
    	return addOrSubDatetime(originalTime,years,0,0,0,0,0);
    }
    /**
     * 月份加减
     * @param originalTime
     * @param months
     * @return
     */
    public static Date addOrSubMonths(Date originalTime,int months){
    	return addOrSubDatetime(originalTime,0,months,0,0,0,0);
    }
    /**
     * 天数加减
     * @param originalTime
     * @param days
     * @return
     */
    public static Date addOrSubDays(Date originalTime,int days){
    	return addOrSubDatetime(originalTime,0,0,days,0,0,0);
    }
    /**
     * 小时加减
     * @param originalTime
     * @param hours
     * @return
     */
    public static Date addOrSubHours(Date originalTime,int hours){
    	return addOrSubDatetime(originalTime,0,0,0,hours,0,0);
    }
    /**
     * 分钟加减
     * @param originalTime
     * @param mins
     * @return
     */
    public static Date addOrSubMinutes(Date originalTime,int mins){
    	return addOrSubDatetime(originalTime,0,0,0,0,mins,0);
    }
    /**
     * 秒数加减
     * @param originalTime
     * @param secs
     * @return
     */
    public static Date addOrSubSeconds(Date originalTime,int secs){
    	return addOrSubDatetime(originalTime,0,0,0,0,0,secs);
    }
	
	 /**
     * 时间加减
     * @param originalTime 原始时间
     * @param years
     * @param months
     * @param days
     * @param hours
     * @param mins
     * @param secs
     * @return 
     */
    public static Date addOrSubDatetime(Date originalTime,int years,int months,int days,int hours,int mins,int secs){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(originalTime);
    	cal.add(Calendar.YEAR, years);
    	cal.add(Calendar.MONTH, months);
    	cal.add(Calendar.DAY_OF_MONTH, days);
    	cal.add(Calendar.HOUR_OF_DAY, hours);
    	cal.add(Calendar.MINUTE, mins);
    	cal.add(Calendar.SECOND, secs);
    	return cal.getTime();
    }
	
	
	/**
	 * 获取当前时间 格式化的的日期字符串
	 * @param format
	 * @return
	 */
	public static String todayDateString(String format){
		return dateFormat(format,new Date());
	}
	
	/**
	 * 字符串转为日期
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date string2Date(String str) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
    	Date date = sdf.parse(str);
    	return date;
	}

	/**
	 * 格式化日期格式，日期字符串 如:yyyy-MM-dd HH:mm:ss
	 * @param format
	 * @param date
	 * @return
	 */
	public static String dateFormat(String format,Date date){
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	
	/*public static void main(String args[]){
		System.out.println(addOrSubHours(new Date(), 0));
	}*/
	
}
