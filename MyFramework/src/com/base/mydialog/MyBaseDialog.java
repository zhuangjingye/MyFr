package com.base.mydialog;

import com.cn.myframework.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class MyBaseDialog extends Dialog {

	private static MyBaseDialog myBaseDialog;
	private Context context;
	private FrameLayout content;

	public MyBaseDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.setContentView(R.layout.base_dialog);
		this.context = context;
		content = (FrameLayout) findViewById(R.id.content);
	}

	public static MyBaseDialog getInstance(Context context) {
		myBaseDialog = new MyBaseDialog(context,R.style.My_dialog);
		myBaseDialog.setCanceledOnTouchOutside(false);
		return myBaseDialog;
	}
	
	/**
	 * 
	 * 方法描述 : 设置对话框的内容
	 * 创建者：lixin 
	 * 创建时间： 2015年1月15日 下午5:34:03
	 * @param layout_id
	 * @return View
	 *
	 */
	public View setContentViewId(int layout_id) {
		content.removeAllViews();
		LayoutInflater lf = LayoutInflater.from(context);
		View view = lf.inflate(layout_id, content);
		return view;
	}
}
