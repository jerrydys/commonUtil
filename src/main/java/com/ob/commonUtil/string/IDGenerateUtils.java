package com.ob.commonUtil.string;

import org.apache.commons.lang3.StringUtils;


public class IDGenerateUtils {
	
	
	
	/**
	 * 前缀+时间戳
	 * @param prefix  Id前缀
	 * @return
	 */
	final public synchronized static String IDCreate(String prefix){
		return StringUtils.isBlank(prefix)?currentTimeString():prefix+currentTimeString();
	}
	
	
	
	/**
	 * 前缀+时间戳+随机位数
	 * @param prefix  Id前缀
	 * @param randomDigits  随机位数
	 * @return
	 */
	final public synchronized static String IDCreate(String prefix,int randomDigits){
		String rs = "";
		for(int i=0;i<randomDigits;i++){
			rs+=RandomNum(0,9);
		}
		return StringUtils.isBlank(prefix)?currentTimeString()+rs:prefix+currentTimeString()+rs;
	}
	
	
	/**
	 * 当前时间字符串
	 * @return
	 */
	public static String currentTimeString(){
		return Long.toString(System.currentTimeMillis());
	}

	/**
	 * 生产特定范围内随机数
	 * @param start
	 * @param end
	 * @return
	 */
	public static int RandomNum(int start,int end){
		return (int)(Math.random()*(end-start+1));
	}
	
	public static void main(String args[]){
		for(int i=30;i>=0;i--){
	    	System.out.println(IDGenerateUtils.IDCreate(null, 1));
		
		}
		
	}
	
	
}
