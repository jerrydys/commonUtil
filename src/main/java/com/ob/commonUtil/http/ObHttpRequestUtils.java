package com.ob.commonUtil.http;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ob.commonUtil.entity.ObEntityUtils;


public class ObHttpRequestUtils {
	
	/**
	 * 将Http请求的参数封装成HashMap的key和value对
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> requestParameterToMap(HttpServletRequest request){
		Map<String,String> m = new HashMap<String,String>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			m.put(name, request.getParameter(name));
		}
		return m;
	}
	
	/**
	 * 请求参数中除了ID 外其他属性组成的字符串数组
	 * @param request
	 * @return
	 */
	public static String[] requestNeedUpdateFields(HttpServletRequest request,Class<?> clz){
		List<String> list = new ArrayList<String>();
		Map<String,String> m = requestParameterToMap(request);
		for (Map.Entry<String, String> entry : m.entrySet()) {
			String key = entry.getKey();
			if(!key.equals("id")&&ObEntityUtils.fieldIsInObject(key, clz)){
				list.add(key);
			}
		}
		return list.toArray(new String[]{});
	}

}
