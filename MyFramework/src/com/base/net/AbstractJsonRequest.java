package com.base.net;

import org.json.JSONObject;

public abstract class AbstractJsonRequest {

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

	
	private AbstractJsonRequestListener listener;//监听请求结果是成功还是失败
	
	public AbstractJsonRequest(AbstractJsonRequestListener listener) {
		super();
		this.listener = listener;
	}

	/**
	 * 请求成功
	 * @param response
	 */
	public void successed(JSONObject response) {
		onSuccessed(response);
		if (null != listener) {
			listener.onSuccessed(response);
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
	public interface AbstractJsonRequestListener{
		public void onSuccessed(JSONObject response);
		public void onFailed();
	}
	
	//请求结果处理
	abstract public void onSuccessed(JSONObject response);
	//获得请求url
	abstract public String getUrl();
	//请求方式
	abstract public int getMothed();
	//post方式发送请求的的body体 为json格式 默认返回空
	abstract public JSONObject getBody();
}
