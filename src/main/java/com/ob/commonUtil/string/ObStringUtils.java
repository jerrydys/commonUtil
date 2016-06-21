package com.ob.commonUtil.string;

import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;

public class ObStringUtils {
	
	
	
	/**
     * 生成唯一性36位javaUUID
     * @return
     */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}

	
	public static void main(String args[]){
		String[] s = new String[]{"454ddfd4f","fdfdkudfk"};
		System.out.println(ArrayUtils.toString(s));
	}
	
}
