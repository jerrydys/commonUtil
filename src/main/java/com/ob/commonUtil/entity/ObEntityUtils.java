package com.ob.commonUtil.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;


public class ObEntityUtils {
	
	private static final Logger logger = Logger.getLogger(ObEntityUtils.class);
	
	/**
	 * 判断对象在给定的属性组里面是否存在属性值为null或""，存在则返回false,全部不为空则返回true
	 * @param notNullFields
	 * @param obj
	 * @return
	 */
	public static boolean notNullFields(String[] notNullFields,Object obj){
		try {
			for(String field:notNullFields){
				Object reflectValue = FieldUtils.readField(obj, field, true);
				if(reflectValue==null||StringUtils.isBlank(reflectValue.toString().trim())){
					return false;
				}
			}
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * 判断字段是否是类里的属性
	 * @param fieldName
	 * @param obj
	 * @return
	 */
	public static boolean fieldIsInObject(String fieldName,Class<?> clz){
		Field[] fields = clz.getDeclaredFields();
		for(Field field:fields){
			if(field.getName().equals(fieldName)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断TYPE常量是否正确，常量为空返回true
	 * @param constant
	 * @param obj
	 * @return
	 */
	public static boolean typeConstantIsCorrect(String constant,Object obj){
		return constantIsCorrect(constant, "TYPE", obj);
	}
	
	/**
	 * 判断STATE，STATUS常量是否正确，常量为空返回true
	 * @param constant
	 * @param obj
	 * @return
	 */
	public static boolean stateConstantIsCorrect(String constant,Object obj){
		return   constantIsCorrect(constant, "STATE", obj)==true?true:constantIsCorrect(constant, "STATUS", obj);
	}
	
	
	/**
	 * 判断常量是否正确，常量为空返回true
	 * @param constant
	 * @param constantprefix  常量前缀
	 * @param obj
	 * @return
	 */
	public static boolean constantIsCorrect(String constant,String constantprefix,Object obj){
		if(StringUtils.isBlank(constant)){
			return true;
		}
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for(Field field:fields){
				String modifier = Modifier.toString(field.getModifiers()); 
				if (modifier != null && modifier.indexOf("final")> -1){  //常量
					if(field.getName().indexOf(constantprefix)==0){ 
						if(constant.equals(FieldUtils.readField(obj, field.getName(), true))){
							return true;
						}
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/*public class Cat{
		
		public static final String STATUS_NORMAL = "normal";
		public static final String STATE_STOP = "stop";
		
		public static final String TYPE_STANDARD = "standard"; //标准类
		public static final String TYPE_FAST = "fast"; //快速类
		public static final String TYPE_ECONOMIC  = "economic";
		
		String name;
		String color;
		String type;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	}
	
	public static void main(String[] args){
		ObEntityUtils.Cat c = new ObEntityUtils().new Cat();
		c.setName(" ");
		c.setType("hashiqi");
//		System.out.println(stateConstantIsCorrect("stop",c));
		System.out.println(fieldIsInObject("color",Cat.class));
	}*/

}
