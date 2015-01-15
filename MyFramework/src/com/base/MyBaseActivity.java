package com.base;

/**
 * 积累，项目所有的activity都将继承此类，方便统一控制和管理
 * 主要功能
 * 1:统一dialog控制，保证全局只有一个dialog
 * 2:统一的toast提示
 * 3:统一的activity关闭系统，所有的activity的启动模式均为stander
 * 4:统一的网络请求，通过volley，activity结束时 会关闭所有的请求
 * 
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Window;

public class MyBaseActivity extends Activity {

	/** 用来标志退出所有Activity的广播action */
	public static String BROAD_CASET_FINISH = "finish";
	/** 选择关闭Activity的广播action */
	public static String BROAD_CASET_CHOSE_FINISH = "broad.caset.chose.finish";
	private BroadcastReceiverHelper rhelper;// 广播接收器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 发送广播选择关闭界面
		rhelper = new BroadcastReceiverHelper(this);
		rhelper.registerAction(BROAD_CASET_CHOSE_FINISH);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(rhelper);
	}

	/**
	 * 
	 * 类描述： 监听广播，根据收到的广播信息，关闭activity 创建者： pi 项目名称： MyFramework 创建时间： 2015年1月15日
	 * 下午4:38:40 版本号： v1.0
	 *
	 */
	class BroadcastReceiverHelper extends BroadcastReceiver {
		Context ct = null;
		BroadcastReceiverHelper receiver;

		public BroadcastReceiverHelper(Context c) {
			ct = c;
			receiver = this;
		}

		// 注册
		public void registerAction(String action) {
			IntentFilter filter = new IntentFilter();
			filter.addAction(action);
			ct.registerReceiver(receiver, filter);
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (BROAD_CASET_CHOSE_FINISH.equals(intent.getAction())) {
				ArrayList<String> list = intent
						.getStringArrayListExtra("closeList");
				finishChosedActivity(list);
			}
		}
	}

	/**
	 * 
	 * 方法描述 : 创建者：pizhuang 创建时间： 2014年6月24日 上午11:08:28 void
	 * 
	 */
	private void finishChosedActivity(ArrayList<String> list) {
		if (list.contains("all")) {
			finish();
			return;
		}
		if (list.contains(this.getClass().getName())) {
			if (!onFinish()) {
				// IntentUtils.finishTopToBottom(BaseActivity.this, null, null);
				finish();
			}
		}
	}

	/**
	 * 
	 * 方法描述 : 选择关闭activity 创建者：pizhuang 创建时间： 2014年6月24日 上午11:00:57
	 * 
	 * @param arg
	 *            className的数组 void
	 * 
	 */
	protected void closeActivity(String... arg) {
		if (arg == null || arg.length == 0) {
			return;
		}
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < arg.length; i++) {
			list.add(arg[i]);
		}

		Intent intent = new Intent();
		intent.setAction(BROAD_CASET_CHOSE_FINISH);
		intent.putStringArrayListExtra("closeList", list);
		sendBroadcast(intent);
	}

	/**
	 * 
	 * 方法描述 : 关闭activity的方式，子类可自定义 如果自己定义的过重写并返回true 创建者：pizhuang 创建时间：
	 * 2014年6月24日 上午10:55:00 void
	 * 
	 */
	public boolean onFinish() {
		return false;
	}

}
