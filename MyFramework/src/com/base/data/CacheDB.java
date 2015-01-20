package com.base.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * 缓存数据库
 * 
 * @author gyx
 * 
 */
public class CacheDB {

	public static final String ID = "_id";
	public static final String URL = "url";
	public static final String RESPONSE = "response";

	// 数据库版本
	private static int DB_VERSION = 1;
	private SQLiteDatabase db;
	private SqliteHelper dbHelper;

	public CacheDB(Context context,String dbName) {
		dbHelper = new SqliteHelper(context, dbName+".db", null, DB_VERSION);
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
		dbHelper.close();
	}

	// 添加缓存
	public Long saveCache(String key, String value) {
		deleteCache(key);
		ContentValues values = new ContentValues();
		values.put(URL, key);
		values.put(RESPONSE, value);
		Long uid = db.insert(SqliteHelper.TB_NAME, ID, values);
		Log.e("saveCache", uid + "");
		return uid;
	}

	// 删除缓存
	public int deleteCache(String url) {
		int id = db.delete(SqliteHelper.TB_NAME, URL + "=?",
				new String[] { url });
		Log.e("deleteCache", id + "");
		return id;
	}

	// 获取缓存
	public String getCacheByUrl(String url) {
		String value = "";
		Cursor cursor;
		cursor = db.query(SqliteHelper.TB_NAME, null, URL + "='" + url + "'",
				null, null, null, ID + " DESC");

		Log.v("pi", "cursor.getCount()="+cursor.getCount());
		cursor.moveToFirst();
		if (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
			value = cursor.getString(2);

		}
		cursor.close();
		return value;
	}

	class SqliteHelper extends SQLiteOpenHelper {
		// 表名
		public static final String TB_NAME = "cache";

		public SqliteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		// 创建表
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME + "(" + ID
					+ " integer primary key," + URL + " varchar," + RESPONSE
					+ " blob)");
			Log.e("Database", "onCreate");
		}

		// 更新表
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
			onCreate(db);
			Log.e("Database", "onUpgrade");
		}

	}
}
