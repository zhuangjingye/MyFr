/**
 * 所有的dialog都将继承这个类
 */
package com.base.mydialog;

import com.base.MyBaseActivity;

import android.content.Context;
import android.os.Handler;
import android.view.View;

abstract public class BaseDialog {

	private MyBaseActivity myContext;
	
	private MyBaseDialog dialog;//对话框
	
	private View contentView;//内容容器
	
	protected Handler myHandler;//用于与activity交互
	
	public BaseDialog(final MyBaseActivity context,Handler myHandler) {
		this.myContext = context;
		dialog = MyBaseDialog.getInstance(context);
		this.myHandler = myHandler;
	}
	
	/**
	 * 
	 * 方法描述 :  设置要显示都内容
	 * 创建者：lixin 
	 * 创建时间： 2015年1月16日 上午11:25:33 void
	 *
	 */
	public void setConentViewId(int layout_id) {
		contentView = dialog.setContentViewId(layout_id);
	}
	
	public MyBaseDialog getDialog() {
		return dialog;
	}
	
	/**
	 * 
	 * 方法描述 : 隐藏dialog 
	 * 创建者：lixin 
	 * 创建时间： 2015年1月16日 上午11:36:29 void
	 *
	 */
	public void hindDialog() {
		if (null != dialog && dialog.isShowing()) {
			dialog.hide();
			dialog.dismiss();
		}
		dialog = null;
	}
	
	/**
	 * 
	 * 方法描述 : 根据控件id 查找相应都控件
	 * 创建者：lixin 
	 * 创建时间： 2015年1月16日 上午11:42:27
	 * @param id
	 * @return View
	 *
	 */
	public View findViewById(int id) {
		return contentView.findViewById(id);
	}
	
	/**
	 * 
	 * 方法描述 : 返回启动dialog都activity
	 * 创建者：lixin 
	 * 创建时间： 2015年1月16日 下午3:02:00
	 * @return MyBaseActivity
	 *
	 */
	public MyBaseActivity getActivity() {
		return myContext;
	}
}
