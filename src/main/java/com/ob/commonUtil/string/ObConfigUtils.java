package com.ob.commonUtil.string;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author dys
 * 
 */
public class ObConfigUtils {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");


	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		return bundle.getString(key);
	}
	/**
	 * 通过键值取int值
	 * @param key
	 * @return
	 */
	public static final int getIntValue(String key){
		return Integer.valueOf(bundle.getString(key));
	}

}
