/**
 * 单例模式，数据管理，所有的本地数据处理都在这个类中
 * 主要方法有
 * 1:put存储数据
 * 2:get获得数据
 */
package com.base.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataManager {

	private static DataManager dataManager = null;
	
	private DataManager() {
		
	}
	
	public static DataManager getInstance(){
		if(null == dataManager) {
			synchronized (DataManager.class) {
				if(null == dataManager) {
					dataManager = new DataManager();
				}
			}
		}
		return dataManager;
	}
	
	/**
	 * 
	 * 方法描述 : 获得数据 sqlite
	 * 创建者：lixin 
	 * 创建时间： 2015年1月19日 上午9:45:40
	 * @param key
	 * @return String
	 *
	 */
	public String getDataBySq(String dbName,Context context,String key) {
		String value = "";
		CacheDB cacheDb = new CacheDB(context, dbName);
		value = cacheDb.getCacheByUrl(key);
		cacheDb.close();
		return value;
	}
	/**
	 * 
	 * 方法描述 : 存储数据 sqlite
	 * 创建者：lixin 
	 * 创建时间： 2015年1月19日 上午9:46:09
	 * @param key
	 * @param value void
	 *
	 */
	public void putDataBySq(String dbName,Context context,String key,String value) {
		CacheDB cacheDb = new CacheDB(context, dbName);
		cacheDb.saveCache(key, value);
		cacheDb.close();
	}
	/**
	 * 
	 * 方法描述 : 获得数据sharedpreferences
	 * 创建者：lixin 
	 * 创建时间： 2015年1月19日 上午9:45:40
	 * @param key
	 * @return String
	 *
	 */
	public String getDataByShared(String name,Context context,String key) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		String value = preferences.getString(key, "");
		return value;
	}
	/**
	 * 
	 * 方法描述 : 存储数据haredpreferences
	 * 创建者：lixin 
	 * 创建时间： 2015年1月19日 上午9:46:09
	 * @param key
	 * @param value void
	 *
	 */
	public void putDataByShared(String name,Context context,String key,String value) {
		SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
}
