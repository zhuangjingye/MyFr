package com.base.net;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public abstract class AbstractImageRequest {

	/**
     * Supported request methods.
     */
    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }

	
	private AbstractImageRequestListener listener;//监听请求结果是成功还是失败
	
	public AbstractImageRequest(AbstractImageRequestListener listener) {
		super();
		this.listener = listener;
	}

	/**
	 * 请求成功
	 * @param response
	 */
	public void successed(Bitmap arg0) {
		onSuccessed(arg0);
		if (null != listener) {
			listener.onSuccessed(arg0);
		}
	}
	
	/**
	 * 请求谁败
	 */
	public void failed() {
		if(null != listener) {
			listener.onFailed();
		}
	}
	

	/**
	 * 反馈json的请求结果
	 * @author pi
	 *
	 */
	public interface AbstractImageRequestListener{
		public void onSuccessed(Bitmap arg0);
		public void onFailed();
	}
	
	//请求结果处理
	abstract public void onSuccessed(Bitmap arg0);
	//获得请求url
	abstract public String getUrl();
	//获得图片宽度
	abstract public int getImageWith();
	//获得图片高度
	abstract public int getImageHeight();
	//返回图片格式
	public Config getDecodeConfig() {
		return Config.ARGB_8888;
	}
	
}
