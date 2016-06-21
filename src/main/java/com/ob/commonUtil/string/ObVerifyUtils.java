package com.ob.commonUtil.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObVerifyUtils {
	
	/** 
   * 判断字符是否是中文 
   * @param c 
   * @return 
   */ 
   public static boolean isChinese(char c) { 
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c); 
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS 
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS 
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A 
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION 
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION 
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true; 
        } 
        return false; 
    } 
   
   /**
    * 判断字符串是否含汉字(不包括标点符号)
    * @param str
    * @return
    */
   public static boolean isContainChinese(String str){ 
	      String regEx = "[\\u4e00-\\u9fa5]+"; 
	      Pattern p = Pattern.compile(regEx); 
	      Matcher m = p.matcher(str); 
	     if(m.find()) 
	       return true; 
	     else 
	       return false; 
   } 
   /**
    * 判断字符串是否含汉字(包括标点符号)
    * @param chineseStr
    * @return
    */
   public static final boolean isContainChineseCharacter(String chineseStr) {  
	   char[] ch = chineseStr.toCharArray();
       for (int i = 0; i < ch.length; i++) {
           char c = ch[i];
           if (isChinese(c)) {
               return true;
           }
       }
       return false;  
   }  
   
   /** 
    * 判断字符串是否是英文 
    * @param c 
    * @return 
    */ 
    public static boolean isEnglish(String charaString){ 
       return charaString.matches("^[a-zA-Z]*"); 
     } 
    
    public static boolean isNumber(String charaString){
    	return charaString.matches("^[0-9]*");
    }
	
	/**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
    
    /**
     * 判断字符串中是否仅包含字母数字和汉字
     * @param str
     * @return
     */
    public static boolean isLetterDigitChinese(String str) {
	  String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
	  return str.matches(regex);
	 }
    
    /**
     * 是否是6-20位的密码只能由数字，英文字母，!,$,@,%,#,*,-,_ 组成
     * @param str
     * @return
     */
    public static boolean isPassword(String str){
    	String regex = "^[\\w\\!\\$\\@\\%\\&\\#\\*\\-\\_]{6,20}$";
    	return str.matches(regex);
    }
     
    public static void main(String[] args){
    	System.out.println(isNumber("1564454548987525a256"));
    }

}
