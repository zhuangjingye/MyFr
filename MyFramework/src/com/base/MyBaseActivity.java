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

import com.base.net.volley.Request;
import com.base.net.volley.RequestQueue;
import com.base.net.volley.toolbox.ImageLoader;
import com.base.net.volley.toolbox.Volley;
import com.base.net.volley.toolbox.ImageLoader.ImageCache;
import com.base.net.volley.toolbox.ImageLoader.ImageListener;
import com.base.mydialog.BaseDialog;
import com.base.mydialog.MyBaseDialog;
import com.base.view.toast.MyToast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.Window;
import android.widget.Toast;

public class MyBaseActivity extends Activity {

	private RequestQueue requestQueue;//请求队列
	
	public static final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(100);
	
	/** 用来标志退出所有Activity的广播action */
	public static String BROAD_CASET_FINISH = "finish";
	/** 选择关闭Activity的广播action */
	public static String BROAD_CASET_CHOSE_FINISH = "broad.caset.chose.finish";
	private BroadcastReceiverHelper rhelper;// 广播接收器

	private MyBaseDialog myBaseDialog = null;//每一个acitivity全局唯一的dialog

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestQueue = Volley.newRequestQueue(this);
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
		requestQueue.cancelAll(this);
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
	
	/**
	 * 
	 * 方法描述 : 根据提供都dialog对象显示对应都dialog
	 * 创建者：lixin 
	 * 创建时间： 2015年1月16日 上午11:12:09
	 * @param dialog void
	 *
	 */
	public void showDialog(BaseDialog dialog) {
		if(myBaseDialog != null && myBaseDialog.isShowing()) {
			myBaseDialog.hide();
			myBaseDialog.dismiss();
		}
		myBaseDialog = null;
		myBaseDialog = dialog.getDialog();
		myBaseDialog.show();
	}

	/**
	 * 
	 * 方法描述 : 隐藏dialog
	 * 创建者：lixin 
	 * 创建时间： 2015年1月16日 上午11:16:08 void
	 *
	 */
	public void hindDialog() {
		if(null != myBaseDialog && myBaseDialog.isShowing()) {
			myBaseDialog.hide();
			myBaseDialog.dismiss();
		}
		myBaseDialog = null;
	}
	/**
	 * 
	 * 方法描述 : 通过toast显示信息
	 * 创建者：lixin 
	 * 创建时间： 2015年1月16日 下午2:33:38
	 * @param str
	 * @param showlong void
	 *
	 */
	public void showToast(String str,int duration){
		MyToast.makeText(this, str, duration).show();;
	}
	
	public void showToast(int strId,int duration){
		MyToast.makeText(this, strId, duration).show();;
	}
	
	/**
	 * 
	 * 方法描述 : 默认短时间
	 * 创建者：lixin 
	 * 创建时间： 2015年1月16日 下午2:37:43
	 * @param str void
	 *
	 */
	public void showToast(String str) {
		showToast(str, Toast.LENGTH_SHORT);
	}
	public void showToast(int strId) {
		showToast(strId, Toast.LENGTH_SHORT);
	}
	
	/**
	 * 
	 * 方法描述 : 开始请求
	 * 创建者：lixin 
	 * 创建时间： 2015年1月16日 下午4:42:57
	 * @param request void
	 *
	 */
	public <T>void startRequest(Request<T> request) {
		requestQueue.add(request);
	}
	
	/**
	 * 
	 * 方法描述 : 获得请求队列
	 * 创建者：lixin 
	 * 创建时间： 2015年1月16日 下午4:52:53
	 * @return RequestQueue
	 *
	 */
	public RequestQueue getRequestQueue(){
		return requestQueue;
	}
	
	/**
	 * 
	 * 方法描述 : 加载图片
	 * 创建者：lixin 
	 * 创建时间： 2015年1月16日 下午5:13:37
	 * @param url
	 * @param listener void
	 *
	 */
	public void loadImage(String url,ImageListener listener) {
		ImageCache imageCache = new ImageCache() {
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				// TODO Auto-generated method stub
				lruCache.put(url, bitmap);
			}
			
			@Override
			public Bitmap getBitmap(String url) {
				// TODO Auto-generated method stub
				return lruCache.get(url);
			}
		};
		ImageLoader imageLoader = new ImageLoader(getRequestQueue(), imageCache);
		imageLoader.get(url, listener);
	}
}


