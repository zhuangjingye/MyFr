/**
 * 工具包，提供一些常用的方法
 * 1:判断字符串是否为空
 * 2:判断一个字符串是否为中文
 * 3:判断两个字符串是否相等
 */
package com.base;

public class MyUtils {
	/**
	 * 
	 * 方法描述 :判断一个字符串是否为空，空位true 不空false 
	 * 创建者：pizhuang 
	 * 创建时间： 2014年5月26日 下午4:10:16
	 * @param str
	 * @return boolean
	 *
	 */
	public static boolean isEmpty(String str){
		if (null == str || "".equals(str.replace(" ", ""))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * 方法描述 : 
	 * 创建者：pizhuang 
	 * 创建时间： 2014年7月29日 下午5:12:44
	 * @param str
	 * @return boolean
	 *
	 */
	public static boolean isChiness(String str) {
	     if(str.matches("[\u4e00-\u9fa5]+")){
	    	 return true;
	     } 
	     return false;
	}
	
	/**
	 * 
	 * 方法描述 : 判断两个字符串是否等
	 * 创建者：pizhuang 
	 * 创建时间： 2014年8月8日 上午10:25:40
	 * @param str1
	 * @param str2
	 * @return boolean
	 *
	 */
	public static boolean strIsEqual(String str1,String str2) {
		if(isEmpty(str1) && isEmpty(str2)) {
			return true;
		}
		if(!isEmpty(str1) && isEmpty(str2)) {
			return false;
		}
		if(isEmpty(str1) && !isEmpty(str2)) {
			return false;
		}
		if(!isEmpty(str1) && !isEmpty(str2) && str1.equals(str2)) {
			return true;
		}
		return false;
	}
}
